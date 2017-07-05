package mx.bidg.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;

import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.pojos.FilePojo;
import mx.bidg.service.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by desarrollador on 14/06/17.
 */
@Controller
@RequestMapping("/purchase-invoice")
public class PurchaseInvoicesController {

    @Autowired
    private PurchaseInvoicesService purchaseInvoicesService;

    @Autowired
    private PurchaseInvoicesFilesService purchaseInvoicesFilesService;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private ProvidersService providersService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment env;

    @Autowired
    private RequestsDatesService requestsDatesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {

        List<PurchaseInvoices> purchaseInvoices = purchaseInvoicesService.findAll();
        List<PurchaseInvoices> invoicesList = new ArrayList<>();
        JsonNode node = mapper.readTree(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purchaseInvoices));
        for (JsonNode jsonNode : node) {
            PurchaseInvoices purchase = purchaseInvoicesService.findById(jsonNode.get("idPurchaseInvoices").asInt());
            Integer limitDay = jsonNode.get("provider").get("creditDays").asInt();
            LocalDateTime requestDate = LocalDateTime.parse(jsonNode.get("request").get("creationDateFormats").get("iso").asText(),
                    DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime limit = (requestDate.plusDays(limitDay));
            purchase.setLimitDay(limit);
            invoicesList.add(purchase);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(invoicesList),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/request/file", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveRequestInvoicesFile(@RequestBody String data, HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");

        String SAVE_PATH = env.getRequiredProperty("purchaseInvoice.documents_dir");

        JsonNode node = mapper.readTree(data);

        PurchaseInvoices purchaseInvoice = new PurchaseInvoices();

        if (node.get("isXmlFile").asBoolean() == true) {

            purchaseInvoice.setAmount(node.get("billInformation").get("total").decimalValue().subtract(node.get("billInformation").get("impuestos").get("totalImpuestosTrasladados").decimalValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
            purchaseInvoice.setAmountWithIva(node.get("billInformation").get("total").decimalValue());
            purchaseInvoice.setTotalAmount(node.get("billInformation").get("total").decimalValue());
            purchaseInvoice.setCreationDate(LocalDateTime.now());
            purchaseInvoice.setFolio(node.get("billInformation").get("folio").asText());
            purchaseInvoice.setCurrencyType(node.get("billInformation").get("moneda").asText());
            purchaseInvoice.setRate(node.get("billInformation").get("tipoCambio").decimalValue());
            purchaseInvoice.setUsername(user.getUsername());

            Requests request = requestsService.findById(node.get("idRequest").asInt());
            if (request != null) {
                purchaseInvoice.setRequest(request);
            }

            Providers provider = providersService.findById(node.get("idProvider").asInt());
            if (provider != null) {
                purchaseInvoice.setProvider(provider);
            }

            Accounts account = accountsService.findById(node.get("providerAccount").get("idAccount").asInt());
            if (account != null) {
                purchaseInvoice.setAccount(account);
            }


            purchaseInvoice = purchaseInvoicesService.save(purchaseInvoice);


            FilePojo fileXML = mapper.treeToValue(node.get("fileVoucher").get("voucherXmlFile"), FilePojo.class);
            FilePojo filePDF = mapper.treeToValue(node.get("fileVoucher").get("voucherPdfFile"), FilePojo.class);


            PurchaseInvoicesFiles purchaseInvoicesFileXML = new PurchaseInvoicesFiles();


            String destDir = "/purchaseInvoices_" + purchaseInvoice.getIdPurchaseInvoices();
            String destFile = destDir + "/Documento." + purchaseInvoice.getCreationDate().toInstant(ZoneOffset.UTC).getEpochSecond() + (int) (Math.random() * 30) + 1;

            purchaseInvoicesFileXML.setFilePath(destFile);
            purchaseInvoicesFileXML.setFileName(fileXML.getName());
            purchaseInvoicesFileXML.setCreationDate(LocalDateTime.now());
            purchaseInvoicesFileXML.setPurchaseInvoices(purchaseInvoice);
            purchaseInvoicesFileXML.setUsername(user.getUsername());

            File dir = new File(SAVE_PATH + destDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            String encodingPrefix = "base64,";
            int contentStartIndex = fileXML.getDataUrl().indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] byteArreyData = Base64.decodeBase64(fileXML.getDataUrl().substring(contentStartIndex));

            FileOutputStream out = new FileOutputStream(new File(SAVE_PATH + destFile));
            out.write(byteArreyData);
            out.close();

            purchaseInvoicesFilesService.save(purchaseInvoicesFileXML);

            PurchaseInvoicesFiles purchaseInvoicesFilePDF = new PurchaseInvoicesFiles();


            String destFilePDF = destDir + "/Documento." + purchaseInvoice.getCreationDate().toInstant(ZoneOffset.UTC).getEpochSecond() + (int) (Math.random() * 30) + 1;

            purchaseInvoicesFilePDF.setFilePath(destFilePDF);
            purchaseInvoicesFilePDF.setFileName(filePDF.getName());
            purchaseInvoicesFilePDF.setCreationDate(LocalDateTime.now());
            purchaseInvoicesFilePDF.setPurchaseInvoices(purchaseInvoice);
            purchaseInvoicesFilePDF.setUsername(user.getUsername());

            File dirPDF = new File(SAVE_PATH + destDir);
            if (!dirPDF.exists()) {
                dirPDF.mkdir();
            }

            String encodingPrefixPDF = "base64,";
            int contentStartIndexPDF = filePDF.getDataUrl().indexOf(encodingPrefixPDF) + encodingPrefixPDF.length();
            byte[] byteArreyDataPDF = Base64.decodeBase64(filePDF.getDataUrl().substring(contentStartIndexPDF));

            FileOutputStream outPDF = new FileOutputStream(new File(SAVE_PATH + destFilePDF));
            outPDF.write(byteArreyDataPDF);
            outPDF.close();

            purchaseInvoicesFilesService.save(purchaseInvoicesFilePDF);
        } else {


            purchaseInvoice.setAmount(node.get("billInformation").get("amountNotIva").decimalValue());
            purchaseInvoice.setAmountWithIva(node.get("billInformation").get("amountWithIva").decimalValue());
            purchaseInvoice.setTotalAmount(node.get("billInformation").get("total").decimalValue());
            purchaseInvoice.setCreationDate(LocalDateTime.now());
            purchaseInvoice.setFolio(node.get("billInformation").get("folio").asText());
            purchaseInvoice.setCurrencyType(node.get("billInformation").get("moneda").asText());
            purchaseInvoice.setRate(node.get("billInformation").get("tipoCambio").decimalValue());
            purchaseInvoice.setConceptName(node.get("billInformation").get("concept").asText());
            purchaseInvoice.setUsername(user.getUsername());

            Requests request = requestsService.findById(node.get("idRequest").asInt());
            if (request != null) {
                purchaseInvoice.setRequest(request);
            }

            Providers provider = providersService.findById(node.get("idProvider").asInt());
            if (provider != null) {
                purchaseInvoice.setProvider(provider);
            }

            Accounts account = accountsService.findById(node.get("providerAccount").get("idAccount").asInt());
            if (account != null) {
                purchaseInvoice.setAccount(account);
            }


            purchaseInvoice = purchaseInvoicesService.save(purchaseInvoice);

            FilePojo filePDF = mapper.treeToValue(node.get("fileVoucher").get("voucherPdfFile"), FilePojo.class);

            String destDir = "/purchaseInvoices_" + purchaseInvoice.getIdPurchaseInvoices();
            String destFile = destDir + "/Documento." + purchaseInvoice.getCreationDate().toInstant(ZoneOffset.UTC).getEpochSecond() + (int) (Math.random() * 30) + 1;

            PurchaseInvoicesFiles purchaseInvoicesFilePDF = new PurchaseInvoicesFiles();

            purchaseInvoicesFilePDF.setFilePath(destFile);
            purchaseInvoicesFilePDF.setFileName(filePDF.getName());
            purchaseInvoicesFilePDF.setCreationDate(LocalDateTime.now());
            purchaseInvoicesFilePDF.setPurchaseInvoices(purchaseInvoice);
            purchaseInvoicesFilePDF.setUsername(user.getUsername());

            File dirPDF = new File(SAVE_PATH + destDir);
            if (!dirPDF.exists()) {
                dirPDF.mkdir();
            }

            String encodingPrefixPDF = "base64,";
            int contentStartIndexPDF = filePDF.getDataUrl().indexOf(encodingPrefixPDF) + encodingPrefixPDF.length();
            byte[] byteArreyDataPDF = Base64.decodeBase64(filePDF.getDataUrl().substring(contentStartIndexPDF));

            FileOutputStream outPDF = new FileOutputStream(new File(SAVE_PATH + destFile));
            outPDF.write(byteArreyDataPDF);
            outPDF.close();

            purchaseInvoicesFilesService.save(purchaseInvoicesFilePDF);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purchaseInvoice), HttpStatus.OK);
    }

    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByIdRequest(@PathVariable Integer idRequest) throws IOException {
        PurchaseInvoices purchaseInvoice = purchaseInvoicesService.findByIdRequest(idRequest);

        if (purchaseInvoice != null) {
            List<PurchaseInvoicesFiles> purchaseInvoicesFiles = purchaseInvoicesFilesService.findByIdPurchaseInvoices(purchaseInvoice.getIdPurchaseInvoices());
            purchaseInvoice.setPurchaseInvoicesFiles(purchaseInvoicesFiles);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purchaseInvoice), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idPurchaseInvoices}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer idPurchaseInvoices) throws Exception {

        PurchaseInvoices purchaseInvoices = purchaseInvoicesService.findById(idPurchaseInvoices);
        boolean status = purchaseInvoicesService.delete(purchaseInvoices);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(status), HttpStatus.OK);
    }

    /**
     * Cuentas por pagar:
     * Vigentes.
     */
    @RequestMapping(value = "/requests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByRequestCategoryAndType
    (@RequestParam(name = "idRequestCategory", required = false) Integer idRequestCategory
            , @RequestParam(name = "idRequestType", required = false) Integer idRequestType
            , @RequestParam(name = "idRequestStatus", required = false) Integer idRequestStatus) throws IOException {

        List<PurchaseInvoices> invList = purchaseInvoicesService.findByRequestTypeAndCatgory(idRequestCategory, idRequestType, idRequestStatus);

        if (!invList.isEmpty()) {
            for (PurchaseInvoices invoices : invList) {
                Integer limitDay = invoices.getProvider().getCreditDays();
                Integer status = invoices.getRequest().getIdRequestStatus();
                LocalDateTime requestDate = LocalDateTime.parse(invoices.getRequest().getCreationDateFormats().getIso(), DateTimeFormatter.ISO_DATE_TIME);
                if (status == 3) {
                    LocalDateTime limit = (requestDate.plusDays(limitDay));
                    invoices.setLimitDay(limit);
                }
            }
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(invList), HttpStatus.OK);
    }

    /**
     * Cuentas por pagar:
     * Reprogramadas en proceso de pago.
     */
    @RequestMapping(value = "/reprogrammed-process", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByCategoryAndTypeRep
    (@RequestParam(name = "idRequestCategory", required = false) Integer idRequestCategory
            , @RequestParam(name = "idRequestType", required = false) Integer idRequestType
            , @RequestParam(name = "idRequestStatus", required = false) Integer idRequestStatus) throws IOException {
        List<PurchaseInvoices> purInvoicesList = purchaseInvoicesService.findAll();
        List<PurchaseInvoices> list = new ArrayList<>();

        for (PurchaseInvoices purchaseInvoices : purInvoicesList) {
            List<PurchaseInvoices> invoices = purchaseInvoicesService.findByRequestTypeAndCatgory
                    (purchaseInvoices.getRequest().getIdRequestCategory().intValue(),
                            purchaseInvoices.getRequest().getIdRequestType().intValue(),
                            purchaseInvoices.getRequest().getIdRequestStatus().intValue());
            List<RequestsDates> requestsDate = requestsDatesService.findAll();
            for (RequestsDates dates : requestsDate) {

                Integer count = dates.getCountUpdate();
                Integer idRequ = dates.getIdRequests();
                Integer idReqInv = purchaseInvoices.getIdRequest();
                Integer limitDay = purchaseInvoices.getProvider().getCreditDays();
                LocalDateTime requestDate = LocalDateTime.parse(purchaseInvoices.getRequest().getCreationDateFormats().getIso(),
                        DateTimeFormatter.ISO_DATE_TIME);
                if (idRequ == idReqInv && count > 1 && idRequestCategory == purchaseInvoices.getRequest().getIdRequestCategory().intValue()
                        && idRequestType == purchaseInvoices.getRequest().getIdRequestType().intValue()
                        && idRequestStatus == purchaseInvoices.getRequest().getIdRequestStatus().intValue()) {
                    LocalDateTime limitD = (requestDate.plusDays(limitDay));
                    purchaseInvoices.setCountUpdate(count);
                    purchaseInvoices.setLimitDay(limitD);
                    list.add(purchaseInvoices);
                }
            }

        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK);
    }

    /**
     * Cuentas por pagar:
     * Vigentes en proceso de pago.
     */
    @RequestMapping(value = "/requests-process", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByCategoryAndTypeProg
    (@RequestParam(name = "idRequestCategory", required = false) Integer idRequestCategory
            , @RequestParam(name = "idRequestType", required = false) Integer idRequestType
            , @RequestParam(name = "idRequestStatus", required = false) Integer idRequestStatus) throws IOException {
        List<PurchaseInvoices> purInvoicesList = purchaseInvoicesService.findAll();
        List<PurchaseInvoices> list = new ArrayList<>();

        for (PurchaseInvoices purchaseInvoices : purInvoicesList) {
            List<PurchaseInvoices> invoices = purchaseInvoicesService.findByRequestTypeAndCatgory
                    (purchaseInvoices.getRequest().getIdRequestCategory().intValue(),
                            purchaseInvoices.getRequest().getIdRequestType().intValue(),
                            purchaseInvoices.getRequest().getIdRequestStatus().intValue());
            List<RequestsDates> requestsDate = requestsDatesService.findAll();
            for (RequestsDates dates : requestsDate) {

                Integer count = dates.getCountUpdate();
                Integer idRequ = dates.getIdRequests();
                Integer idReqInv = purchaseInvoices.getIdRequest();
                Integer limitDay = purchaseInvoices.getProvider().getCreditDays();
                LocalDateTime requestDate = LocalDateTime.parse(purchaseInvoices.getRequest().getCreationDateFormats().getIso(),
                        DateTimeFormatter.ISO_DATE_TIME);
                if (idRequ == idReqInv && count == 1 && idRequestCategory == purchaseInvoices.getRequest().getIdRequestCategory().intValue()
                        && idRequestType == purchaseInvoices.getRequest().getIdRequestType().intValue()
                        && idRequestStatus == purchaseInvoices.getRequest().getIdRequestStatus().intValue()) {
                    LocalDateTime limitD = (requestDate.plusDays(limitDay));
                    purchaseInvoices.setCountUpdate(count);
                    purchaseInvoices.setLimitDay(limitD);
                    list.add(purchaseInvoices);
                }
            }

        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK);
    }

    /**
     * Busqueda por folio
     */
    @RequestMapping(value = "/find-folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findFolio(@RequestParam(name = "folio", required = false) String folio) throws IOException {
        List<PurchaseInvoices> purInvoicesList = purchaseInvoicesService.findFolio(folio);

        for (PurchaseInvoices purchaseInvoices : purInvoicesList) {
            List<RequestsDates> requestsDate = requestsDatesService.findAll();
            for (RequestsDates dates : requestsDate) {
                Integer count = dates.getCountUpdate();
                Integer idRequ = dates.getIdRequests();
                Integer idReqInv = purchaseInvoices.getIdRequest();
                Integer limitDay = purchaseInvoices.getProvider().getCreditDays();
                LocalDateTime requestDate = LocalDateTime.parse(purchaseInvoices.getRequest().getCreationDateFormats().getIso(),
                        DateTimeFormatter.ISO_DATE_TIME);
                Integer sta = purchaseInvoices.getRequest().getIdRequestStatus();
                if (idRequ == idReqInv && (sta == 3 || sta == 8)) {
                    LocalDateTime limitD = (requestDate.plusDays(limitDay));
                    purchaseInvoices.setCountUpdate(count);
                    purchaseInvoices.setLimitDay(limitD);
                }
            }
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purInvoicesList), HttpStatus.OK);
    }

    /**
     * Exportar Excel cuentas por pagar
     */
    @RequestMapping(value = "/report-payables", method = RequestMethod.GET)
    public ResponseEntity<String> reportPayables(HttpServletResponse response,
                    @RequestParam(name= "startDate", required=true) String startDate,
                    @RequestParam(name="endDate", required=true) String endDate,
                    @RequestParam(name="status", required=true) Integer status) throws IOException{

        LocalDateTime ofDate = (startDate == null || startDate.equals("")) ? null : LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (endDate == null || endDate.equals("")) ? null : LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte_cuentas_por_pagar.xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        purchaseInvoicesService.payablesRequests(outputStream, ofDate, untilDate, status);
        outputStream.flush();
        outputStream.close();

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purchaseInvoicesService.findAll()));
    }
}







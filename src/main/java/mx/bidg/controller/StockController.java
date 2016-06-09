package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.dao.CArticleStatusDao;
import mx.bidg.dao.CArticlesDao;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.model.Properties;
import mx.bidg.service.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@RestController
@RequestMapping("stock")
@PropertySource(value = {"classpath:application.properties"})
public class StockController {

    @Autowired
    private Environment env;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDocumentsService stockDocumentsService;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private StockEmployeeAssignmentsService assignmentsService;

    @Autowired
    private DwEnterprisesService dwEnterprisesService;

    @Autowired
    private CArticlesService cArticlesService;

    @Autowired
    private CArticleStatusService cArticleStatusService;

    @Autowired
    private EmployeesService employeesService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(
            @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
            @RequestParam(name = "idRegion", required = false) Integer idRegion,
            @RequestParam(name = "idBranch", required = false) Integer idBranch,
            @RequestParam(name = "idArea", required = false) Integer idArea
            ) throws IOException {

        List<Stocks> stock;

        if (idDistributor == 0 && idArea == 0 && idBranch == 0 && idRegion ==0) {
            stock = stockService.findAll();
        } else {
            stock = stockService.filter(idDistributor,idRegion,idBranch,idArea);
        }

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> save(HttpServletRequest request) throws Exception {

        Stocks stock = new Stocks();
        StockEmployeeAssignments assignment = new StockEmployeeAssignments();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime purchaseDate =
                LocalDateTime.parse(request.getParameter("purchaseDate") + " 00:00", formatter);

        stock.setInvoiceNumber(request.getParameter("invoiceNumber"));
        stock.setSerialNumber(request.getParameter("serialNumber"));
        stock.setPurchaseDate(purchaseDate);
        stock.setIdAccessLevel(1);
        stock.setArticle(cArticlesService.findById(Integer.valueOf(request.getParameter("article"))));
        stock.setArticleStatus(cArticleStatusService.findById(CArticleStatus.ALTA));

        stock = stockService.save(stock);


        if (request.getParameter("dwEnterprise") != null && request.getParameter("employee") != null) {
            DwEnterprises dwEnterprises =
                    dwEnterprisesService.findById(Integer.parseInt(request.getParameter("dwEnterprise")));

            Employees employee =
                    employeesService.findById(Integer.parseInt(request.getParameter("employee")));

            stock.setDwEnterprises(dwEnterprises);
            stockService.update(stock);

            assignment.setDwEnterprises(stock.getDwEnterprises());
            assignment.setEmployee(employee);
            assignment.setStocks(stock);
            assignment.setAssignmentDate(LocalDateTime.now());
            assignment.setCurrentAssignment(1);
            assignment.setIdAccessLevel(1);

            assignmentsService.saveAssignment(assignment);
        } else {

            stock.setDwEnterprises(DwEnterprises.DEFAULT_DW_ENTERPRISES);
            stockService.update(stock);

        }

        saveAttachDocuments(stock.getIdStock(),request);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(
            value = "/{idStock}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> updateStock(@PathVariable int idStock, @RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);

        Stocks stock = stockService.findSimpleById(idStock);

        if (jnode.get("serialNumber").asText().equals("null")) {
            stock.setSerialNumber(null);
        } else {
            stock.setSerialNumber(jnode.get("serialNumber").asText());
        }
        stock.setArticleStatus(new CArticleStatus(jnode.get("articleStatus").get("idArticleStatus").asInt()));

        stockService.update(stock);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @RequestMapping(value = "/{idStock}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributor(@PathVariable int idStock) throws IOException {
        Stocks stock = stockService.findById(idStock);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/properties", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getProperties(@PathVariable int idStock) throws IOException {
        List<Properties> properties = propertiesService.getAllFor(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(properties),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/properties", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> saveProperty(@PathVariable Integer idStock, @RequestBody String data) throws Exception {

        JsonNode jnode = mapper.readTree(data);

        Stocks stocks = stockService.findSimpleById(idStock);

        if (jnode.isArray()) {
            for (JsonNode node : jnode) {
                CArticles article = cArticlesService.findById(node.get("attributesArticles").get("idArticle").asInt());
                CValues value = mapper.treeToValue(node.get("value"), CValues.class);
                CAttributes attribute = mapper.treeToValue(node.get("attributesArticles").get("attributes"), CAttributes.class);

                Properties property = new Properties();
                property.setStocks(stocks);
                property.setValue(value);

                propertiesService.save(property, article, attribute);
            }
        } else {
            Properties property = new Properties();

            CArticles article = cArticlesService.findById(jnode.get("attributesArticles").get("idArticle").asInt());
            CValues value = mapper.treeToValue(jnode.get("value"), CValues.class);
            CAttributes attribute = mapper.treeToValue(jnode.get("attributesArticles").get("attributes"), CAttributes.class);

            property.setStocks(stocks);
            property.setValue(value);

            propertiesService.save(property, article, attribute);
        }

        return new ResponseEntity<>("Registro almacenado con exito", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/properties/{idProperty}", method = RequestMethod.DELETE,
            consumes = "application/json", produces = "text/plain;charset=UTF-8"
    )
    public ResponseEntity<String> deleteProperty(@PathVariable Integer idProperty) {
        propertiesService.delete(propertiesService.findById(idProperty));
        return new ResponseEntity<>("Registro eliminado con exito", HttpStatus.OK);
    }

    @RequestMapping(value = "/attachments/download/{idStockDocument}", method = RequestMethod.GET)
    public void readFile(@PathVariable int idStockDocument, HttpServletResponse response) throws IOException {
        String SAVE_PATH = env.getRequiredProperty("stock.documents_dir");
        StockDocuments document = stockDocumentsService.findById(idStockDocument);
        File file = new File(SAVE_PATH + document.getDocumentUrl());

        if (! file.canRead()) {
            throw new ValidationException(
                    "El archivo "+ SAVE_PATH + document.getDocumentUrl() +" no existe",
                    "El documento solicitado no existe"
            );
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getDocumentName() + "\"");
        response.setContentLengthLong(file.length());

        byte[] buffer = new  byte[1024];
        int len;

        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @RequestMapping(value = "{idStock}/attachments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAttachedDocuments(@PathVariable int idStock) throws IOException {
        List<StockDocuments> documents = stockDocumentsService.findByIdStock(idStock);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documents),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "{idStock}/attachments", method = RequestMethod.POST)
    public ResponseEntity<String> attachDocuments(@PathVariable Integer idStock, HttpServletRequest request) throws Exception {
        saveAttachDocuments(idStock,request);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @RequestMapping(value = "{idStock}/attachments/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAttachedDocumentsRecord(@PathVariable int idStock) throws IOException {
        List<StockDocuments> documents = stockDocumentsService.findRecordBy(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documents),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/assignments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAssignments(@PathVariable int idStock) throws IOException {
        StockEmployeeAssignments assignment = assignmentsService.getAssignmentFor(new Stocks(idStock));
        List<StockEmployeeAssignments> assignments = new ArrayList<>();
        assignments.add(assignment);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(assignments),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/assignments/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAssignmentsRecord(@PathVariable int idStock) throws IOException {
        List<StockEmployeeAssignments> assignments = assignmentsService.getAssignmentsRecordFor(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(assignments),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/assignments/{idEmployee}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveAssignment(@PathVariable Integer idStock,@PathVariable Integer idEmployee, @RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);
        DwEnterprises dwEnterprises = new DwEnterprises(jnode.get("idDwEnterprise").asInt());
        String invoiceNumber = jnode.get("invoiceNumber").asText();

        Stocks stock = stockService.findSimpleById(idStock);
        stock.setInvoiceNumber(invoiceNumber);
        StockEmployeeAssignments assignment = assignmentsService.getAssignmentFor(stock);
        StockEmployeeAssignments newAssignment = new StockEmployeeAssignments();

        stock.setDwEnterprises(dwEnterprises);

        newAssignment.setStocks(stock);
        newAssignment.setDwEnterprises(stock.getDwEnterprises());
        newAssignment.setEmployee(employeesService.findById(idEmployee));
        newAssignment.setAssignmentDate(LocalDateTime.now());
        newAssignment.setCurrentAssignment(1);
        newAssignment.setIdAccessLevel(1);


        if (assignment != null) {
            assignment.setCurrentAssignment(0);
            assignmentsService.update(assignment);
        }

        assignmentsService.saveAssignment(newAssignment);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stockService.update(stock)),
                HttpStatus.CREATED
        );
    }

    @RequestMapping(value = "/{idStock}/assignments", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteAssignment(@PathVariable int idStock) throws IOException {
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(""),
                HttpStatus.CREATED
        );
    }

    private StockDocuments findDocument(Integer idDocumentType, List<StockDocuments> documents) {
        if (documents.isEmpty()) {
            return null;
        }
        for (StockDocuments document : documents) {
            if (document.getIdDocumentType().equals(idDocumentType)) {
                return document;
            }
        }

        return null;
    }

    private void saveAttachDocuments(Integer idStock, HttpServletRequest request) throws Exception{
        String SAVE_PATH = env.getRequiredProperty("stock.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("stock.attachments.media_types").split(",");
        List<StockDocuments> documents = stockDocumentsService.findByIdStock(idStock);
        Pattern pattern = Pattern.compile("(\\d+)");

        for (Part filePart: request.getParts()) {
            boolean isValidMediaType = false;

            if (! filePart.getName().matches("^file-type-[0-9]+$")) {
                continue;
            }
            if (filePart.getSize() <= 0) {
                continue;
            }

            for (String mediaType : fileMediaTypes) {
                if (filePart.getContentType().equals(mediaType)) {
                    isValidMediaType = true;
                    break;
                }
            }

            if (! isValidMediaType) {
                throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
            }

            Matcher matcher = pattern.matcher(filePart.getName());
            matcher.find();
            Integer idDocumentType = Integer.parseInt(matcher.group(0));

            String destDir = "/article_" + idStock;
            String destFile = destDir + "/Documento." + idDocumentType +
                    "." + Calendar.getInstance().getTimeInMillis();

            File dir = new File(SAVE_PATH + destDir);
            if (! dir.exists()) {
                dir.mkdir();
            }

            OutputStream outputStream = new FileOutputStream(new File(SAVE_PATH + destFile));
            InputStream inputStream = filePart.getInputStream();
            int read;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            StockDocuments document = new StockDocuments();
            document.setStock(new Stocks(idStock));
            document.setCStockDocumentsTypes(new CStockDocumentsTypes(idDocumentType));
            document.setDocumentUrl(destFile);
            document.setDocumentName(filePart.getSubmittedFileName());
            document.setUploadingDate(LocalDateTime.now());
            document.setCurrentDocument(1);
            stockDocumentsService.save(document);

            StockDocuments oldDocument = this.findDocument(idDocumentType, documents);
            if (oldDocument != null) {
                oldDocument.setCurrentDocument(0);
                stockDocumentsService.update(oldDocument);
            }
        }
    }
}

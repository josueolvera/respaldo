package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.pojos.PdfAternaPojo;
import mx.bidg.service.*;
import mx.bidg.utils.BuildPdfAterna;
import mx.bidg.utils.FtpConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Desarrollador on 18/01/2017.
 */
@Controller
@RequestMapping("policy-truckdriver-monthly")
public class PolicyTruckdriverMonthlyController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PolicyTruckdriverMonthlyService policyTruckdriverMonthlyService;

    @Autowired
    PolicyTruckdriverService policyTruckdriverService;

    @Autowired
    ConciliationFileService conciliationFileService;

    @Autowired
    Environment env;

    @Autowired
    EmailDeliveryService emailDeliveryService;

    @Autowired
    EmailTemplatesService emailTemplatesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<PolicyTruckdriverMonthly> policyTruckdriverMonthlyList = policyTruckdriverMonthlyService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverMonthlyList), HttpStatus.OK);
    }

    @RequestMapping(value = "/exist-policy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> checkExistPolicyRecord(@RequestParam("file") MultipartFile file, HttpSession session)throws  IOException{
        Users users = (Users) session.getAttribute("user");
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverMonthlyService.existsPolicyTruckDriverRecord(file,users.getIdUser())), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-excel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveFromExcel(@RequestParam("file") MultipartFile file, HttpSession session)throws IOException{
        Users user = (Users) session.getAttribute("user");
        List<PolicyTruckdriverMonthly> policyTruckdriverMonthlyList = policyTruckdriverMonthlyService.saveFromCsv(file, user.getIdUser());
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverMonthlyList), HttpStatus.OK);
    }

    //    @Scheduled(cron = "0 15 0 * * ?")
    @RequestMapping(value = "/csv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void readCSV()throws Exception{
        Calendar fecha = Calendar.getInstance();
        String name = "ATERNA_CANALDEPAGO_";
        fecha.add(Calendar.DAY_OF_YEAR,-1);
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        if (dia < 10){
            name = name+"0"+dia;
        }else{
            name = name+dia;
        }
        if (mes < 10){
            name = name+"0"+mes;
        }else {
            name = name + mes;
        }
        name = name+año;
        File file = new File(env.getRequiredProperty("aterna_truckDriver.documents_dir")+name+".csv");
        if (file.exists()){

            policyTruckdriverMonthlyService.readCsvAlterna(name);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

            Calendar fechas = Calendar.getInstance();

            fechas.add(fechas.MONTH, -1);
            fechas.set(fechas.DAY_OF_MONTH, fechas.getActualMinimum(fechas.DAY_OF_MONTH));


            String fechaMin = null;

            int añoI = fechas.get(Calendar.YEAR);
            int mesI = fechas.get(Calendar.MONTH) + 1;
            int diaI = fechas.get(Calendar.DAY_OF_MONTH);
            if (diaI < 10){
                fechaMin = "0" + diaI + "/";
            }else{
                fechaMin = diaI + "/";
            }
            if (mesI < 10){
                fechaMin = fechaMin + "0" + mesI + "/";
            }else {
                fechaMin = fechaMin + mesI +"/";
            }

            fechaMin = fechaMin + añoI;

            System.out.println("Fecha inicial: "+ LocalDate.parse(fechaMin,formatter));

            Calendar fechaFinal = Calendar.getInstance();
            fechaFinal.add(fechaFinal.MONTH, -1);
            fechaFinal.set(fechaFinal.DAY_OF_MONTH, fechaFinal.getActualMaximum(fechaFinal.DAY_OF_MONTH));

            String fechaMax = null;

            int añoF = fechaFinal.get(Calendar.YEAR);
            int mesF = fechaFinal.get(Calendar.MONTH) + 1;
            int diaF = fechaFinal.get(Calendar.DAY_OF_MONTH);

            if (diaF < 10){
                fechaMax = "0" + diaF + "/";
            }else{
                fechaMax = diaF + "/";
            }
            if (mesF < 10){
                fechaMax = fechaMax + "0" + mesF + "/";
            }else {
                fechaMax = fechaMax + mesF +"/";
            }

            fechaMax = fechaMax + añoF;

            System.out.println("Fecha final: " + LocalDate.parse(fechaMax,formatter));

            List listaApp = policyTruckdriverService.findFoliosCommissionIvaByDStartValidity(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

            List listaAterna = policyTruckdriverMonthlyService.findFoliosCommissionIvaByDStartValidity(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

            if (listaAterna.size() > 0){
                PdfAternaPojo aternaPojo = policyTruckdriverMonthlyService.validateAmounts(listaApp, listaAterna);

                if (aternaPojo.isConciliation()){
                    System.out.println("Si conciliaron");
                    BuildPdfAterna pdfAterna = new BuildPdfAterna();
                    ConciliationFile conciliationFile = new ConciliationFile();
                    conciliationFile.setFileName(name + ".pdf");
                    conciliationFile.setFilePath(env.getRequiredProperty("conciliation_aterna.documents_dir")+name+".pdf");
                    conciliationFile = conciliationFileService.save(conciliationFile);
                    pdfAterna.createPdf(conciliationFile.getFilePath(), aternaPojo);

                    File aternaPdfFile = new File(conciliationFile.getFilePath());

                    if (aternaPdfFile.exists()){
                        EmailTemplates emailTemplates = emailTemplatesService.findByName("conciliation_aterna");
                        emailDeliveryService.sendEmailwithAttachmentTruckDriver(emailTemplates,conciliationFile.getFilePath());
                    }else {
                        System.out.println("No se encontro el archivo");
                    }

                }else {
                    System.out.println("No conciliaron");
                    EmailTemplates emailTemplates = emailTemplatesService.findByName("no_conciliation_aterna");
                    emailTemplates.addProperty("aternaPojo", aternaPojo);
                    emailDeliveryService.deliverEmail(emailTemplates);
                }
            } else {
                System.out.println("No se cargo nada");
                PdfAternaPojo aternaPojo = policyTruckdriverMonthlyService.validateAmounts(listaApp, listaAterna);

                if (!aternaPojo.isConciliation()){
                    EmailTemplates emailTemplates = emailTemplatesService.findByName("no_conciliation_aterna");
                    emailTemplates.addProperty("aternaPojo", aternaPojo);
                    emailDeliveryService.deliverEmail(emailTemplates);
                }

            }
        }
    }

    //    @Scheduled(cron = "0 15 0 * * ?")
    @RequestMapping(value = "/ftp-download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void ftpDownload() throws Exception{
        Calendar fecha = Calendar.getInstance();
        String name = "ATERNA_CANALDEPAGO_";
        fecha.add(Calendar.DAY_OF_YEAR,-1);
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        if (dia < 10){
            name = name+"0"+dia;
        }else{
            name = name+dia;
        }
        if (mes < 10){
            name = name+"0"+mes;
        }else {
            name = name + mes;
        }
        name = name+año+".csv";

        File file = new File(env.getRequiredProperty("aterna_truckDriver.documents_dir")+name);
        if (!file.exists()){
            FtpConnect ftpConnect = new FtpConnect();
            ftpConnect.connect(env.getRequiredProperty("ftp_server_domain"), env.getRequiredProperty("ftp_server_user"), env.getRequiredProperty("ftp_server_password"));
            ftpConnect.downloadFile(name,env.getRequiredProperty("aterna_truckDriver.documents_dir")+name);
            ftpConnect.disconnect();
        }else {
            System.out.println("ya existe");
        }
    }

    @RequestMapping(value = "/conciliation-file", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> conciliation(@RequestBody String data, HttpSession session)throws Exception {

        Calendar fecha = Calendar.getInstance();
        String name = "ATERNA_CANALDEPAGO_";
        fecha.add(Calendar.DAY_OF_YEAR,-1);
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        if (dia < 10){
            name = name+"0"+dia;
        }else{
            name = name+dia;
        }
        if (mes < 10){
            name = name+"0"+mes;
        }else {
            name = name + mes;
        }
        name = name+año+".pdf";

        Users user = (Users) session.getAttribute("user");

        JsonNode jsonNode = mapper.readTree(data);

        PdfAternaPojo aternaPojo = new PdfAternaPojo();

        aternaPojo.setNumSecures(0);
        aternaPojo.setSubtotalD(new BigDecimal(0.00));
        aternaPojo.setIvaD(new BigDecimal(0.00));
        aternaPojo.setTotalD(new BigDecimal(0.00));
        aternaPojo.setSubtotalM(new BigDecimal(jsonNode.get("subtotal").asDouble()));
        aternaPojo.setIvaM(new BigDecimal(jsonNode.get("iva").asDouble()));
        aternaPojo.setTotalM(new BigDecimal(jsonNode.get("total").asDouble()));
        aternaPojo.setConciliation(false);

        BuildPdfAterna pdfAterna = new BuildPdfAterna();

        ConciliationFile conciliationFile = new ConciliationFile();
        conciliationFile.setFileName(name);
        conciliationFile.setFilePath(env.getRequiredProperty("conciliation_aterna.documents_dir") + name);
        conciliationFile.setCreationDate(LocalDateTime.now());
        conciliationFile.setUsername(user.getUsername());
        conciliationFile.setReason(jsonNode.get("reason").asText());
        conciliationFile = conciliationFileService.save(conciliationFile);

        pdfAterna.createPdf(conciliationFile.getFilePath(), aternaPojo);

        File aternaPdfFile = new File(conciliationFile.getFilePath());

        if (aternaPdfFile.exists()) {
            EmailTemplates emailTemplates = emailTemplatesService.findByName("conciliation_aterna");
            emailDeliveryService.sendEmailwithAttachmentTruckDriver(emailTemplates, conciliationFile.getFilePath());
        } else {
            System.out.println("No se encontro el archivo");

        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverMonthlyService.findAll()));
    }

    @RequestMapping(value = "/get-by-dates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getByDate(@RequestParam(name = "startDate", required = true) String startDate,
                                            @RequestParam(name = "endDate", required = true) String endDate) throws Exception{

        LocalDate startVigencyDate = (startDate == null || startDate.equals("")) ? null :
                LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate endVigencyDate = (endDate == null || endDate.equals("")) ? null :
                LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);


        List<PolicyTruckdriverMonthly> policyTruckdriverList = policyTruckdriverMonthlyService.findDStartValidityBetween(startVigencyDate, endVigencyDate);

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverList));
    }
}

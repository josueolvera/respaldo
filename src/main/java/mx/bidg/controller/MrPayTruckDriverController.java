package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.ConciliationFile;
import mx.bidg.model.EmailTemplates;
import mx.bidg.model.MrPayTruckDriver;
import mx.bidg.model.Users;
import mx.bidg.pojos.PdfAternaPojo;
import mx.bidg.service.*;
import mx.bidg.utils.BuildPdfMrPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
 * Created by Desarrollador on 19/01/2017.
 */
@Controller
@RequestMapping("/mr-pay")
public class MrPayTruckDriverController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private Environment env;

    @Autowired
    MrPayTruckDriverService mrPayTruckDriverService;

    @Autowired
    EmailDeliveryService emailDeliveryService;

    @Autowired
    EmailTemplatesService emailTemplatesService;

    @Autowired
    ConciliationFileService conciliationFileService;

    @Autowired
    private PolicyTruckdriverService policyTruckdriverService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<MrPayTruckDriver> mrPayTruckDrivers = mrPayTruckDriverService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(mrPayTruckDrivers), HttpStatus.OK);
    }

//    @Scheduled(cron = "0 25 0 * * ?")
    @RequestMapping(value = "/csv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void readCsv ()throws IOException{
        Calendar fecha = Calendar.getInstance();
        String name = "MRPAY_CANALDEPAGO_";
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
        File file = new File(env.getRequiredProperty("mr_pay_truckDriver.documents_dir")+name);
        if (file.exists()){
            mrPayTruckDriverService.readCsv(name);
        }
    }

    @RequestMapping(value = "/get-by-payment-date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByPaymentDate(@RequestParam(name = "paymentDate", required = true) String paymentDate) throws Exception {

        LocalDate lcPaymentDate = (paymentDate == null || paymentDate.equals("")) ? null :
                LocalDate.parse(paymentDate, DateTimeFormatter.ISO_LOCAL_DATE);

        List<MrPayTruckDriver> mrPayTruckDrivers = mrPayTruckDriverService.findByPaymentDate(lcPaymentDate);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(mrPayTruckDrivers), HttpStatus.OK);
    }

    //    @Scheduled(cron = "0 25 0 * * ?")
    @RequestMapping(value = "/conciliation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void conciliationMonth ()throws Exception{

        Calendar fecha = Calendar.getInstance();
        String name = "MRPAY_CANALDEPAGO_";
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


        List<String> noAutorizationMrPay = mrPayTruckDriverService.findNoAutorizationByDStartValidityBetween(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

        List<String> noAutorizationApp = policyTruckdriverService.getNoAutorizationByDStartValidityBetween(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

        if (noAutorizationMrPay.size() > 0){
            PdfAternaPojo aternaPojo = mrPayTruckDriverService.conciliationByAutorizationNumber(noAutorizationMrPay, noAutorizationApp, LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

            if (aternaPojo.isConciliation()){
                BuildPdfMrPay pdfMrPay = new BuildPdfMrPay();
                ConciliationFile conciliationFile = new ConciliationFile();
                conciliationFile.setFileName(name);
                conciliationFile.setFilePath(env.getRequiredProperty("conciliation_aterna.documents_dir")+name);
                conciliationFile = conciliationFileService.save(conciliationFile);
                pdfMrPay.createPdf(conciliationFile.getFilePath(), aternaPojo);

                File improPdfFile = new File(conciliationFile.getFilePath());
                if (improPdfFile.exists()){
                    EmailTemplates emailTemplates = emailTemplatesService.findByName("conciliation_mrpay");
                    emailDeliveryService.sendEmailwithAttachmentTruckDriver(emailTemplates, conciliationFile.getFilePath());
                }
            }else {
                EmailTemplates emailTemplates = emailTemplatesService.findByName("no_conciliation_mrpay");
                emailTemplates.addProperty("aternaPojo", aternaPojo);
                emailDeliveryService.deliverEmail(emailTemplates);
            }
        }
    }

    @RequestMapping(value = "/get-by-dates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getByDate(@RequestParam(name = "startDate", required = true) String startDate,
                                            @RequestParam(name = "endDate", required = true) String endDate) throws Exception{

        LocalDate startVigencyDate = (startDate == null || startDate.equals("")) ? null :
                LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate endVigencyDate = (endDate == null || endDate.equals("")) ? null :
                LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);


        List<MrPayTruckDriver> mrPayTruckDrivers = mrPayTruckDriverService.findByPaymentDateBetween(startVigencyDate, endVigencyDate);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(mrPayTruckDrivers), HttpStatus.OK);
    }

    @RequestMapping(value = "/conciliation-file", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> conciliation(@RequestBody String data, HttpSession session)throws Exception {

        Calendar fecha = Calendar.getInstance();
        String name = "MRPAY_CANALDEPAGO_";
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

        BuildPdfMrPay pdfMrPay = new BuildPdfMrPay();

        ConciliationFile conciliationFile = new ConciliationFile();
        conciliationFile.setFileName(name);
        conciliationFile.setFilePath(env.getRequiredProperty("conciliation_aterna.documents_dir") + name);
        conciliationFile.setCreationDate(LocalDateTime.now());
        conciliationFile.setUsername(user.getUsername());
        conciliationFile.setReason(jsonNode.get("reason").asText());
        conciliationFile = conciliationFileService.save(conciliationFile);

        pdfMrPay.createPdf(conciliationFile.getFilePath(), aternaPojo);

        File aternaPdfFile = new File(conciliationFile.getFilePath());

        if (aternaPdfFile.exists()) {
            EmailTemplates emailTemplates = emailTemplatesService.findByName("conciliation_mrpay");
            emailDeliveryService.sendEmailwithAttachmentTruckDriver(emailTemplates, conciliationFile.getFilePath());
        } else {
            System.out.println("No se encontro el archivo");

        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(mrPayTruckDriverService.findAll()));
    }
}

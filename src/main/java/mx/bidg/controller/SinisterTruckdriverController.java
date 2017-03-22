package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.pojos.PdfAternaPojo;
import mx.bidg.service.*;
import mx.bidg.utils.BuildPdfAterna;
import mx.bidg.utils.BuildPdfImpro;
import mx.bidg.utils.FtpConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
 * Created by Kevin Salvador on 11/01/2017.
 */
@Controller
@RequestMapping("sinister-truck-driver")
public class SinisterTruckdriverController {

    @Autowired
    SinisterTruckdriverService sinisterTruckdriverService;

    @Autowired
    PolicyTruckdriverService policyTruckdriverService;

    @Autowired
    EmailDeliveryService emailDeliveryService;

    @Autowired
    EmailTemplatesService emailTemplatesService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    InsurancePremiumService insurancePremiumService;

    @Autowired
    Environment env;

    @Autowired
    ConciliationFileService conciliationFileService;

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>findAll()throws Exception{
        List<SinisterTruckdriver>sinisterTruckdrivers=sinisterTruckdriverService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(sinisterTruckdrivers),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/get-polize",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getSinister(@RequestParam (name = "startDate",required = true)String startDate
                                             )throws Exception{
        LocalDateTime creationDate = (startDate == null || startDate.equals("")) ? null :
                LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        List<SinisterTruckdriver> ListFindIdAndDate = sinisterTruckdriverService.findByCreationDate(creationDate);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(ListFindIdAndDate),
                HttpStatus.OK
        );
    }

//    @Scheduled(cron = "0 15 0 * * ?")
    @RequestMapping(value = "/csv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void readCSV()throws Exception {
        Calendar fecha = Calendar.getInstance();
        String name = "IMPRO_CANALDEPAGO_";
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
        File file = new File(env.getRequiredProperty("sinister_truckDriver.documents_dir")+name+".csv");
        if (file.exists()){
            sinisterTruckdriverService.readCsv(name);



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

            List<SinisterTruckdriver> sinisterTruckdriverList = sinisterTruckdriverService.findByDStartValidity(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

            if (sinisterTruckdriverList.size() > 0){
                for (SinisterTruckdriver sinisterTruckdriver : sinisterTruckdriverList){
                    if (sinisterTruckdriver != null){
                        PolicyTruckdriver policyTruckdriver = policyTruckdriverService.findByFolio(sinisterTruckdriver.getNumFolio());
                        if (policyTruckdriver != null){
                            if (policyTruckdriver.getInsurancePremium() != null){
                                InsurancePremium insurancePremium = insurancePremiumService.findById(policyTruckdriver.getInsurancePremium().getIdInsurancePremium());
                                sinisterTruckdriver.setInsurancePremium(insurancePremium);
                                sinisterTruckdriverService.update(sinisterTruckdriver);
                            }
                        }
                    }
                }
            }

            List<String> folios = policyTruckdriverService.findNoAutorizationByDStartValidityBetween(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

            List<String> foliosMImpro = sinisterTruckdriverService.findNoAutizationByDStartValidity(LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));


            if (foliosMImpro.size() > 0){
                PdfAternaPojo aternaPojo = sinisterTruckdriverService.conciliationByFolio(folios, foliosMImpro, LocalDate.parse(fechaMin,formatter), LocalDate.parse(fechaMax,formatter));

                if (aternaPojo.isConciliation()){
                    BuildPdfImpro pdfImpro = new BuildPdfImpro();
                    ConciliationFile conciliationFile = new ConciliationFile();
                    conciliationFile.setFileName(name+".pdf");
                    conciliationFile.setFilePath(env.getRequiredProperty("conciliation_aterna.documents_dir")+name+".pdf");
                    conciliationFile = conciliationFileService.save(conciliationFile);
                    pdfImpro.createPdf(conciliationFile.getFilePath(), aternaPojo);

                    File improPdfFile = new File(conciliationFile.getFilePath());
                    if (improPdfFile.exists()){
                        EmailTemplates emailTemplates = emailTemplatesService.findByName("conciliation_impro");
                        emailDeliveryService.sendEmailwithAttachmentTruckDriver(emailTemplates, conciliationFile.getFilePath());
                    }
                }else {
                    EmailTemplates emailTemplates = emailTemplatesService.findByName("no_conciliation_impro");
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
        String name = "IMPRO_CANALDEPAGO_";
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

        File file = new File(env.getRequiredProperty("sinister_truckDriver.documents_dir")+name);
        if (!file.exists()){
            FtpConnect ftpConnect = new FtpConnect();
            ftpConnect.connect(env.getRequiredProperty("ftp_server_domain"), env.getRequiredProperty("ftp_server_user"), env.getRequiredProperty("ftp_server_password"));
            ftpConnect.downloadFile(name,env.getRequiredProperty("sinister_truckDriver.documents_dir")+name);
            ftpConnect.disconnect();
        }else {
            System.out.println("ya existe");
        }
    }

    @RequestMapping(value = "/get-by-dates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getByDate(@RequestParam(name = "startDate", required = true) String startDate,
                                            @RequestParam(name = "endDate", required = true) String endDate) throws Exception{

        LocalDate startVigencyDate = (startDate == null || startDate.equals("")) ? null :
                LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate endVigencyDate = (endDate == null || endDate.equals("")) ? null :
                LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);


        List<SinisterTruckdriver> sinisterTruckdriverList = sinisterTruckdriverService.findByDStartValidity(startVigencyDate, endVigencyDate);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sinisterTruckdriverList), HttpStatus.OK);
    }

    @RequestMapping(value = "/conciliation-file", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> conciliation(@RequestBody String data, HttpSession session)throws Exception {

        Users user = (Users) session.getAttribute("user");

        JsonNode jsonNode = mapper.readTree(data);

        Calendar fecha = Calendar.getInstance();
        String name = "IMPRO_CANALDEPAGO_";
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

        PdfAternaPojo aternaPojo = new PdfAternaPojo();

        aternaPojo.setNumSecures(0);
        aternaPojo.setSubtotalD(new BigDecimal(0.00));
        aternaPojo.setIvaD(new BigDecimal(0.00));
        aternaPojo.setTotalD(new BigDecimal(0.00));
        aternaPojo.setSubtotalM(new BigDecimal(jsonNode.get("subtotal").asDouble()));
        aternaPojo.setIvaM(new BigDecimal(jsonNode.get("iva").asDouble()));
        aternaPojo.setTotalM(new BigDecimal(jsonNode.get("total").asDouble()));
        aternaPojo.setConciliation(false);

        BuildPdfImpro pdfImpro = new BuildPdfImpro();

        ConciliationFile conciliationFile = new ConciliationFile();
        conciliationFile.setFileName(name);
        conciliationFile.setFilePath(env.getRequiredProperty("conciliation_aterna.documents_dir") + name);
        conciliationFile.setCreationDate(LocalDateTime.now());
        conciliationFile.setUsername(user.getUsername());
        conciliationFile.setReason(jsonNode.get("reason").asText());
        conciliationFile = conciliationFileService.save(conciliationFile);

        pdfImpro.createPdf(conciliationFile.getFilePath(), aternaPojo);

        File aternaPdfFile = new File(conciliationFile.getFilePath());

        if (aternaPdfFile.exists()) {
            EmailTemplates emailTemplates = emailTemplatesService.findByName("conciliation_impro");
            emailDeliveryService.sendEmailwithAttachmentTruckDriver(emailTemplates, conciliationFile.getFilePath());
        } else {
            System.out.println("No se encontro el archivo");

        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sinisterTruckdriverService.findAll()));
    }

}

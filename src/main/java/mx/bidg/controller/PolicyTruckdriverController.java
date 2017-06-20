package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.service.CTypeSecureService;
import mx.bidg.service.PolicyTruckdriverService;
import mx.bidg.utils.FtpConnect;
import org.apache.http.HttpStatus;
import org.apache.xml.serialize.Method;
import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

@Controller
@RequestMapping("/policy-truckdriver")
public class PolicyTruckdriverController {

    @Autowired
    private PolicyTruckdriverService policyTruckdriverService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception {
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverService.findAll()));
    }

//    @Scheduled(cron = "0 30 0 * * ?")
    @RequestMapping(value = "/csv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void readCSV()throws IOException{
        Calendar fecha = Calendar.getInstance();
        String name = "EMIASEG_CANALDEPAGO_";
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
        File file = new File(env.getRequiredProperty("policy_truckDriver.documents_dir")+name);
        if (file.exists()){
            policyTruckdriverService.readCsvPolicya(name);
        }else {
            System.out.println("No existio el archivo");
        }
    }

//    @Scheduled(cron = "0 10 0 * * ?")
    @RequestMapping(value = "/ftp-download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void ftpDownload() throws Exception{
        Calendar fecha = Calendar.getInstance();
        String name = "EMIASEG_CANALDEPAGO_";
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

        File file = new File(env.getRequiredProperty("policy_truckDriver.documents_dir")+name);
        if (!file.exists()){
            FtpConnect ftpConnect = new FtpConnect();
            ftpConnect.connect(env.getRequiredProperty("ftp_server_domain"), env.getRequiredProperty("ftp_server_user"), env.getRequiredProperty("ftp_server_password"));
            ftpConnect.downloadFile(name,env.getRequiredProperty("policy_truckDriver.documents_dir")+name);
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


        List<PolicyTruckdriver> policyTruckdriverList = policyTruckdriverService.findDStartValidityBetween(startVigencyDate, endVigencyDate);

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverList));
    }
}

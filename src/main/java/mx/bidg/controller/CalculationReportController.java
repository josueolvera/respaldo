package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.AuthorizationReports;
import mx.bidg.model.CalculationReport;
import mx.bidg.service.AuthorizationReportsService;
import mx.bidg.service.CalculationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Desarrollador on 14/12/2016.
 */
@Controller
@RequestMapping("calculation-reports")
public class CalculationReportController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CalculationReportService calculationReportService;

    @Autowired
    AuthorizationReportsService authorizationReportsService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<CalculationReport> calculationReportList = calculationReportService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(calculationReportList), HttpStatus.OK);
    }

    @RequestMapping(value = "/not-sended", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllNotSended()throws IOException{
        List<CalculationReport> calculationReportList = calculationReportService.findAllReportsNotSendedAndNotAuthorized();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(calculationReportList), HttpStatus.OK);
    }

    @RequestMapping(value = "/sended-not-authorized", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllSendedNotAuthorized() throws IOException{
        List<AuthorizationReports> authorizationReportsList = authorizationReportsService.findAllFlagsWithReportsNotAuthorized();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationReportsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{idCalculationReports}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void download(
            @PathVariable Integer idCalculationReports,
            HttpServletResponse response
    ) throws Exception {

        CalculationReport calculationReport = calculationReportService.findById(idCalculationReports);

        if (calculationReport != null){
            File file = new File(calculationReport.getFilePath());

            if (! file.canRead()) {
                throw new ValidationException(
                        "El archivo "+ calculationReport.getFilePath() +" no existe",
                        "El documento solicitado no existe"
                );
            }

            FileInputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + calculationReport.getFileName()+".xlsx" + "\"");
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

    }

    @RequestMapping(value = "/authorized-corporate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllAuthorizedCorporate()throws IOException{
        List<CalculationReport> calculationReportList = calculationReportService.findAllAuthorizedCorporate();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(calculationReportList), HttpStatus.OK);
    }

    @RequestMapping(value = "/authorized-outsourcing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllAuthorizedOutsourcing()throws IOException{
        List<CalculationReport> calculationReportList = calculationReportService.findAllAuthorizedOutsourcing();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(calculationReportList), HttpStatus.OK);
    }
}

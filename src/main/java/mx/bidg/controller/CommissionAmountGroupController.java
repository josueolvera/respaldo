package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CommissionAmountGroup;
import mx.bidg.service.CommissionAmountGroupService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by josue on 13/10/2016.
 */
@Controller
@RequestMapping("commission-amount-group")
public class CommissionAmountGroupController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CommissionAmountGroupService commissionAmountGroupService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CommissionAmountGroup> amountGroupList = commissionAmountGroupService.findAll();
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(amountGroupList));
    }

    @RequestMapping(value = "/report-advisers", method = RequestMethod.GET)
    public ResponseEntity<String> reporteSemanal(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +"Reporte de calculo de comisiones.xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        commissionAmountGroupService.comissionByReport(outputStream);
        outputStream.flush();
        outputStream.close();

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionAmountGroupService.findAll()));
    }

    @RequestMapping(value = "/report-all-commissions", method = RequestMethod.GET)
    public ResponseEntity<String> reporteMensual(HttpServletResponse response, @RequestParam(name= "fromDate", required=true) String fromDate
            , @RequestParam(name="toDate", required=true) String toDate) throws IOException{


        LocalDateTime ofDate = (fromDate == null || fromDate.equals("")) ? null :
                LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (toDate == null || toDate.equals("")) ? null :
                LocalDateTime.parse(toDate, DateTimeFormatter.ISO_DATE_TIME);


        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +"Reporte de calculo mensual.xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        commissionAmountGroupService.reportMonthlyCommissions(outputStream, ofDate, untilDate);
        outputStream.flush();
        outputStream.close();

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionAmountGroupService.findAll()));
    }
}

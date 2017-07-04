package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.PayRequestsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Created by User on 21/06/2017.
 */
@Controller
@RequestMapping("/pay-requests-history")
public class PayRequestsHistoryController {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    PayRequestsHistoryService payRequestsHistoryService;

    @RequestMapping(value = "/report-pay-requests", method = RequestMethod.GET)
    public ResponseEntity<String> reporteSemanal(HttpServletResponse response, @RequestParam(name= "startDate", required=true) String startDate
            , @RequestParam(name="endDate", required=true) String endDate) throws IOException{

        LocalDateTime ofDate = (startDate == null || startDate.equals("")) ? null :
                LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (endDate == null || endDate.equals("")) ? null :
                LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"ReporteCuentasPagadas.xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        payRequestsHistoryService.payRequestsReport(outputStream, ofDate, untilDate);
        outputStream.flush();
        outputStream.close();

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(payRequestsHistoryService.findAll()));
    }

}

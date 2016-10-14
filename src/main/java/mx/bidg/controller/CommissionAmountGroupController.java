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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
}

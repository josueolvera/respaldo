package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAccountsPayableStatus;
import mx.bidg.service.CAccountsPayableStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Controller
@RequestMapping("accounts-payable-status")
public class CAccountsPayableStatusController {

    @Autowired
    CAccountsPayableStatusService cAccountsPayableStatusService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CAccountsPayableStatus> cAccountsPayableStatuses = cAccountsPayableStatusService.findAll();
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cAccountsPayableStatuses),
                HttpStatus.OK
        );
    }
}

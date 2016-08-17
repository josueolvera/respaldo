package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBalanceStatus;
import mx.bidg.service.CBalanceStatusService;
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
@RequestMapping("balance-status")
public class CBalanceStatusController {

    @Autowired
    CBalanceStatusService cBalanceStatusService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CBalanceStatus> cBalanceStatuses = cBalanceStatusService.findAll();
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cBalanceStatuses),
                HttpStatus.OK
        );
    }
}

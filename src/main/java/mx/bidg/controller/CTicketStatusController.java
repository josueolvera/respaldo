package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CTicketStatus;
import mx.bidg.service.CTicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@RestController
@RequestMapping("ticket-status")
public class CTicketStatusController {

    @Autowired
    private CTicketStatusService cTicketStatusService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CTicketStatus> ticketStatusList = cTicketStatusService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(ticketStatusList), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTicketStatus}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable int idTicketStatus) throws IOException {
        CTicketStatus cTicketStatus = cTicketStatusService.findById(idTicketStatus);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cTicketStatus), HttpStatus.OK);
    }
}

package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CTicketStatus;
import mx.bidg.model.EmailTemplates;
import mx.bidg.model.Ticket;
import mx.bidg.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<Ticket> tickets = ticketService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTicket}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idTicket) throws IOException {
        Ticket ticket = ticketService.findById(idTicket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(value = "/folio",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@RequestBody String folio) throws IOException {
        Ticket ticket = ticketService.findByFolio(folio);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody Ticket ticket) throws IOException {
        ticket = ticketService.save(ticket);
        ticketService.sendEmail(ticket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody String ticket) throws IOException {
        System.out.println(ticket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(""), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-ticket-status/{idTicket}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody CTicketStatus ticketStatus,@PathVariable Integer idTicket) throws IOException {

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class)
                        .writeValueAsString(ticketService.changeTicketStatus(idTicket,ticketStatus)),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/priority/{idPriority}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByPriority(@PathVariable Integer idPriority) throws IOException {
        List<Ticket> tickets = ticketService.findByPriority(idPriority);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/ticket-status/{idTicketStatus}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByTicketStatus(@PathVariable Integer idTicketStatus) throws IOException {
        List<Ticket> tickets = ticketService.findByTicketStatus(idTicketStatus);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTicketStatus}/{idPriority}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByTicketStatusPriority(@PathVariable Integer idTicketStatus, @PathVariable Integer idPriority) throws IOException {
        List<Ticket> tickets = ticketService.findByTicketStatusPriority(idTicketStatus,idPriority);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(tickets), HttpStatus.OK);
    }
}

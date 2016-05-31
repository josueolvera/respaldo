package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.CIncidenceService;
import mx.bidg.service.CPriorityService;
import mx.bidg.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@Controller
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CIncidenceService cIncidenceService;

    @Autowired
    private CPriorityService cPriorityService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<Ticket> tickets = ticketService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTicket}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idTicket) throws IOException {
        Ticket ticket = ticketService.findById(idTicket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@RequestParam(name = "folio", required = true) String folio) throws IOException {
        Ticket ticket = ticketService.findByFolio(folio);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws IOException {
        System.out.println(data);
        JsonNode node = mapper.readTree(data);
        Ticket ticket = new Ticket();
        ticket.setUser((Users) session.getAttribute("user"));
        ticket.setDescripcionProblema(node.get("descripcionProblema").asText());
        ticket.setIncidence(cIncidenceService.findById(node.get("incidence").get("idIncidence").asInt()));
        ticket.setPriority(cPriorityService.findById(node.get("priority").get("idPriority").asInt()));
        ticket.setTicketStatus(CTicketStatus.ABIERTO);
        ticket = ticketService.save(ticket);
        ticketService.sendEmail(ticket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody String ticket) throws IOException {
        System.out.println(ticket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(""), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-ticket-status/{idTicket}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody CTicketStatus ticketStatus,@PathVariable Integer idTicket) throws IOException {

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class)
                        .writeValueAsString(ticketService.changeTicketStatus(idTicket,ticketStatus)),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/priority/{idPriority}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByPriority(@PathVariable Integer idPriority) throws IOException {
        List<Ticket> tickets = ticketService.findByPriority(idPriority);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/ticket-status/{idTicketStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByTicketStatus(@PathVariable Integer idTicketStatus) throws IOException {
        List<Ticket> tickets = ticketService.findByTicketStatus(idTicketStatus);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTicketStatus}/{idPriority}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByTicketStatusPriority(@PathVariable Integer idTicketStatus, @PathVariable Integer idPriority) throws IOException {
        List<Ticket> tickets = ticketService.findByTicketStatusPriority(idTicketStatus,idPriority);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(tickets), HttpStatus.OK);
    }
}

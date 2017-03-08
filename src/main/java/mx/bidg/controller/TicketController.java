package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.CIncidenceService;
import mx.bidg.service.CPriorityService;
import mx.bidg.service.CTicketStatusService;
import mx.bidg.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private CTicketStatusService cTicketStatusService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/category/{idTicketCategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(@PathVariable int idTicketCategory) throws IOException {
        List<Ticket> tickets = ticketService.findAll(new CTicketsCategories(idTicketCategory));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(tickets), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTicket}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idTicket) throws IOException {
        Ticket ticket = ticketService.findById(idTicket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByFolio(@RequestParam(name = "folio", required = true) String folio) throws IOException {
        Ticket ticket = ticketService.findByFolio(folio);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws IOException {
        JsonNode node = mapper.readTree(data);
        Ticket ticket = new Ticket();
        ticket.setUser((Users) session.getAttribute("user"));
        ticket.setDescripcionProblema(node.get("descripcionProblema").asText());
        ticket.setIncidence(cIncidenceService.findById(node.get("incidence").get("idIncidence").asInt()));
        ticket.setPriority(cPriorityService.findById(node.get("priority").get("idPriority").asInt()));
        ticket.setTicketStatus(CTicketStatus.ABIERTO);
        ticket = ticketService.save(ticket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticket), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-ticket-status/{idTicket}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer idTicket,@RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        Ticket t = ticketService.findById(idTicket);
        CTicketStatus ts = cTicketStatusService.findById(node.get("idTicketStatus").asInt());
        System.out.println("idStatus"+node.get("idTicketStatus").asInt());
        t.setTicketStatus(ts);
        t.setFechaFinal(LocalDateTime.now());
        ticketService.update(t);
        List<Ticket>findAll= ticketService.findStatusOpen(1);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class)
                        .writeValueAsString(findAll), HttpStatus.OK);
    }

    @RequestMapping(value = "/open/{idTicketStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findOpen(@PathVariable int idTicketStatus) throws IOException {
        List<Ticket> ticketsOpen = ticketService.findStatusOpen(idTicketStatus);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticketsOpen), HttpStatus.OK);
    }

    @RequestMapping(value = "/priority/{idPriority}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findPriority(@PathVariable int idPriority) throws IOException {
        List<Ticket> ticketsPriority = ticketService.findByPriority(idPriority);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticketsPriority), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idPriority}/{idTicketStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findPriority(@PathVariable int idPriority, @PathVariable int idTicketStatus) throws IOException {
        List<Ticket> ticketsPriorityAndStatus = ticketService.findByTicketStatusPriority(idPriority,idTicketStatus);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(ticketsPriorityAndStatus), HttpStatus.OK);
    }
}

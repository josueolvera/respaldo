/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Events;
import mx.bidg.model.Ticket;
import mx.bidg.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rubens
 */
@Controller
@RequestMapping("/events")
public class EventsController {
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private EventsService eventsService;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEvents(
            @RequestParam(name = "room", required = false) Integer idRoom,
            @RequestParam(name = "user", required = false) Integer idUser,
            @RequestParam(name = "date", required = false) String date
    ) throws Exception {
        List<Events> events = eventsService.getEvents(idRoom, idUser,date);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(events));
    }
    
     @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody Events event) throws IOException{
        event = eventsService.save(event);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(event));
    }
    
     
}

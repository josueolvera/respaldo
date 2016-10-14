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
            @RequestParam(name="day",required= false) String day
    ) throws Exception {
        List<Events> events = eventsService.getEvents(idRoom, idUser, day);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(events));
    }
    
     @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, @RequestParam(name="room") Integer idRoom) throws IOException, Exception{
        Events event = eventsService.save(data, idRoom);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(event));
            
    
    
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer id)throws IOException{
        Events event = eventsService.findById(id);
        
        if (event != null) {
            eventsService.delete(event);
        }
        return new ResponseEntity<>("Registro Eliminado",HttpStatus.OK);
    }
    
    
     
}

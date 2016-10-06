/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Events;
import mx.bidg.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    
    @RequestMapping(value = "/event",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEvents(
            @RequestParam(name = "room", required = false) Integer idRoom,
            @RequestParam(name = "user", required = false) Integer idUser
    ) throws Exception {
        List<Events> events = eventsService.getEvents(idRoom, idUser);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(events));
    }
}

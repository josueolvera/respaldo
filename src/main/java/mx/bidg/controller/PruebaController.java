/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.ActiveSessionsList;
import mx.bidg.config.Permissions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.bidg.config.JsonViews;
import mx.bidg.model.CTasks;
import mx.bidg.model.Users;
import mx.bidg.service.CTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpSession;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/prueba")
public class PruebaController {
    
    @Autowired
    CTasksService pruebaService;
    @Autowired
    Permissions permissions;
    @Autowired
    ActiveSessionsList activeSessions;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody String prueba() throws Exception {
        List<CTasks> tasks = pruebaService.findAll();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(tasks);
    }
    
    @RequestMapping(value = "/build-permissions", method = RequestMethod.GET)
    public @ResponseBody String newHashMap() {
        permissions.buildTasksPermissions();
        return "Muchos Exito";
    }
    
    @RequestMapping(value = "/hashmap", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String hashMap() throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        response.put("permissions", permissions.getMap());

        HashMap<String, Users> usersSessions = new HashMap<>();
        for (Map.Entry<String, HttpSession> sessionEntry : activeSessions.getSessionList().entrySet()) {
            usersSessions.put(sessionEntry.getKey(), (Users) sessionEntry.getValue().getAttribute("user"));
        }
        response.put("sessions", usersSessions);
        return mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(response);
    }
    
    @RequestMapping(value = "/ctasks", produces = "Application/json")
    public @ResponseBody String ctasks() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(pruebaService.findAll());
    }

    @RequestMapping(value = "/{uno}/{dos}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String dobleVariable (@PathVariable int uno, @PathVariable int dos) {
        return uno + ":" + dos;
    }

}

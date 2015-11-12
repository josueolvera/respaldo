/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CTasks;
import mx.bidg.service.CTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

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
    
    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody String prueba() throws Exception {
        
        List<CTasks> tasks = pruebaService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(tasks);
    }
    
    @RequestMapping(value = "/newHashmap", method = RequestMethod.GET)
    public @ResponseBody String newHashMap(){
        permissions.buildTasksPermissions();
        return "Muchos Exito";
    }
    
    @RequestMapping(value = "/hashmap", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, ArrayList<Integer>> hashMap() {
        return permissions.getMap();
    }
    
    @RequestMapping(value = "/ctasks", produces = "Application/json")
    public @ResponseBody String ctasks() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(pruebaService.findAll());
    }
    
}

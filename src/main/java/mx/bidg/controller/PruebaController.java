/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import mx.bidg.config.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.bidg.model.CTasks;
import mx.bidg.service.CTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/")
public class PruebaController {
    
    @Autowired
    CTasksService pruebaService;
    @Autowired
    Permissions permissions;
    
    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody List<CTasks> prueba() throws Exception {
        
        List<CTasks> tasks = pruebaService.findAll();
        return tasks;
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
    
}

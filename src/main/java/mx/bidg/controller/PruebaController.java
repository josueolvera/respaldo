/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.dao.CTasksDao;
import mx.bidg.dao.CTasksDaoImpl;
import mx.bidg.model.CTasks;
import mx.bidg.service.PruebaService;
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
    PruebaService pruebaService;
    
    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody List<CTasks> prueba() throws Exception {
        
        List<CTasks> tasks = pruebaService.findAll();
        return tasks;
    }
    
    
}

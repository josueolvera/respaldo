/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Providers;
import mx.bidg.service.ProvidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/providers")
public class ProvidersController {
    
    @Autowired
    ProvidersService providersService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(produces = "application/json")
    public @ResponseBody ResponseEntity<String> getProvidersList() throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(providersService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}

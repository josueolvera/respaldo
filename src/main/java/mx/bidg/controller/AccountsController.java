/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Providers;
import mx.bidg.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController {
    
    @Autowired
    AccountsService accountsService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> accountsByProvider(@PathVariable int idProvider) throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(accountsService
                .findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/user/{idUser}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> accountsByUser(@PathVariable int idUser) throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString("");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}

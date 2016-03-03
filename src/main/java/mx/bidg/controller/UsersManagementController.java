/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/users")
public class UsersManagementController {
    
    @Autowired
    UsersService usersService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getUsersList() throws Exception {
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(usersService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}

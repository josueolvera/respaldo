/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.RequestTypesProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/request-type-product")
public class RequestTypesProductController {
    
    @Autowired
    RequestTypesProductService requestTypesProductService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/{idRequestCategory}/{idRequestType}/{idProductType}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByCombination(@PathVariable int idRequestCategory, 
            @PathVariable int idRequestType, @PathVariable int idProductType) throws Exception {
        
        String response = mapper.writerWithView(JsonViews.Root.class).
                writeValueAsString(requestTypesProductService.findByCombination(idRequestCategory, idRequestType, idProductType));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.RequestTypesProduct;
import mx.bidg.service.RequestTypesProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import mx.bidg.model.CRequestsCategories;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/request-type-product")
public class RequestTypesProductController {
    
    @Autowired
    RequestTypesProductService requestTypesProductService;
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(value = "/{idRequestCategory}/{idRequestType}/{idProductType}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByCombination(@PathVariable int idRequestCategory, 
            @PathVariable int idRequestType, @PathVariable int idProductType) throws Exception {
        
        String response = mapper.writerWithView(JsonViews.Root.class).
                writeValueAsString(requestTypesProductService.findByCombination(idRequestCategory, idRequestType, idProductType));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{idRequestType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByRequestType(@PathVariable int idRequestType) throws IOException{
        List<RequestTypesProduct> requestTypesProduct = requestTypesProductService.findByRequestType(new CRequestTypes(idRequestType));
        return new ResponseEntity<String>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestTypesProduct),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/requestCateogy/{idRequestCategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByRequestCategory(@PathVariable int idRequestCategory) throws IOException{
        List<RequestTypesProduct> requestTypesProduct = requestTypesProductService.findByRequestCategory(new CRequestsCategories(idRequestCategory));
        return new ResponseEntity<String>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestTypesProduct),HttpStatus.OK);
    }
    
}

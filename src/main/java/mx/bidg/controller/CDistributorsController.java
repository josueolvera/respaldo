/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CDistributors;
import mx.bidg.service.CDistributorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/distributors")
public class CDistributorsController {
    
    @Autowired
    private CDistributorsService cDistributorsService;
    
    ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getCDistributors(@RequestParam(name = "forStock", required = false) boolean forStock) throws Exception {
        List<CDistributors> list;

        if (forStock) {
            list = cDistributorsService.findAllForStock();
        } else {
            list = cDistributorsService.findAll();
        }

        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }

    @RequestMapping(value = "/agreement", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDistributorsByAgreement() throws IOException{
        List<CDistributors> cDistributors = cDistributorsService.findAllForAgreement();
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(cDistributors), HttpStatus.OK);
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDistributorByStock() throws IOException{
        List<CDistributors> cDistributorses = cDistributorsService.findAllForStock();
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(cDistributorses), HttpStatus.OK);
    }
}

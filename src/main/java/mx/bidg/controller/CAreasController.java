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
import mx.bidg.model.CAreas;
import mx.bidg.model.CGroups;
import mx.bidg.model.DwEnterprises;
import mx.bidg.service.CAreasService;
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
@RequestMapping("/areas")
public class CAreasController {
    
    @Autowired
    CAreasService cAreasService;
    
    ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getAreas() throws Exception {
        List<CAreas> list = cAreasService.findAll();
        return map.writerWithView(JsonViews.Embedded.class).writeValueAsString(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findSapSaleById(@PathVariable int id) throws Exception {
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(cAreasService.findById(id));
    }

    @RequestMapping(value = "/area-role/{idArea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAreasWithRoles(@PathVariable Integer idArea)throws IOException{
        CAreas areas = cAreasService.findAreaWhitRole(idArea);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(areas),HttpStatus.OK);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAreas;
import mx.bidg.model.CGroups;
import mx.bidg.model.DwEnterprises;
import mx.bidg.pojos.HierarchicalLevel;
import mx.bidg.service.DwEnterprisesService;
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
@RequestMapping("/dw-enterprises")
public class DwEnterprisesController {
    
    @Autowired
    private DwEnterprisesService dwEnterprisesService;
    
    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(value = "/{idGroup}/{idArea}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByGroupArea(@PathVariable int idGroup, @PathVariable int idArea) throws Exception {
        List<DwEnterprises> list = dwEnterprisesService.findByGroupArea(new CGroups(idGroup), new CAreas(idArea));
        return new ResponseEntity<>(map.writerWithView(JsonViews.IdsEnterprises.class).writeValueAsString(list), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/{idUser}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByIdUser(@PathVariable int idUser) throws JsonProcessingException {
        DwEnterprises dw = dwEnterprisesService.findByIdUser(idUser);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Root.class).writeValueAsString(dw), HttpStatus.OK);
    }

    @RequestMapping(value = "/hierarchy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getHierarchy() throws IOException {
        List<HierarchicalLevel> hierarchy = dwEnterprisesService.findHierarchicalStructure();
        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(hierarchy),
                HttpStatus.OK
        );
    }
}

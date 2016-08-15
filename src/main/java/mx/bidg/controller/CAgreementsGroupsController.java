/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.service.CAgreementsGroupsService;
import org.hibernate.annotations.AnyMetaDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author josueolvera
 */
@Controller
@RequestMapping("c-agreements-groups")
public class CAgreementsGroupsController {

    @Autowired
    CAgreementsGroupsService cAgreementsGroupsService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll () throws IOException{
        List<CAgreementsGroups> agreementsGroups = cAgreementsGroupsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agreementsGroups), HttpStatus.OK);
    }
    
}

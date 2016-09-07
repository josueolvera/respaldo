/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAgreements;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.service.CAgreementsGroupsService;
import mx.bidg.service.CAgreementsService;
import mx.bidg.service.GroupsAgreementsService;
import org.hibernate.annotations.AnyMetaDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOError;
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
    CAgreementsService agreementsService;

    @Autowired
    GroupsAgreementsService groupsAgreementsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll () throws IOException{
        List<CAgreementsGroups> agreementsGroups = cAgreementsGroupsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agreementsGroups), HttpStatus.OK);
    }

    @RequestMapping(value="/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveGroupAgreements(@RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);

        CAgreementsGroups agreementsGroups = new CAgreementsGroups();
        agreementsGroups.setAgreementGroupName(node.get("agreementGroupName").asText().toUpperCase());
        agreementsGroups.setStatus(1);
        agreementsGroups = cAgreementsGroupsService.save(agreementsGroups);

        List<CAgreements> agreementsList = agreementsService.findActives();

        for (CAgreements agreements : agreementsList){
            GroupsAgreements groupsAgreement = new GroupsAgreements();
            groupsAgreement.setHasAgreement(false);
            groupsAgreement.setAgreement(agreements);
            groupsAgreement.setAgreementGroup(agreementsGroups);
            groupsAgreement.setIdAccessLevel(1);
            groupsAgreementsService.save(groupsAgreement);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agreementsGroups), HttpStatus.OK);
    }

    @RequestMapping(value = "/low-group/{idAg}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> lowGroup(@PathVariable Integer idAg) throws IOException{
        CAgreementsGroups agreementsGroups = cAgreementsGroupsService.lowGroup(idAg);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agreementsGroups),HttpStatus.OK);
    }

    @RequestMapping(value = "/actives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> groupsActives() throws IOException{
        List<CAgreementsGroups> groupsList = cAgreementsGroupsService.findGroupsActives();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(groupsList), HttpStatus.OK);
    }
    
}

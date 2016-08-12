/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.xml.ws.Response;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAgreements;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.service.CAgreementsGroupsService;
import mx.bidg.service.CAgreementsService;
import mx.bidg.service.GroupsAgreementsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author josueolvera
 */
@Controller
@RequestMapping("groups-agreements")
public class GroupsAgreementsController {
    
    @Autowired
    GroupsAgreementsService groupsAgreementsService;

    @Autowired
    CAgreementsService agreementsService;

    @Autowired
    CAgreementsGroupsService cAgreementsGroupsService;

    @Autowired
    ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<GroupsAgreements> groupsAgreements = groupsAgreementsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(groupsAgreements), HttpStatus.OK);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        CAgreements agreements = new CAgreements();
        String clearAgreementName = StringUtils.stripAccents(node.get("agreementName").asText());
        String agreementNameClean = clearAgreementName.replaceAll("\\W", "").toUpperCase();
        if (!agreementsService.diferentAgreement(agreementNameClean)) {
            CAgreements cAgreements = new CAgreements();
            cAgreements.setAgreementName(node.get("agreementName").asText());
            cAgreements.setUploadedDate(LocalDateTime.now());
            cAgreements.setAgreementNameClean(agreementNameClean);
            cAgreements.setStatus(1);
            cAgreements.setIdAccessLevel(1);
            agreements = agreementsService.save(cAgreements);
        }

        GroupsAgreements groupAgreement = new GroupsAgreements();

        groupAgreement.setAgreement(agreements);
        groupAgreement.setAgreementGroup(cAgreementsGroupsService.findById(node.get("idAg").asInt()));
        groupAgreement.setIdAccessLevel(1);

        groupAgreement = groupsAgreementsService.save(groupAgreement);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(groupAgreement), HttpStatus.OK);
    }

    @RequestMapping(value = "/re-asign/{idGa}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reAsign (@PathVariable Integer idGa, @RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);

        GroupsAgreements groupAgreement = groupsAgreementsService.findById(idGa);
        groupAgreement.setAgreementGroup(cAgreementsGroupsService.findById(node.get("agreementGroup").get("idAg").asInt()));
        groupAgreement = groupsAgreementsService.update(groupAgreement);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(groupAgreement), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idGa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById (@PathVariable Integer idGa) throws IOException{
        GroupsAgreements groupsAgreements = groupsAgreementsService.findById(idGa);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(groupsAgreements), HttpStatus.OK);
    }
}

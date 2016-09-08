package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.RolesGroupAgreements;
import mx.bidg.service.RolesGroupAgreementsService;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 7/09/16.
 */
@Controller
@RequestMapping("roles-group-agreements")
public class RolesGroupAgreementsController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    RolesGroupAgreementsService rolesGroupAgreementsService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(rolesGroupAgreementsList), HttpStatus.OK);
    }

    @RequestMapping(value="/role/{idRole}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByGroupAgreement(@PathVariable Integer idRole)throws IOException{
        List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsService.findByRoles(idRole);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(rolesGroupAgreementsList),HttpStatus.OK);
    }

    @RequestMapping(value="/change-status", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeStatus(@RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);

        RolesGroupAgreements rolesGroupAgreements  = rolesGroupAgreementsService.findById(node.get("idRoleGroupAgreement").asInt());
        rolesGroupAgreements.setHasGroup(node.get("hasGroup").asBoolean());
        rolesGroupAgreements = rolesGroupAgreementsService.update(rolesGroupAgreements);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(rolesGroupAgreements), HttpStatus.OK);
    }
}

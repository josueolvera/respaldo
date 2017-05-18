package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Salvador on 15/03/2017.
 */
@Controller
@RequestMapping("authorizathion-costcenter")
public class AuthorizationCostCenterController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private AuthorizationCostCenterService authorizationCostCenterService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private CCostCenterService cCostCenterService;

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ResponseEntity<String> findAll() throws IOException{
        List<AuthorizationCostCenter> authorizationCostCenters = authorizationCostCenterService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenters), HttpStatus.OK);
    }

    @RequestMapping(value = "/cost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetsbyCC(@RequestParam(name = "cost_center", required = false) Integer idCostCenter,
                                                 @RequestParam(name = "year", required = false) Integer year) throws Exception {
        AuthorizationCostCenter authorizationCostCenter = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter, year);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenter));
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> setBudgetModify(@RequestParam(name = "cost_center", required = false) Integer idCostCenter,
                                                  @RequestParam(name = "year", required = false)Integer year,
                                                  HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        AuthorizationCostCenter a = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter,year);
        a.setcCostCenterStatus(CCostCenterStatus.SOLICITUD_MODIFICACION);
        a.setUsers(user);
        authorizationCostCenterService.update(a);
        EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_modify_required");
        emailTemplate.addProperty("costCenter",c);
        emailTemplate.addProperty("user",user);
        emailDeliveryService.deliverEmail(emailTemplate);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(a));
    }

    @RequestMapping(value ="/get-costcenter-rn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getCostCenterRejectedAndNotAuthorized (@RequestBody String data)throws IOException{

        JsonNode node = mapper.readTree(data);

        List<Integer> idsBussinessLines = new ArrayList<>();
        List<Integer> idsDistributors = new ArrayList<>();
        List<Integer> idsCCs = new ArrayList<>();

        for (JsonNode jsonNode : node.get("bussinessLinesSelected")){
            Integer idBussinessLine = jsonNode.get("idBusinessLine").asInt();
            idsBussinessLines.add(idBussinessLine);
        }

        for (JsonNode jsonNode : node.get("costCenterSelected")){
            Integer idCostCenter = jsonNode.get("idCostCenter").asInt();
            idsCCs.add(idCostCenter);
        }

        for (JsonNode jsonNode : node.get("distributorsSelected")){
            Integer idDistributor = jsonNode.get("idDistributor").asInt();
            idsDistributors.add(idDistributor);
        }


        List<Integer> idsCostCenters = distributorCostCenterService.getIdsCostCentersByBusinessLineDistributorCC(idsBussinessLines, idsDistributors, idsCCs);

        List<CCostCenter> costCenterList = new ArrayList<>();
        if (!idsCostCenters.isEmpty()){
            List<Integer> idsCostCentersNotAutorizhed = authorizationCostCenterService.getAllCostCentersRNAByIdsCostCenters(idsCostCenters);
            if (!idsCostCentersNotAutorizhed.isEmpty()){
                for (Integer idCostCenter : idsCostCentersNotAutorizhed){
                    CCostCenter costCenter = cCostCenterService.findById(idCostCenter);
                    costCenterList.add(costCenter);
                }
            }
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(costCenterList), HttpStatus.OK);
    }

    @RequestMapping(value = "/modify-request/{type}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> modifyRequest(@PathVariable Integer type, @RequestBody String data, HttpSession session) throws IOException{

        Users user = (Users) session.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        AuthorizationCostCenter authorizationCostCenter = authorizationCostCenterService.findByIdCostCenterAndYear(node.get("idCostCenter").asInt(), node.get("year").asInt());

        if (authorizationCostCenter != null){
            if (type == 1){
                authorizationCostCenter.setcCostCenterStatus(CCostCenterStatus.INICIAL);
                authorizationCostCenter.setAdjustment(1);
                authorizationCostCenterService.update(authorizationCostCenter);
                EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_modification_authorized");
                emailTemplate.addProperty("authorizationCostCenter", authorizationCostCenter);
                emailTemplate.addProperty("user", user);
                emailDeliveryService.deliverEmailWithUser(emailTemplate, authorizationCostCenter.getUsers());
            }else {
                authorizationCostCenter.setcCostCenterStatus(CCostCenterStatus.AUTORIZADA);
                authorizationCostCenterService.update(authorizationCostCenter);
                EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_modification_reject");
                emailTemplate.addProperty("authorizationCostCenter", authorizationCostCenter);
                emailTemplate.addProperty("user", user);
                emailDeliveryService.deliverEmailWithUser(emailTemplate, authorizationCostCenter.getUsers());
            }
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenter), HttpStatus.OK);
    }

    @RequestMapping(value = "/role/{idRole}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ResponseEntity<String> findByRole(@PathVariable Integer idRole) throws IOException{
        List<AuthorizationCostCenter> authorizationCostCenters = authorizationCostCenterService.findByRoleCostCenter(idRole);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenters), HttpStatus.OK);
    }

}

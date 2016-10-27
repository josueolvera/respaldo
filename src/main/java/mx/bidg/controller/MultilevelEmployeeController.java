/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javafx.scene.media.Media;
import javax.xml.ws.Response;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBranchs;
import mx.bidg.model.Employees;
import mx.bidg.model.MultilevelEmployee;
import mx.bidg.service.CBranchsService;
import mx.bidg.service.EmployeesService;
import mx.bidg.service.MultilevelEmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Cristhian de la cruz
 */
@Controller
@RequestMapping("multilevel-employee")
public class MultilevelEmployeeController {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    MultilevelEmployeeService multilevelEmployeeService;
    
    @Autowired
    EmployeesService employeesService;
    
    @Autowired
    CBranchsService cbranchService;
    
    
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
    
        List<MultilevelEmployee> multilevelEmployees = multilevelEmployeeService.findAll();
        
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(multilevelEmployees), HttpStatus.OK);
    }
    
    @RequestMapping(value="/new-multilevel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<String> save(@RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);
        
        for(JsonNode jNode : node.get("arrayAdvisers")){
            MultilevelEmployee multiemployee = new MultilevelEmployee();
        multiemployee.setLevel(1);
        multiemployee.setIdEmployeeMultilevel(node.get("supervisor").get("idEmployee").asInt());
        multiemployee.setBranchs(new CBranchs(node.get("supervisor").get("dwEnterprisesR").get("branch").get("idBranch").asInt()));
        multiemployee.setEmployees(new Employees(jNode.get("employee").get("idEmployee").asInt()));
        multilevelEmployeeService.save(multiemployee);            
        }
        
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(multilevelEmployeeService.findAll()), HttpStatus.OK); 
    }
    
}

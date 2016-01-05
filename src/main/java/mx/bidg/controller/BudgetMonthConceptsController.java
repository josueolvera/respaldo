/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.service.BudgetMonthConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-month-concepts")
public class BudgetMonthConceptsController {
    
    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;
    
    ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudgetMonthConceptsList(@RequestBody String data) throws Exception {
       
        System.out.println(data);
        
        List<BudgetMonthConcepts> list = budgetMonthConceptsService.saveList(data);
        
//        return new ResponseEntity<>(map.writerWithView(JsonViews.EmbeddedBudget.class).writeValueAsString(list), HttpStatus.OK);
        return new ResponseEntity<>("Concepto(s) guardado(s) con éxito", HttpStatus.OK);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import java.io.IOException;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.BudgetMonthConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-month-concepts")
public class BudgetMonthConceptsController {
    
    @Autowired
    private BudgetMonthConceptsService budgetMonthConceptsService;
    
    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/{idConcept}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByConcept(@PathVariable int idConcept) throws IOException {
        List<BudgetMonthConcepts> list = budgetMonthConceptsService.findByConcept(new CBudgetConcepts(idConcept));
        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK
        );
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveBudgetMonthConceptsList(@RequestBody String data) throws Exception {
        
        List<BudgetMonthConcepts> list = budgetMonthConceptsService.saveList(data);
        return new ResponseEntity<>("Concepto(s) guardado(s) con éxito", HttpStatus.OK);
    }
    
}

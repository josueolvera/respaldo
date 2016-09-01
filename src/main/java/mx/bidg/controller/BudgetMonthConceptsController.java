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
import mx.bidg.model.Users;
import mx.bidg.service.BudgetMonthConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-month-concepts")
public class BudgetMonthConceptsController {
    
    @Autowired
    private BudgetMonthConceptsService budgetMonthConceptsService;
    
    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/{idConcept}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByConcept(@PathVariable int idConcept) throws IOException {
        List<BudgetMonthConcepts> list = budgetMonthConceptsService.findByConcept(new CBudgetConcepts(idConcept));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK
        );
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveBudgetMonthConceptsList(@RequestBody String data, HttpSession httpSession) throws Exception {
        Users user = (Users) httpSession.getAttribute("user");
        List<BudgetMonthConcepts> list = budgetMonthConceptsService.saveList(data, user);
        return ResponseEntity.ok("Concepto(s) guardado(s) con éxito");
    }
    
}

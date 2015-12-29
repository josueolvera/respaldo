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
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.CBudgetConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-concepts")
public class CBudgetConceptsController {
    
    @Autowired
    CBudgetConceptsService budgetConceptsService;
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(value = "/budget/{idBudget}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> getByBudget(@PathVariable int idBudget) throws Exception {
        List<CBudgetConcepts> list = budgetConceptsService.findByBudget(new Budgets(idBudget));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class)
                .writeValueAsString(list), HttpStatus.OK);
    }
    
}

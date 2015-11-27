/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetMonth;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CGroups;
import mx.bidg.model.CMonths;
import mx.bidg.service.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budgets")
public class BudgetController { 
    
    @Autowired
    BudgetsService budgetsService;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudget(@RequestBody String data) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
                
        Budgets budget = new Budgets();
        
        budget.setGroup(new CGroups(jsonRequest.get("group").asInt()));
        budget.setArea(new CAreas(jsonRequest.get("area").asInt()));
        budget.setBudgetCategory(new CBudgetCategories(jsonRequest.get("category").asInt()));
        budget.setBudgetSubcategory(new CBudgetSubcategories(jsonRequest.get("subcategory").asInt()));
        budget.setYear(jsonRequest.get("year").asInt());
        
        ArrayList<BudgetMonth> budgetMonthList = new ArrayList<>();
        BudgetMonth budgetMonth;
        
        for(JsonNode jsonBudgetMonth : jsonRequest.get("budgetMonthList")) {
            budgetMonth = new BudgetMonth();
            
            ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
            BudgetMonthConcepts budgetMonthConcept;
            
            for(JsonNode jsonBudgetMonthConcept : jsonBudgetMonth.get("budgetMonthConceptList")) {
                budgetMonthConcept = new BudgetMonthConcepts();
                budgetMonthConcept.setAmount(jsonBudgetMonthConcept.get("amountConcept").decimalValue());
                budgetMonthConcept.setIdBudgetMonth(budgetMonth);
                budgetMonthConcept.setIdBudgetConcept(new CBudgetConcepts(jsonBudgetMonthConcept.get("budgetConcept").asInt()));
                budgetMonthConcept.setIdAccessLevel(1);
                budgetMonthConceptsList.add(budgetMonthConcept);
            }
            
            budgetMonth.setBudgetMonthConceptsList(budgetMonthConceptsList);
            budgetMonth.setAmount(jsonBudgetMonth.get("amountMonth").decimalValue());
            budgetMonth.setIdBudget(budget);
            budgetMonth.setIdMonth(new CMonths(jsonBudgetMonth.get("cMonth").asInt()));
            budgetMonth.setIdAccessLevel(1);
            budgetMonthList.add(budgetMonth);
        }
        
        budget.setIdAccessLevel(1);
        budget.setBudgetMonthList(budgetMonthList);
        budgetsService.saveBudget(budget);
        
        return new ResponseEntity<>("Presupuesto guardado con éxito", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{idGroup}/{idArea}/{idCategory}/{idSubcategory}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> getByCombination(@PathVariable int idGroup, @PathVariable int idArea, 
            @PathVariable int idCategory, @PathVariable int idSubcategory) throws Exception {
        
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idCategory, idSubcategory);
        System.out.println(budget);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Root.class).writeValueAsString(budget), HttpStatus.OK);
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.model.BudgetMonth;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CMonths;
import mx.bidg.service.BudgetMonthService;
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
@RequestMapping("/budget-month")
public class BudgetMonthController {
    
    @Autowired
    BudgetMonthService budgetMonthService;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetMonths() {
        List<BudgetMonth> list;
        return null;
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudgetMonths(@RequestBody String data) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
        BudgetMonth budgetMonth = new BudgetMonth();
        
        ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
        BudgetMonthConcepts budgetMonthConcept;

        for(JsonNode jsonBudgetMonthConcept : jsonRequest.get("budgetMonthConceptList")) {
            budgetMonthConcept = new BudgetMonthConcepts();
            budgetMonthConcept.setAmount(jsonBudgetMonthConcept.get("amountConcept").decimalValue());
            budgetMonthConcept.setIdBudgetMonth(budgetMonth);
            budgetMonthConcept.setIdBudgetConcept(new CBudgetConcepts(jsonBudgetMonthConcept.get("budgetConcept").asInt()));
            budgetMonthConcept.setIdAccessLevel(1);
            budgetMonthConceptsList.add(budgetMonthConcept);
        }
            
        budgetMonth.setBudgetMonthConceptsList(budgetMonthConceptsList);
        budgetMonth.setIdBudget(new Budgets(jsonRequest.get("budget").asInt()));
        budgetMonth.setIdMonth(new CMonths(jsonRequest.get("month").asInt()));
        budgetMonth.setAmount(jsonRequest.get("amountMonth").decimalValue());
        budgetMonth.setYear(jsonRequest.get("year").asInt());
        budgetMonth.setIdAccessLevel(1);
        budgetMonthService.saveBudgetMonth(budgetMonth);
        
        return new ResponseEntity<>("Presupuesto guardado con éxito", HttpStatus.OK);
    }
    
}

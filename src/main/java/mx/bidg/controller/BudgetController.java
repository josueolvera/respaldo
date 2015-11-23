/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import mx.bidg.model.BudgetMonth;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetAreas;
import mx.bidg.model.CBudgetPeriods;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.model.CMonths;
import mx.bidg.service.BudgetsService;
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
@RequestMapping("/budgets")
public class BudgetController { 
    
    @Autowired
    BudgetsService budgetsService;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudget(@RequestBody String data) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
                
        Budgets budget = new Budgets();
        budget.setIdBudgetArea(new CBudgetAreas(jsonRequest.get("budgetArea").asInt()));
        budget.setIdBudgetPeriod(new CBudgetPeriods(jsonRequest.get("budgetPeriod").asInt()));
        budget.setIdBudgetType(new CBudgetTypes(jsonRequest.get("budgetType").asInt()));
        budget.setYear(jsonRequest.get("year").asInt());
        
        ArrayList<BudgetMonth> budgetMonthList = new ArrayList<>();
        BudgetMonth budgetMonth;
        
        for(JsonNode jsonBudgetMonth : jsonRequest.get("budgetMonthList")) {
            budgetMonth = new BudgetMonth();
            budgetMonth.setAmount(jsonBudgetMonth.get("amount").decimalValue());
            budgetMonth.setIdBudget(budget);
            budgetMonth.setIdMonth(new CMonths(jsonBudgetMonth.get("cMonth").asInt()));
            budgetMonthList.add(budgetMonth);
        }
        
        budget.setIdAccessLevel(1);
        budget.setBudgetMonthList(budgetMonthList);
        budget.setIdRegTable(jsonRequest.get("idReg").asInt());
        budgetsService.saveBudget(budget);
        
        return new ResponseEntity<>("Presupuesto guardado con éxito", HttpStatus.OK);
    }
    
}

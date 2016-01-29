/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.service.BudgetMonthBranchService;
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
@RequestMapping("/budget-month-branch")
public class BudgetMonthBranchController {
    
    @Autowired
    BudgetMonthBranchService budgetMonthBranchService;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(value = "/request", headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> getFromRequest(@RequestBody String data) throws Exception {
        
        BudgetMonthBranch budgetMonthBranch = budgetMonthBranchService.findFromRequest(data);
        
        if(budgetMonthBranch == null) {
            return new ResponseEntity<>("Error al guardar la solicitud", HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity<>(map.writerWithView(JsonViews.Root.class).writeValueAsString(budgetMonthBranch), HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/authorize", method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody String authorize(@RequestBody String data) throws Exception {
        return budgetMonthBranchService.authorizeBudget(data);
    }
    
}

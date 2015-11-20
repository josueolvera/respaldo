/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.model.BudgetMonth;
import mx.bidg.service.BudgetMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
}

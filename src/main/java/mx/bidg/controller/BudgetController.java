/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBudgetAreas;
import mx.bidg.model.CBudgetPeriods;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.service.CBudgetAreasService;
import mx.bidg.service.CBudgetPeriodsService;
import mx.bidg.service.CBudgetTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budgets")
public class BudgetController { 
    
    @Autowired
    CBudgetAreasService cbas;
    
    @Autowired
    CBudgetPeriodsService cbps;
    
    @Autowired
    CBudgetTypesService cbts;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(value = "/get-budget-areas", produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetAreas() throws Exception {
        List<CBudgetAreas> list = cbas.findAll();
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
    @RequestMapping(value = "/get-budget-periods", produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetPeriods() throws Exception {
        List<CBudgetPeriods> list = cbps.findAll();
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
    @RequestMapping(value = "/get-budget-types", produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetPTypes() throws Exception {
        List<CBudgetTypes> list = cbts.findAll();
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
}

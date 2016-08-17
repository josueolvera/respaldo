/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBudgetPeriods;
import mx.bidg.service.CBudgetPeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-periods")
public class CBudgetPerdiodsController {
    
    @Autowired
    CBudgetPeriodsService cBudgetPeriodsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetPeriods() throws Exception {
        List<CBudgetPeriods> list = cBudgetPeriodsService.findAll();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
}

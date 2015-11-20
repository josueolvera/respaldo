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
import mx.bidg.service.CBudgetAreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-areas")
public class CBudgetAreasController {
    
    @Autowired
    CBudgetAreasService cBudgetAreasService;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetAreas() throws Exception {
        List<CBudgetAreas> list = cBudgetAreasService.findAll();
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
}

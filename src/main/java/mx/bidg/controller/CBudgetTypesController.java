/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.service.CBudgetTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-types")
public class CBudgetTypesController {
    
    @Autowired
    CBudgetTypesService cBudgetTypesService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetTypes() throws Exception {
        List<CBudgetTypes> list = cBudgetTypesService.findAll();
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.RootExtras.class).writeValueAsString(list));
    }
    
}

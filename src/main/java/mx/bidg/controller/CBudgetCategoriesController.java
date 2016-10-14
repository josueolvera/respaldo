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
import mx.bidg.model.CBudgetCategories;
import mx.bidg.service.CBudgetCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-categories")
public class CBudgetCategoriesController {
    
    @Autowired
    CBudgetCategoriesService cBudgetCategoriesService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getCBudgetCategories(
            @RequestParam(name = "cost_center", required = false) Integer idCostCenter,
            @RequestParam(name = "request_category", required = false) Integer idRequestCategory
    ) throws Exception {
        return ResponseEntity.ok(
                mapper.writerWithView(JsonViews.Root.class)
                        .writeValueAsString(
                                cBudgetCategoriesService.getRequestCategories(idCostCenter, idRequestCategory)
                        )
        );
    }
    
    @RequestMapping(value="/request", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String getCBudgetCategoriesofRequest() throws Exception {
        List<CBudgetCategories> list = cBudgetCategoriesService.findAllRequest();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
}

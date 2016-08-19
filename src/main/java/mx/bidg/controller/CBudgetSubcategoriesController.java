/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.service.CBudgetSubcategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-subcategories")
public class CBudgetSubcategoriesController {
    
    @Autowired
    CBudgetSubcategoriesService cBudgetSubcategoriesService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String getCBudgetSubcategories() throws Exception {
        List<CBudgetSubcategories> list = cBudgetSubcategoriesService.findAll();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }

    @RequestMapping(value = "/category/{idBudgetCategory}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String getByBudgetCategory(@PathVariable Integer idBudgetCategory) throws Exception {
        List<CBudgetSubcategories> list = cBudgetSubcategoriesService.getByBudgetCategory(idBudgetCategory);
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
}

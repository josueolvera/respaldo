/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.service.BudgetYearConceptService;
import mx.bidg.service.BudgetMonthConceptsService;
import mx.bidg.service.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-year-concept")
public class BudgetYearConceptController {
    
    @Autowired
    BudgetYearConceptService budgetYearConceptService;
    
    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;
    
    @Autowired
    private BudgetsService budgetsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(value = "/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getFromRequest(@RequestBody String data) throws Exception {
        
        BudgetYearConcept budgetYearConcept = budgetYearConceptService.findFromRequest(data);
        
        if(budgetYearConcept == null) {
            return new ResponseEntity<>("Error al guardar la solicitud", HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(budgetYearConcept), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idBudgetYearConcept}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer idBudgetYearConcept) throws Exception {
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(budgetYearConceptService.delete(idBudgetYearConcept)));
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String authorize(@RequestBody String data) throws Exception {
        return budgetYearConceptService.authorizeBudget(data);
    }

    @RequestMapping(value = "/copy-budget", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> copyBudget(
            @RequestParam(name = "cost_center", required = false) Integer idCostCenter,
            @RequestParam(name = "budget_type", required = false) Integer idBudgetType,
            @RequestParam(name = "nature", required = false) Integer idBudgetNature,
            @RequestParam(name = "category", required = false) Integer idBudgetCategory,
            @RequestParam("year_from_copy") Integer yearFromCopy,
            @RequestParam("year_to_copy") Integer yearToCopy,
            @RequestParam(name = "overwrite", required = false, defaultValue = "false") Boolean overwrite,
            HttpSession httpSession
    ) throws Exception {
        Users user = (Users) httpSession.getAttribute("user");

        List<BudgetYearConcept> budgetYearConceptList = budgetYearConceptService.copyBudget(
                idCostCenter,
                idBudgetType,
                idBudgetCategory,
                yearFromCopy,
                yearToCopy,
                overwrite,
                idBudgetNature,
                user
        );

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetYearConceptList));
    }
    
}

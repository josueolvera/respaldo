/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CGroups;
import mx.bidg.model.CMonths;
import mx.bidg.pojos.BudgetPojo;
import mx.bidg.pojos.ConceptMonthPojo;
import mx.bidg.pojos.ConceptPojo;
import mx.bidg.pojos.TotalMonthPojo;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.CBudgetConceptsService;
import mx.bidg.service.CMonthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-concepts")
public class CBudgetConceptsController {
    
    @Autowired
    CBudgetConceptsService budgetConceptsService;
    
    @Autowired
    BudgetsService budgetsService;
    
    @Autowired
    CMonthsService monthsService;
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(value = "/group-area/{idGroup}/{idArea}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> getByGroupArea(@PathVariable int idGroup, @PathVariable int idArea) 
            throws Exception {
        
        List<BudgetPojo> list = new ArrayList<>();
        List<ConceptMonthPojo> conceptMonthPojoList;
        List<TotalMonthPojo> totalMonthPojoList;
        List<ConceptPojo> conceptPojoList;
        BudgetPojo budgetPojo;
        ConceptPojo conceptPojo;
        ConceptMonthPojo conceptMonthPojo;
        TotalMonthPojo totalMonthPojo;
        CMonths cMonth;
        List<Budgets> budgetList =  budgetsService.findByGroupArea(new CGroups(idGroup), new CAreas(idArea));
        
        for(Budgets budget: budgetList) {
            
            List<CBudgetConcepts> conceptsList = budgetConceptsService.findByBudget(budget);
            if(!conceptsList.isEmpty()) {
                
                conceptPojoList = new ArrayList<>();
                totalMonthPojoList = new ArrayList<>();
                
                for(CBudgetConcepts budgetConcept : conceptsList) {
                    
                    conceptMonthPojoList = new ArrayList<>();
                    totalMonthPojoList = new ArrayList<>();
                    List<BudgetMonthConcepts> budgetMonthConceptList = budgetConcept.getBudgetMonthConceptsList();
                    conceptPojo = new ConceptPojo();
                    conceptPojo.setIdConcept(budgetConcept.getIdBudgetConcept());
                    conceptPojo.setIdBudget(budget.getIdBudget());
                    conceptPojo.setConceptName(budgetConcept.getBudgetConcept());
                    conceptPojo.setTotal(BigDecimal.ZERO);
                    conceptPojo.setEquals(false);
                    
                    for(BudgetMonthConcepts budgetMonthConcept : budgetMonthConceptList) {
                        
                        BudgetMonthBranch budgetMonthBranch = budgetMonthConcept.getIdBudgetMonthBranch();
                        cMonth = monthsService.findById(budgetMonthBranch.getMonth());
                        
                        conceptMonthPojo = new ConceptMonthPojo(
                                cMonth.getIdMonth(),
                                cMonth.getMonth(),
                                budgetMonthConcept.getAmount()
                        );
                        
                        totalMonthPojo = new TotalMonthPojo(
                                cMonth.getIdMonth(), 
                                budgetMonthBranch.getAmount());
                        
                        conceptMonthPojoList.add(conceptMonthPojo);
                        totalMonthPojoList.add(totalMonthPojo);
                        conceptPojo.setDwEnterprise(budgetMonthBranch.getIdDwEnterprise().getIdDwEnterprise());
                        conceptPojo.setYear(budgetMonthBranch.getYear());
                    }
                    
                    conceptPojo.setConceptMonth(conceptMonthPojoList);
                    conceptPojoList.add(conceptPojo);
                    
                }
                
                budgetPojo = new BudgetPojo(
                        budget.getIdBudget(),
                        budget.getIdGroup(),
                        budget.getIdArea(),
                        budget.getIdBudgetCategory(),
                        budget.getIdBudgetSubcategory(),
                        conceptPojoList,
                        totalMonthPojoList
                );
                budgetPojo.setGranTotal(BigDecimal.ZERO);
                
                list.add(budgetPojo);
            }            
            
        }
        
//        List<List<CBudgetConcepts>> list = new ArrayList<>();
//        List<Budgets> budgetList =  budgetsService.findByGroupArea(new CGroups(idGroup), new CAreas(idArea));
//        
//        for(Budgets budget: budgetList) {
//            List<CBudgetConcepts> conceptsList = budgetConceptsService.findByBudget(budget);
//            if(!conceptsList.isEmpty())
//                list.add(conceptsList);
//        }
//
//        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class)
//                .writeValueAsString(list), HttpStatus.OK);
        
        return new ResponseEntity<>(mapper.writeValueAsString(list), HttpStatus.OK);
        
    }
    
}

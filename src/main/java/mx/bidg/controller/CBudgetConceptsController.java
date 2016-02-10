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
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(value = "/group-area/{idGroup}/{idArea}/{idDwEnterprise}/{year}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<String> getByGroupArea(@PathVariable int idGroup, @PathVariable int idArea, 
            @PathVariable Integer idDwEnterprise, @PathVariable int year) throws Exception {
        
        List<BudgetPojo> list = new ArrayList<>();
        List<ConceptMonthPojo> conceptMonthPojoList;
        List<TotalMonthPojo> totalMonthPojoList;
        List<ConceptPojo> conceptPojoList;
        BudgetPojo budgetPojo;
        ConceptPojo conceptPojo;
        ConceptMonthPojo conceptMonthPojo;
        TotalMonthPojo totalMonthPojo;
        CMonths cMonth;
        List<Budgets> budgetList =  budgetsService.findByGroupAreaEnterprise(new CGroups(idGroup), new CAreas(idArea), idDwEnterprise);
        
        for(Budgets budget: budgetList) {
            
            List<CBudgetConcepts> conceptsList = budgetConceptsService.findByBudget(budget, year);
            if(!conceptsList.isEmpty()) {
                
                conceptPojoList = new ArrayList<>();
                totalMonthPojoList = new ArrayList<>();
                budgetPojo = new BudgetPojo();
                
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
                        
                        BudgetMonthBranch budgetMonthBranch = budgetMonthConcept.getBudgetMonthBranch();
                        cMonth = monthsService.findById(budgetMonthBranch.getIdMonth());
                        
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
                        conceptPojo.setDwEnterprise(budgetMonthBranch.getDwEnterprise().getIdDwEnterprise());
                        conceptPojo.setYear(budgetMonthBranch.getYear());
                        budgetPojo.setYear(budgetMonthBranch.getYear());
                        budgetPojo.setIsAuthorized(budgetMonthBranch.getIsAuthorized());
                    }
                    
                    conceptPojo.setConceptMonth(conceptMonthPojoList);
                    conceptPojoList.add(conceptPojo);
                    
                }
                
                budgetPojo.setIdBudget(budget.getIdBudget());
                budgetPojo.setIdGroup(budget.getIdGroup());
                budgetPojo.setIdArea(budget.getIdArea());
                budgetPojo.setIdBudgetCategory(budget.getIdBudgetCategory());
                budgetPojo.setIdBudgetSubcategory(budget.getIdBudgetSubcategory());
                budgetPojo.setConceptos(conceptPojoList);
                budgetPojo.setTotalMonth(totalMonthPojoList);
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
    
    
    @RequestMapping(value = "/{idConcept}", method = RequestMethod.DELETE, produces = "application/jeson")
    public @ResponseBody ResponseEntity<String> delete(@PathVariable int idConcept) throws Exception {
        
        CBudgetConcepts budgetConcept = new CBudgetConcepts(idConcept);
        if(budgetConceptsService.delete(budgetConcept)) {
            return new ResponseEntity<>("El concepto se elimino correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Hubo un problema al eliminar el concepto", HttpStatus.CONFLICT);
        }
        
    }
    
}

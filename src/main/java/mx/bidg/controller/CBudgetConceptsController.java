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
import org.springframework.http.MediaType;
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

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(value = "/group-area/{idDistributor}/{idArea}/{idDwEnterprise}/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByDistributorArea(@PathVariable int idDistributor, @PathVariable int idArea,
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
        List<Budgets> budgetList =  budgetsService.findByDistributorAreaAndEnterprise(idDistributor, idArea, idDwEnterprise);
        
        for(Budgets budget: budgetList) {

            List<CBudgetConcepts> conceptsList = budgetConceptsService.findByBudgetEnterprise(budget, year, idDwEnterprise);
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
                budgetPojo.setIdDistributor(budget.getIdDistributor());
                budgetPojo.setIdArea(budget.getIdArea());
                budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
                budgetPojo.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
                budgetPojo.setConceptos(conceptPojoList);
                budgetPojo.setTotalMonth(totalMonthPojoList);
                budgetPojo.setGranTotal(BigDecimal.ZERO);
                
                list.add(budgetPojo);
            }            
            
        }
        
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

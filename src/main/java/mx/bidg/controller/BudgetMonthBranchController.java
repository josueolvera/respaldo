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
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;
import mx.bidg.service.BudgetMonthBranchService;
import mx.bidg.service.BudgetMonthConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-month-branch")
public class BudgetMonthBranchController {
    
    @Autowired
    BudgetMonthBranchService budgetMonthBranchService;
    
    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(value = "/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getFromRequest(@RequestBody String data) throws Exception {
        
        BudgetMonthBranch budgetMonthBranch = budgetMonthBranchService.findFromRequest(data);
        
        if(budgetMonthBranch == null) {
            return new ResponseEntity<>("Error al guardar la solicitud", HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(budgetMonthBranch), HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/authorize", method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody String authorize(@RequestBody String data) throws Exception {
        return budgetMonthBranchService.authorizeBudget(data);
    }
    
    @RequestMapping(value = "/find/{idDwEnterprise}/{fromYear}/{toYear}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findDwAndYear(@PathVariable int idDwEnterprise, @PathVariable int fromYear, @PathVariable int toYear) throws Exception {
        List<BudgetMonthBranch> budgetMonthBranchs = budgetMonthBranchService.findByDWEnterpriseAndYear(idDwEnterprise, fromYear);
        
        for (BudgetMonthBranch bmb : budgetMonthBranchs) {
            BudgetMonthBranch budgetMonth = new BudgetMonthBranch();
            budgetMonth.setAmount(bmb.getAmount());
            budgetMonth.setExpendedAmount(bmb.getExpendedAmount());
            budgetMonth.setYear(toYear);
            budgetMonth.setBudget(new Budgets(bmb.getIdBudget()));
            budgetMonth.setMonth(new CMonths(bmb.getIdMonth()));
//            budgetMonth.setDwEnterprise(new DwEnterprises(bmb.getIdDwEnterprise()));
            budgetMonth.setCurrency(new CCurrencies(bmb.getIdCurrency()));
            budgetMonth.setIdAccessLevel(1);
            budgetMonth.setIsAuthorized(0);
            BudgetMonthBranch bmb1= budgetMonthBranchService.saveBudgetMonthBranch(budgetMonth);
            
            for (BudgetMonthConcepts bmc : bmb.getBudgetMonthConceptsList()) {
               BudgetMonthConcepts bmc1 = new BudgetMonthConcepts();
               bmc1.setAmount(bmc.getAmount());
               bmc1.setIdAccessLevel(1);
               bmc1.setBudgetMonthBranch(bmb1);
               bmc1.setBudgetConcept(new CBudgetConcepts(bmc.getIdBudgetConcept()));
               bmc1.setCurrency(new CCurrencies(bmc.getIdCurrency()));
               budgetMonthConceptsService.saveBudgetMonthConcepts(bmc1);
            }
            
        }
        
        return mapper.writeValueAsString(budgetMonthBranchs);
    }
    
}

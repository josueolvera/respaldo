/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.pojos.BudgetPojo;
import mx.bidg.service.BudgetsService;
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
@RequestMapping("/budgets")
public class BudgetController { 
    
    @Autowired
    BudgetsService budgetsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudget(@RequestBody String data) throws Exception {
        
        JsonNode json = mapper.readTree(data);
                
        Budgets budget = new Budgets();
        
//        budget.setArea(new CAreas(json.get("area").asInt()));
//        budget.setBudgetCategory(new CBudgetCategories(json.get("category").asInt()));
//        budget.setBudgetSubcategory(new CBudgetSubcategories(json.get("subcategory").asInt()));
        
        ArrayList<BudgetMonthBranch> budgetMonthBranchList = new ArrayList<>();
        BudgetMonthBranch budgetMonthBranch;
        
        for(JsonNode jsonRequest : json.get("budgetMonthBranchList")) {
            
            budgetMonthBranch = new BudgetMonthBranch();
            ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
            BudgetMonthConcepts budgetMonthConcept;

            for(JsonNode jsonBudgetMonthConcept : jsonRequest.get("budgetMonthConceptList")) {
                budgetMonthConcept = new BudgetMonthConcepts();
                budgetMonthConcept.setAmount(jsonBudgetMonthConcept.get("amountConcept").decimalValue());
                budgetMonthConcept.setBudgetConcept(new CBudgetConcepts(jsonBudgetMonthConcept.get("budgetConcept").asInt()));
                budgetMonthConcept.setIdAccessLevel(1);
                budgetMonthConcept.setBudgetMonthBranch(budgetMonthBranch);
                budgetMonthConceptsList.add(budgetMonthConcept);
            }

            budgetMonthBranch.setBudgetMonthConceptsList(budgetMonthConceptsList);
            budgetMonthBranch.setBudget(new Budgets(jsonRequest.get("budget").asInt()));
            budgetMonthBranch.setMonth(new CMonths(jsonRequest.get("month").asInt()));
            budgetMonthBranch.setDwEnterprise(new DwEnterprises(jsonRequest.get("dwEnterprise").asInt()));
            budgetMonthBranch.setAmount(jsonRequest.get("amountMonth").decimalValue());
            budgetMonthBranch.setExpendedAmount(jsonRequest.get("expendedAmount").decimalValue());
            budgetMonthBranch.setYear(jsonRequest.get("year").asInt());
            budgetMonthBranch.setIdAccessLevel(1);
            
        }
        
        budget.setIdAccessLevel(1);
        budget.setBudgetMonthBranchList(budgetMonthBranchList);
        budgetsService.saveBudget(budget);
        
        return new ResponseEntity<>("Presupuesto guardado con éxito", HttpStatus.OK);
    }
    
   /* @RequestMapping(value = "/{idGroup}/{idArea}/{idCategory}/{idSubcategory}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> getByCombination(@PathVariable int idGroup, @PathVariable int idArea, 
            @PathVariable int idCategory, @PathVariable int idSubcategory) throws Exception {
        
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idCategory, idSubcategory);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(budget), HttpStatus.OK);
    } 
        Revisar este controller ya que la estructura cambio de categoria y subcategoria a cuenta contable
    */
    
    @RequestMapping(value = "/{idGroup}/{idArea}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> getByGroupArea(@PathVariable int idGroup, @PathVariable int idArea) 
            throws Exception {
        
        ArrayList<Budgets> list = budgetsService.findByGroupArea(new CGroups(idGroup), new CAreas(idArea));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor/{idDistributor}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByDistributor(@PathVariable Integer idDistributor) throws Exception {
        List<Budgets> budgets = budgetsService.findByDistributor(idDistributor);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgets), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor/{idDistributor}/area/{idArea}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByDistributor(@PathVariable Integer idDistributor, @PathVariable Integer idArea) throws Exception {
        List<Budgets> budgets = budgetsService.findByDistributorAndArea(idDistributor, idArea);
        List<BudgetPojo> budgetPojos = new ArrayList<>();

        for (Budgets budget : budgets) {
            BudgetPojo budgetPojo = new BudgetPojo();
//            budgetPojo.setIdDistributor(budget.getIdDistributor());
//            budgetPojo.setIdArea(budget.getIdArea());
            budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
            budgetPojo.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
            budgetPojo.setIdBudget(budget.getIdBudget());
            budgetPojo.setBudgetCategory(budget.getAccountingAccount().getBudgetCategory());
            budgetPojo.setBudgetSubcategory(budget.getAccountingAccount().getBudgetSubcategory());
            budgetPojos.add(budgetPojo);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetPojos), HttpStatus.OK);
    }
    
}

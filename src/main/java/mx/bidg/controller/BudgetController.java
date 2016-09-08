/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.pojos.*;
import mx.bidg.service.BudgetYearConceptService;
import mx.bidg.service.BudgetMonthConceptsService;
import mx.bidg.service.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    BudgetYearConceptService budgetYearConceptService;

    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudget(@RequestBody String data) throws Exception {
        
        JsonNode json = mapper.readTree(data);
                
        Budgets budget = new Budgets();
        
//        budget.setArea(new CAreas(json.get("area").asInt()));
//        budget.setBudgetCategory(new CBudgetCategories(json.get("category").asInt()));
//        budget.setBudgetSubcategory(new CBudgetSubcategories(json.get("subcategory").asInt()));
        
        ArrayList<BudgetYearConcept> budgetYearConceptList = new ArrayList<>();
        BudgetYearConcept budgetYearConcept;
        
        for(JsonNode jsonRequest : json.get("budgetYearConceptList")) {
            
            budgetYearConcept = new BudgetYearConcept();
            ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
            BudgetMonthConcepts budgetMonthConcept;

            for(JsonNode jsonBudgetMonthConcept : jsonRequest.get("budgetMonthConceptList")) {
                budgetMonthConcept = new BudgetMonthConcepts();
                budgetMonthConcept.setAmount(jsonBudgetMonthConcept.get("amountConcept").decimalValue());
                budgetMonthConcept.setBudgetConcept(new CBudgetConcepts(jsonBudgetMonthConcept.get("budgetConcept").asInt()));
                budgetMonthConcept.setIdAccessLevel(1);
//                budgetMonthConcept.setBudgetYearConcept(budgetYearConcept);
                budgetMonthConceptsList.add(budgetMonthConcept);
            }

//            budgetYearConcept.setBudgetMonthConceptsList(budgetMonthConceptsList);
            budgetYearConcept.setBudget(new Budgets(jsonRequest.get("budget").asInt()));
//            budgetYearConcept.setMonth(new CMonths(jsonRequest.get("month").asInt()));
//            budgetYearConcept.setDwEnterprise(new DwEnterprises(jsonRequest.get("dwEnterprise").asInt()));
//            budgetYearConcept.setAmount(jsonRequest.get("amountMonth").decimalValue());
//            budgetYearConcept.setExpendedAmount(jsonRequest.get("expendedAmount").decimalValue());
            budgetYearConcept.setYear(jsonRequest.get("year").asInt());
            budgetYearConcept.setIdAccessLevel(1);
            
        }
        
        budget.setIdAccessLevel(1);
        budget.setBudgetYearConceptList(budgetYearConceptList);
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
            budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
            budgetPojo.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
            budgetPojo.setIdBudget(budget.getIdBudget());
            budgetPojo.setBudgetCategory(budget.getAccountingAccount().getBudgetCategory());
            budgetPojo.setBudgetSubcategory(budget.getAccountingAccount().getBudgetSubcategory());
            budgetPojos.add(budgetPojo);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetPojos), HttpStatus.OK);
    }

    @RequestMapping(value = "/cost-center/{idCostCenter}/budget-type/{idBudgetType}/budget-nature/{idBudgetNature}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgets(@PathVariable Integer idCostCenter, @PathVariable Integer idBudgetType, @PathVariable Integer idBudgetNature) throws Exception {
//        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature);
//        List<BudgetPojo> budgetPojos = new ArrayList<>();
//
//        for (Budgets budget : budgets) {
//            BudgetPojo budgetPojo = new BudgetPojo();
//            budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
//            budgetPojo.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
//            budgetPojo.setIdCostCenter(budget.getIdCostCenter());
//            budgetPojo.setIdBudgetType(budget.getIdBudgetType());
//            budgetPojo.setIdBudgetNature(budget.getIdBudgetNature());
//            budgetPojo.setIdBudget(budget.getIdBudget());
//            budgetPojo.setBudgetCategory(budget.getAccountingAccount().getBudgetCategory());
//            budgetPojo.setBudgetSubcategory(budget.getAccountingAccount().getBudgetSubcategory());
//            budgetPojos.add(budgetPojo);
//        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(""));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public ResponseEntity<String> getBudgetByCategory(
            @RequestParam("cost_center") Integer idCostCenter,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "category", required = false) Integer idBudgetCategory,
            @RequestParam(name = "budget_type", required = false) Integer idBudgetType,
            @RequestParam(name = "budget_nature", required = false) Integer idBudgetNature,
            @RequestParam(name = "create_report", required = false, defaultValue = "false") Boolean createReport,
            HttpServletResponse response
    ) throws Exception {

        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);
        List<BudgetCategory> budgetCategories = new ArrayList<>();

        for (Budgets budget : budgets) {
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setName(budget.getAccountingAccount().getBudgetCategory().getBudgetCategory());
            budgetCategory.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());

            List<BudgetYearConcept> budgetYearConceptList = budgetYearConceptService.findByBudgetAndYear(budget.getIdBudget(), year);

            BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
            budgetSubcategory.setName(budget.getAccountingAccount().getBudgetSubcategory().getBudgetSubcategory());
            budgetSubcategory.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
            budgetSubcategory.setIdBudget(budget.getIdBudget());
            budgetSubcategory.setBudgetNature(budget.getBudgetNature());
            budgetSubcategory.setCostCenter(budget.getCostCenter());
            budgetSubcategory.setBudgetType(budget.getBudgetType());
            budgetSubcategory.setBudgetYearConceptList(budgetYearConceptList);

            if (!budgetCategories.contains(budgetCategory)) {
                List<BudgetSubcategory> budgetSubcategories = new ArrayList<>();
                budgetSubcategories.add(budgetSubcategory);
                budgetCategory.setBudgetSubcategories(budgetSubcategories);
                budgetCategories.add(budgetCategory);
            } else {
                BudgetCategory oldBudgetCategory = budgetCategories.get(budgetCategories.indexOf(budgetCategory));
                oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                budgetCategories.set(budgetCategories.indexOf(oldBudgetCategory), oldBudgetCategory);
            }

        }

//        if (createReport) {
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
//        OutputStream outputStream = response.getOutputStream();
//        dwEmployeesService.createReport(dwEmployees, outputStream);
//        outputStream.flush();
//        outputStream.close();
//        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetCategories));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.config.JsonViews;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.pojos.Budget;
import mx.bidg.pojos.BudgetCategory;
import mx.bidg.pojos.BudgetPojo;
import mx.bidg.pojos.BudgetSubcategory;
import mx.bidg.service.BudgetMonthBranchService;
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
    BudgetMonthBranchService budgetMonthBranchService;

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
//            budgetMonthBranch.setDwEnterprise(new DwEnterprises(jsonRequest.get("dwEnterprise").asInt()));
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
        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature);
        List<BudgetPojo> budgetPojos = new ArrayList<>();

        for (Budgets budget : budgets) {
            BudgetPojo budgetPojo = new BudgetPojo();
            budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
            budgetPojo.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
            budgetPojo.setIdCostCenter(budget.getIdCostCenter());
            budgetPojo.setIdBudgetType(budget.getIdBudgetType());
            budgetPojo.setIdBudgetNature(budget.getIdBudgetNature());
            budgetPojo.setIdBudget(budget.getIdBudget());
            budgetPojo.setBudgetCategory(budget.getAccountingAccount().getBudgetCategory());
            budgetPojo.setBudgetSubcategory(budget.getAccountingAccount().getBudgetSubcategory());
            budgetPojos.add(budgetPojo);
        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetPojos));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public ResponseEntity<String> getBudgetByCategory(
            @RequestParam("cost_center") Integer idCostCenter,
            @RequestParam("year") Integer year,
            @RequestParam(name = "category", required = false) Integer idBudgetCategory,
            @RequestParam(name = "create_report", required = false, defaultValue = "false") Boolean createReport,
            HttpServletResponse response
    ) throws Exception {

        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetCategory);
        List<Budget> budgetList = new ArrayList<>();

        for (Budgets budget : budgets) {

            Budget budgetPojo = new Budget();
            BudgetCategory budgetCategory = new BudgetCategory();

            budgetCategory.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
            budgetCategory.setName(budget.getAccountingAccount().getBudgetCategory().getBudgetCategory());

            budgetPojo.setIdBudget(budget.getIdBudget());
            budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
            budgetPojo.setBudgetNature(budget.getBudgetNature());
            budgetPojo.setCostCenter(budget.getCostCenter());
            budgetPojo.setBudgetType(budget.getBudgetType());
            budgetPojo.setBudgetCategory(budgetCategory);

            BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
            List<BudgetMonthBranch> budgetMonthBranchList = budgetMonthBranchService.findByBudgetAndYear(budget.getIdBudget(), year);

            budgetSubcategory.setName(budget.getAccountingAccount().getBudgetSubcategory().getBudgetSubcategory());
            budgetSubcategory.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());

            for (BudgetMonthBranch budgetMonthBranch : budgetMonthBranchList) {
                budgetMonthBranch.setBudgetMonthConceptsList(budgetMonthConceptsService.findByBudgetMonthBranch(budgetMonthBranch.getIdBudgetMonthBranch()));
            }

            budgetSubcategory.setBudgetMonthBranchList(budgetMonthBranchList);

            if (!budgetList.contains(budgetPojo)) {
                List<BudgetSubcategory> budgetSubcategories = new ArrayList<>();
                budgetSubcategories.add(budgetSubcategory);
                budgetCategory.setBudgetSubcategories(budgetSubcategories);
                budgetList.add(budgetPojo);
            } else {
                Budget oldBudget = budgetList.get(budgetList.indexOf(budgetPojo));
                List<BudgetSubcategory> oldBudgetSubcategories = oldBudget.getBudgetCategory().getBudgetSubcategories();
                oldBudgetSubcategories.add(budgetSubcategory);
                oldBudget.getBudgetCategory().setBudgetSubcategories(oldBudgetSubcategories);
                budgetList.set(budgetList.indexOf(budgetPojo), oldBudget);
            }

        }

        if (createReport) {
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportFileName + "_" + dateTime.format(formatter) + ".xlsx"+ "\"");
//        OutputStream outputStream = response.getOutputStream();
//        dwEmployeesService.createReport(dwEmployees, outputStream);
//        outputStream.flush();
//        outputStream.close();
        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetList));
    }

    @RequestMapping(value = "/copy-budget", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> copyBudget(
            @RequestParam("cost_center") Integer idCostCenter,
            @RequestParam("year_from_copy") Integer yearFromCopy,
            @RequestParam("year_to_copy") Integer yearToCopy,
            @RequestParam(name = "overwrite", required = false, defaultValue = "false") Boolean overwrite,
            @RequestParam("nature") Integer idBudgetNature,
            HttpSession httpSession
    ) throws Exception {
        Users users = (Users) httpSession.getAttribute("user");
        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, null, idBudgetNature);
        List<BudgetMonthBranch> budgetMonthBranchYearToCopyList = budgetMonthBranchService.findByBudgetsAndYear(budgets, yearToCopy);

        if (!budgetMonthBranchYearToCopyList.isEmpty() && !overwrite) {
            throw new ValidationException(
                    "Presupuesto asignado",
                    "Ya hay presupuesto asignado para el año " + yearToCopy + ". ¿Desea sobreescribir los datos?"
            );
        } else {
            LocalDateTime now = LocalDateTime.now();
            List<BudgetMonthBranch> budgetMonthBranchYearFromCopyList = budgetMonthBranchService.findByBudgetsAndYear(budgets, yearFromCopy);

            for (BudgetMonthBranch budgetMonthBranchToCopy : budgetMonthBranchYearToCopyList) {
                budgetMonthBranchService.delete(budgetMonthBranchToCopy);
            }

            for (BudgetMonthBranch budgetMonthBranch : budgetMonthBranchYearFromCopyList) {
                BudgetMonthBranch newBudgetMonthBranch = new BudgetMonthBranch();
                newBudgetMonthBranch.setBudget(budgetMonthBranch.getBudget());
                newBudgetMonthBranch.setAmount(budgetMonthBranch.getAmount());
                newBudgetMonthBranch.setCreationDate(now);
                newBudgetMonthBranch.setCurrency(budgetMonthBranch.getCurrency());
                newBudgetMonthBranch.setExpendedAmount(budgetMonthBranch.getExpendedAmount());
                newBudgetMonthBranch.setAmount(budgetMonthBranch.getAmount());
                newBudgetMonthBranch.setUsername(users.getUsername());
                newBudgetMonthBranch.setMonth(budgetMonthBranch.getMonth());
                newBudgetMonthBranch.setYear(yearToCopy);
                newBudgetMonthBranch.setIdAccessLevel(1);
                newBudgetMonthBranch.setAuthorized(false);
                budgetMonthBranchService.saveBudgetMonthBranch(newBudgetMonthBranch);
                List<BudgetMonthConcepts> budgetMonthConceptList = budgetMonthConceptsService.findByBudgetMonthBranch(budgetMonthBranch.getIdBudgetMonthBranch());
                for (BudgetMonthConcepts budgetMonthConcept : budgetMonthConceptList) {
                    BudgetMonthConcepts newBudgetMonthConcept = new BudgetMonthConcepts();
                    newBudgetMonthConcept.setBudgetMonthBranch(newBudgetMonthBranch);
                    newBudgetMonthConcept.setAmount(budgetMonthConcept.getAmount());
                    newBudgetMonthConcept.setCurrency(budgetMonthConcept.getCurrency());
                    newBudgetMonthConcept.setIdAccessLevel(1);
                    newBudgetMonthConcept.setBudgetConcept(budgetMonthConcept.getBudgetConcept());
                    budgetMonthConceptsService.saveBudgetMonthConcepts(newBudgetMonthConcept);
                }
            }
        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString("OK"));
    }
}

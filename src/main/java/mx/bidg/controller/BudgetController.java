/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.pojos.*;
import mx.bidg.service.*;
import mx.bidg.utils.BudgetHelper;
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
    CCostCenterService cCostCenterService;

    @Autowired
    RealBudgetSpendingService realBudgetSpendingService;

    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private AccountingAccountsService accountingAccountsService;

    @Autowired
    private BudgetsService budgetsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthorizationCostCenterService authorizationCostCenterService;

    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveBudget(@RequestBody String data) throws Exception {

        JsonNode json = mapper.readTree(data);

        Budgets budget = new Budgets();

//        budget.setArea(new CAreas(json.get("area").asInt()));
//        budget.setBudgetCategory(new CBudgetCategories(json.get("category").asInt()));
//        budget.setBudgetSubcategory(new CBudgetSubcategories(json.get("subcategory").asInt()));

        ArrayList<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
        RealBudgetSpending realBudgetSpending;

        for(JsonNode jsonRequest : json.get("realBudgetSpendingList")) {

            realBudgetSpending = new RealBudgetSpending();
            ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
            BudgetMonthConcepts budgetMonthConcept;

            for(JsonNode jsonBudgetMonthConcept : jsonRequest.get("budgetMonthConceptList")) {
                budgetMonthConcept = new BudgetMonthConcepts();
                budgetMonthConcept.setAmount(jsonBudgetMonthConcept.get("amountConcept").decimalValue());

                budgetMonthConcept.setIdAccessLevel(1);
//                budgetMonthConcept.setBudgetYearConcept(realBudgetSpending);
                budgetMonthConceptsList.add(budgetMonthConcept);
            }

//            realBudgetSpending.setBudgetMonthConceptsList(budgetMonthConceptsList);
            realBudgetSpending.setBudget(new Budgets(jsonRequest.get("budget").asInt()));
//            realBudgetSpending.setMonth(new CMonths(jsonRequest.get("month").asInt()));
//            realBudgetSpending.setDwEnterprise(new DwEnterprises(jsonRequest.get("dwEnterprise").asInt()));
//            realBudgetSpending.setAmount(jsonRequest.get("amountMonth").decimalValue());
//            realBudgetSpending.setExpendedAmount(jsonRequest.get("expendedAmount").decimalValue());
            realBudgetSpending.setYear(jsonRequest.get("year").asInt());
            //realBudgetSpending.setIdAccessLevel(1);

        }

        budget.setIdAccessLevel(1);
        //budget.setRealBudgetSpendingList(realBudgetSpendingList);
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
            //budgetPojo.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());
            //budgetPojo.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
            budgetPojo.setIdBudget(budget.getIdBudget());
            //budgetPojo.setBudgetCategory(budget.getAccountingAccount().getBudgetCategory());
            //budgetPojo.setBudgetSubcategory(budget.getAccountingAccount().getBudgetSubcategory());
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
            @RequestParam(name = "download", required = false, defaultValue = "false") Boolean download,
            HttpServletResponse response
    ) throws Exception {
        List<RealBudgetSpending> budgetCategories = budgetHelper.getOrderedBudget(idCostCenter,idBudgetType,idBudgetNature,year);
        if (download) {

            String fileName = "Presupuesto";
            CCostCenter costCenter = null;

            if (idCostCenter != null) {
                costCenter = cCostCenterService.findById(idCostCenter);
                fileName += "_" + costCenter.getAcronym();
            }

            if (year != null) {
                fileName += "_" + year;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();

            fileName += "_" + now.format(formatter) + ".xlsx";

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            OutputStream outputStream = response.getOutputStream();
            //budgetsService.createReport(budgetCategories, costCenter, year, outputStream);
            outputStream.flush();
            outputStream.close();
        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetCategories));
    }

    @RequestMapping(value = "/center-year",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> getBudgetByCostcenterAndYear(
            @RequestParam("cost_center") Integer idCostCenter,
            @RequestParam(name = "year", required = false) Integer year,
            HttpServletResponse response
    ) throws Exception {
        List<BudgetCategory> budgetCategories = budgetHelper.getModifyOrderBudget(idCostCenter,year);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetCategories));
    }

    @RequestMapping(value = "/validated",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> updateBudgetByCostcenterAndYear(
            @RequestParam(name = "cost_center", required = false) Integer idCostCenter,
            @RequestParam(name = "year", required = false) Integer year) throws Exception {
        //List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        AuthorizationCostCenter a = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter,year);
        a.setAuthorization(true);
        authorizationCostCenterService.update(a);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(null));
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reviewBudget(
            @RequestParam("cost_center") Integer idCostCenter,
            @RequestParam(name = "year", required = false) Integer year,
            HttpSession session)throws Exception{
        Users user = (Users) session.getAttribute("user");
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        AuthorizationCostCenter a = new AuthorizationCostCenter();
        a.setCostCenter(c);
        a.setValidation(true);
        a.setYear(year);
        a.setUsers(user);
        authorizationCostCenterService.save(a);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(a));
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetByCategory(
            @RequestParam(name = "bussinessline")Integer idBussinessline,
            @RequestParam(name = "distributor") Integer idDistributor,
            @RequestParam(name = "cost_center") Integer idCostCenter,
            @RequestParam(name = "year", required = false) Integer year
    ) throws Exception {
        List<BudgetCategory> budgetCategories = budgetHelper.getAuthorizationBudget(idBussinessline,idDistributor,idCostCenter,year);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetCategories),HttpStatus.OK);
    }

    @RequestMapping(value = "/cost-center/{idCostCenter}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetsbyCC(@PathVariable Integer idCostCenter) throws Exception {
        List<Budgets> budgets = budgetsService.findByCostCenter(idCostCenter);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgets));
    }

    @RequestMapping(value = "/center-cost/{idCostCenter}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgets(@PathVariable Integer idCostCenter) throws  Exception {
        //List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        return  ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(null));
    }
}

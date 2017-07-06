/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sun.org.apache.regexp.internal.RE;
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
    private  DistributorCostCenterService distributorCostCenterService;

    @Autowired
    private RealBudgetSpendingHistoryService realBudgetSpendingHistoryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthorizationCostCenterService authorizationCostCenterService;

    @Autowired
    private CConceptBudgetService cConceptBudgetService;

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
        List<AccountingAccountPojo> accountingAccountPojos = budgetHelper.pojoCaptureBudget(idCostCenter, idBudgetType, idBudgetNature, year);
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

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccountPojos));
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

    @RequestMapping(value = "/validated", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateBudgetByCostcenterAndYear(
            @RequestBody String data,
            HttpSession session)throws Exception {

        Users user = (Users) session.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        for (JsonNode jsonNode : node.get("authorizeOrDenieCostCenters")){
            AuthorizationCostCenter authorizationCostCenter = authorizationCostCenterService.findByIdCostCenterAndYear(jsonNode.get("costCenter").get("idCostCenter").asInt(), jsonNode.get("year").asInt());
            if (authorizationCostCenter != null){
                if (jsonNode.get("status").asInt() == 1){
                    authorizationCostCenter.setcCostCenterStatus(CCostCenterStatus.AUTORIZADA);
                    authorizationCostCenter.setReason(jsonNode.get("reason").asText());
                    AuthorizationCostCenter authorizationCostCenters = authorizationCostCenterService.update(authorizationCostCenter);
                    List<DistributorCostCenter> distributorCostCenterList = distributorCostCenterService.findByCostCenter(jsonNode.get("costCenter").get("idCostCenter").asInt());
                    List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
                    List<RealBudgetSpendingHistory>realBudgetSpendingHistoryList = new ArrayList<>();
                    if (!distributorCostCenterList.isEmpty()){
                        for(DistributorCostCenter distributorCostCenter : distributorCostCenterList){
                            if (distributorCostCenter != null){
                                List<Budgets> budgetsList = budgetsService.findByIdDistributor(distributorCostCenter.getIdDistributorCostCenter());
                                if (!budgetsList.isEmpty()){
                                    for (Budgets budget : budgetsList){
                                        if (budget != null){
                                            RealBudgetSpending realBudgetSpending = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(), jsonNode.get("year").asInt());
                                            RealBudgetSpendingHistory realBudgetSpendingHistory = realBudgetSpendingHistoryService.findByIdBudgetandYear(budget.getIdBudget(), jsonNode.get("year").asInt());
                                            if (realBudgetSpending != null){
                                                realBudgetSpendingList.add(realBudgetSpending);
                                            }
                                            if (realBudgetSpendingHistory != null){
                                                realBudgetSpendingHistoryList.add(realBudgetSpendingHistory);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (realBudgetSpendingHistoryList.isEmpty()){
                            if (!realBudgetSpendingList.isEmpty()){
                                for(RealBudgetSpending r: realBudgetSpendingList){
                                    RealBudgetSpendingHistory real = new RealBudgetSpendingHistory();
                                    if(r.getJanuaryBudgetAmount()!= null) {
                                        real.setJanuaryBudgetAmount(r.getJanuaryBudgetAmount());
                                        real.setJanuaryExpendedAmount(r.getJanuaryBudgetAmount());
                                    }
                                    if(r.getFebruaryBudgetAmount()!= null) {
                                        real.setFebruaryBudgetAmount(r.getFebruaryBudgetAmount());
                                        real.setFebruaryExpendedAmount(r.getFebruaryExpendedAmount());
                                    }
                                    if(r.getMarchBudgetAmount()!= null){
                                        real.setMarchBudgetAmount(r.getMarchBudgetAmount());
                                        real.setMarchExpendedAmount(r.getMarchExpendedAmount());
                                    }
                                    if(r.getAprilBudgetAmount()!= null){
                                        real.setAprilBudgetAmount(r.getAprilBudgetAmount());
                                        real.setAprilExpendedAmount(r.getAprilExpendedAmount());
                                    }
                                    if(r.getMayBudgetAmount()!= null){
                                        real.setMayBudgetAmount(r.getMayBudgetAmount());
                                        real.setMayExpendedAmount(r.getMayExpendedAmount());
                                    }
                                    if(r.getJuneBudgetAmount()!= null){
                                        real.setJuneBudgetAmount(r.getJuneBudgetAmount());
                                        real.setJuneExpendedAmount(r.getJuneExpendedAmount());
                                    }
                                    if(r.getJulyBudgetAmount()!= null){
                                        real.setJulyBudgetAmount(r.getJulyBudgetAmount());
                                        real.setJulyExpendedAmount(r.getJulyExpendedAmount());
                                    }
                                    if(r.getAugustBudgetAmount()!= null){
                                        real.setAugustBudgetAmount(r.getAugustBudgetAmount());
                                        real.setAugustExpendedAmount(r.getAugustExpendedAmount());
                                    }
                                    if(r.getSeptemberBudgetAmount()!= null){
                                        real.setSeptemberBudgetAmount(r.getSeptemberBudgetAmount());
                                        real.setSeptemberExpendedAmount(r.getSeptemberExpendedAmount());
                                    }
                                    if(r.getOctoberBudgetAmount()!= null){
                                        real.setOctoberBudgetAmount(r.getOctoberBudgetAmount());
                                        real.setOctoberExpendedAmount(r.getOctoberExpendedAmount());
                                    }
                                    if(r.getNovemberBudgetAmount()!= null){
                                        real.setNovemberBudgetAmount(r.getNovemberBudgetAmount());
                                        real.setNovemberExpendedAmount(r.getNovemberExpendedAmount());
                                    }
                                    if(r.getDecemberBudgetAmount()!= null){
                                        real.setDecemberBudgetAmount(r.getDecemberBudgetAmount());
                                        real.setDecemberExpendedAmount(r.getDecemberExpendedAmount());
                                    }
                                    if(r.getTotalBudgetAmount()!= null){
                                        real.setTotalBudgetAmount(r.getTotalBudgetAmount());
                                        real.setTotalExpendedAmount(r.getTotalExpendedAmount());
                                    }
                                    if(r.getYear()!= null){
                                        real.setYear(r.getYear());
                                    }
                                    if(r.getBudget()!= null){
                                        real.setBudget(r.getBudget());
                                    }
                                    if(r.getCurrency()!= null){
                                        real.setCurrency(r.getCurrency());
                                    }
                                    if (r.getUsername() != null){
                                        real.setUsername(r.getUsername());
                                    }
                                    realBudgetSpendingHistoryService.save(real);
                                }

                            }
                        }
                    }
                    EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_authorized");
                    emailTemplate.addProperty("authorizationCostCenter", authorizationCostCenters);
                    emailTemplate.addProperty("user", user);
                    emailDeliveryService.deliverEmailWithUser(emailTemplate, authorizationCostCenter.getUsers());
                }else if (jsonNode.get("status").asInt() == 2){
                    authorizationCostCenter.setcCostCenterStatus(CCostCenterStatus.RECHAZADA);
                    authorizationCostCenter.setReason(jsonNode.get("reason").asText());
                    AuthorizationCostCenter authorizationCostCenters = authorizationCostCenterService.update(authorizationCostCenter);

                    EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_reject");
                    emailTemplate.addProperty("authorizationCostCenter", authorizationCostCenters);
                    emailTemplate.addProperty("user", user);
                    emailDeliveryService.deliverEmailWithUser(emailTemplate, authorizationCostCenter.getUsers());
                }
            }
        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(data));
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reviewBudget(
            @RequestParam(name = "cost_center") Integer idCostCenter,
            @RequestParam(name = "year", required = true) Integer year,
            HttpSession session)throws Exception{
        Users user = (Users) session.getAttribute("user");
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        AuthorizationCostCenter authorizationCostCenter = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter,year);
        authorizationCostCenter.setcCostCenterStatus(CCostCenterStatus.NO_AUTORIZADA);
        authorizationCostCenter.setUsers(user);
        authorizationCostCenterService.update(authorizationCostCenter);

        EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_validation_required");
        emailTemplate.addProperty("costCenter",c);
        emailTemplate.addProperty("user",user);
        emailDeliveryService.deliverEmail(emailTemplate);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenter));
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetByCategory(
            @RequestParam(name = "cost_center") Integer idCostCenter,
            @RequestParam(name = "year", required = false) Integer year
    ) throws Exception {
        List<RealBudgetSpending> budgetCategories = budgetHelper.getBudgetReport(idCostCenter,year);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetCategories),HttpStatus.OK);
    }

//    @RequestMapping(value = "/get-budget-levels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<String> getBudgetByCategoryLevels(
//            @RequestParam(name = "bussinessline")Integer idBussinessLine,
//            @RequestParam(name = "distributor")Integer idDistributor,
//            @RequestParam(name = "cost_center") Integer idCostCenter,
//            @RequestParam(name = "year", required = false) Integer year
//    ) throws Exception {
//        List<BudgetCategory> budgetCategories = budgetHelper.getAuthorizationBudget(idBussinessLine,idDistributor,idCostCenter,year);
//        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(budgetCategories),HttpStatus.OK);
//    }

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
    /*EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_validation_required");
        emailTemplate.addProperty("costCenter",c);*/

    @RequestMapping(value = "/get-emails",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmail(@RequestParam(name = "cost_center") Integer idCostCenter) throws  Exception {
        //List<Budgets> budgets = budgetsService.findByIdCostCenter(idCostCenter);
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_validation_required");
        emailTemplate.addProperty("costCenter",c);
        return  ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(emailTemplate));
    }

    @RequestMapping(value = "/get-emails-modify",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmailModify(@RequestParam(name = "cost_center") Integer idCostCenter) throws  Exception {
        CCostCenter c = cCostCenterService.findById(idCostCenter);
        EmailTemplates emailTemplate = emailTemplatesService.findByName("budget_modify_required");
        emailTemplate.addProperty("costCenter",c);
        return  ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(emailTemplate));
    }

    @RequestMapping(value = "/budget-nature/{idBudgetType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findBudgetNatureByType(@PathVariable Integer idBudgetType)throws IOException{
        List<CBudgetNature> cBudgetNatureList = budgetsService.findBudgetNatureByType(idBudgetType);
        return new  ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cBudgetNatureList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveDistributorCostCenterBudget(@RequestBody String data, HttpSession session)throws IOException{

        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);

        CConceptBudget cConceptBudget = new CConceptBudget();

        cConceptBudget.setCreationDate(LocalDateTime.now());
        cConceptBudget.setUsername(user.getUsername());
        cConceptBudget.setNameConcept(node.get("idConceptBudget").asText());

        cConceptBudget = cConceptBudgetService.save(cConceptBudget);

        DistributorCostCenter distributorCostCenter = distributorCostCenterService.findByCostCenterAndAA(node.get("idCostCenter").asInt(), node.get("idAccountingAccount").asInt());
        Budgets budget = new Budgets();

        budget.setBudgetNature(new CBudgetNature(node.get("idBudgetNature").asInt()));
        budget.setBudgetType(new CBudgetTypes(node.get("idBudgetType").asInt()));
        budget.setConceptBudget(cConceptBudget);
        budget.setCreationDate(LocalDateTime.now());
        budget.setDistributorCostCenter(distributorCostCenter);
        budget.setIdAccessLevel(1);
        budget.setUsername(user.getUsername());

        budget = budgetsService.saveBudget(budget);

        RealBudgetSpending realBudgetSpending = new RealBudgetSpending();

        Calendar fecha = Calendar.getInstance();

        realBudgetSpending.setUsername(user.getUsername());
        realBudgetSpending.setCreationDate(LocalDateTime.now());
        realBudgetSpending.setYear(fecha.get(Calendar.YEAR));
        realBudgetSpending.setCurrency(new CCurrencies(1));
        realBudgetSpending.setBudget(budget);
        realBudgetSpending.setJanuaryBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setFebruaryBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setMarchBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setAprilBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setMayBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setJuneBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setJulyBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setAugustBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setSeptemberBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setOctoberBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setNovemberBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setDecemberBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setJanuaryExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setFebruaryExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setMarchExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setAprilExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setMayExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setJuneExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setJulyExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setAugustExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setSeptemberExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setOctoberExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setNovemberExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setDecemberExpendedAmount(new BigDecimal(0.00));
        realBudgetSpending.setTotalBudgetAmount(new BigDecimal(0.00));
        realBudgetSpending.setTotalExpendedAmount(new BigDecimal(0.00));

        realBudgetSpending = realBudgetSpendingService.save(realBudgetSpending);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(realBudgetSpending), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-accounting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAccByCCAndBTAndBN(@RequestParam(name = "idCostCenter", required = true) Integer idCostCenter
            ,@RequestParam(name = "idBudgetType", required = true) Integer idBudgetType
            ,@RequestParam(name = "idBudgetNature", required = true) Integer idBudgetNature) throws IOException{
        List<AccountingAccounts> accountingAccounts = budgetsService.findAccountingAccountByCCAndNatureAndType(idCostCenter, idBudgetType, idBudgetNature);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountingAccounts), HttpStatus.OK);
    }

}

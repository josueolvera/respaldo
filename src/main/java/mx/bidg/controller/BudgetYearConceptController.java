/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.model.RealBudgetSpending;
import mx.bidg.pojos.BudgetCategory;
import mx.bidg.service.*;
import mx.bidg.utils.BudgetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author sistemask
 */
@Controller
@RequestMapping("/budget-year-concept")
public class BudgetYearConceptController {

    @Autowired
    AccountingAccountsService accountingAccountsService;

    @Autowired
    RealBudgetSpendingService realBudgetSpendingService;

    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;

    @Autowired
    private BudgetsService budgetsService;

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    ResponseEntity<String> getFromRequest(@RequestBody String data) throws Exception {

        RealBudgetSpending realBudgetSpending = realBudgetSpendingService.findFromRequest(data);

        if (realBudgetSpending == null) {
            return new ResponseEntity<>("Error al guardar la solicitud", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(realBudgetSpending), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idBudgetYearConcept}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer idBudgetYearConcept) throws Exception {
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(realBudgetSpendingService.delete(idBudgetYearConcept)));
    }

    @RequestMapping(value = "/{idBudget}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, @PathVariable Integer idBudget, HttpSession httpSession) throws Exception {

        Users user = (Users) httpSession.getAttribute("user");
        JsonNode budgetYearConceptListNode = mapper.readTree(data);
        Integer year = budgetYearConceptListNode.get(0).get("year").asInt();

        List<RealBudgetSpending> realBudgetSpendingList = realBudgetSpendingService.saveList(data, idBudget, user);

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(realBudgetSpendingList));
    }

    @RequestMapping(value = "/update/{idBudget}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@RequestBody String data, @PathVariable Integer idBudget, HttpSession httpSession) throws Exception {

        Users user = (Users) httpSession.getAttribute("user");
        JsonNode budgetYearConceptListNode = mapper.readTree(data);
        Integer year = budgetYearConceptListNode.get(0).get("year").asInt();

        List<RealBudgetSpending> realBudgetSpendingList = realBudgetSpendingService.updateList(data, idBudget, user);

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(realBudgetSpendingList));
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    String authorize(@RequestBody String data) throws Exception {
        return realBudgetSpendingService.authorizeBudget(data);
    }

    @RequestMapping(value = "/copy-budget", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> copyBudget(
            @RequestParam(name = "cost_center", required = false) Integer idCostCenter,
            @RequestParam(name = "budget_type", required = false) Integer idBudgetType,
            @RequestParam(name = "budget_nature", required = false) Integer idBudgetNature,
            @RequestParam(name = "year_from_copy") Integer yearFromCopy,
            @RequestParam(name = "year_to_copy") Integer yearToCopy,
            HttpSession httpSession
    ) throws Exception {
        Users user = (Users) httpSession.getAttribute("user");
        List<DistributorCostCenter> distributorCostCenters = distributorCostCenterService.findByCostCenter(idCostCenter);
        for (DistributorCostCenter d: distributorCostCenters){
            List<Budgets> budgets = budgetsService.findByIdDistributorCostCenter(d.getIdDistributorCostCenter(),idBudgetType,idBudgetNature);
            for (Budgets budget: budgets){
                RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(),yearFromCopy);
                RealBudgetSpending realBudgetSpending = new RealBudgetSpending();
                if(r == null) {
                    break;
                }else{
                    realBudgetSpending.setBudget(budget);
                    realBudgetSpending.setJanuaryBudgetAmount(r.getJanuaryBudgetAmount());
                    realBudgetSpending.setJanuaryExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setFebruaryBudgetAmount(r.getFebruaryBudgetAmount());
                    realBudgetSpending.setFebruaryExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setMarchBudgetAmount(r.getMarchBudgetAmount());
                    realBudgetSpending.setMarchExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setAprilBudgetAmount(r.getAprilBudgetAmount());
                    realBudgetSpending.setAprilExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setMayBudgetAmount(r.getMayBudgetAmount());
                    realBudgetSpending.setMayExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setJuneBudgetAmount(r.getJuneBudgetAmount());
                    realBudgetSpending.setJuneExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setJulyBudgetAmount(r.getJulyBudgetAmount());
                    realBudgetSpending.setJulyExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setAugustBudgetAmount(r.getAugustBudgetAmount());
                    realBudgetSpending.setAugustExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setSeptemberBudgetAmount(r.getSeptemberBudgetAmount());
                    realBudgetSpending.setSeptemberExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setOctoberBudgetAmount(r.getOctoberBudgetAmount());
                    realBudgetSpending.setOctoberExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setNovemberBudgetAmount(r.getNovemberBudgetAmount());
                    realBudgetSpending.setNovemberExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setDecemberBudgetAmount(r.getDecemberBudgetAmount());
                    realBudgetSpending.setDecemberExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setTotalBudgetAmount(r.getTotalBudgetAmount());
                    realBudgetSpending.setTotalExpendedAmount(BigDecimal.ZERO);
                    realBudgetSpending.setCurrency(r.getCurrency());
                    realBudgetSpending.setYear(yearToCopy);
                    realBudgetSpending.setUsername(user.getUsername());
                    realBudgetSpendingService.save(realBudgetSpending);
                }
            }
        }
        //List<Budgets> budgets = budgetsService.getBudgetsfindNatureTypeAndCostCenter(idCostCenter, idBudgetType, idBudgetNature);
        /*if (!budgets.isEmpty()) {
            for (Budgets b : budgets){
                if (b.getIdBudget() != null){
                    List<RealBudgetSpending> rbs = realBudgetSpendingService.findByBudgetAndYear(b.getIdBudget(),yearFromCopy);
                    if (!rbs.isEmpty()){
                        for (RealBudgetSpending rbd : rbs){
                            RealBudgetSpending real = new RealBudgetSpending();
                            if (rbd.getBudget() != null){
                                real.setBudget(rbd.getBudget());
                            }
                            if (rbd.getCurrency() != null){
                                real.setCurrency(rbd.getCurrency());
                            }
                            real.setJanuaryBudgetAmount(rbd.getJanuaryBudgetAmount());
                            real.setJanuaryExpendedAmount(rbd.getJanuaryExpendedAmount());
                            real.setFebruaryBudgetAmount(rbd.getFebruaryBudgetAmount());
                            real.setFebruaryExpendedAmount(rbd.getFebruaryExpendedAmount());
                            real.setMarchBudgetAmount(rbd.getMarchBudgetAmount());
                            real.setMarchExpendedAmount(rbd.getMarchExpendedAmount());
                            real.setAprilBudgetAmount(rbd.getAprilBudgetAmount());
                            real.setAprilExpendedAmount(rbd.getAprilExpendedAmount());
                            real.setMayBudgetAmount(rbd.getMayBudgetAmount());
                            real.setMayExpendedAmount(rbd.getMayExpendedAmount());
                            real.setJuneBudgetAmount(rbd.getJuneBudgetAmount());
                            real.setJuneExpendedAmount(rbd.getJuneExpendedAmount());
                            real.setJulyBudgetAmount(rbd.getJulyBudgetAmount());
                            real.setJulyExpendedAmount(rbd.getJulyExpendedAmount());
                            real.setAugustBudgetAmount(rbd.getAugustBudgetAmount());
                            real.setAugustExpendedAmount(rbd.getAugustExpendedAmount());
                            real.setSeptemberBudgetAmount(rbd.getSeptemberBudgetAmount());
                            real.setSeptemberExpendedAmount(rbd.getSeptemberExpendedAmount());
                            real.setOctoberBudgetAmount(rbd.getOctoberBudgetAmount());
                            real.setOctoberExpendedAmount(rbd.getOctoberExpendedAmount());
                            real.setNovemberBudgetAmount(rbd.getNovemberBudgetAmount());
                            real.setNovemberExpendedAmount(rbd.getNovemberExpendedAmount());
                            real.setDecemberBudgetAmount(rbd.getDecemberBudgetAmount());
                            real.setDecemberExpendedAmount(rbd.getDecemberExpendedAmount());
                            real.setTotalBudgetAmount(rbd.getTotalBudgetAmount());
                            real.setTotalExpendedAmount(rbd.getTotalExpendedAmount());
                            real.setYear(yearToCopy);
                            real.setUsername(user.getUsername());
                            realBudgetSpendingService.save(real);
                        }
                    }
                }*/



        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(realBudgetSpendingService.findAll()));
    }

    @RequestMapping(value = "/find/{idCostCenter}/{idAccountingAccount}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetYearConcept(@PathVariable int idCostCenter, @PathVariable int idAccountingAccount) throws Exception {
        RealBudgetSpending realBudgetSpending = realBudgetSpendingService.findByAccountingAccountAndCostCenter(idCostCenter, idAccountingAccount);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(realBudgetSpending));
    }
}

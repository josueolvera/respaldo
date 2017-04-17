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
import mx.bidg.exceptions.ValidationException;
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
    private AuthorizationCostCenterService authorizationCostCenterService;

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
        AuthorizationCostCenter a = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter, yearToCopy);
        if (a.getcCostCenterStatus().getIdCostCenterStatus() != 4 && a.getcCostCenterStatus().getIdCostCenterStatus() != 5) {
            System.out.print("La authorization esta nula");
            for (DistributorCostCenter d : distributorCostCenters) {
                List<Budgets> budgets = budgetsService.findByIdDistributorCostCenter(d.getIdDistributorCostCenter(), idBudgetType, idBudgetNature);
                for (Budgets budget : budgets) {
                    //Objeto del año que se obtendra la informacion
                    RealBudgetSpending real = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(), yearFromCopy);
                    RealBudgetSpending r = realBudgetSpendingService.findByIdBudgetAndYear(budget.getIdBudget(), yearToCopy);
                    RealBudgetSpending realBudgetSpending = new RealBudgetSpending();
                    if (r == null) {
                        realBudgetSpending.setBudget(budget);
                        realBudgetSpending.setJanuaryBudgetAmount(real.getJanuaryBudgetAmount());
                        realBudgetSpending.setJanuaryExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setFebruaryBudgetAmount(real.getFebruaryBudgetAmount());
                        realBudgetSpending.setFebruaryExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setMarchBudgetAmount(real.getMarchBudgetAmount());
                        realBudgetSpending.setMarchExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setAprilBudgetAmount(real.getAprilBudgetAmount());
                        realBudgetSpending.setAprilExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setMayBudgetAmount(real.getMayBudgetAmount());
                        realBudgetSpending.setMayExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setJuneBudgetAmount(real.getJuneBudgetAmount());
                        realBudgetSpending.setJuneExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setJulyBudgetAmount(real.getJulyBudgetAmount());
                        realBudgetSpending.setJulyExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setAugustBudgetAmount(real.getAugustBudgetAmount());
                        realBudgetSpending.setAugustExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setSeptemberBudgetAmount(real.getSeptemberBudgetAmount());
                        realBudgetSpending.setSeptemberExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setOctoberBudgetAmount(real.getOctoberBudgetAmount());
                        realBudgetSpending.setOctoberExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setNovemberBudgetAmount(real.getNovemberBudgetAmount());
                        realBudgetSpending.setNovemberExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setDecemberBudgetAmount(real.getDecemberBudgetAmount());
                        realBudgetSpending.setDecemberExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setTotalBudgetAmount(real.getTotalBudgetAmount());
                        realBudgetSpending.setTotalExpendedAmount(BigDecimal.ZERO);
                        realBudgetSpending.setCurrency(real.getCurrency());
                        realBudgetSpending.setYear(yearToCopy);
                        realBudgetSpending.setUsername(user.getUsername());
                        realBudgetSpendingService.save(realBudgetSpending);
                    } else {
                        r.setBudget(budget);
                        r.setJanuaryBudgetAmount(real.getJanuaryBudgetAmount());
                        r.setJanuaryExpendedAmount(BigDecimal.ZERO);
                        r.setFebruaryBudgetAmount(real.getFebruaryBudgetAmount());
                        r.setFebruaryExpendedAmount(BigDecimal.ZERO);
                        r.setMarchBudgetAmount(real.getMarchBudgetAmount());
                        r.setMarchExpendedAmount(BigDecimal.ZERO);
                        r.setAprilBudgetAmount(real.getAprilBudgetAmount());
                        r.setAprilExpendedAmount(BigDecimal.ZERO);
                        r.setMayBudgetAmount(real.getMayBudgetAmount());
                        r.setMayExpendedAmount(BigDecimal.ZERO);
                        r.setJuneBudgetAmount(real.getJuneBudgetAmount());
                        r.setJuneExpendedAmount(BigDecimal.ZERO);
                        r.setJulyBudgetAmount(real.getJulyBudgetAmount());
                        r.setJulyExpendedAmount(BigDecimal.ZERO);
                        r.setAugustBudgetAmount(real.getAugustBudgetAmount());
                        r.setAugustExpendedAmount(BigDecimal.ZERO);
                        r.setSeptemberBudgetAmount(real.getSeptemberBudgetAmount());
                        r.setSeptemberExpendedAmount(BigDecimal.ZERO);
                        r.setOctoberBudgetAmount(real.getOctoberBudgetAmount());
                        r.setOctoberExpendedAmount(BigDecimal.ZERO);
                        r.setNovemberBudgetAmount(real.getNovemberBudgetAmount());
                        r.setNovemberExpendedAmount(BigDecimal.ZERO);
                        r.setDecemberBudgetAmount(real.getDecemberBudgetAmount());
                        r.setDecemberExpendedAmount(BigDecimal.ZERO);
                        r.setTotalBudgetAmount(real.getTotalBudgetAmount());
                        r.setTotalExpendedAmount(BigDecimal.ZERO);
                        r.setCurrency(real.getCurrency());
                        r.setYear(yearToCopy);
                        r.setUsername(user.getUsername());
                        realBudgetSpendingService.update(r);
                    }
                }
            }
        } else {
            throw new ValidationException("El centro de costos a copiar ya se encuentra autorizado", "El centro de costos a copiar ya se encuentra autorizado");
        }
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(realBudgetSpendingService.findAll()));
    }

    @RequestMapping(value = "/find/{idCostCenter}/{idAccountingAccount}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetYearConcept(@PathVariable int idCostCenter, @PathVariable int idAccountingAccount) throws Exception {
        RealBudgetSpending realBudgetSpending = realBudgetSpendingService.findByAccountingAccountAndCostCenter(idCostCenter, idAccountingAccount);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(realBudgetSpending));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.RealBudgetSpendingDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.model.RealBudgetSpending;
import mx.bidg.service.RealBudgetSpendingService;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.DwEnterprisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class RealBudgetSpendingServiceImpl implements RealBudgetSpendingService {

    @Autowired
    RealBudgetSpendingDao budgetYearConceptDao;

    @Autowired
    BudgetsService budgetsService;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    DwEnterprisesService dwEnterprisesService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<RealBudgetSpending> saveList(String data, Integer idBudget, Users user) throws Exception {
        JsonNode budgetYearConceptListNode = mapper.readTree(data);
        LocalDateTime now = LocalDateTime.now();

        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
        Double totalBudgetAmount = 0.0;
        for (JsonNode budgetYearConceptNode : budgetYearConceptListNode) {

            if (budgetYearConceptNode.has("idBudgetYearConcept")) {
                RealBudgetSpending realBudgetSpending = budgetYearConceptDao.findById(budgetYearConceptNode.get("idBudgetYearConcept").asInt());
                realBudgetSpending.setUsername(user.getUsername());
                realBudgetSpending.setJanuaryBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("januaryBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getJanuaryBudgetAmount()));
                realBudgetSpending.setFebruaryBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("februaryBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getFebruaryBudgetAmount()));
                realBudgetSpending.setMarchBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("marchBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getMarchBudgetAmount()));
                realBudgetSpending.setAprilBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("aprilBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getAprilBudgetAmount()));
                realBudgetSpending.setMayBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("mayBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getMayBudgetAmount()));
                realBudgetSpending.setJuneBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("juneBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getJuneBudgetAmount()));
                realBudgetSpending.setJulyBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("julyBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getJulyBudgetAmount()));
                realBudgetSpending.setAugustBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("augustBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getAugustBudgetAmount()));
                realBudgetSpending.setSeptemberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("septemberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getSeptemberBudgetAmount()));
                realBudgetSpending.setOctoberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("octoberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getOctoberBudgetAmount()));
                realBudgetSpending.setNovemberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("novemberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getNovemberBudgetAmount()));
                realBudgetSpending.setDecemberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("decemberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getDecemberBudgetAmount()));
                BigDecimal totalBudget = new BigDecimal(totalBudgetAmount);
                realBudgetSpending.setTotalBudgetAmount(totalBudget);
                realBudgetSpendingList.add(realBudgetSpending);
                budgetYearConceptDao.update(realBudgetSpending);
            } else {
                RealBudgetSpending realBudgetSpending = new RealBudgetSpending();
                realBudgetSpending.setUsername(user.getUsername());
                realBudgetSpending.setBudget(budgetsDao.findById(idBudget));
                realBudgetSpending.setCreationDate(now);
                realBudgetSpending.setCurrency(CCurrencies.MXN);
                realBudgetSpending.setYear(budgetYearConceptNode.get("year").asInt());
                realBudgetSpending.setJanuaryBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("januaryBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getJanuaryBudgetAmount()));
                realBudgetSpending.setFebruaryBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("februaryBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getFebruaryBudgetAmount()));
                realBudgetSpending.setMarchBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("marchBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getMarchBudgetAmount()));
                realBudgetSpending.setAprilBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("aprilBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getAprilBudgetAmount()));
                realBudgetSpending.setMarchBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("mayBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getMayBudgetAmount()));
                realBudgetSpending.setJuneBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("juneBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getJuneBudgetAmount()));
                realBudgetSpending.setJulyBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("julyBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getJulyBudgetAmount()));
                realBudgetSpending.setAugustBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("augustBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getAugustBudgetAmount()));
                realBudgetSpending.setSeptemberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("septemberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getSeptemberBudgetAmount()));
                realBudgetSpending.setOctoberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("octoberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getOctoberBudgetAmount()));
                realBudgetSpending.setNovemberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("novemberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getNovemberBudgetAmount()));
                realBudgetSpending.setDecemberBudgetAmount(mapper.treeToValue(budgetYearConceptNode.get("decemberBudgetAmount"), BigDecimal.class));
                totalBudgetAmount += new Double(String.valueOf(realBudgetSpending.getDecemberBudgetAmount()));
                BigDecimal totalBudget = new BigDecimal(totalBudgetAmount);
                realBudgetSpending.setTotalBudgetAmount(totalBudget);
                realBudgetSpendingList.add(realBudgetSpending);
                budgetYearConceptDao.save(realBudgetSpending);
            }
        }

        return realBudgetSpendingList;
    }

    @Override
    public List<RealBudgetSpending> updateList(String data, Integer idBudget, Users user) throws Exception {
        JsonNode budgetYearConceptListNode = mapper.readTree(data);
        LocalDateTime now = LocalDateTime.now();

        List<RealBudgetSpending> realBudgetSpendingList = new ArrayList<>();
        Double totalBudgetAmount = 0.0;
        for (JsonNode budgetYearConceptNode : budgetYearConceptListNode) {
            RealBudgetSpending realBudgetSpending = budgetYearConceptDao.findById(budgetYearConceptNode.get("idBudgetYearConcept").asInt());
            realBudgetSpending.setUsername(user.getUsername());
            realBudgetSpending.setJanuaryBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setFebruaryBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setMarchBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setAprilBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setMayBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setJuneBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setJulyBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setAugustBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setSeptemberBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setOctoberBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setNovemberBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setDecemberBudgetAmount(BigDecimal.ZERO);
            realBudgetSpending.setTotalBudgetAmount(BigDecimal.ZERO);
            realBudgetSpendingList.add(realBudgetSpending);
            budgetYearConceptDao.update(realBudgetSpending);
        }

        return realBudgetSpendingList;
    }

    @Override
    public RealBudgetSpending findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year) {
        return budgetYearConceptDao.findByCombination(new Budgets(budget),
                new CMonths(month), new DwEnterprises(dwEnterprise), year);
    }

    @Override
    public RealBudgetSpending findFromRequest(String data) throws Exception {

        JsonNode jsonRequest = mapper.readTree(data);
        int idGroup = jsonRequest.get("idGroup").asInt();
        int idDistributor = jsonRequest.get("idDistributor").asInt();
        int idRegion = jsonRequest.get("idRegion").asInt();
        int idBranch = jsonRequest.get("idBranch").asInt();
        int idArea = jsonRequest.get("idArea").asInt();
        int month = jsonRequest.get("idMonth").asInt();
        int year = jsonRequest.get("year").asInt();
        int idCategory = jsonRequest.get("idCategory").asInt();
        int idSubcategory = jsonRequest.get("idSubcategory").asInt();

        //Cambios para busqueda de request
        int idAccountingAccount = jsonRequest.get("idAccountingAccount").asInt();

        DwEnterprises dwEnterprise = dwEnterprisesService.findByCombination(idGroup, idDistributor, idRegion, idBranch, idArea);
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idAccountingAccount);

        if (dwEnterprise == null || budget == null) {
            return null;
        }

        RealBudgetSpending realBudgetSpending = budgetYearConceptDao.findByCombination(budget, new CMonths(month), dwEnterprise, year);

        return realBudgetSpending;
    }

    @Override
    public RealBudgetSpending update(RealBudgetSpending realBudgetSpending) {
        return budgetYearConceptDao.update(realBudgetSpending);
    }

    @Override
    public List<RealBudgetSpending> findByBudgetsAndYear(List<Budgets> budgets, Integer year) {
        return budgetYearConceptDao.findByBudgetsAndYear(budgets, year);
    }

    @Override
    public List<RealBudgetSpending> findByBudgetAndYear(Integer idBudget, Integer year) {
        return budgetYearConceptDao.findByBudgetAndYear(idBudget, year);
    }

    @Override
    public RealBudgetSpending findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year) {
        return budgetYearConceptDao.findByBudgetMonthAndYear(idBudget, idMonth, year);
    }

    @Override
    public String authorizeBudget(String data) throws Exception {

        JsonNode json = mapper.readTree(data);

        Integer idBudgetCategory = null;
        Integer idCostCenter = json.get("idCostCenter").asInt();
        Integer idBudgetType = json.get("idBudgetType").asInt();
        Integer idBudgetNature = json.get("idBudgetNature").asInt();
        Integer year = json.get("year").asInt();

        if (json.has("idBudgetCategory")) {
            idBudgetCategory = json.get("idBudgetCategory").asInt();
        }

        List<Budgets> budgets = budgetsDao.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);

        for (Budgets budget : budgets) {
            List<RealBudgetSpending> realBudgetSpendingList = budgetYearConceptDao.findByBudgetAndYear(budget.getIdBudget(), year);
            for (RealBudgetSpending realBudgetSpending : realBudgetSpendingList) {
                //realBudgetSpending.setAuthorized(true);
                budgetYearConceptDao.update(realBudgetSpending);
            }
        }

        return "Presupuesto autorizado";
    }

    @Override
    public List<RealBudgetSpending> findByDWEnterpriseAndYear(Integer dwEnterprise, Integer year) throws Exception {
        return budgetYearConceptDao.findByDWEnterpriseAndYear(dwEnterprise, year);
    }

    @Override
    public RealBudgetSpending saveBudgetMonthBranch(RealBudgetSpending bmb){
        return budgetYearConceptDao.save(bmb);
    }

    @Override
    public Boolean delete(Integer idBudgetYearConcept) {
        RealBudgetSpending realBudgetSpending = budgetYearConceptDao.findById(idBudgetYearConcept);
        return budgetYearConceptDao.delete(realBudgetSpending);
    }

    @Override
    public List<RealBudgetSpending> copyBudget(Integer idCostCenter, Integer idBudgetType, Integer idBudgetCategory, Integer yearFromCopy, Integer yearToCopy, Boolean overwrite, Integer idBudgetNature, Users user) {

        LocalDateTime now = LocalDateTime.now();
        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);
        List<RealBudgetSpending> realBudgetYearConceptYearToCopyList = budgetYearConceptDao.findByBudgetsAndYear(budgets, yearToCopy);

        if (!realBudgetYearConceptYearToCopyList.isEmpty() && !overwrite) {
            throw new ValidationException(
                    "Presupuesto asignado",
                    "Ya hay presupuesto asignado para el año " + yearToCopy + ". ¿Desea sobreescribir los datos?"
            );
        } else {

            List<RealBudgetSpending> realBudgetYearConceptYearFromCopyList = budgetYearConceptDao.findByBudgetsAndYear(budgets, yearFromCopy);

            for (RealBudgetSpending realBudgetSpendingToCopy : realBudgetYearConceptYearToCopyList) {
                budgetYearConceptDao.delete(realBudgetSpendingToCopy);
            }

            for (RealBudgetSpending realBudgetSpending : realBudgetYearConceptYearFromCopyList) {
                RealBudgetSpending newRealBudgetSpending = new RealBudgetSpending();
                newRealBudgetSpending.setBudget(realBudgetSpending.getBudget());
                newRealBudgetSpending.setCreationDate(now);
                newRealBudgetSpending.setCurrency(realBudgetSpending.getCurrency());
                newRealBudgetSpending.setUsername(user.getUsername());
                newRealBudgetSpending.setYear(yearToCopy);
                newRealBudgetSpending.setJanuaryBudgetAmount(realBudgetSpending.getJanuaryBudgetAmount());
                newRealBudgetSpending.setFebruaryBudgetAmount(realBudgetSpending.getFebruaryBudgetAmount());
                newRealBudgetSpending.setMarchBudgetAmount(realBudgetSpending.getMarchBudgetAmount());
                newRealBudgetSpending.setAprilBudgetAmount(realBudgetSpending.getAprilBudgetAmount());
                newRealBudgetSpending.setMayBudgetAmount(realBudgetSpending.getMayBudgetAmount());
                newRealBudgetSpending.setJuneBudgetAmount(realBudgetSpending.getJuneBudgetAmount());
                newRealBudgetSpending.setJulyBudgetAmount(realBudgetSpending.getJulyBudgetAmount());
                newRealBudgetSpending.setAugustBudgetAmount(realBudgetSpending.getAugustBudgetAmount());
                newRealBudgetSpending.setSeptemberBudgetAmount(realBudgetSpending.getSeptemberBudgetAmount());
                newRealBudgetSpending.setOctoberBudgetAmount(realBudgetSpending.getOctoberBudgetAmount());
                newRealBudgetSpending.setNovemberBudgetAmount(realBudgetSpending.getNovemberBudgetAmount());
                newRealBudgetSpending.setDecemberBudgetAmount(realBudgetSpending.getDecemberBudgetAmount());

                budgetYearConceptDao.save(newRealBudgetSpending);
            }
        }

        return budgetYearConceptDao.findByBudgetsAndYear(budgets, yearToCopy);
    }

    @Override
    public RealBudgetSpending findByAccountingAccountAndCostCenter(int idCostCenter, int idAccountingAccount) {
        return budgetYearConceptDao.findByAccountingAccountAndCostCenter(idCostCenter, idAccountingAccount);
    }

    @Override
    public RealBudgetSpending save(RealBudgetSpending realBudgetSpending) {
        return budgetYearConceptDao.save(realBudgetSpending);
    }

    @Override
    public List<RealBudgetSpending> findAll() {
        return budgetYearConceptDao.findAll();
    }

    @Override
    public List<RealBudgetSpending> findByBudgetAndYearAndNoAuthorized(Integer idBudget, Integer year, boolean authorized) {
        return budgetYearConceptDao.findByBudgetAndYearAndNoAuthorized(idBudget,year,authorized);
    }

    @Override
    public RealBudgetSpending findByIdBudgetAndYear(Integer idBudget, Integer year) {
        return budgetYearConceptDao.findByIdBudgetAndYear(idBudget, year);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.BudgetYearConceptDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.CBudgetConceptsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.model.BudgetYearConcept;
import mx.bidg.service.BudgetYearConceptService;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.DwEnterprisesService;
import mx.bidg.utils.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class BudgetYearConceptServiceImpl implements BudgetYearConceptService {

    @Autowired
    BudgetYearConceptDao budgetYearConceptDao;

    @Autowired
    BudgetsService budgetsService;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    DwEnterprisesService dwEnterprisesService;
    
    @Autowired
    CBudgetConceptsService cBudgetConceptsService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<BudgetYearConcept> saveList(String data, Integer idBudget, Users user) throws Exception {
        JsonNode budgetYearConceptListNode = mapper.readTree(data);
        LocalDateTime now = LocalDateTime.now();

        List<BudgetYearConcept> budgetYearConceptList = new ArrayList<>();

        for (JsonNode budgetYearConceptNode : budgetYearConceptListNode) {

            if (budgetYearConceptNode.has("idBudgetYearConcept")) {
                BudgetYearConcept budgetYearConcept = budgetYearConceptDao.findById(budgetYearConceptNode.get("idBudgetYearConcept").asInt());
                budgetYearConcept.setUsername(user.getUsername());
                budgetYearConcept.setBudgetConcept(mapper.treeToValue(budgetYearConceptNode.get("budgetConcept"), CBudgetConcepts.class));
                budgetYearConcept.setJanuaryAmount(mapper.treeToValue(budgetYearConceptNode.get("januaryAmount"), BigDecimal.class));
                budgetYearConcept.setFebruaryAmount(mapper.treeToValue(budgetYearConceptNode.get("februaryAmount"), BigDecimal.class));
                budgetYearConcept.setMarchAmount(mapper.treeToValue(budgetYearConceptNode.get("marchAmount"), BigDecimal.class));
                budgetYearConcept.setAprilAmount(mapper.treeToValue(budgetYearConceptNode.get("aprilAmount"), BigDecimal.class));
                budgetYearConcept.setMayAmount(mapper.treeToValue(budgetYearConceptNode.get("mayAmount"), BigDecimal.class));
                budgetYearConcept.setJuneAmount(mapper.treeToValue(budgetYearConceptNode.get("juneAmount"), BigDecimal.class));
                budgetYearConcept.setJulyAmount(mapper.treeToValue(budgetYearConceptNode.get("julyAmount"), BigDecimal.class));
                budgetYearConcept.setAugustAmount(mapper.treeToValue(budgetYearConceptNode.get("augustAmount"), BigDecimal.class));
                budgetYearConcept.setSeptemberAmount(mapper.treeToValue(budgetYearConceptNode.get("septemberAmount"), BigDecimal.class));
                budgetYearConcept.setOctoberAmount(mapper.treeToValue(budgetYearConceptNode.get("octoberAmount"), BigDecimal.class));
                budgetYearConcept.setNovemberAmount(mapper.treeToValue(budgetYearConceptNode.get("novemberAmount"), BigDecimal.class));
                budgetYearConcept.setDecemberAmount(mapper.treeToValue(budgetYearConceptNode.get("decemberAmount"), BigDecimal.class));

                budgetYearConceptList.add(budgetYearConcept);
                budgetYearConceptDao.update(budgetYearConcept);
            } else {
                BudgetYearConcept budgetYearConcept = new BudgetYearConcept();
                budgetYearConcept.setUsername(user.getUsername());
                budgetYearConcept.setBudget(budgetsDao.findById(idBudget));
                budgetYearConcept.setAuthorized(false);
                budgetYearConcept.setCreationDate(now);
                budgetYearConcept.setCurrency(CCurrencies.MXN);
                budgetYearConcept.setIdAccessLevel(1);
                budgetYearConcept.setYear(budgetYearConceptNode.get("year").asInt());
                budgetYearConcept.setBudgetConcept(mapper.treeToValue(budgetYearConceptNode.get("budgetConcept"), CBudgetConcepts.class));
                budgetYearConcept.setJanuaryAmount(mapper.treeToValue(budgetYearConceptNode.get("januaryAmount"), BigDecimal.class));
                budgetYearConcept.setFebruaryAmount(mapper.treeToValue(budgetYearConceptNode.get("februaryAmount"), BigDecimal.class));
                budgetYearConcept.setMarchAmount(mapper.treeToValue(budgetYearConceptNode.get("marchAmount"), BigDecimal.class));
                budgetYearConcept.setAprilAmount(mapper.treeToValue(budgetYearConceptNode.get("aprilAmount"), BigDecimal.class));
                budgetYearConcept.setMayAmount(mapper.treeToValue(budgetYearConceptNode.get("mayAmount"), BigDecimal.class));
                budgetYearConcept.setJuneAmount(mapper.treeToValue(budgetYearConceptNode.get("juneAmount"), BigDecimal.class));
                budgetYearConcept.setJulyAmount(mapper.treeToValue(budgetYearConceptNode.get("julyAmount"), BigDecimal.class));
                budgetYearConcept.setAugustAmount(mapper.treeToValue(budgetYearConceptNode.get("augustAmount"), BigDecimal.class));
                budgetYearConcept.setSeptemberAmount(mapper.treeToValue(budgetYearConceptNode.get("septemberAmount"), BigDecimal.class));
                budgetYearConcept.setOctoberAmount(mapper.treeToValue(budgetYearConceptNode.get("octoberAmount"), BigDecimal.class));
                budgetYearConcept.setNovemberAmount(mapper.treeToValue(budgetYearConceptNode.get("novemberAmount"), BigDecimal.class));
                budgetYearConcept.setDecemberAmount(mapper.treeToValue(budgetYearConceptNode.get("decemberAmount"), BigDecimal.class));

                budgetYearConceptList.add(budgetYearConcept);
                budgetYearConceptDao.save(budgetYearConcept);
            }
        }

        return budgetYearConceptList;
    }

    @Override
    public BudgetYearConcept findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year) {
        return budgetYearConceptDao.findByCombination(new Budgets(budget),
                new CMonths(month), new DwEnterprises(dwEnterprise), year);
    }

    @Override
    public BudgetYearConcept findFromRequest(String data) throws Exception {

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

        BudgetYearConcept budgetYearConcept = budgetYearConceptDao.findByCombination(budget, new CMonths(month), dwEnterprise, year);

        return budgetYearConcept;
    }

    @Override
    public BudgetYearConcept update(BudgetYearConcept budgetYearConcept) {
        return budgetYearConceptDao.update(budgetYearConcept);
    }

    @Override
    public List<BudgetYearConcept> findByBudgetsAndYear(List<Budgets> budgets, Integer year) {
        return budgetYearConceptDao.findByBudgetsAndYear(budgets, year);
    }

    @Override
    public List<BudgetYearConcept> findByBudgetAndYear(Integer idBudget, Integer year) {
        return budgetYearConceptDao.findByBudgetAndYear(idBudget, year);
    }

    @Override
    public BudgetYearConcept findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year) {
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
            List<BudgetYearConcept> budgetYearConceptList = budgetYearConceptDao.findByBudgetAndYear(budget.getIdBudget(), year);
            for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                budgetYearConcept.setAuthorized(true);
                budgetYearConceptDao.update(budgetYearConcept);
            }
        }

        return "Presupuesto autorizado";
    }

    @Override
    public List<BudgetYearConcept> findByDWEnterpriseAndYear(Integer dwEnterprise, Integer year) throws Exception {
        return budgetYearConceptDao.findByDWEnterpriseAndYear(dwEnterprise, year);
    }
    
    @Override
    public BudgetYearConcept saveBudgetMonthBranch(BudgetYearConcept bmb){
        return budgetYearConceptDao.save(bmb);
    }

    @Override
    public Boolean delete(Integer idBudgetYearConcept) {
        BudgetYearConcept budgetYearConcept = budgetYearConceptDao.findById(idBudgetYearConcept);
        return budgetYearConceptDao.delete(budgetYearConcept);
    }

    @Override
    public List<BudgetYearConcept> copyBudget(Integer idCostCenter, Integer idBudgetType, Integer idBudgetCategory, Integer yearFromCopy, Integer yearToCopy, Boolean overwrite, Integer idBudgetNature, Users user) {

        LocalDateTime now = LocalDateTime.now();
        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);
        List<BudgetYearConcept> budgetYearConceptYearToCopyList = budgetYearConceptDao.findByBudgetsAndYear(budgets, yearToCopy);

        if (!budgetYearConceptYearToCopyList.isEmpty() && !overwrite) {
            throw new ValidationException(
                    "Presupuesto asignado",
                    "Ya hay presupuesto asignado para el año " + yearToCopy + ". ¿Desea sobreescribir los datos?"
            );
        } else {

            List<BudgetYearConcept> budgetYearConceptYearFromCopyList = budgetYearConceptDao.findByBudgetsAndYear(budgets, yearFromCopy);

            for (BudgetYearConcept budgetYearConceptToCopy : budgetYearConceptYearToCopyList) {
                budgetYearConceptDao.delete(budgetYearConceptToCopy);
            }

            for (BudgetYearConcept budgetYearConcept : budgetYearConceptYearFromCopyList) {
                BudgetYearConcept newBudgetYearConcept = new BudgetYearConcept();
                newBudgetYearConcept.setBudget(budgetYearConcept.getBudget());
                newBudgetYearConcept.setCreationDate(now);
                newBudgetYearConcept.setCurrency(budgetYearConcept.getCurrency());
                newBudgetYearConcept.setBudgetConcept(budgetYearConcept.getBudgetConcept());
                newBudgetYearConcept.setUsername(user.getUsername());
                newBudgetYearConcept.setYear(yearToCopy);
                newBudgetYearConcept.setIdAccessLevel(1);
                newBudgetYearConcept.setAuthorized(false);
                newBudgetYearConcept.setJanuaryAmount(budgetYearConcept.getJanuaryAmount());
                newBudgetYearConcept.setFebruaryAmount(budgetYearConcept.getFebruaryAmount());
                newBudgetYearConcept.setMarchAmount(budgetYearConcept.getMarchAmount());
                newBudgetYearConcept.setAprilAmount(budgetYearConcept.getAprilAmount());
                newBudgetYearConcept.setMayAmount(budgetYearConcept.getMayAmount());
                newBudgetYearConcept.setJuneAmount(budgetYearConcept.getJuneAmount());
                newBudgetYearConcept.setJulyAmount(budgetYearConcept.getJulyAmount());
                newBudgetYearConcept.setAugustAmount(budgetYearConcept.getAugustAmount());
                newBudgetYearConcept.setSeptemberAmount(budgetYearConcept.getSeptemberAmount());
                newBudgetYearConcept.setOctoberAmount(budgetYearConcept.getOctoberAmount());
                newBudgetYearConcept.setNovemberAmount(budgetYearConcept.getNovemberAmount());
                newBudgetYearConcept.setDecemberAmount(budgetYearConcept.getDecemberAmount());

                budgetYearConceptDao.save(newBudgetYearConcept);
            }
        }

        return budgetYearConceptDao.findByBudgetsAndYear(budgets, yearToCopy);
    }

}

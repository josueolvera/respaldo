/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.model.*;
import mx.bidg.pojos.BudgetCategory;

/**
 *
 * @author sistemask
 */
public interface BudgetsService {

    Budgets saveBudget(Budgets budgets);
    Budgets findById(Integer idBudget);
    Budgets findByCombination(Integer idDistributor, Integer idArea, Integer idAccountingAccount);
    ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise);
    ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea);
    List<Budgets> findByDistributorAndArea(Integer idDistributor, Integer idArea);
    List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, Integer idBudgetCategory);
    List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise);
    List<Budgets> findByDistributor(Integer idDistributor);
    void createReport(List<BudgetCategory> budgetCategories, CCostCenter costCenter, Integer year, OutputStream outputStream) throws IOException;
    ArrayList<Budgets> findByCostCenter(Integer idCostCenter);
    List<Budgets> getBudgetsfindNatureTypeAndCostCenter(Integer idCostCenter,Integer idBudgetType, Integer idBudgetNature);
    Budgets getBudgetByNatureAndCostAndTypeAndConcept(Integer idCostCenter,Integer idBudgetType, Integer idBudgetNature, Integer idConceptBudget);
    Budgets update(Budgets budgets);
    List<Budgets> findByIdDistributorCostCenter(Integer idDistributorCostCenter, Integer idBudgetType, Integer idBudgetNature);
    Budgets findByNatureTypeAndDistributor(Integer idBudgetNature, Integer idBudgetType, Integer idDistributorCostCenter);
    List<Budgets> findByIdDistributor(Integer idDistributorCostCenter);
}

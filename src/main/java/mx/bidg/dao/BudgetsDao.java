/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.ArrayList;
import java.util.List;

import mx.bidg.model.*;

/**
 *
 * @author sistemask
 */
public interface BudgetsDao extends InterfaceDao<Budgets> {

    Budgets findByCombination(CDistributors cDistributors, CAreas idArea, AccountingAccounts accountingAccounts);

    ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea);

    List<Budgets> findByDistributor(Integer idDistributor);

    List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, Integer idBudgetCategory);

    List<Budgets> findByDistributorAndArea(Integer idDistributor, Integer idArea);

    List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise);

    ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise);

    Budgets findByAccountingAccountAndCostCenter(Integer idAccountingAccount, Integer idCostCenter);

    ArrayList<Budgets> findByCostCenter(Integer idCostCenter);

    List<Budgets> getBudgetsByCostCenterAndRequestCategory(Integer idCostCenter, Integer idRequestCategory);

    Budgets getBudgetForRequest(int idCostCenter, int idBudgetCategory, int idBudgetSubcategory, int idRequestCategory);

    List<Budgets> getBudgetsfindNatureTypeAndCostCenter(Integer idCostCenter,Integer idBudgetType, Integer idBudgetNature);

    Budgets getBudgetByNatureAndCostAndTypeAndConcept(Integer idCostCenter,Integer idBudgetType, Integer idBudgetNature, Integer idConceptBudget);

    List<Budgets> findByIdDistributorCostCenter(Integer idDistributorCostCenter, Integer idBudgetType, Integer idBudgetNature);

    Budgets findByNatureTypeAndDistributor(Integer idBudgetNature, Integer idBudgetType, Integer idDistributorCostCenter);

    List<Budgets> findByIdDistributor(Integer idDistributorCostCenter);

    List<Integer> findBudgetNatureByType(Integer idBudgetType);
}

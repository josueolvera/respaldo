/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.ArrayList;
import java.util.List;

import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CGroups;

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
    List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature);
    List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetCategory);
    List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise);
    List<Budgets> findByDistributor(Integer idDistributor);
}

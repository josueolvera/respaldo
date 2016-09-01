/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.ArrayList;
import java.util.List;
import mx.bidg.model.AccountingAccounts;

import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CGroups;

/**
 *
 * @author sistemask
 */
public interface BudgetsDao extends InterfaceDao<Budgets> {
    
    /**
     *
     * @param cDistributors
     * @param idArea
     * @param accountingAccounts
     * @param idCategory
     * @param idSubcategory
     * @return
     */
    Budgets findByCombination(CDistributors cDistributors, CAreas idArea, AccountingAccounts accountingAccounts);
    ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea);
    List<Budgets> findByDistributor(Integer idDistributor);
    List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature);
    List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetCategory);
    List<Budgets> findByDistributorAndArea(Integer idDistributor, Integer idArea);
    List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise);
    ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise);
    
}

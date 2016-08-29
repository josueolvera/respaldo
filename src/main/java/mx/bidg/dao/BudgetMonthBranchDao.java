/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.Budgets;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthBranchDao extends InterfaceDao<BudgetMonthBranch> {
    
    BudgetMonthBranch findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year);
    BudgetMonthBranch findByCombination(Budgets budget, CMonths month, Integer year);
    List<BudgetMonthBranch> findByBudgetsAndYear(List<Budgets> budgets, Integer year);
    List<BudgetMonthBranch> findByBudget(Budgets budget);
    boolean authorizeBudget(int idDistributor, int idArea, int year);
    List<BudgetMonthBranch> findByDWEnterpriseAndYear(int dwEnterprise, int year);
    
}

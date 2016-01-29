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
    
    public BudgetMonthBranch findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year);
    public List<BudgetMonthBranch> findByBudget(Budgets budget);
    public boolean authorizeBudget(int idBudget, int year);
    
}

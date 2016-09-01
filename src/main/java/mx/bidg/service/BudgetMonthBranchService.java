/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.Budgets;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthBranchService {
    
    List<BudgetMonthBranch> saveList(String data) throws Exception;
    
    BudgetMonthBranch findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year);
    
    BudgetMonthBranch findFromRequest(String data) throws Exception;
    
    BudgetMonthBranch update(BudgetMonthBranch budgetMonthBranch);

    List<BudgetMonthBranch> findByBudgetsAndYear(List<Budgets> budgets, Integer year);

    List<BudgetMonthBranch> findByBudgetAndYear(Integer idBudget, Integer year);
    
    String authorizeBudget(String data) throws Exception;
    
    List<BudgetMonthBranch> findByDWEnterpriseAndYear(Integer dwEnterprise, Integer year) throws Exception;
    
    BudgetMonthBranch saveBudgetMonthBranch(BudgetMonthBranch bmb);

    Boolean delete(BudgetMonthBranch budgetMonthBranch);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.CRequestTypes;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthBranchService {
    
    public List<BudgetMonthBranch> saveList(String data) throws Exception;
    
    public BudgetMonthBranch findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year);
    
    public BudgetMonthBranch findFromRequest(String data) throws Exception;
    
    public BudgetMonthBranch update(BudgetMonthBranch budgetMonthBranch);
    
    public String authorizeBudget(String data) throws Exception;
    
    public List<BudgetMonthBranch> findByDWEnterpriseAndYear(Integer dwEnterprise, Integer year) throws Exception;
    
    public BudgetMonthBranch saveBudgetMonthBranch(BudgetMonthBranch bmb);
    
}

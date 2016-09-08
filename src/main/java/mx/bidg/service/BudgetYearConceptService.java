/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.model.Budgets;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface BudgetYearConceptService {
    
    List<BudgetYearConcept> saveList(String data, Integer idBudget, Users user) throws Exception;
    
    BudgetYearConcept findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year);
    
    BudgetYearConcept findFromRequest(String data) throws Exception;
    
    BudgetYearConcept update(BudgetYearConcept budgetYearConcept);

    List<BudgetYearConcept> findByBudgetsAndYear(List<Budgets> budgets, Integer year);

    List<BudgetYearConcept> findByBudgetAndYear(Integer idBudget, Integer year);

    BudgetYearConcept findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year);

    String authorizeBudget(String data) throws Exception;
    
    List<BudgetYearConcept> findByDWEnterpriseAndYear(Integer dwEnterprise, Integer year) throws Exception;
    
    BudgetYearConcept saveBudgetMonthBranch(BudgetYearConcept bmb);

    Boolean delete(Integer idBudgetYearConcept);

    List<BudgetYearConcept> copyBudget(Integer idCostCenter, Integer idBudgetType, Integer idBudgetCategory, Integer yearFromCopy, Integer yearToCopy, Boolean overwrite, Integer idBudgetNature, Users user);
}

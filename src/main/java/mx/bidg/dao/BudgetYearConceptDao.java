/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.model.Budgets;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;

/**
 *
 * @author sistemask
 */
public interface BudgetYearConceptDao extends InterfaceDao<BudgetYearConcept> {
    
    BudgetYearConcept findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year);
    BudgetYearConcept findByCombination(Budgets budget, CMonths month, Integer year);
    List<BudgetYearConcept> findByBudgetsAndYear(List<Budgets> budgets, Integer year);
    List<BudgetYearConcept> findByBudgetAndYear(Integer idBudget, Integer year);
    BudgetYearConcept findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year);
    List<BudgetYearConcept> findByBudget(Budgets budget);
    boolean authorizeBudget(int idDistributor, int idArea, int year);
    List<BudgetYearConcept> findByDWEnterpriseAndYear(int dwEnterprise, int year);
    
}

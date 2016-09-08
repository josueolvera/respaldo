/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;

import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthConceptsDao extends InterfaceDao<BudgetMonthConcepts> {
    
    List<BudgetMonthConcepts> findByConcept(CBudgetConcepts budgetConcept);
    List<BudgetMonthConcepts> findByConcept(Integer idBudgetConcept);
    List<BudgetMonthConcepts> findByBudgetMonthBranch(Integer idBudgetMonthBranch);

}

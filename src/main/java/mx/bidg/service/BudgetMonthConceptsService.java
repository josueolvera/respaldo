/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthConceptsService {
    
    public List<BudgetMonthConcepts> saveList(String data) throws Exception;
    public boolean delete(BudgetMonthConcepts budgetMonthConcepts);
    public List<BudgetMonthConcepts> findByConcept(CBudgetConcepts budgetConcept);
    
}

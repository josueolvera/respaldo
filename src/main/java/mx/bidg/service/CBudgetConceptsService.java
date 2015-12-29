/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;

/**
 *
 * @author sistemask
 */
public interface CBudgetConceptsService {
    
    public CBudgetConcepts save(CBudgetConcepts cBudgetConcept);
    public CBudgetConcepts findById(int idBudgetConcept);
    public CBudgetConcepts update(CBudgetConcepts cBudgetConcept);
    public List<CBudgetConcepts> findByBudget(Budgets budget);
    
}

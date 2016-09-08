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
    
    CBudgetConcepts save(CBudgetConcepts cBudgetConcept);
    CBudgetConcepts findById(int idBudgetConcept);
    CBudgetConcepts update(CBudgetConcepts cBudgetConcept);
    boolean delete(CBudgetConcepts cBudgetConcept);
    List<CBudgetConcepts> findByBudget(Budgets budget, int year);
    List<CBudgetConcepts> findAll();
    List<CBudgetConcepts> findByBudgetEnterprise(Budgets budget, int year, Integer idDwEnterprise);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;

import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthConceptsService {

    List<BudgetMonthConcepts> saveList(String data, Users user) throws Exception;
    boolean delete(BudgetMonthConcepts budgetMonthConcepts);
    List<BudgetMonthConcepts> findByConcept(Integer idBudgetConcept);
    BudgetMonthConcepts saveBudgetMonthConcepts (BudgetMonthConcepts bmc);
    List<BudgetMonthConcepts> findByBudgetMonthBranch(Integer idBudgetMonthBranch);
}

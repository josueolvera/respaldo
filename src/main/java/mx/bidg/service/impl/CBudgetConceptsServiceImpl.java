/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mx.bidg.service.CBudgetConceptsService;
import mx.bidg.dao.CBudgetConceptsDao;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.BudgetMonthBranchService;
import mx.bidg.service.BudgetMonthConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CBudgetConceptsServiceImpl implements CBudgetConceptsService {
    
    @Autowired
    CBudgetConceptsDao budgetConceptsDao;
    
    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;
    
    @Autowired
    BudgetMonthBranchService budgetMonthBranchService;

    @Override
    public CBudgetConcepts save(CBudgetConcepts cBudgetConcept) {
        return budgetConceptsDao.save(cBudgetConcept);
    }

    @Override
    public CBudgetConcepts findById(int idBudgetConcept) {
        return budgetConceptsDao.findById(idBudgetConcept);
    }

    @Override
    public CBudgetConcepts update(CBudgetConcepts cBudgetConcept) {
        return budgetConceptsDao.update(cBudgetConcept);
    }

    @Override
    public List<CBudgetConcepts> findByBudget(Budgets budget) {
        return budgetConceptsDao.findByBudget(budget);
    }

    @Override
    public boolean delete(CBudgetConcepts cBudgetConcept) {
        
        List<BudgetMonthConcepts> budgetMonthConceptsList = budgetMonthConceptsService.findByConcept(cBudgetConcept);
        cBudgetConcept = budgetMonthConceptsList.get(0).getBudgetConcept();
        
        for(BudgetMonthConcepts budgetMonthConcept : budgetMonthConceptsList) {
            
            BigDecimal amount = budgetMonthConcept.getAmount();
            BudgetMonthBranch budgetMonthBranch = budgetMonthConcept.getBudgetMonthBranch();
            budgetMonthBranch.setAmount(budgetMonthBranch.getAmount().subtract(amount));
            budgetMonthBranchService.update(budgetMonthBranch);
            budgetMonthConceptsService.delete(budgetMonthConcept);
            
        }
        return budgetConceptsDao.delete(cBudgetConcept);
    }
    
}

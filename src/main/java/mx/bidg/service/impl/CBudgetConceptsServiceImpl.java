package mx.bidg.service.impl;

import mx.bidg.dao.CBudgetConceptsDao;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.BudgetYearConceptService;
import mx.bidg.service.BudgetMonthConceptsService;
import mx.bidg.service.CBudgetConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CBudgetConceptsServiceImpl implements CBudgetConceptsService {
    
    @Autowired
    CBudgetConceptsDao budgetConceptsDao;
    
    @Autowired
    BudgetMonthConceptsService budgetMonthConceptsService;
    
    @Autowired
    BudgetYearConceptService budgetYearConceptService;

    @Override
    public CBudgetConcepts save(CBudgetConcepts cBudgetConcept) {
        cBudgetConcept.setIdAccessLevel(1);
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
    public List<CBudgetConcepts> findByBudget(Budgets budget, int year) {
        return budgetConceptsDao.findByBudget(budget, year);
    }

    @Override
    public List<CBudgetConcepts> findAll() {
        return budgetConceptsDao.findAll();
    }

    @Override
    public List<CBudgetConcepts> findByBudgetEnterprise(Budgets budget, int year, Integer idDwEnterprise) {
        return budgetConceptsDao.findByBudgetEnterprise(budget, year, idDwEnterprise);
    }

    @Override
    public boolean delete(CBudgetConcepts cBudgetConcept) {
        List<BudgetMonthConcepts> budgetMonthConceptsList = budgetMonthConceptsService.findByConcept(cBudgetConcept.getIdBudgetConcept());
        CBudgetConcepts concept = new CBudgetConcepts(cBudgetConcept.getIdBudgetConcept());

        for(BudgetMonthConcepts budgetMonthConcept : budgetMonthConceptsList) {
            
            BigDecimal amount = budgetMonthConcept.getAmount();
//            BudgetYearConcept budgetYearConcept = budgetMonthConcept.getBudgetYearConcept();
//            budgetYearConcept.setAmount(budgetYearConcept.getAmount().subtract(amount));
//            budgetYearConceptService.update(budgetYearConcept);
            budgetMonthConceptsService.delete(budgetMonthConcept);
            
        }
        return budgetConceptsDao.delete(concept);
    }
    
}

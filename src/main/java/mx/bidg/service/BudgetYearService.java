package mx.bidg.service;

import mx.bidg.model.BudgetYear;

/**
 * Created by gerardo8 on 29/09/16.
 */
public interface BudgetYearService {
    BudgetYear findByBudgetAndYear(Integer idBudget, Integer year);
    BudgetYear saveOrUpdate(Integer idBudget, Integer year);
    void updateAll();
}

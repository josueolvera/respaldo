package mx.bidg.dao;

import mx.bidg.model.BudgetYear;

/**
 * Created by gerardo8 on 14/09/16.
 */
public interface BudgetYearDao extends InterfaceDao<BudgetYear> {
    BudgetYear findByBudgetAndYear(Integer idBudget, Integer year);
}

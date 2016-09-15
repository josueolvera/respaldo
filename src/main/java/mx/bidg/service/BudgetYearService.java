package mx.bidg.service;

import mx.bidg.model.BudgetYear;
import mx.bidg.model.Users;

import java.io.IOException;

/**
 * Created by gerardo8 on 14/09/16.
 */
public interface BudgetYearService {

    BudgetYear findByBudgetAndYear(Integer idBudget, Integer year);

    BudgetYear save(BudgetYear budgetYear);

    BudgetYear findById(Integer idBudgetYear);

    BudgetYear update(BudgetYear budgetYear);
}

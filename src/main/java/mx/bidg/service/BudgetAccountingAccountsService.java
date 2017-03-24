package mx.bidg.service;

import mx.bidg.model.BudgetAccountingAccounts;

import java.util.List;

/**
 * Created by Kevin Salvador on 13/03/2017.
 */
public interface BudgetAccountingAccountsService {
    BudgetAccountingAccounts save(BudgetAccountingAccounts budgetAccountingAccounts);
    BudgetAccountingAccounts update(BudgetAccountingAccounts budgetAccountingAccounts);
    boolean delete(BudgetAccountingAccounts budgetAccountingAccounts);
    List<BudgetAccountingAccounts>findAll();
    BudgetAccountingAccounts findByConceptBudget(Integer idConceptBudget);
    BudgetAccountingAccounts findById(Integer id);
}

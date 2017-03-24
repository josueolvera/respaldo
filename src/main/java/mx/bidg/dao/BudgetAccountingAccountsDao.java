package mx.bidg.dao;

import mx.bidg.model.BudgetAccountingAccounts;

import java.util.List;

/**
 * Created by Kevin Salvador on 13/03/2017.
 */
public interface BudgetAccountingAccountsDao extends InterfaceDao<BudgetAccountingAccounts> {
    BudgetAccountingAccounts findByConceptBudget(Integer idConceptBudget);
}

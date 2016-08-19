package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.AccountingAccounts;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsDao extends InterfaceDao<AccountingAccounts> {
    AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel);
    List<AccountingAccounts> findAllCategories();
    List<AccountingAccounts> findByBudgetCategory(Integer idBudgetCategory);
}

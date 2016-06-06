package mx.bidg.dao;

import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsDao extends InterfaceDao<AccountingAccounts> {
    AccountingAccounts findByThreeLevels(CDistributors distributor, Integer firstLevel, Integer secondLevel, Integer thirdLevel);
}

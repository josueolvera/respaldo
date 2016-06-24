package mx.bidg.service;

import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsService {
    AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel);
    List<AccountingAccounts> findAll();
}

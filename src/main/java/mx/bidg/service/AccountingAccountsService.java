package mx.bidg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsService {
    AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel);
    List<AccountingAccounts> findByFirstLevel(Integer firstLevel);
    List<AccountingAccounts> findBySecondLevel(Integer secondLevel);
    List<AccountingAccounts> findByThirdLevel(Integer thirdLevel);
    List<AccountingAccounts> findByFirstAndSecondLevel(Integer firstLevel, Integer secondLevel);
    List<AccountingAccounts> findAll();
    AccountingAccounts save(String data) throws IOException;
    AccountingAccounts update(AccountingAccounts accountingAccounts);
    AccountingAccounts findById(Integer idAccountingAccount);
    List<AccountingAccounts> findAllCategories();
}

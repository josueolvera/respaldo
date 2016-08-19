package mx.bidg.service.impl;

import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;
import mx.bidg.service.AccountingAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
@Service
@Transactional
public class AccountingAccountsServiceImpl implements AccountingAccountsService {

    @Autowired
    private AccountingAccountsDao accountingAccountsDao;

    @Override
    public AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel) {
        return accountingAccountsDao.findByThreeLevels(firstLevel, secondLevel, thirdLevel);
    }

    @Override
    public List<AccountingAccounts> findByFirstLevel(Integer firstLevel) {
        return accountingAccountsDao.findByFirstLevel(firstLevel);
    }

    @Override
    public List<AccountingAccounts> findBySecondLevel(Integer secondLevel) {
        return accountingAccountsDao.findBySecondLevel(secondLevel);
    }

    @Override
    public List<AccountingAccounts> findByThirdLevel(Integer thirdLevel) {
        return accountingAccountsDao.findByThirdLevel(thirdLevel);
    }

    @Override
    public List<AccountingAccounts> findAll() {
        return accountingAccountsDao.findAll();
    }

    @Override
    public AccountingAccounts save(AccountingAccounts accountingAccounts) {
        accountingAccountsDao.save(accountingAccounts);
        return accountingAccounts;
    }

    @Override
    public AccountingAccounts update(AccountingAccounts accountingAccounts) {
        accountingAccountsDao.update(accountingAccounts);
        return accountingAccounts;
    }

    @Override
    public AccountingAccounts findById(Integer idAccountingAccount) {
        return accountingAccountsDao.findById(idAccountingAccount);
    }

    @Override
    public List<AccountingAccounts> findAllCategories() {
        return accountingAccountsDao.findAllCategories();
    }
}

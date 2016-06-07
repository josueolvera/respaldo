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
    public AccountingAccounts findByThreeLevels(CDistributors distributor, Integer firstLevel, Integer secondLevel, Integer thirdLevel) {
        return accountingAccountsDao.findByThreeLevels(distributor, firstLevel, secondLevel, thirdLevel);
    }

    @Override
    public List<AccountingAccounts> findAll() {
        return accountingAccountsDao.findAll();
    }
}

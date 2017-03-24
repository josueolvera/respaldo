package mx.bidg.service.impl;

import mx.bidg.dao.BudgetAccountingAccountsDao;
import mx.bidg.model.BudgetAccountingAccounts;
import mx.bidg.service.BudgetAccountingAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 13/03/2017.
 */
@Service
@Transactional
public class BudgetAccountingAccountsServiceImpl implements BudgetAccountingAccountsService{

    @Autowired
    private BudgetAccountingAccountsDao budgetAccountingAccountsDao;

    @Override
    public BudgetAccountingAccounts save(BudgetAccountingAccounts budgetAccountingAccounts) {
        return budgetAccountingAccountsDao.save(budgetAccountingAccounts);
    }

    @Override
    public BudgetAccountingAccounts update(BudgetAccountingAccounts budgetAccountingAccounts) {
        return budgetAccountingAccountsDao.update(budgetAccountingAccounts);
    }

    @Override
    public boolean delete(BudgetAccountingAccounts budgetAccountingAccounts) {
        return budgetAccountingAccountsDao.delete(budgetAccountingAccounts);
    }

    @Override
    public List<BudgetAccountingAccounts> findAll() {
        return budgetAccountingAccountsDao.findAll();
    }

    @Override
    public BudgetAccountingAccounts findByConceptBudget(Integer idConceptBudget) {
        return budgetAccountingAccountsDao.findByConceptBudget(idConceptBudget);
    }


    @Override
    public BudgetAccountingAccounts findById(Integer id) {
        return budgetAccountingAccountsDao.findById(id);
    }
}

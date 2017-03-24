/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.dao.CBudgetSubcategoriesDao;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.service.CBudgetSubcategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetSubcategoriesServiceImpl implements CBudgetSubcategoriesService {

    @Autowired
    CBudgetSubcategoriesDao cBudgetSubcategoriesDao;

    @Autowired
    AccountingAccountsDao accountingAccountsDao;

    @Override
    public List<CBudgetSubcategories> findAll() {
        return cBudgetSubcategoriesDao.findAll();
    }

    @Override
    public Set<CBudgetSubcategories> getByBudgetCategory(Integer idBudgetCategory) {

        List<AccountingAccounts> accountingAccounts = accountingAccountsDao.findByBudgetCategory(idBudgetCategory);
        Set<CBudgetSubcategories> budgetSubcategories = new HashSet<>();

        for (AccountingAccounts accountingAccount : accountingAccounts) {
            budgetSubcategories.add(accountingAccount.getBudgetSubcategory());
        }

        return budgetSubcategories;
    }

    @Override
    public CBudgetSubcategories save(CBudgetSubcategories budgetSubcategory) {
        return cBudgetSubcategoriesDao.save(budgetSubcategory);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mx.bidg.dao.BudgetsDao;
import mx.bidg.dao.CBudgetCategoriesDao;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.CBudgetCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetCategoriesServiceImpl implements CBudgetCategoriesService {
    
    @Autowired
    CBudgetCategoriesDao cBudgetCategoriesDao;

    @Autowired
    BudgetsDao budgetsDao;

    @Override
    public List<CBudgetCategories> findAll() {
        return cBudgetCategoriesDao.findAll();
    }

    @Override
    public List<CBudgetCategories> findAllRequest() {
        return cBudgetCategoriesDao.findAllRequest();
    }

    @Override
    public CBudgetCategories save(CBudgetCategories budgetCategory) {
        return cBudgetCategoriesDao.save(budgetCategory);
    }

    @Override
    public List<CBudgetCategories> getRequestCategories(Integer idCostCenter, Integer idRequestCategory) {

        List<Budgets> budgets = budgetsDao.getBudgetsByCostCenterAndRequestCategory(idCostCenter, idRequestCategory);

        return budgets.stream().map(budget -> budget.getAccountingAccount().getBudgetCategory()).collect(Collectors.toList());
    }

}

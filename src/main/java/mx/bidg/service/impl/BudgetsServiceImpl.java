/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.bidg.dao.BudgetsDao;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CGroups;
import mx.bidg.service.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class BudgetsServiceImpl implements BudgetsService {

    @Autowired
    BudgetsDao budgetsDao;

    @Override
    public Budgets saveBudget(Budgets budgets) {
        return budgetsDao.save(budgets);
    }

    @Override
    public Budgets findById(Integer idBudget) {
        return budgetsDao.findById(idBudget);
    }

    @Override
    public Budgets findByCombination(Integer idDistributor, Integer idArea, Integer idAccountingAccount) {
        return budgetsDao.findByCombination(new CDistributors(idDistributor), new CAreas(idArea), 
                new AccountingAccounts(idAccountingAccount));
    }

    @Override
    public ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea) {
        return budgetsDao.findByGroupArea(idGroup, idArea);
    }

    @Override
    public List<Budgets> findByDistributorAndArea(Integer idDistributor, Integer idArea) {
        return budgetsDao.findByDistributorAndArea(idDistributor, idArea);
    }

    @Override
    public List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature) {
        return budgetsDao.getBudgets(idCostCenter, idBudgetType, idBudgetNature);
    }

    @Override
    public List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetCategory) {
        return budgetsDao.getBudgets(idCostCenter, idBudgetCategory);
    }

    @Override
    public List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise) {
        return budgetsDao.findByDistributorAreaAndEnterprise(idDistributor, idArea, idDwEnterprise);
    }

    @Override
    public List<Budgets> findByDistributor(Integer idDistributor) {
        return budgetsDao.findByDistributor(idDistributor);
    }

    @Override
    public ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise) {
        return budgetsDao.findByGroupAreaEnterprise(idGroup, idArea, idDwEnterprise);
    }
    
}

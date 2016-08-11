/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import mx.bidg.dao.BudgetsDao;
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
    BudgetsDao dao;

    @Override
    public Budgets saveBudget(Budgets budgets) {
        return dao.save(budgets);
    }

    @Override
    public Budgets findByCombination(Integer idDistributor, Integer idArea, Integer idCategory, 
            Integer idSubcategory) {
        return dao.findByCombination(new CDistributors(idDistributor), new CAreas(idArea), 
                new CBudgetCategories(idCategory), new CBudgetSubcategories(idSubcategory));
    }

    @Override
    public ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea) {
        return dao.findByGroupArea(idGroup, idArea);
    }

    @Override
    public ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise) {
        return dao.findByGroupAreaEnterprise(idGroup, idArea, idDwEnterprise);
    }
    
}

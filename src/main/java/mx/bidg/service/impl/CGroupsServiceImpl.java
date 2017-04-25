/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.dao.CGroupsDao;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CGroups;
import mx.bidg.service.CGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CGroupsServiceImpl implements CGroupsService {
    
    @Autowired
    CGroupsDao dao;
    
    @Autowired
    BudgetsDao budgetsDao;

    @Override
    public List<CGroups> findAll() {
        return dao.findAll();
    }

    @Override
    public CGroups getByIdBudgetsCatalogs(Integer idGroup) {
        return dao.getByIdBudgetsCatalogs(idGroup);
    }

    @Override
    public CGroups getBudgetListByGroupsArea(Integer idGroup, Integer idArea) {
        CGroups group = dao.findById(idGroup);
        CAreas area = new CAreas(idArea);
        List<Budgets> budgets = budgetsDao.findByGroupArea(group, area);
//        group.setBudgetsList(budgets);
        return group;
    }

    @Override
    public CGroups save(CGroups cGroups) {
        return dao.save(cGroups) ;
    }

    @Override
    public CGroups update(CGroups cGroups) {
        return dao.update(cGroups);
    }

}

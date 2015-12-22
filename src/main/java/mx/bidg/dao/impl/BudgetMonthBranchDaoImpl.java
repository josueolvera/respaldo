/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.Budgets;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class BudgetMonthBranchDaoImpl extends AbstractDao<Integer, BudgetMonthBranch> implements BudgetMonthBranchDao{

    @Override
    public BudgetMonthBranch save(BudgetMonthBranch entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetMonthBranch findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BudgetMonthBranch> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BudgetMonthBranch update(BudgetMonthBranch entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetMonthBranch entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BudgetMonthBranch findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year) {
        
        HashMap<String,Object> map = new HashMap<>();
        map.put("idBudget", budget);
        map.put("idMonth", month);
        map.put("idDwEnterprise", dwEnterprise);
        map.put("year", year);
        
        BudgetMonthBranch budgetMonthBranch = (BudgetMonthBranch) createEntityCriteria().add(Restrictions.allEq(map)).uniqueResult();
        return budgetMonthBranch;
    }
    
}

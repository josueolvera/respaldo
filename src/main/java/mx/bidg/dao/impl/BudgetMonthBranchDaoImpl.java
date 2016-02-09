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
import org.hibernate.Criteria;
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
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetMonthBranch entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BudgetMonthBranch findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year) {
        
        HashMap<String,Object> map = new HashMap<>();
        map.put("budget", budget);
        map.put("month", month);
        map.put("dwEnterprise", dwEnterprise);
        map.put("year", year);
        
        BudgetMonthBranch budgetMonthBranch = (BudgetMonthBranch) createEntityCriteria().add(Restrictions.allEq(map)).uniqueResult();
        return budgetMonthBranch;
    }

    @Override
    public List<BudgetMonthBranch> findByBudget(Budgets budget) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("budget", budget));
        return (List<BudgetMonthBranch>) criteria.list();
    }

    @Override
    public boolean authorizeBudget(int idGroup, int idArea, int year) {
        globalTracer("UPDATE", new BudgetMonthBranch());
        String query = "update BUDGET_MONTH_BRANCH bmb "
                + "inner join BUDGETS b on bmb.ID_BUDGET = b.ID_BUDGET set IS_AUTHORIZED = 1 "
                + "where bmb.YEAR = :year and b.ID_GROUP = :idGroup and b.ID_AREA = :idArea";
        int updatedEntities = getSession()
                .createSQLQuery(query)
                .setInteger("idGroup", idGroup)
                .setInteger("idArea", idArea)
                .setInteger("year", year)
                .executeUpdate();
        return updatedEntities > 0;
    }
    
}

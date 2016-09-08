/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetYearConceptDao;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.model.Budgets;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
@SuppressWarnings("unchecked")
public class BudgetYearConceptDaoImpl extends AbstractDao<Integer, BudgetYearConcept> implements BudgetYearConceptDao {

    @Override
    public BudgetYearConcept save(BudgetYearConcept entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetYearConcept findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BudgetYearConcept> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BudgetYearConcept update(BudgetYearConcept entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetYearConcept entity) {
        remove(entity);
        return true;
    }

    @Override
    public BudgetYearConcept findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year) {
        
        HashMap<String,Object> map = new HashMap<>();
        map.put("budget", budget);
        map.put("month", month);
        map.put("dwEnterprise", dwEnterprise);
        map.put("year", year);
        
        BudgetYearConcept budgetYearConcept = (BudgetYearConcept) createEntityCriteria().add(Restrictions.allEq(map)).uniqueResult();
        return budgetYearConcept;
    }

    @Override
    public BudgetYearConcept findByCombination(Budgets budget, CMonths month, Integer year) {
        return (BudgetYearConcept) createEntityCriteria()
                .add(Restrictions.eq("budget",budget))
                .add(Restrictions.eq("month",month))
                .add(Restrictions.eq("year",year))
                .uniqueResult();
    }

    @Override
    public List<BudgetYearConcept> findByBudgetsAndYear(List<Budgets> budgets, Integer year) {
        Disjunction disjunction = Restrictions.disjunction();
        Criteria criteria = createEntityCriteria();

        for (Budgets budget : budgets) {
            disjunction.add(Restrictions.eq("idBudget", budget.getIdBudget()));
        }

        criteria.add(Restrictions.eq("year",year))
                .add(disjunction);

        return criteria.list();
    }

    @Override
    public List<BudgetYearConcept> findByBudgetAndYear(Integer idBudget, Integer year) {

        Criteria criteria = createEntityCriteria();

        if (idBudget != null) {
            criteria.add(Restrictions.eq("idBudget", idBudget));
        }

        if (year != null) {
            criteria.add(Restrictions.eq("year", year));
        }

        return criteria.list();
    }

    @Override
    public BudgetYearConcept findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year) {
        return (BudgetYearConcept) createEntityCriteria()
                .add(Restrictions.eq("idBudget", idBudget))
                .add(Restrictions.eq("idMonth", idMonth))
                .add(Restrictions.eq("year", year))
                .uniqueResult();
    }

    @Override
    public List<BudgetYearConcept> findByBudget(Budgets budget) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("budget", budget));
        return (List<BudgetYearConcept>) criteria.list();
    }

    @Override
    public boolean authorizeBudget(int idDistributor, int idArea, int year) {
        globalTracer("UPDATE", new BudgetYearConcept());
        String query = "update BUDGET_MONTH_BRANCH bmb "
                + "inner join BUDGETS b on bmb.ID_BUDGET = b.ID_BUDGET set IS_AUTHORIZED = 1 "
                + "where bmb.YEAR = :year and b.ID_DISTRIBUTOR = :idDistributor and b.ID_AREA = :idArea";
        int updatedEntities = getSession()
                .createSQLQuery(query)
                .setInteger("idDistributor", idDistributor)
                .setInteger("idArea", idArea)
                .setInteger("year", year)
                .executeUpdate();
        return updatedEntities > 0;
    }

    @Override
    public List<BudgetYearConcept> findByDWEnterpriseAndYear(int dwEnterprise, int year) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("dwEnterprise", new DwEnterprises(dwEnterprise)))
                .add(Restrictions.eq("year", year))
                .setFetchMode("budgetMonthConceptsList", FetchMode.JOIN);
        return (List<BudgetYearConcept>) criteria.list();
    }
    
}

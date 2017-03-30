/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RealBudgetSpendingDao;
import mx.bidg.model.Budgets;
import mx.bidg.model.RealBudgetSpending;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
@SuppressWarnings("unchecked")
public class RealBudgetSpendingDaoImpl extends AbstractDao<Integer, RealBudgetSpending> implements RealBudgetSpendingDao {

    @Override
    public RealBudgetSpending save(RealBudgetSpending entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RealBudgetSpending findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RealBudgetSpending> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RealBudgetSpending update(RealBudgetSpending entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RealBudgetSpending entity) {
        remove(entity);
        return true;
    }

    @Override
    public RealBudgetSpending findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("budget", budget);
        map.put("month", month);
        map.put("dwEnterprise", dwEnterprise);
        map.put("year", year);

        RealBudgetSpending realBudgetSpending = (RealBudgetSpending) createEntityCriteria().add(Restrictions.allEq(map)).uniqueResult();
        return realBudgetSpending;
    }

    @Override
    public RealBudgetSpending findByCombination(Budgets budget, CMonths month, Integer year) {
        return (RealBudgetSpending) createEntityCriteria()
                .add(Restrictions.eq("budget",budget))
                .add(Restrictions.eq("month",month))
                .add(Restrictions.eq("year",year))
                .uniqueResult();
    }

    @Override
    public List<RealBudgetSpending> findByBudgetsAndYear(List<Budgets> budgets, Integer year) {
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
    public List<RealBudgetSpending> findByBudgetAndYear(Integer idBudget, Integer year) {

        Criteria criteria = createEntityCriteria();

        if (idBudget != null) {
            criteria.add(Restrictions.eq("idBudget", idBudget));
        }

        if (year != null) {
            criteria.add(Restrictions.eq("year", year));
        }

        criteria.addOrder(Order.asc("idBudgetYearConcept"));

        return criteria.list();
    }

    @Override
    public RealBudgetSpending findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year) {
        return (RealBudgetSpending) createEntityCriteria()
                .add(Restrictions.eq("idBudget", idBudget))
                .add(Restrictions.eq("idMonth", idMonth))
                .add(Restrictions.eq("year", year))
                .uniqueResult();
    }

    @Override
    public List<RealBudgetSpending> findByBudget(Budgets budget) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("budget", budget));
        return (List<RealBudgetSpending>) criteria.list();
    }

    @Override
    public boolean authorizeBudget(int idDistributor, int idArea, int year) {
        globalTracer("UPDATE", new RealBudgetSpending());
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
    public List<RealBudgetSpending> findByDWEnterpriseAndYear(int dwEnterprise, int year) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("dwEnterprise", new DwEnterprises(dwEnterprise)))
                .add(Restrictions.eq("year", year))
                .setFetchMode("budgetMonthConceptsList", FetchMode.JOIN);
        return (List<RealBudgetSpending>) criteria.list();
    }

    @Override
    public RealBudgetSpending findByAccountingAccountAndCostCenter(int idCostCenter, int idAccountingAccount) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("idCostCenter", idCostCenter);
        map.put("idAccountingAccount", idAccountingAccount);
        DetachedCriteria budgetOfCostCenter = DetachedCriteria.forClass(Budgets.class);
        budgetOfCostCenter.setProjection(Property.forName("idBudget"));
        budgetOfCostCenter.add(Restrictions.allEq(map));

        Criteria criteria = getSession().createCriteria(RealBudgetSpending.class);
        RealBudgetSpending byc = (RealBudgetSpending) criteria.add(Property.forName("idBudget").in(budgetOfCostCenter))
                .uniqueResult();
        return byc;
    }

    @Override
    public List<RealBudgetSpending> findByBudgetAndYearAndNoAuthorized(Integer idBudget, Integer year, boolean authorized) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idBudget",idBudget)).
                add(Restrictions.eq("year",year)).
                add(Restrictions.eq("authorized",false)).list();
    }

    @Override
    public RealBudgetSpending findByIdBudgetAndYear(Integer idBudget, Integer year) {
        Criteria criteria = createEntityCriteria();
        return (RealBudgetSpending) criteria.add(Restrictions.eq("idBudget",idBudget)).
                add(Restrictions.eq("year",year)).uniqueResult();
    }

}

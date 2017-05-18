package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RealBudgetSpendingHistoryDao;
import mx.bidg.model.RealBudgetSpendingHistory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kevin Salvador on 29/03/2017.
 */
@Repository
public class RealBudgetSpendingHistoryDaoImpl extends AbstractDao<Integer,RealBudgetSpendingHistory> implements RealBudgetSpendingHistoryDao{
    @Override
    public RealBudgetSpendingHistory save(RealBudgetSpendingHistory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RealBudgetSpendingHistory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RealBudgetSpendingHistory> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RealBudgetSpendingHistory update(RealBudgetSpendingHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RealBudgetSpendingHistory entity) {
        remove(entity);
        return true;
    }

    @Override
    public RealBudgetSpendingHistory findByIdBudgetandYear(Integer idBudget, Integer year) {
        Criteria criteria= createEntityCriteria();
        return (RealBudgetSpendingHistory) criteria.add(Restrictions.eq("idBudget",idBudget)).
                add(Restrictions.eq("year",year)).uniqueResult();
    }

    @Override
    public BigDecimal getRealTotalBudgetAmount(Integer idBuget, int year) {
        return (BigDecimal) createEntityCriteria()
                .add(Restrictions.eq("idBudget", idBuget))
                .add(Restrictions.eq("year", year))
                .setProjection(Projections.property("totalBudgetAmount"))
                .uniqueResult();
    }
}

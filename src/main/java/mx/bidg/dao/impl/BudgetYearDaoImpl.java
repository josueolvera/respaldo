package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetYearDao;
import mx.bidg.model.BudgetYear;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 14/09/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class BudgetYearDaoImpl extends AbstractDao<Integer, BudgetYear> implements BudgetYearDao {

    @Override
    public BudgetYear save(BudgetYear entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetYear findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BudgetYear> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public BudgetYear update(BudgetYear entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetYear entity) {
        remove(entity);
        return true;
    }

    @Override
    public BudgetYear findByBudgetAndYear(Integer idBudget, Integer year) {
        Criteria criteria = createEntityCriteria();

        if (idBudget != null) {
            criteria.add(Restrictions.eq("idBudget", idBudget));
        }

        if (year != null) {
            criteria.add(Restrictions.eq("year", year));
        }

        return (BudgetYear) criteria.uniqueResult();
    }
}

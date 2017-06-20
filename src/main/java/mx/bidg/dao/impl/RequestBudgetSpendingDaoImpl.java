package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestBudgetSpendingDao;
import mx.bidg.model.RequestBudgetSpending;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 19/06/17.
 */
@Repository
public class RequestBudgetSpendingDaoImpl extends AbstractDao<Integer, RequestBudgetSpending> implements RequestBudgetSpendingDao {

    @Override
    public RequestBudgetSpending save(RequestBudgetSpending entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestBudgetSpending findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestBudgetSpending> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RequestBudgetSpending update(RequestBudgetSpending entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestBudgetSpending entity) {
        remove(entity);
        return true;
    }

    @Override
    public RequestBudgetSpending findByIdDistributorCostCenterYear(Integer idDistributorCostCenter, int year) {
        return (RequestBudgetSpending) createEntityCriteria()
                .add(Restrictions.eq("idDistributorCostCenter", idDistributorCostCenter))
                .add(Restrictions.eq("year", year))
                .uniqueResult();
    }
}

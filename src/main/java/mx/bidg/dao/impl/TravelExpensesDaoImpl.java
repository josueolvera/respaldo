package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.TravelExpensesDao;
import mx.bidg.model.TravelExpenses;
import org.codehaus.groovy.control.io.ReaderSource;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class TravelExpensesDaoImpl extends AbstractDao<Integer,TravelExpenses> implements TravelExpensesDao {

    @Override
    public TravelExpenses save(TravelExpenses entity) {
        persist(entity);
        return entity;
    }

    @Override
    public TravelExpenses findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<TravelExpenses> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public TravelExpenses update(TravelExpenses entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(TravelExpenses entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<TravelExpenses> getTravelExpenses(Integer idUser) {
        Criteria criteria = createEntityCriteria();

        if (idUser != null) {
            criteria.createAlias("request", "rq")
                    .add(Restrictions.eq("rq.idUserRequest", idUser));
        }

        return criteria.list();
    }
}

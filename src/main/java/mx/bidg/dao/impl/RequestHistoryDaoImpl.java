package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestHistoryDao;
import mx.bidg.model.RequestHistory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
@Repository
public class RequestHistoryDaoImpl extends AbstractDao<Integer, RequestHistory> implements RequestHistoryDao {

    @Override
    public RequestHistory save(RequestHistory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestHistory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestHistory> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RequestHistory update(RequestHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestHistory entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<RequestHistory> findAllByRequest(Integer idRequest) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRequest", idRequest))
                .addOrder(Order.asc("creationDate"))
                .list();
    }
}

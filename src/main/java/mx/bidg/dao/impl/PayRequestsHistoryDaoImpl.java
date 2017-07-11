package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PayRequestsHistoryDao;
import mx.bidg.model.PayRequestsHistory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by User on 21/06/2017.
 */
@SuppressWarnings("unchecked")
@Repository
public class PayRequestsHistoryDaoImpl extends AbstractDao<Integer, PayRequestsHistory> implements PayRequestsHistoryDao{
    @Override
    public PayRequestsHistory save(PayRequestsHistory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PayRequestsHistory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PayRequestsHistory> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public PayRequestsHistory update(PayRequestsHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PayRequestsHistory entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PayRequestsHistory> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate){

        LocalDateTime from = fromDate.toLocalDate().atStartOfDay();
        LocalDateTime to = toDate.toLocalDate().atTime(23, 59, 59);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.between("creationDate",from,to));
        return criteria.list();
    }
}

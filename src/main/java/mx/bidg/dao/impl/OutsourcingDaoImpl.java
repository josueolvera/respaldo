package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.OutsourcingDao;
import mx.bidg.model.Outsourcing;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class OutsourcingDaoImpl extends AbstractDao<Integer, Outsourcing> implements OutsourcingDao {
    @Override
    public Outsourcing save(Outsourcing entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Outsourcing findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Outsourcing> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Outsourcing update(Outsourcing entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Outsourcing entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Outsourcing finfByidEmployee(int idEmployee,LocalDateTime applicationDate) {
        return (Outsourcing) createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("applicationDate",applicationDate))
                .uniqueResult();
    }
}

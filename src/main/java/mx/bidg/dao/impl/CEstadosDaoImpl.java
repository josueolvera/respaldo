package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CEstadosDao;
import mx.bidg.model.CEstados;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class CEstadosDaoImpl extends AbstractDao<Integer,CEstados> implements CEstadosDao {
    @Override
    public CEstados save(CEstados entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CEstados findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CEstados> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CEstados>) criteria
                .addOrder(Order.asc("nombreEstado"))
                .list();
    }

    @Override
    public CEstados update(CEstados entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CEstados entity) {
        remove(entity);
        return true;
    }
}

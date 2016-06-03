package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAsentamientosDao;
import mx.bidg.dao.CMunicipiosDao;
import mx.bidg.model.CAsentamientos;
import mx.bidg.model.CAsentamientos;
import mx.bidg.model.CEstados;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class CAsentamientosDaoImpl extends AbstractDao<Integer,CAsentamientos> implements CAsentamientosDao {
    @Override
    public CAsentamientos save(CAsentamientos entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAsentamientos findById(int id) {
        return (CAsentamientos) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CAsentamientos> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CAsentamientos>) criteria
                .addOrder(Order.asc("nombreAsentamiento"))
                .list();
    }

    @Override
    public CAsentamientos update(CAsentamientos entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAsentamientos entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CAsentamientos> findByPostCode(String codigoPostal) {
        Criteria criteria = createEntityCriteria();
        return (List<CAsentamientos>) criteria
                .add(Restrictions.eq("codigoPostal",codigoPostal))
                .list();
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTiposAsentamientosDao;
import mx.bidg.model.CTiposAsentamientos;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 2/06/16.
 */
@Repository
public class CTiposAsentamientosDaoImpl extends AbstractDao <Integer,CTiposAsentamientos> implements CTiposAsentamientosDao {
    @Override
    public CTiposAsentamientos save(CTiposAsentamientos entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTiposAsentamientos findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTiposAsentamientos> findAll() {
        return (List<CTiposAsentamientos>) createEntityCriteria().list();
    }

    @Override
    public CTiposAsentamientos update(CTiposAsentamientos entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTiposAsentamientos entity) {
        remove(entity);
        return true;
    }
}

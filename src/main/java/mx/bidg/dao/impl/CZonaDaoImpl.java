package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CZonaDao;
import mx.bidg.model.CZonas;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
@Repository
public class CZonaDaoImpl extends AbstractDao <Integer, CZonas> implements CZonaDao {

    @Override
    public CZonas save(CZonas entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CZonas findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CZonas> findAll() {
        return (List<CZonas>) createEntityCriteria().list();
    }

    @Override
    public CZonas update(CZonas entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CZonas entity) {
        remove(entity);
        return true;
    }
}

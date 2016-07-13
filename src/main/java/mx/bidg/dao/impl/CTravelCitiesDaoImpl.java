package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTravelCitiesDao;
import mx.bidg.model.CTravelCities;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CTravelCitiesDaoImpl extends AbstractDao<Integer, CTravelCities> implements CTravelCitiesDao {
    @Override
    public CTravelCities save(CTravelCities entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTravelCities findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTravelCities> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CTravelCities update(CTravelCities entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTravelCities entity) {
        remove(entity);
        return true;
    }
}

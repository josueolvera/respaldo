package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.FlightsDao;
import mx.bidg.model.Flights;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class FlightsDaoImpl extends AbstractDao<Integer, Flights> implements FlightsDao {
    @Override
    public Flights save(Flights entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Flights findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Flights> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Flights update(Flights entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Flights entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PassengersDao;
import mx.bidg.model.Passengers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class PassengersDaoImpl extends AbstractDao<Integer, Passengers> implements PassengersDao {
    @Override
    public Passengers save(Passengers entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Passengers findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Passengers> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Passengers update(Passengers entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Passengers entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.FixedOperationsDao;
import mx.bidg.model.FixedOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class FixedOperationsDaoImpl extends AbstractDao<Integer, FixedOperations> implements FixedOperationsDao {

    @Override
    public FixedOperations save(FixedOperations entity) {
        persist(entity);
        return entity;
    }

    @Override
    public FixedOperations findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<FixedOperations> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public FixedOperations update(FixedOperations entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(FixedOperations entity) {
        remove(entity);
        return true;
    }
}

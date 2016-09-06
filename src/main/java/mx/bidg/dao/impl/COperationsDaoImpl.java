package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.COperationsDao;
import mx.bidg.model.COperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class COperationsDaoImpl extends AbstractDao<Integer, COperations> implements COperationsDao {

    @Override
    public COperations save(COperations entity) {
        persist(entity);
        return entity;
    }

    @Override
    public COperations findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<COperations> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public COperations update(COperations entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(COperations entity) {
        remove(entity);
        return true;
    }
}

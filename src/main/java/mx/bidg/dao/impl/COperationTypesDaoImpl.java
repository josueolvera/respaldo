package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.COperationTypesDao;
import mx.bidg.model.COperationTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Repository
public class COperationTypesDaoImpl extends AbstractDao<Integer,COperationTypes> implements COperationTypesDao {
    @Override
    public COperationTypes save(COperationTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public COperationTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<COperationTypes> findAll() {
        return (List<COperationTypes>) createEntityCriteria().list();
    }

    @Override
    public COperationTypes update(COperationTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(COperationTypes entity) {
        remove(entity);
        return true;
    }
}

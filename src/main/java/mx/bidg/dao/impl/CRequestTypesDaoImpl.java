package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRequestTypesDao;
import mx.bidg.model.CRequestTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 2/06/17.
 */
@Repository
public class CRequestTypesDaoImpl extends AbstractDao<Integer, CRequestTypes> implements CRequestTypesDao {

    @Override
    public CRequestTypes save(CRequestTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CRequestTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRequestTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CRequestTypes update(CRequestTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CRequestTypes entity) {
        remove(entity);
        return true;
    }
}

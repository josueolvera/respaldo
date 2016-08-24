package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CActionTypesDao;
import mx.bidg.model.CActionTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 23/08/16.
 */
@Repository
public class CActionTypesDaoImpl extends AbstractDao<Integer, CActionTypes> implements CActionTypesDao {

    @Override
    public CActionTypes save(CActionTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CActionTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CActionTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CActionTypes update(CActionTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CActionTypes entity) {
        remove(entity);
        return true;
    }
}

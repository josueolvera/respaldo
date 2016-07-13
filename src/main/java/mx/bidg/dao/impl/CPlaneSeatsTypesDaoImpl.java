package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CPlaneSeatsTypesDao;
import mx.bidg.model.CPlaneSeatsTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CPlaneSeatsTypesDaoImpl extends AbstractDao<Integer, CPlaneSeatsTypes> implements CPlaneSeatsTypesDao {
    @Override
    public CPlaneSeatsTypes save(CPlaneSeatsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CPlaneSeatsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CPlaneSeatsTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CPlaneSeatsTypes update(CPlaneSeatsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CPlaneSeatsTypes entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CPlaneTicketsTypesDao;
import mx.bidg.model.CPlaneTicketsTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CPlaneTicketsTypesDaoImpl extends AbstractDao<Integer, CPlaneTicketsTypes> implements CPlaneTicketsTypesDao {
    @Override
    public CPlaneTicketsTypes save(CPlaneTicketsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CPlaneTicketsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CPlaneTicketsTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CPlaneTicketsTypes update(CPlaneTicketsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CPlaneTicketsTypes entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SinisterTruckdriverDao;
import mx.bidg.model.SinisterTruckdriver;

import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public class SinisterTruckdriverDaoImpl extends AbstractDao<Integer,SinisterTruckdriver> implements SinisterTruckdriverDao {
    @Override
    public SinisterTruckdriver save(SinisterTruckdriver entity) {
        persist(entity);
        return entity;
    }

    @Override
    public SinisterTruckdriver findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<SinisterTruckdriver> findAll() {
        return (List<SinisterTruckdriver>)createEntityCriteria().list() ;
    }

    @Override
    public SinisterTruckdriver update(SinisterTruckdriver entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(SinisterTruckdriver entity) {
        delete(entity);
        return true;
    }
}

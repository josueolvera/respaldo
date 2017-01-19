package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.MrPayTruckDriverDao;
import mx.bidg.model.MrPayTruckDriver;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
@Repository
public class MrPayTruckDriverDaoImpl extends AbstractDao<Integer,MrPayTruckDriver>implements MrPayTruckDriverDao{
    @Override
    public MrPayTruckDriver save(MrPayTruckDriver entity) {
        persist(entity);
        return entity;
    }

    @Override
    public MrPayTruckDriver findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<MrPayTruckDriver> findAll() {
        return (List<MrPayTruckDriver>)createEntityCriteria().list();
    }

    @Override
    public MrPayTruckDriver update(MrPayTruckDriver entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(MrPayTruckDriver entity) {
        remove(entity);
        return true;
    }
}

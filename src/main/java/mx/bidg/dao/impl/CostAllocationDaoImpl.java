package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CostAllocationDao;
import mx.bidg.model.CostAllocation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 20/12/2016.
 */
@Repository
public class CostAllocationDaoImpl extends AbstractDao<Integer,CostAllocation> implements CostAllocationDao{
    @Override
    public CostAllocation save(CostAllocation entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CostAllocation findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CostAllocation> findAll() {
        return (List<CostAllocation>)createEntityCriteria().list();
    }

    @Override
    public CostAllocation update(CostAllocation entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CostAllocation entity) {
        remove(entity);
        return true;
    }
}

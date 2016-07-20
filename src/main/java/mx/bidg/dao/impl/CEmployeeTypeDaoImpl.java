package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CEmployeeTypeDao;
import mx.bidg.model.CEmployeeType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
@Repository
public class CEmployeeTypeDaoImpl extends AbstractDao<Integer, CEmployeeType> implements CEmployeeTypeDao {
    @Override
    public CEmployeeType save(CEmployeeType entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CEmployeeType findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CEmployeeType> findAll() {
        return (List<CEmployeeType>) createEntityCriteria().list();
    }

    @Override
    public CEmployeeType update(CEmployeeType entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CEmployeeType entity) {
        remove(entity);
        return true;
    }
}

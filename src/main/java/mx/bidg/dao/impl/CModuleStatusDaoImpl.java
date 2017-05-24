package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CModuleStatusDao;
import mx.bidg.model.CModuleStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */

@Repository
public class CModuleStatusDaoImpl extends AbstractDao<Integer, CModuleStatus> implements CModuleStatusDao{

    @Override
    public CModuleStatus save(CModuleStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CModuleStatus update(CModuleStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CModuleStatus entity) {
        remove(entity);
        return true;
    }

    @Override
    public CModuleStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CModuleStatus> findAll() {
        return (List<CModuleStatus>) createEntityCriteria().list();
    }
}

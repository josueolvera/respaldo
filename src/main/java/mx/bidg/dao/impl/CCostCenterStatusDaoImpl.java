package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CCostCenterStatusDao;
import mx.bidg.model.CCostCenterStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Desarrollador on 05/04/2017.
 */
@Repository
public class CCostCenterStatusDaoImpl extends AbstractDao<Integer, CCostCenterStatus> implements CCostCenterStatusDao {

    @Override
    public CCostCenterStatus save(CCostCenterStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CCostCenterStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CCostCenterStatus> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CCostCenterStatus update(CCostCenterStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CCostCenterStatus entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRequestStatusDao;
import mx.bidg.model.CRequestStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 03/10/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class CRequestStatusDaoImpl extends AbstractDao<Integer, CRequestStatus> implements CRequestStatusDao {
    @Override
    public CRequestStatus save(CRequestStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CRequestStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRequestStatus> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CRequestStatus update(CRequestStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CRequestStatus entity) {
        remove(entity);
        return true;
    }
}

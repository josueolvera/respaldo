package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBalanceStatusDao;
import mx.bidg.model.CBalanceStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Repository
public class CBalanceStatusDaoImpl extends AbstractDao<Integer,CBalanceStatus> implements CBalanceStatusDao {
    @Override
    public CBalanceStatus save(CBalanceStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBalanceStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBalanceStatus> findAll() {
        return (List<CBalanceStatus>) createEntityCriteria().list();
    }

    @Override
    public CBalanceStatus update(CBalanceStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBalanceStatus entity) {
        remove(entity);
        return true;
    }
}

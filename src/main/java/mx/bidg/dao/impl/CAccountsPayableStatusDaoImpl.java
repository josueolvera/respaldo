package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAccountsPayableStatusDao;
import mx.bidg.model.CAccountsPayableStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Repository
public class CAccountsPayableStatusDaoImpl extends AbstractDao<Integer,CAccountsPayableStatus> implements CAccountsPayableStatusDao {
    @Override
    public CAccountsPayableStatus save(CAccountsPayableStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAccountsPayableStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAccountsPayableStatus> findAll() {
        return (List<CAccountsPayableStatus>) createEntityCriteria().list();
    }

    @Override
    public CAccountsPayableStatus update(CAccountsPayableStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAccountsPayableStatus entity) {
        remove(entity);
        return true;
    }
}

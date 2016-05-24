package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTransactionsStatusDao;
import mx.bidg.model.CTransactionsStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Repository
public class CTransactionsStatusDaoImpl extends AbstractDao<Integer,CTransactionsStatus> implements CTransactionsStatusDao {
    @Override
    public CTransactionsStatus save(CTransactionsStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTransactionsStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTransactionsStatus> findAll() {
        return (List<CTransactionsStatus>)createEntityCriteria().list();
    }

    @Override
    public CTransactionsStatus update(CTransactionsStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTransactionsStatus entity) {
        remove(entity);
        return true;
    }
}

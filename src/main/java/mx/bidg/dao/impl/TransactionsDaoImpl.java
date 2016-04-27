package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.TransactionsDao;
import mx.bidg.model.Transactions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Repository
public class TransactionsDaoImpl extends AbstractDao<Integer,Transactions> implements TransactionsDao {
    @Override
    public Transactions save(Transactions entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Transactions findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Transactions> findAll() {
        return (List<Transactions>)createEntityCriteria().list();
    }

    @Override
    public Transactions update(Transactions entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Transactions entity) {
        remove(entity);
        return true;
    }
}

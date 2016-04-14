package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BalancesDao;
import mx.bidg.model.Balances;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Repository
public class BalancesDaoImpl extends AbstractDao<Integer,Balances> implements BalancesDao {
    @Override
    public Balances save(Balances entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Balances findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Balances> findAll() {
        return (List<Balances>)createEntityCriteria().list();
    }

    @Override
    public Balances update(Balances entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Balances entity) {
        remove(entity);
        return true;
    }
}

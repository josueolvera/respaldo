package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AdmonAccountsDao;
import mx.bidg.model.AdmonAccounts;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Repository
public class AdmonAccountsDaoImpl extends AbstractDao<Integer,AdmonAccounts> implements AdmonAccountsDao {

    @Override
    public AdmonAccounts save(AdmonAccounts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AdmonAccounts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<AdmonAccounts> findAll() {
        return (List<AdmonAccounts>) createEntityCriteria().list();
    }

    @Override
    public AdmonAccounts update(AdmonAccounts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AdmonAccounts entity) {
        remove(entity);
        return true;
    }
}

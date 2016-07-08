package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAccountingAccountTypeDao;
import mx.bidg.model.CAccountingAccountType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
@Repository
public class CAccountingAccountTypeDaoImpl extends AbstractDao <Integer, CAccountingAccountType> implements CAccountingAccountTypeDao {
    @Override
    public CAccountingAccountType save(CAccountingAccountType entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAccountingAccountType findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAccountingAccountType> findAll() {
        return (List<CAccountingAccountType>) createEntityCriteria().list();
    }

    @Override
    public CAccountingAccountType update(CAccountingAccountType entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAccountingAccountType entity) {
        remove(entity);
        return true;
    }
}

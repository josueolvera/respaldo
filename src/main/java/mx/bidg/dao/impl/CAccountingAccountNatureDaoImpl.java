package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAccountingAccountNatureDao;
import mx.bidg.model.CAccountingAccountNature;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
@Repository
public class CAccountingAccountNatureDaoImpl extends AbstractDao <Integer, CAccountingAccountNature> implements CAccountingAccountNatureDao {

    @Override
    public CAccountingAccountNature save(CAccountingAccountNature entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAccountingAccountNature findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAccountingAccountNature> findAll() {
        return (List<CAccountingAccountNature>) createEntityCriteria().list();
    }

    @Override
    public CAccountingAccountNature update(CAccountingAccountNature entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAccountingAccountNature entity) {
        remove(entity);
        return true;
    }
}

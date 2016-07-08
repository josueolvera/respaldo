package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAccountingAccountCategoryDao;
import mx.bidg.model.CAccountingAccountCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
@Repository
public class CAccountingAccountCategoryDaoImpl extends AbstractDao <Integer, CAccountingAccountCategory> implements CAccountingAccountCategoryDao {

    @Override
    public CAccountingAccountCategory save(CAccountingAccountCategory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAccountingAccountCategory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAccountingAccountCategory> findAll() {
        return (List<CAccountingAccountCategory>) createEntityCriteria().list();
    }

    @Override
    public CAccountingAccountCategory update(CAccountingAccountCategory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAccountingAccountCategory entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CCommissionsCashDao;
import mx.bidg.model.CCommissionsCash;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC_YAIR on 15/11/2016.
 */
@Repository
public class CCommissionsCashDaoImpl extends AbstractDao<Integer, CCommissionsCash> implements CCommissionsCashDao  {

    @Override
    public CCommissionsCash save(CCommissionsCash entity) {
        persist(entity);
        return entity;

    }

    @Override
    public CCommissionsCash findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CCommissionsCash> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CCommissionsCash update(CCommissionsCash entity) {

        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CCommissionsCash entity) {
        delete(entity);
        return true;
    }
}

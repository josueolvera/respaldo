package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CCommissionBonusDao;
import mx.bidg.model.CCommissionBonus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
@Repository
public class CCommissionBonusDaoImpl extends AbstractDao<Integer, CCommissionBonus> implements CCommissionBonusDao {

    @Override
    public CCommissionBonus save(CCommissionBonus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CCommissionBonus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CCommissionBonus> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CCommissionBonus update(CCommissionBonus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CCommissionBonus entity) {
        remove(entity);
        return true;
    }
}

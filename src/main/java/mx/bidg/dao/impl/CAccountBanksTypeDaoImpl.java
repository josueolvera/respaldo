package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAccountBanksTypeDao;
import mx.bidg.model.CAccountBanksType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Leonardo on 12/06/2017.
 */
@Repository
public class CAccountBanksTypeDaoImpl  extends AbstractDao<Integer, CAccountBanksType> implements CAccountBanksTypeDao{

    @Override
    public CAccountBanksType save(CAccountBanksType entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAccountBanksType findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAccountBanksType> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CAccountBanksType update(CAccountBanksType entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAccountBanksType entity) {
        remove(entity);
        return true;
    }
}

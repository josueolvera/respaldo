package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTypeMethodPayDao;
import mx.bidg.model.CTypeMethodPay;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
@Repository
public class CTypeMethodPayDaoImpl extends AbstractDao<Integer,CTypeMethodPay> implements CTypeMethodPayDao {
    @Override
    public CTypeMethodPay save(CTypeMethodPay entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTypeMethodPay findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTypeMethodPay> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CTypeMethodPay update(CTypeMethodPay entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTypeMethodPay entity) {
        remove(entity);
        return true;
    }
}

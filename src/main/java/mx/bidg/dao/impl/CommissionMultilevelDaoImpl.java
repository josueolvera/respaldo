package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionMultilevelDao;
import mx.bidg.model.CommissionMultilevel;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by PC_YAIR on 17/11/2016.
 */
@Repository
@SuppressWarnings("unchecked")
public class CommissionMultilevelDaoImpl extends AbstractDao<Integer, CommissionMultilevel> implements CommissionMultilevelDao {
    @Override
    public CommissionMultilevel save(CommissionMultilevel entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CommissionMultilevel findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CommissionMultilevel> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CommissionMultilevel update(CommissionMultilevel entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CommissionMultilevel entity) {
        remove(entity);
        return true;
    }
}

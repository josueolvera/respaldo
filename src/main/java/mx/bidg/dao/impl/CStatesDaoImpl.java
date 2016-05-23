package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CStatesDao;
import mx.bidg.model.CStates;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class CStatesDaoImpl extends AbstractDao<Integer,CStates> implements CStatesDao {
    @Override
    public CStates save(CStates entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CStates findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CStates> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CStates>) criteria
                .addOrder(Order.asc("stateName"))
                .list();
    }

    @Override
    public CStates update(CStates entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CStates entity) {
        remove(entity);
        return true;
    }
}

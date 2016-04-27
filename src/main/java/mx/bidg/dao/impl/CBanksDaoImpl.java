package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBanksDao;
import mx.bidg.model.CBanks;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 13/04/2016.
 */
@Repository
public class CBanksDaoImpl extends AbstractDao<Integer,CBanks> implements CBanksDao {
    @Override
    public CBanks save(CBanks entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBanks findById(int id) {
        return getByKey(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CBanks> findAll() {
       return  (List<CBanks>) createEntityCriteria().list();

    }

    @Override
    public CBanks update(CBanks entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBanks entity) {
        remove(entity);
        return true;
    }
}

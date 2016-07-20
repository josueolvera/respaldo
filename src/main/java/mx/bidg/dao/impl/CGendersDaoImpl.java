package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CGendersDao;
import mx.bidg.model.CGenders;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
@Repository
public class CGendersDaoImpl extends AbstractDao<Integer, CGenders> implements CGendersDao {
    @Override
    public CGenders save(CGenders entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CGenders findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CGenders> findAll() {
        return (List<CGenders>) createEntityCriteria().list();
    }

    @Override
    public CGenders update(CGenders entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CGenders entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CSizesDao;
import mx.bidg.model.CSizes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 01/08/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CSizesDaoImpl extends AbstractDao<Integer, CSizes> implements CSizesDao {
    @Override
    public CSizes save(CSizes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CSizes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CSizes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CSizes update(CSizes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CSizes entity) {
        remove(entity);
        return true;
    }
}

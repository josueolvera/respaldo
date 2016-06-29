package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CStatusMaritalDao;
import mx.bidg.model.CStatusMarital;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 23/06/16.
 */
@Repository
public class CStatusMaritalDaoImpl extends AbstractDao <Integer,CStatusMarital> implements CStatusMaritalDao {
    @Override
    public CStatusMarital save(CStatusMarital entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CStatusMarital findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CStatusMarital> findAll() {
        return (List<CStatusMarital>) createEntityCriteria().list();
    }

    @Override
    public CStatusMarital update(CStatusMarital entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CStatusMarital entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetNatureDao;
import mx.bidg.model.CBudgetNature;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 03/04/2017.
 */
@Repository
public class CBudgetNatureDaoImpl extends AbstractDao<Integer, CBudgetNature> implements CBudgetNatureDao {
    @Override
    public CBudgetNature save(CBudgetNature entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBudgetNature findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBudgetNature> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CBudgetNature update(CBudgetNature entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBudgetNature entity) {
        remove(entity);
        return true;
    }
}

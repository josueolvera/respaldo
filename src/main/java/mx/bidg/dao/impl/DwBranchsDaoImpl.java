package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwBranchsDao;
import mx.bidg.model.DwBranchs;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class DwBranchsDaoImpl extends AbstractDao<Integer, DwBranchs> implements DwBranchsDao {
    @Override
    public DwBranchs save(DwBranchs entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DwBranchs findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DwBranchs> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DwBranchs update(DwBranchs entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(DwBranchs entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

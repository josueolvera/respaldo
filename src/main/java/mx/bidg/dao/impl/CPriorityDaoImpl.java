package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CPriorityDao;
import mx.bidg.model.CPriority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CPriorityDaoImpl extends AbstractDao<Integer,CPriority> implements CPriorityDao {
    @Override
    public CPriority save(CPriority entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CPriority findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CPriority> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CPriority update(CPriority entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CPriority entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

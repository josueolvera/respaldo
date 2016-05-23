package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CIncidenceDao;
import mx.bidg.model.CIncidence;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CIncidenceDaoImpl extends AbstractDao<Integer,CIncidence> implements CIncidenceDao {
    @Override
    public CIncidence save(CIncidence entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CIncidence findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CIncidence> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CIncidence update(CIncidence entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CIncidence entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

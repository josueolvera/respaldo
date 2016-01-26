package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRequestTypesDao;
import mx.bidg.model.CRequestTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 *         Created on 18/11/15.
 */
@Repository
public class CRequestTypesDaoImpl extends AbstractDao<Integer, CRequestTypes> implements CRequestTypesDao {
    @Override
    public CRequestTypes save(CRequestTypes entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CRequestTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRequestTypes> findAll() {
        return (List<CRequestTypes>) createEntityCriteria().list();
    }

    @Override
    public CRequestTypes update(CRequestTypes entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CRequestTypes entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

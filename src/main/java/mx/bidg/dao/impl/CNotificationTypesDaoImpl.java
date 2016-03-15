package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CNotificationTypesDao;
import mx.bidg.model.CNotificationTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 11/03/16.
 */
@Repository
public class CNotificationTypesDaoImpl extends AbstractDao<Integer, CNotificationTypes> implements CNotificationTypesDao {
    @Override
    public CNotificationTypes save(CNotificationTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CNotificationTypes findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CNotificationTypes> findAll() {
        return (List<CNotificationTypes>) createEntityCriteria().list();
    }

    @Override
    public CNotificationTypes update(CNotificationTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CNotificationTypes entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

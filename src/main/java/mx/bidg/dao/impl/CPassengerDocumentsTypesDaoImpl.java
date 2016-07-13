package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CPassengerDocumentsTypesDao;
import mx.bidg.model.CPassengerDocumentsTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CPassengerDocumentsTypesDaoImpl extends AbstractDao<Integer, CPassengerDocumentsTypes> implements CPassengerDocumentsTypesDao {
    @Override
    public CPassengerDocumentsTypes save(CPassengerDocumentsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CPassengerDocumentsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CPassengerDocumentsTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CPassengerDocumentsTypes update(CPassengerDocumentsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CPassengerDocumentsTypes entity) {
        remove(entity);
        return true;
    }
}

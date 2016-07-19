package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PassengerDocumentsDao;
import mx.bidg.model.PassengerDocuments;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class PassengerDocumentsDaoImpl extends AbstractDao<Integer, PassengerDocuments> implements PassengerDocumentsDao {
    @Override
    public PassengerDocuments save(PassengerDocuments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PassengerDocuments findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PassengerDocuments> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PassengerDocuments update(PassengerDocuments entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PassengerDocuments entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PassengerDocuments> findByIdDocumentTypeAndIdPassenger(Integer idDocumentType, Integer idPassenger) {
        return createEntityCriteria()
                .add(Restrictions.eq("idPassengerDocumentType",idDocumentType))
                .add(Restrictions.eq("idPassenger",idPassenger))
                .list();
    }
}

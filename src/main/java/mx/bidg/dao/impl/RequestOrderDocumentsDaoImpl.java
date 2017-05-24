package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestOrderDocumentsDao;
import mx.bidg.model.RequestOrderDocuments;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */

@Repository
public class RequestOrderDocumentsDaoImpl extends AbstractDao<Integer, RequestOrderDocuments> implements RequestOrderDocumentsDao{

    @Override
    public RequestOrderDocuments save(RequestOrderDocuments entity){
        persist(entity);
        return entity;
    }

    @Override
    public RequestOrderDocuments update(RequestOrderDocuments entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestOrderDocuments entity) {
        remove(entity);
        return true;
    }

    @Override
    public RequestOrderDocuments findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestOrderDocuments> findAll() {
        return (List<RequestOrderDocuments>) createEntityCriteria().list();
    }
}

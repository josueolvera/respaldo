package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorsEmployeeDocumentsDao;
import mx.bidg.model.DitributorsEmployeeDocuments;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
@Repository
public class DistributorsEmployeeDocumentsDaoImpl extends AbstractDao<Integer,DitributorsEmployeeDocuments> implements DistributorsEmployeeDocumentsDao {

    @Override
    public DitributorsEmployeeDocuments save(DitributorsEmployeeDocuments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DitributorsEmployeeDocuments findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DitributorsEmployeeDocuments> findAll() {
        return (List<DitributorsEmployeeDocuments>) createEntityCriteria().list();
    }

    @Override
    public DitributorsEmployeeDocuments update(DitributorsEmployeeDocuments entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DitributorsEmployeeDocuments entity) {
        remove(entity);
        return true;
    }
}

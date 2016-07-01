package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CEmployeeDocumentsTypesDao;
import mx.bidg.model.CEmployeeDocumentsTypes;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@Repository
public class CEmployeeDocumentsTypesDaoImpl extends AbstractDao<Integer,CEmployeeDocumentsTypes> implements CEmployeeDocumentsTypesDao {

    @Override
    public CEmployeeDocumentsTypes save(CEmployeeDocumentsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CEmployeeDocumentsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CEmployeeDocumentsTypes> findAll() {
        return (List<CEmployeeDocumentsTypes>) createEntityCriteria().list();
    }

    @Override
    public CEmployeeDocumentsTypes update(CEmployeeDocumentsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CEmployeeDocumentsTypes entity) {
        remove(entity);
        return true;
    }
}

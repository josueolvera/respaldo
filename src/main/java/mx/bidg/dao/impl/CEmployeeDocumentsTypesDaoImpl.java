package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CEmployeeDocumentsTypesDao;
import mx.bidg.model.CEmployeeDocumentsTypes;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@SuppressWarnings("unchecked")
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

    @Override
    public List<CEmployeeDocumentsTypes> findByInputField() {
        return (List<CEmployeeDocumentsTypes>) createEntityCriteria().add(Restrictions.eq("field",1)).list();
    }

    @Override
    public List<CEmployeeDocumentsTypes> findByEmployee(List<CEmployeeDocumentsTypes> employeeDocumentTypes) {

        if (employeeDocumentTypes.isEmpty()) {
            return new ArrayList<>();
        }

        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        for (CEmployeeDocumentsTypes cEmployeeDocumentType : employeeDocumentTypes) {
            disjunction.add(Restrictions.eq("idDocumentType", cEmployeeDocumentType.getIdDocumentType()));
        }

        criteria.add(disjunction);

        return criteria.list();
    }
}

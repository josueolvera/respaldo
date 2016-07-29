package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeeDocumentsDao;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.model.Employees;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class EmployeeDocumentsDaoImpl extends AbstractDao<Integer,EmployeeDocuments> implements EmployeeDocumentsDao {
    @Override
    public List<EmployeeDocuments> findByIdEmployee(Integer idEmployee) {
        return (List<EmployeeDocuments>) createEntityCriteria()
                .add(Restrictions.eq("idEmployee", idEmployee))
                .add(Restrictions.eq("currentDocument", 1))
                .addOrder(Order.asc("idDocumentType"))
                .list();
    }

    @Override
    public EmployeeDocuments findBy(Employees employee, CEmployeeDocumentsTypes documentType) {
        return (EmployeeDocuments) createEntityCriteria()
                .add(Restrictions.eq("idEmployee", employee.getIdEmployee()))
                .add(Restrictions.eq("idDocumentType", documentType.getIdDocumentType()))
                .uniqueResult();
    }

    @Override
    public List<EmployeeDocuments> findRecordBy(Employees employees) {
        return (List<EmployeeDocuments>) createEntityCriteria()
                .add(Restrictions.eq("idEmployee", employees.getIdEmployee()))
                .add(Restrictions.eq("currentDocument", 0))
                .addOrder(Order.asc("idDocumentType"))
                .addOrder(Order.desc("uploadingDate"))
                .list();
    }

    @Override
    public EmployeeDocuments save(EmployeeDocuments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public EmployeeDocuments findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<EmployeeDocuments> findAll() {
        return (List<EmployeeDocuments>) createEntityCriteria().list();
    }

    @Override
    public EmployeeDocuments update(EmployeeDocuments entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(EmployeeDocuments entity) {
        remove(entity);
        return true;
    }
}

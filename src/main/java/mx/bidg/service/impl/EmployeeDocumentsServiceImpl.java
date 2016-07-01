package mx.bidg.service.impl;

import mx.bidg.dao.EmployeeDocumentsDao;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.model.Employees;
import mx.bidg.service.EmployeeDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@Service
@Transactional
public class EmployeeDocumentsServiceImpl implements EmployeeDocumentsService {

    @Autowired
    EmployeeDocumentsDao employeeDocumentsDao;

    @Override
    public List<EmployeeDocuments> findByIdEmployee(Integer idEmployee) {
        return employeeDocumentsDao.findByIdEmployee(idEmployee);
    }

    @Override
    public EmployeeDocuments findBy(Employees employee, CEmployeeDocumentsTypes documentType) {
        return employeeDocumentsDao.findBy(employee, documentType);
    }

    @Override
    public List<EmployeeDocuments> findRecordBy(Employees employee) {
        return employeeDocumentsDao.findRecordBy(employee);
    }

    @Override
    public EmployeeDocuments save(EmployeeDocuments entity) {
        employeeDocumentsDao.save(entity);
        return entity;
    }

    @Override
    public EmployeeDocuments findById(int id) {
        return employeeDocumentsDao.findById(id);
    }

    @Override
    public List<EmployeeDocuments> findAll() {
        return employeeDocumentsDao.findAll();
    }

    @Override
    public EmployeeDocuments update(EmployeeDocuments entity) {
        employeeDocumentsDao.update(entity);
        return entity;
    }

    @Override
    public boolean delete(EmployeeDocuments entity) {
        employeeDocumentsDao.delete(entity);
        return true;
    }
}

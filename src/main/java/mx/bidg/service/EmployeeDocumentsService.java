package mx.bidg.service;

import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.model.Employees;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
public interface EmployeeDocumentsService {
    List<EmployeeDocuments> findByIdEmployee(Integer idEmployee);
    EmployeeDocuments findBy(Employees employee, CEmployeeDocumentsTypes documentType);
    List<EmployeeDocuments> findRecordBy(Employees employee);
    EmployeeDocuments save(EmployeeDocuments entity);
    EmployeeDocuments findById(int id);
    List<EmployeeDocuments> findAll();
    EmployeeDocuments update(EmployeeDocuments entity);
    boolean delete(EmployeeDocuments entity);
}

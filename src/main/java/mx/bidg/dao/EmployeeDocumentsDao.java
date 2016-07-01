package mx.bidg.dao;

import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.model.Employees;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
public interface EmployeeDocumentsDao extends InterfaceDao<EmployeeDocuments> {

    List<EmployeeDocuments> findByIdEmployee(Integer idEmployee);
    EmployeeDocuments findBy(Employees employee, CEmployeeDocumentsTypes documentType);
    List<EmployeeDocuments> findRecordBy(Employees employees);
}

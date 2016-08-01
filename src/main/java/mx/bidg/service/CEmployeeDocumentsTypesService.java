package mx.bidg.service;

import mx.bidg.model.CEmployeeDocumentsTypes;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
public interface CEmployeeDocumentsTypesService {
    List<CEmployeeDocumentsTypes> findAll();
    List<CEmployeeDocumentsTypes> findByEmployee(Integer idDwEmployee);
    List<CEmployeeDocumentsTypes> findByInput();
}

package mx.bidg.service.impl;

import mx.bidg.dao.CEmployeeDocumentsTypesDao;
import mx.bidg.dao.EmployeeDocumentsDao;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.service.CEmployeeDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@Service
@Transactional
public class CEmployeeDocumentsTypesServiceImpl implements CEmployeeDocumentsTypesService {

    @Autowired
    CEmployeeDocumentsTypesDao cEmployeeDocumentsTypesDao;

    @Autowired
    EmployeeDocumentsDao employeeDocumentsDao;

    @Override
    public List<CEmployeeDocumentsTypes> findAll() {
        return cEmployeeDocumentsTypesDao.findAll();
    }

    @Override
    public List<CEmployeeDocumentsTypes> findByEmployee(Integer idEmployee) {

        List<EmployeeDocuments> employeeDocuments = employeeDocumentsDao.findByIdEmployee(idEmployee);
        List<CEmployeeDocumentsTypes> cEmployeeDocumentsTypes = new ArrayList<>();

        for (EmployeeDocuments employeeDocument : employeeDocuments) {
            cEmployeeDocumentsTypes.add(employeeDocument.getcDocumentType());
        }

        return cEmployeeDocumentsTypesDao.findByEmployee(cEmployeeDocumentsTypes);
    }

    @Override
    public List<CEmployeeDocumentsTypes> findByInput() {
        return cEmployeeDocumentsTypesDao.findByInputField();
    }

}

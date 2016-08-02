package mx.bidg.service.impl;

import mx.bidg.dao.CEmployeeDocumentsTypesDao;
import mx.bidg.dao.DistributorsEmployeeDocumentsDao;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.dao.EmployeeDocumentsDao;
import mx.bidg.model.*;
import mx.bidg.service.CEmployeeDocumentsTypesService;
import mx.bidg.service.DistributorsEmployeeDocumentsService;
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

    @Autowired
    DwEmployeesDao dwEmployeesDao;

    @Autowired
    DistributorsEmployeeDocumentsDao distributorsEmployeeDocumentsDao;

    @Override
    public List<CEmployeeDocumentsTypes> findAll() {
        return cEmployeeDocumentsTypesDao.findAll();
    }

    @Override
    public List<CEmployeeDocumentsTypes> findByEmployee(Integer idDwEmployee) {

        DwEmployees dwEmployee = dwEmployeesDao.findById(idDwEmployee);
        DwEnterprises dwEnterprise = dwEmployee.getDwEnterprise();

        List<CEmployeeDocumentsTypes> currentEmployeeDocumentsTypes = new ArrayList<>();
        List<CEmployeeDocumentsTypes> employeeDocumentsTypes = new ArrayList<>();
        List<EmployeeDocuments> employeeDocuments = employeeDocumentsDao.findByIdEmployee(dwEmployee.getIdEmployee());
        List<DitributorsEmployeeDocuments> ditributorsEmployeeDocuments = distributorsEmployeeDocumentsDao.findByDistributor(dwEnterprise.getIdDistributor());



        for (DitributorsEmployeeDocuments ditributorsEmployeeDocument : ditributorsEmployeeDocuments) {
            employeeDocumentsTypes.add(ditributorsEmployeeDocument.getDocumentType());
        }

        for (EmployeeDocuments employeeDocument : employeeDocuments) {
            currentEmployeeDocumentsTypes.add(employeeDocument.getcDocumentType());
        }

        employeeDocumentsTypes.removeAll(currentEmployeeDocumentsTypes);


        return cEmployeeDocumentsTypesDao.findByEmployee(employeeDocumentsTypes);
    }

    @Override
    public List<CEmployeeDocumentsTypes> findByInput() {
        return cEmployeeDocumentsTypesDao.findByInputField();
    }

}

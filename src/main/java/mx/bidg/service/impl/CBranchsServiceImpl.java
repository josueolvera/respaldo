/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.CBranchsDao;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBranchsServiceImpl implements CBranchsService {
    
    @Autowired
    CBranchsDao cBranchsDao;

    @Autowired
    DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    DwEmployeesDao dwEmployeesDao;

    @Autowired
    EmployeesDao employeesDao;

    @Autowired
    EmployeesAccountsService employeesAccountsService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    EmployeesHistoryService employeesHistoryService;

    @Autowired
    EmailDeliveryService emailDeliveryService;

    @Autowired
    EmailTemplatesService emailTemplatesService;

    @Override
    public List<CBranchs> findAll() {
        return cBranchsDao.findAll();
    }

    @Override
    public CBranchs findById(int idBranch) {
        return cBranchsDao.findById(idBranch);
    }

    @Override
    public boolean delete(int idBranch) {
        return cBranchsDao.delete(cBranchsDao.findById(idBranch));
    }

    @Override
    public CBranchs update(CBranchs cBranchs) {
        return cBranchsDao.update(cBranchs);
    }

    @Override
    public CBranchs save(CBranchs cBranchs, int idDistributor ,int idRegion, int idZona) {
        cBranchs.setUploadedDate(LocalDateTime.now());
        cBranchs.setBranchName(cBranchs.getBranchName().toUpperCase());
        cBranchs.setBranchShort(cBranchs.getBranchShort().toUpperCase());
        cBranchs.setStatus(true);
        cBranchs = cBranchsDao.save(cBranchs);
        DwEnterprises dwEnterprises = new DwEnterprises();
        dwEnterprises.setBranch(cBranchs);
        dwEnterprises.setDistributor(new CDistributors(idDistributor));
        dwEnterprises.setRegion(new CRegions(idRegion));
        dwEnterprises.setZona(new CZonas(idZona));
        dwEnterprises.setGroup(new CGroups(1));
        dwEnterprises.setArea(new CAreas(2));
        dwEnterprises.setBudgetable(1);
        dwEnterprises.setStatus(true);
        dwEnterprisesDao.save(dwEnterprises);

        return cBranchs;
    }

    @Override
    public CBranchs changeBranchStatus(int idBranch, Users user) {
        CBranchs branch = cBranchsDao.findById(idBranch);
        List<DwEnterprises> dwEnterprises = branch.getDwEnterprises();
        List<DwEmployees> dwEmployees;
        for (DwEnterprises dwEnterprise : dwEnterprises) {

            if (dwEnterprise.getStatus()) {
                dwEnterprise.setStatus(false);
                dwEnterprisesDao.update(dwEnterprise);
                dwEmployees = dwEmployeesDao.findByDwEnterprise(dwEnterprise.getIdDwEnterprise());
                for (DwEmployees dwEmployee : dwEmployees) {
                    Employees employee = employeesDao.findById(dwEmployee.getIdEmployee());
                    EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());
                    Accounts accounts = employeesAccounts.getAccount();
                    employee.setStatus(0);
                    employeesDao.update(employee);
                    employeesHistoryService.save(dwEmployee, CActionTypes.BAJA, accounts, user);

                    dwEmployeesDao.delete(dwEmployee);

                    EmailTemplates emailTemplate = emailTemplatesService.findByName("employee_low_notification");
                    emailTemplate.addProperty("dwEmployee", dwEmployee);

                    emailDeliveryService.deliverEmail(emailTemplate);
                }
            }
        }

        branch.setStatus(false);
        cBranchsDao.update(branch);
        return branch;
    }

    @Override
    public List<CBranchs> findSaemFlag(Integer idBranch,Integer saemFlag) {
        return cBranchsDao.findBySaemFlag(idBranch,saemFlag);
    }

}

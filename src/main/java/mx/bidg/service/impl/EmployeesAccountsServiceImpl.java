/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;

import mx.bidg.dao.AccountsDao;
import mx.bidg.dao.EmployeesAccountsDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.model.Accounts;
import mx.bidg.model.Employees;
import mx.bidg.model.EmployeesAccounts;
import mx.bidg.model.Users;
import mx.bidg.service.EmployeesAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeesAccountsServiceImpl implements EmployeesAccountsService {
    
    @Autowired
    EmployeesAccountsDao employeesAccountsDao;
    
    @Autowired
    UsersDao usersDao;

    @Autowired
    AccountsDao accountsDao;

    @Override
    public List<EmployeesAccounts> findByEmployee(Employees e) {
        return employeesAccountsDao.findByEmployee(e);
    }

    @Override
    public List<EmployeesAccounts> findByIdUser(int idUser) {
        Users user = usersDao.findByIdFetchDwEmployee(idUser);
        return employeesAccountsDao.findByEmployee(user.getDwEmployee().getEmployee());
    }

    @Override
    public EmployeesAccounts save(EmployeesAccounts employeesAccounts) {
        employeesAccountsDao.save(employeesAccounts);
        return employeesAccounts;
    }

    @Override
    public EmployeesAccounts findByIdEmployee(Integer idEmployee) {
        return employeesAccountsDao.findByIdEmployee(idEmployee);
    }

    @Override
    public EmployeesAccounts findEmployeeAccountActive(Integer idEmployee) {
        return employeesAccountsDao.findByIdEmployee(idEmployee);
    }

}

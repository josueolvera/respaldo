/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.EmployeesAccountsDao;
import mx.bidg.dao.UsersDao;
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

    @Override
    public List<EmployeesAccounts> findByEmployee(Employees e) {
        return employeesAccountsDao.findByEmployee(e);
    }

    @Override
    public List<EmployeesAccounts> findByIdUser(int idUser) {
        Users user = usersDao.findByIdFetchDwEmployee(idUser);
        return employeesAccountsDao.findByEmployee(user.getDwEmployee().getEmployee());
    }
    
}

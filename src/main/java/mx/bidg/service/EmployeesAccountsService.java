/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Employees;
import mx.bidg.model.EmployeesAccounts;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface EmployeesAccountsService {
    
    List<EmployeesAccounts> findByEmployee(Employees e);
    
    List<EmployeesAccounts> findByIdUser(int idUser);

    EmployeesAccounts save (EmployeesAccounts employeesAccounts);

    EmployeesAccounts findByIdEmployee(Integer idEmployee);

    EmployeesAccounts findEmployeeAccountActive(Integer idEmployee);
}

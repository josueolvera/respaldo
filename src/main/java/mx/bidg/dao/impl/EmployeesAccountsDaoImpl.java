/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import mx.bidg.dao.EmployeesAccountsDao;

import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.model.Accounts;
import mx.bidg.model.Employees;
import mx.bidg.model.EmployeesAccounts;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeesAccountsDaoImpl extends AbstractDao<Integer, EmployeesAccounts>implements EmployeesAccountsDao {

    @Override
    public EmployeesAccounts save(EmployeesAccounts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public EmployeesAccounts findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EmployeesAccounts> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmployeesAccounts update(EmployeesAccounts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(EmployeesAccounts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<EmployeesAccounts> findByEmployee(Employees e) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("employee", e))
                .setFetchMode("account", FetchMode.JOIN);
        return (List<EmployeesAccounts>) criteria.list();
    }

    @Override
    public EmployeesAccounts findByIdEmployee(Integer idEmployee) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("idEmployee", idEmployee));
        return (EmployeesAccounts) criteria.uniqueResult();
    }

    @Override
    public EmployeesAccounts findActiveEmployeeAccounts(Integer idEmployee, List<Accounts> accounts) {
        Criteria criteria = createEntityCriteria();
                criteria.add(Restrictions.eq("idEmployee", idEmployee));
        Disjunction accountsDisjunction = Restrictions.disjunction();

        if (!accounts.isEmpty()) {
            for (Accounts account : accounts) {
                accountsDisjunction.add(Restrictions.eq("idAccount", account.getIdAccount()));
            }
            criteria.add(accountsDisjunction);
        }

        return (EmployeesAccounts) criteria.uniqueResult();
    }

}

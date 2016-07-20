package mx.bidg.service.impl;

import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import mx.bidg.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 26/01/16.
 */
@Service
@Transactional
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesDao employeesDao;

    @Override
    public List<Employees> findSimpleBy(DwEnterprises dwEnterprises) {
        return employeesDao.findSimpleBy(dwEnterprises);
    }

    @Override
    public List<Employees> findAll() {
        return employeesDao.findAll();
    }

    @Override
    public Employees findById(Integer id) {
        return employeesDao.findById(id);
    }

    @Override
    public Employees save(Employees employee) {
        return employeesDao.save(employee);
    }
}

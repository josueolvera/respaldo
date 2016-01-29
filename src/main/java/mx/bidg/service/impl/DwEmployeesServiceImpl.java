package mx.bidg.service.impl;

import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import mx.bidg.service.DwEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
@Service
@Transactional
public class DwEmployeesServiceImpl implements DwEmployeesService {

    @Autowired
    private DwEmployeesDao dwEmployeesDao;

    @Override
    public DwEmployees findById(Integer id) {
        return dwEmployeesDao.findById(id);
    }

    @Override
    public DwEmployees findBy(Employees employees, DwEnterprises dwEnterprises) {
        return dwEmployeesDao.findBy(employees, dwEnterprises);
    }
}

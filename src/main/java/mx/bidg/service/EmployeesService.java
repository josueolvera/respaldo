package mx.bidg.service;

import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 26/01/16.
 */
public interface EmployeesService {
    List<Employees> findSimpleBy(DwEnterprises dwEnterprises);
    List<Employees> findAll();
    Employees findById(Integer id);
    Employees save(Employees employee);
    Employees update(Employees employee);
    Employees findByRfc(String rfc);
    Employees findByCurp(String curp);
}

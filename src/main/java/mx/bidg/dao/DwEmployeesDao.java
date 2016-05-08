package mx.bidg.dao;

import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
public interface DwEmployeesDao extends InterfaceDao<DwEmployees> {
    DwEmployees findBy(Employees employee, DwEnterprises dwEnterprises);
    List<DwEmployees> findByDwEnterprisesId(DwEnterprises dwEnterprises);
}

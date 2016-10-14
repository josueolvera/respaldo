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
    List<DwEmployees> findByEmployeeAndDwEnterpriseAndRole
            (
                    List<Employees> employees,
                    List<DwEnterprises> dwEnterprises,
                    Integer idRole
            );
    List<DwEmployees> findByDwEnterprises
            (
                    List<DwEnterprises> dwEnterprises
            );
    DwEmployees findByEmployee(Integer idEmployee);
    List<DwEmployees> findByDwEnterprise(Integer idDwEnterprise);
    List<DwEmployees> findDwEmployeeByDwEnterpirseAndRole(Integer idDwEnterprise, Integer idRole);
    List<DwEmployees> findByRolePromotor(List<Employees> employeesList);
    DwEmployees findByIdEmployee(Integer idEmployee);
}

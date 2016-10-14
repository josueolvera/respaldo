package mx.bidg.dao;

import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 26/01/16.
 */
public interface EmployeesDao extends InterfaceDao<Employees> {
    List<Employees> findSimpleBy(DwEnterprises dwEnterprises);
    Employees findByClaveSap(String claveSap);
    List<Employees> findByNameAndRfc(String employeeName, String employeeRfc);
    List<Employees> findBetweenJoinDateAndStatus(String startDate, String endDate, Integer Status);
    List<Employees> findByStatus(Integer status);
    Employees findByRfc(String rfc);
    Employees findByCurp(String curp);
    List<Employees> findByJoinDate(LocalDateTime fromDate, LocalDateTime toDate);
}

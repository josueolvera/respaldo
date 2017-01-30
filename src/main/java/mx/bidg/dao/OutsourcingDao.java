package mx.bidg.dao;

import mx.bidg.model.DwEnterprises;
import mx.bidg.model.EmployeesHistory;
import mx.bidg.model.Outsourcing;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
public interface OutsourcingDao extends InterfaceDao<Outsourcing> {
    Outsourcing finfByidEmployee(int idEmployee,LocalDateTime applicationDate);
    List<Outsourcing> findByDwEnterprise(List<DwEnterprises> dwEnterprisesList, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd);
    List<Outsourcing> findByType(Integer type, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd);
    Object findSumRhmasByDwEnterprise(List<DwEnterprises> dwEnterprisesList, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd);
    List<Outsourcing> findByAllEmployeesAndApplicationDate(List<EmployeesHistory> employeesHistoryList, LocalDateTime iniialDate, LocalDateTime finalDate);
}

package mx.bidg.dao;

import mx.bidg.model.CDistributors;
import mx.bidg.model.EmployeesHistory;

import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
public interface EmployeesHistoryDao  extends InterfaceDao<EmployeesHistory>  {
    List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
            Integer status,
            List<CDistributors> distributors,
            Integer idRegion,
            Integer idZona,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String fullName,
            String rfc,
            String startDate,
            String endDate,
            Integer idEmployee
    );
    List<EmployeesHistory> findByIdEmployee(Integer idEmployee);
    EmployeesHistory findByIdEmployeeAndLastRegister(Integer idEmployee);
    List<EmployeesHistory>findByDistributor(Integer idDistributor);
    EmployeesHistory findIdEmployee(Integer idEmployee);
    EmployeesHistory findIdDistributor(Integer idDistributor);
    List<EmployeesHistory> findIdEmployeeAndIdDistributor(Integer idEmployee,Integer idDistributor);
    EmployeesHistory findEmployeeByClaveSap(String claveSap);
}

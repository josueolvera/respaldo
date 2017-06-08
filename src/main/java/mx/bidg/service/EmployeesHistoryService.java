package mx.bidg.service;

import mx.bidg.model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
public interface EmployeesHistoryService {
    EmployeesHistory findById(Integer id);
    List<EmployeesHistory> findAll();
    List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
            Integer status,
            List<CDistributors> distributors,
            Integer idRegion,
            Integer idZona,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String fullname,
            String rfc,
            String startDate,
            String endDate,
            Integer idEmployee
    );
    EmployeesHistory save(DwEmployees dwEmployee, CActionTypes actionType, Accounts accounts, Users user,String reason);
    List<EmployeesHistory>findByIdDistributor(Integer idDistributor);
    EmployeesHistory findByIdEmployeeAndLastRegister(Integer idEmployee);
    EmployeesHistory findIdEmployee(Integer idEmployee);
    EmployeesHistory findIdDistributor(Integer idDistributor);
    List<EmployeesHistory> findIdEmployeeAndIdDistributor(Integer idEmployee,Integer idDistributor);
}

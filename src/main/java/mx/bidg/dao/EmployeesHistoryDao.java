package mx.bidg.dao;

import mx.bidg.model.EmployeesHistory;

import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
public interface EmployeesHistoryDao  extends InterfaceDao<EmployeesHistory>  {
    List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
            Integer status,
            Integer idDistributor,
            Integer idRegion,
            Integer idZona,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
    List<EmployeesHistory> findByIdEmployee(Integer idEmployee);
}

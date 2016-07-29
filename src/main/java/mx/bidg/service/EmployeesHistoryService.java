package mx.bidg.service;

import mx.bidg.model.Accounts;
import mx.bidg.model.CActionTypes;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.EmployeesHistory;

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
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
    EmployeesHistory save(DwEmployees dwEmployee, CActionTypes actionType, Accounts accounts);
}

package mx.bidg.service;

import mx.bidg.model.EmployeesHistory;

import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
public interface EmployeesHistoryService {
    EmployeesHistory findById(Integer id);
    List<EmployeesHistory> findAll();
    List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRole(
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
}

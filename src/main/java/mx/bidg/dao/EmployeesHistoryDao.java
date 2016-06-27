package mx.bidg.dao;

import mx.bidg.model.EmployeesHistory;

import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
public interface EmployeesHistoryDao  extends InterfaceDao<EmployeesHistory>  {
    List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRole(
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole
    );
}

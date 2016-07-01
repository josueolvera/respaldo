package mx.bidg.service;

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
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
    void createReport(List<EmployeesHistory> employeesHistories, OutputStream outputStream) throws IOException;
}

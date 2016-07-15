package mx.bidg.service;

import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
public interface DwEmployeesService {
    DwEmployees findById(Integer id);
    DwEmployees findBy(Employees employees, DwEnterprises dwEnterprises);
    List<DwEmployees> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
    List<DwEmployees> findAll();
    DwEmployees save(DwEmployees dwEmployees);
    void createReport(List<DwEmployees> dwEmployees, OutputStream outputStream) throws IOException;
    void changeEmployeeStatus(Integer idDwEmployee);
    DwEmployees findByIdDw(Integer idDwEnterprise);
}

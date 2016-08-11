package mx.bidg.service;

import mx.bidg.model.*;

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
            Integer status,
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
    List<DwEmployees> findByDistributorRegionZonaBranchAndArea(
            Integer idDistributor,
            Integer idRegion,
            Integer idZona,
            Integer idBranch,
            Integer idArea
    );
    List<DwEmployees> findAll();
    DwEmployees save(DwEmployees dwEmployees);
    void createReport(List<EmployeesHistory> employeesHistories, OutputStream outputStream) throws IOException;
    void changeEmployeeStatus(Integer idDwEmployee, Users user);
    DwEmployees findByIdDw(Integer idDwEnterprise);
    DwEmployees update(String data, Users user) throws IOException;
    boolean delete (DwEmployees dwEmployees);
}

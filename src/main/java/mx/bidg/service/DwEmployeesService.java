package mx.bidg.service;

import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
public interface DwEmployeesService {
    DwEmployees findById(Integer id);
    DwEmployees findBy(Employees employees, DwEnterprises dwEnterprises);
    List<DwEmployees> findByDistributorAndRegionAndBranchAndAreaAndRole(
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea,
            Integer idRole,
            String startDate,
            String endDate
    );
    List<DwEmployees> findAll();
}

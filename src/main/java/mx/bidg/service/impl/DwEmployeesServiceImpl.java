package mx.bidg.service.impl;

import mx.bidg.dao.CRolesDao;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.CRoles;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import mx.bidg.service.DwEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
@Service
@Transactional
public class DwEmployeesServiceImpl implements DwEmployeesService {

    @Autowired
    private DwEmployeesDao dwEmployeesDao;

    @Autowired
    private DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    private CRolesDao cRolesDao;

    @Autowired
    private EmployeesDao employeesDao;

    @Override
    public DwEmployees findById(Integer id) {
        return dwEmployeesDao.findById(id);
    }

    @Override
    public DwEmployees findBy(Employees employees, DwEnterprises dwEnterprises) {
        return dwEmployeesDao.findBy(employees, dwEnterprises);
    }

    @Override
    public List<DwEmployees> findByDistributorAndRegionAndBranchAndAreaAndRole(Integer idDistributor, Integer idRegion, Integer idBranch, Integer idArea, Integer idRole,String startDate, String endDate) {

        List<Employees> employees = employeesDao.findBetweenJoinDate(startDate,endDate);

        List<DwEnterprises> dwEnterprises =
                dwEnterprisesDao.findByDistributorAndRegionAndBranchAndArea
                        (
                                idDistributor,
                                idRegion,
                                idBranch,
                                idArea
                        );
        return dwEmployeesDao.findByEmployeeAndDwEnterpriseAndRole(employees,dwEnterprises,idRole);
    }

    @Override
    public List<DwEmployees> findAll() {
        return dwEmployeesDao.findAll();
    }
}

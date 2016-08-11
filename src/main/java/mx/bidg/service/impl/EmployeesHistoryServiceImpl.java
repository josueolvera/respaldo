package mx.bidg.service.impl;

import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.*;
import mx.bidg.service.EmployeesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
@Service
@Transactional
public class EmployeesHistoryServiceImpl implements EmployeesHistoryService {

    @Autowired
    private EmployeesHistoryDao employeesHistoryDao;

    @Override
    public EmployeesHistory findById(Integer id) {
        return employeesHistoryDao.findById(id);
    }

    @Override
    public List<EmployeesHistory> findAll() {
        return employeesHistoryDao.findAll();
    }

    @Override
    public List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
        (Integer status, Integer idDistributor, Integer idRegion, Integer idZona ,Integer idBranch, Integer idArea, Integer idRole, 
                String fullname, String rfc,String startDate, String endDate) {
        return employeesHistoryDao.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
        (status,idDistributor,idRegion,idZona,idBranch,idArea,idRole,fullname,rfc,startDate,endDate);
    }

    @Override
    public EmployeesHistory save(DwEmployees dwEmployee, CActionTypes actionType, Accounts accounts, Users user) {

        if (dwEmployee != null) {
            EmployeesHistory employeesHistory = new EmployeesHistory();
            Employees employee = dwEmployee.getEmployee();
            DwEnterprises dwEnterprises = dwEmployee.getDwEnterprise();
            CRoles roles = dwEmployee.getRole();

            if (employee != null) {

                if (!actionType.equals(CActionTypes.ALTA)) {
                    List<EmployeesHistory> employeesHistories =
                            employeesHistoryDao.findByIdEmployee(employee.getIdEmployee());

                    for (EmployeesHistory currentEmployeeHistory : employeesHistories) {
                        currentEmployeeHistory.sethStatus(0);
                        employeesHistoryDao.update(currentEmployeeHistory);
                    }
                }

                employeesHistory.sethStatus(1);


                employeesHistory.setIdDwEmployee(dwEmployee.getIdDwEmployee());


                if (dwEnterprises != null) {
                    employeesHistory.setIdDwEnterprise(dwEnterprises.getIdDwEnterprise());
                    employeesHistory.setIdDistributor(dwEnterprises.getIdDistributor());
                    employeesHistory.setIdRegion(dwEnterprises.getIdRegion());
                    employeesHistory.setIdZona(dwEnterprises.getIdZona());
                    employeesHistory.setIdBranch(dwEnterprises.getIdBranch());
                    employeesHistory.setIdArea(dwEnterprises.getIdArea());
                }

                if (roles != null) {
                    employeesHistory.setIdRole(roles.getIdRole());
                }

                if (accounts != null) {
                    employeesHistory.setIdAccount(accounts.getIdAccount());
                }

                CEducation education = employee.getEducation();

                if (education != null) {
                    employeesHistory.setIdEducation(education.getIdEducation());
                }

                CStatusMarital statusMarital = employee.getStatusMarital();

                if (statusMarital != null) {
                    employeesHistory.setIdStatusMarital(statusMarital.getIdStatusMarital());
                }

                CEmployeeType cEmployeeType = employee.getEmployeeType();

                if (cEmployeeType != null){
                    employeesHistory.setIdEmployeeType(cEmployeeType.getIdEmployeeType());
                }

                CContractType cContractType = employee.getContractType();

                if(cContractType != null){
                    employeesHistory.setIdContractType(cContractType.getIdContractType());
                }

                CGenders cGender = employee.getGender();

                if (cGender != null){
                    employeesHistory.setIdGender(cGender.getIdGender());
                }

                if (user != null){
                    employeesHistory.setUsername(user.getUsername());
                }

                employeesHistory.setIdEmployee(employee.getIdEmployee());
                employeesHistory.setBirthday(employee.getBirthday());
                employeesHistory.setBirthplace(employee.getBirthPlace());
                employeesHistory.setCellPhone(employee.getCellPhone());
                employeesHistory.setClaveSap(employee.getClaveSap());
                employeesHistory.setColonia(employee.getColonia());
                employeesHistory.setCurp(employee.getCurp());
                employeesHistory.setCity(employee.getCity());
                employeesHistory.setEmployeeNumber(employee.getEmployeeNumber());
                employeesHistory.setExteriorNumber(employee.getExteriorNumber());
                employeesHistory.setFatherName(employee.getFatherName());
                employeesHistory.setFirstName(employee.getFirstName());
                employeesHistory.setHomePhone(employee.getHomePhone());
                employeesHistory.setInteriorNumber(employee.getInteriorNumber());
                employeesHistory.setJoinDate(employee.getJoinDate());
                employeesHistory.setMail(employee.getMail());
                employeesHistory.setMiddleName(employee.getMiddleName());
                employeesHistory.setMotherLast(employee.getMotherLast());
                employeesHistory.setMotherName(employee.getMotherName());
                employeesHistory.setParentalLast(employee.getParentalLast());
                employeesHistory.setPostcode(employee.getPostcode());
                employeesHistory.setRfc(employee.getRfc());
                employeesHistory.setSalary(employee.getSalary());
                employeesHistory.setIdSize(employee.getSize().getIdSize());
                employeesHistory.setSizeNumber(employee.getSizeNumber());
                employeesHistory.setState(employee.getState());
                employeesHistory.setStreet(employee.getStreet());
                employeesHistory.setImss(employee.getImss());
                employeesHistory.setInfonavitNumber(employee.getInfonavitNumber());
                employeesHistory.setSistarh(employee.getSistarh());
            }

            employeesHistory.setIdActionType(actionType.getIdActionType());

            employeesHistory.setCreationDate(LocalDateTime.now());

            employeesHistory = employeesHistoryDao.save(employeesHistory);
            return employeesHistory;
        }
        return new EmployeesHistory();
    }
}

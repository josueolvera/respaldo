package mx.bidg.service.impl;

import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.*;
import mx.bidg.service.EmployeesHistoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(Integer idDistributor, Integer idRegion, Integer idBranch, Integer idArea, Integer idRole, String startDate, String endDate) {
        return employeesHistoryDao.findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(idDistributor,idRegion,idBranch,idArea,idRole,startDate,endDate);
    }

    @Override
    public EmployeesHistory save(DwEmployees dwEmployee, CActionTypes actionType) {

        if (dwEmployee != null) {
            EmployeesHistory employeesHistory = new EmployeesHistory();
            Employees employee = dwEmployee.getEmployee();

            if (employee != null) {

                if (!actionType.equals(CActionTypes.ALTA)) {
                    List<EmployeesHistory> employeesHistories =
                            employeesHistoryDao.findByIdEmployee(employee.getIdEmployee());

                    for (EmployeesHistory currentEmployeeHistory : employeesHistories) {
                        currentEmployeeHistory.setStatus(0);
                        employeesHistoryDao.update(currentEmployeeHistory);
                    }
                }

                employeesHistory.setStatus(1);

                CEducation education = employee.getEducation();

                if (education != null) {
                    employeesHistory.setIdEducation(education.getIdEducation());
                }

                CStatusMarital statusMarital = employee.getStatusMarital();

                if (statusMarital != null) {
                    employeesHistory.setIdStatusMarital(statusMarital.getIdStatusMarital());
                }

                CEmployeeType cEmployeeType = employee.getEmployeeTypes();

                if (cEmployeeType != null){
                    employeesHistory.setEmployeeType(cEmployeeType.getEmployeeType());
                }

                CContractType cContractType = employee.getContractTypes();

                if(cContractType != null){
                    employeesHistory.setContractType(cContractType.getContractType());
                }

                CGenders cGender = employee.getGenders();

                if (cGender != null){
                    employeesHistory.setGender(cGender.getGender());
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
                employeesHistory.setSize(employee.getSize());
                employeesHistory.setSizeNumber(employee.getSizeNumber());
                employeesHistory.setState(employee.getState());
                employeesHistory.setStreet(employee.getStreet());
                employeesHistory.setImss(employee.getImss());
                employeesHistory.setInfonavitNumber(employee.getInfonavitNumber());
            }

            employeesHistory.setIdActionType(actionType.getIdActionType());

            employeesHistory.setCreationDate(LocalDateTime.now());

            employeesHistory = employeesHistoryDao.save(employeesHistory);
            return employeesHistory;
        }
        return new EmployeesHistory();
    }
}

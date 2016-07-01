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
    public void createReport(List<EmployeesHistory> employeesHistories, OutputStream outputStream) throws IOException {

        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja = wb.createSheet();

        //Se crea la fila que contiene la cabecera
        Row row = hoja.createRow(0);

        row.createCell(0).setCellValue("EMPRESA");
        row.createCell(1).setCellValue("REGION");
        row.createCell(2).setCellValue("NOMBRE DEL EMPLEADO");
        row.createCell(3).setCellValue("CLAVE SAP");
        row.createCell(4).setCellValue("PUESTO");
        row.createCell(5).setCellValue("BANCO");
        row.createCell(6).setCellValue("N. CUENTA");
        row.createCell(7).setCellValue("CLABE");
        row.createCell(8).setCellValue("SUCURSAL");
        row.createCell(9).setCellValue("RFC");
        row.createCell(10).setCellValue("CURP");
        row.createCell(11).setCellValue("DOMICILIO");
        row.createCell(12).setCellValue("MAIL");
        row.createCell(13).setCellValue("FECHA DE INGRESO");
        row.createCell(14).setCellValue("SUELDO QUINCENAL");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (EmployeesHistory employeeHistory : employeesHistories) {

            row = hoja.createRow(aux);

            Date joinDate = Date.from(employeeHistory.getJoinDate().atZone(ZoneId.systemDefault()).toInstant());


            // Create a cell and put a value in it.
            row.createCell(0).setCellValue(employeeHistory.getDistributorName());
            row.createCell(1).setCellValue(employeeHistory.getRegionName());
            row.createCell(2).setCellValue(employeeHistory.getFullName());
            row.createCell(3).setCellValue(employeeHistory.getClaveSap());
            row.createCell(4).setCellValue(employeeHistory.getRoleName());
            row.createCell(5).setCellValue(employeeHistory.getBankAcronyms());
            row.createCell(6).setCellValue(employeeHistory.getAccountNumber());
            row.createCell(7).setCellValue(employeeHistory.getAccountClabe());
            row.createCell(8).setCellValue(employeeHistory.getBranchShort());
            row.createCell(9).setCellValue(employeeHistory.getRfc());
            row.createCell(10).setCellValue(employeeHistory.getCurp());
            row.createCell(11).setCellValue(employeeHistory.getStreet());
            row.createCell(12).setCellValue(employeeHistory.getMail());
            row.createCell(13);
            row.getCell(13).setCellValue(joinDate);
            row.getCell(13).setCellStyle(cellDateStyle);
            row.createCell(14).setCellValue(employeeHistory.getSalary().floatValue());

            aux++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);

        wb.write(outputStream);
    }

    @Override
    public EmployeesHistory save(DwEmployees dwEmployee) {

        if (dwEmployee != null) {
            EmployeesHistory employeesHistory = new EmployeesHistory();
            CActionTypes actionType = new CActionTypes(3);

            Employees employee = dwEmployee.getEmployee();

            if (employee != null) {
                List<EmployeesHistory> employeesHistories =
                        employeesHistoryDao.findByIdEmployee(employee.getIdEmployee());

                for (EmployeesHistory currentEmployeeHistory : employeesHistories) {
                    currentEmployeeHistory.setHStatus(0);
                    employeesHistoryDao.update(currentEmployeeHistory);
                }


                List<EmployeesAccounts> employeeAccountList = employee.getEmployeesAccountsList();

                if (!employeeAccountList.isEmpty()) {
                    Accounts account = employeeAccountList.get(0).getAccount();

                    if (account != null) {
                        employeesHistory.setAccountClabe(account.getAccountClabe());
                        employeesHistory.setAccountNumber(account.getAccountNumber());

                        CAccountsTypes accountType = account.getAccountType();
                        if (accountType != null) {
                            employeesHistory.setAccountType(accountType.getIdAccountType());
                        }
                        CBanks bank = account.getBank();
                        if (bank != null) {
                            employeesHistory.setBankAcronyms(bank.getAcronyms());
                        }
                    }
                }

                CEducation education = employee.getEducation();

                if (education != null) {
                    employeesHistory.setEducation(education.getEducationName());
                    employeesHistory.setIdEducation(education.getIdEducation());
                }

                CStatusMarital statusMarital = employee.getStatusMarital();

                if (statusMarital != null) {
                    employeesHistory.setIdStatusMarital(statusMarital.getIdStatusMarital());
                    employeesHistory.setStatusMarital(statusMarital.getMaritalName());
                }

                employeesHistory.setBirthdate(employee.getBirthday());
                employeesHistory.setBirthplace(employee.getBirthPlace());
                employeesHistory.setCellPhone(employee.getCellPhone());
                employeesHistory.setClaveSap(employee.getClaveSap());
                employeesHistory.setColonia(employee.getColonia());
                employeesHistory.setContractType(employee.getContractType());
                employeesHistory.setCurp(employee.getCurp());
                employeesHistory.setDelegationMunicipality(employee.getCity());
                employeesHistory.setEmployeeNumber(employee.getEmployeeNumber());
                employeesHistory.setEmployeeType(employee.getEmployeeType());
                employeesHistory.setExteriorNumber(employee.getExteriorNumber());
                employeesHistory.setFatherName(employee.getFatherName());
                employeesHistory.setFirstName(employee.getFirstName());
                employeesHistory.setGender(employee.getGender());
                employeesHistory.setHomePhone(employee.getHomePhone());
                employeesHistory.setIdEmployee(employee.getIdEmployee());
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
            }

            DwEnterprises dwEnterprise = dwEmployee.getDwEnterprise();

            if (dwEnterprise != null) {

                CGroups group = dwEnterprise.getGroup();

                if (group != null) {
                    employeesHistory.setGroupName(group.getGroupName());
                    employeesHistory.setIdGroup(group.getIdGroup());
                    employeesHistory.setGroupAcronyms(group.getAcronyms());
                }

                CDistributors distributor = dwEnterprise.getDistributor();

                if (distributor != null) {
                    employeesHistory.setDistributorName(distributor.getDistributorName());
                    employeesHistory.setIdDistributor(distributor.getIdDistributor());
                }

                CRegions region = dwEnterprise.getRegion();

                if (region != null) {
                    employeesHistory.setIdRegion(region.getIdRegion());
                    employeesHistory.setRegionName(region.getRegionName());
                }

                CBranchs branch = dwEnterprise.getBranch();

                if (branch != null) {
                    employeesHistory.setBranchShort(branch.getBranchShort());
                    employeesHistory.setBranchName(branch.getBranchName());
                    employeesHistory.setIdBranch(branch.getIdBranch());
                }

                CAreas area = dwEnterprise.getArea();

                if (area != null) {
                    employeesHistory.setAreaName(area.getAreaName());
                    employeesHistory.setIdArea(area.getIdArea());
                }

            }

            CRoles role = dwEmployee.getRole();

            if (role != null) {
                employeesHistory.setIdRole(role.getIdRole());
                employeesHistory.setRoleName(role.getRoleName());
            }

            Users user = dwEmployee.getUser();

            if (user != null) {
                employeesHistory.setUsername(user.getUsername());
            }


            employeesHistory.setIdActionType(actionType.getIdActionType());
            employeesHistory.setActionType(actionType.getActionType());

            employeesHistory.setHStatus(1);
            employeesHistory.setCreationDate(LocalDateTime.now());

            employeesHistory = employeesHistoryDao.save(employeesHistory);
            return employeesHistory;
        }
        return new EmployeesHistory();
    }
}

package mx.bidg.service.impl;

import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.ZoneId;
import java.util.Date;
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
    private EmployeesHistoryService employeesHistoryService;

    @Autowired
    private EmployeesDao employeesDao;

    @Autowired
    private EmployeesAccountsDao employeesAccountsDao;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmployeesAccountsService employeesAccountsService;

    @Override
    public DwEmployees findById(Integer id) {
        return dwEmployeesDao.findById(id);
    }

    @Override
    public DwEmployees findBy(Employees employees, DwEnterprises dwEnterprises) {
        return dwEmployeesDao.findBy(employees, dwEnterprises);
    }

    @Override
    public List<DwEmployees> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate(Integer status, Integer idDistributor, Integer idRegion, Integer idBranch, Integer idArea, Integer idRole,String startDate, String endDate) {

        List<Employees> employees = employeesDao.findBetweenJoinDateAndStatus(startDate,endDate, status);

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

    @Override
    public DwEmployees save(DwEmployees dwEmployees) {
        return dwEmployeesDao.save(dwEmployees);
    }

    @Override
    public void createReport(List<DwEmployees> dwEmployees, OutputStream outputStream) throws IOException {

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
        String direccion = "";

        for (DwEmployees dwEmployee : dwEmployees) {

            row = hoja.createRow(aux);

            DwEnterprises dwEnterprise = dwEmployee.getDwEnterprise();
            CDistributors distributor = dwEnterprise.getDistributor();
            CRegions region = dwEnterprise.getRegion();
            CBranchs branch = dwEnterprise.getBranch();
            Employees employee = dwEmployee.getEmployee();
            List<EmployeesAccounts> employeeAccountList = employee.getEmployeesAccountsList();

            if (!employeeAccountList.isEmpty()) {
                Accounts account = employeeAccountList.get(0).getAccount();
                if (account != null) {
                    row.createCell(6).setCellValue(account.getAccountNumber());
                    row.createCell(7).setCellValue(account.getAccountClabe());
                    CBanks bank = account.getBank();
                    if (bank != null) {
                        row.createCell(5).setCellValue(bank.getAcronyms());
                    }
                }
            }

            CRoles role = dwEmployee.getRole();

            if (employee.getJoinDate() != null) {
                Date joinDate = Date.from(employee.getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                row.createCell(13);
                row.getCell(13).setCellValue(joinDate);
                row.getCell(13).setCellStyle(cellDateStyle);
            }


            // Create a cell and put a value in it.
            row.createCell(0).setCellValue(distributor.getDistributorName());
            row.createCell(1).setCellValue(region.getRegionName());
            row.createCell(2).setCellValue(employee.getFullName().replace("_", " "));
            row.createCell(3).setCellValue(employee.getClaveSap());
            row.createCell(4).setCellValue(role.getRoleName());
            row.createCell(8).setCellValue(branch.getBranchName());
            row.createCell(9).setCellValue(employee.getRfc());
            row.createCell(10).setCellValue(employee.getCurp());

            if (employee.getStreet() != null){
                direccion = direccion + employee.getStreet().replace("_", " ");
            }

            if (employee.getExteriorNumber() != null){
                direccion = direccion + " No. Ext. "+ employee.getExteriorNumber().replace("_", " ");
            }

            if (employee.getInteriorNumber() != null){
                direccion = direccion + " No. Int. " + employee.getInteriorNumber().replace("_", " ");
            }

            if(employee.getColonia() != null){
                direccion = direccion + " Col. " + employee.getColonia().replace("_", " ");
            }

            if(employee.getCity() != null){
                direccion = direccion + " Ciudad " + employee.getCity().replace("_", " ");
            }

            if(employee.getState() != null){
                direccion = direccion + " Edo. de " + employee.getState().replace("_", " ");
            }

            if(employee.getPostcode() != null){
                direccion = direccion + " C.P. " + employee.getPostcode().replace("_", " ");
            }
            row.createCell(11).setCellValue(direccion);
            direccion = "";
            row.createCell(12).setCellValue(employee.getMail());
            row.createCell(14).setCellValue((employee.getSalary().floatValue())/2);

            aux++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);

        wb.write(outputStream);
    }

    @Override
    public void changeEmployeeStatus(Integer idDwEmployee) {
        DwEmployees dwEmployee = dwEmployeesDao.findById(idDwEmployee);

        Employees employee = dwEmployee.getEmployee();
        employee.setStatus(0);
        employeesDao.update(employee);

        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());

        CActionTypes actionType = CActionTypes.BAJA;
        employeesHistoryService.save(dwEmployee, actionType, employeesAccounts.getAccount());

        dwEmployeesDao.delete(dwEmployee);

        EmailTemplates emailTemplate = emailTemplatesService.findByName("employee_low_notification");
        emailTemplate.addProperty("dwEmployee", dwEmployee);

        emailDeliveryService.deliverEmail(emailTemplate);
    }

    @Override
    public DwEmployees findByIdDw(Integer idDwEnterprise) {
        return dwEmployeesDao.findByEmployee(idDwEnterprise);
    }

    @Override
    public DwEmployees update(DwEmployees dwEmployee) {
        return dwEmployeesDao.update(dwEmployee);
    }

    @Override
    public boolean delete(DwEmployees dwEmployees) {
        dwEmployeesDao.delete(dwEmployees);
        return true;
    }
}

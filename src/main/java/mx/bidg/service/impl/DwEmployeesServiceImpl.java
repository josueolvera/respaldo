package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mx.bidg.dao.*;
import mx.bidg.dao.impl.CStatusMaritalDaoImpl;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private CRolesDao cRolesDao;

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

    @Autowired
    private AccountsDao accountsDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CDistributorsDao distributorsDao;

    @Autowired
    private CRegionsDao regionsDao;

    @Autowired
    private  CBranchsDao branchsDao;

    @Autowired
    private DwEnterprisesService dwEnterprisesService;

    @Autowired
    private CZonaDao zonaDao;

    @Autowired
    private CAreasDao areasDao;

    @Autowired
    private CGendersDao gendersDao;

    @Autowired
    private CStatusMaritalDao statusMaritalDao;

    @Autowired
    private CEducationDao cEducationDao;

    @Autowired
    private MultilevelEmployeeDao multilevelEmployeeDao;

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
    public List<DwEmployees> findByDistributorRegionZonaBranchAndArea(Integer idDistributor, Integer idRegion, Integer idZona,Integer idBranch, Integer idArea) {
        List<DwEnterprises> dwEnterprises =
                dwEnterprisesDao.findByDistributorRegionZonaBranchAndArea(
                        idDistributor,
                        idRegion,
                        idZona,
                        idBranch,
                        idArea
                );
        return dwEmployeesDao.findByDwEnterprises(dwEnterprises);
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
    public void createReportDistributors(List<EmployeesHistory> dwEmployees, OutputStream outputStream) throws IOException {

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

        row.createCell(0).setCellValue("ID DE EMPLEADO");
        row.createCell(1).setCellValue("EMPRESA");
        row.createCell(2).setCellValue("REGION");
        row.createCell(3).setCellValue("ZONA");
        row.createCell(4).setCellValue("NOMBRE DEL EMPLEADO");
        row.createCell(5).setCellValue("CLAVE SAP");
        row.createCell(6).setCellValue("PUESTO");
        row.createCell(7).setCellValue("BANCO");
        row.createCell(8).setCellValue("N. CUENTA");
        row.createCell(9).setCellValue("CLABE");
        row.createCell(10).setCellValue("SUCURSAL");
        row.createCell(11).setCellValue("RFC");
        row.createCell(12).setCellValue("CURP");
        row.createCell(13).setCellValue("FECHA DE INGRESO");
        row.createCell(14).setCellValue("SUELDO QUINCENAL");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (EmployeesHistory dwEmployee : dwEmployees) {

            row = hoja.createRow(aux);

            if(dwEmployee.getIdEmployee() != null) {
                Employees employee = employeesDao.findById(dwEmployee.getIdEmployee());
                EmployeesAccounts employeeAccount = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());
            }

            if(dwEmployee.getIdDwEnterprise() != null){
                DwEnterprises dwEnterprise = dwEnterprisesDao.findById(dwEmployee.getIdDwEnterprise());
                if(dwEnterprise != null){
                    CDistributors distributor = distributorsDao.findById(dwEnterprise.getIdDistributor());
                    CRegions region = regionsDao.findById(dwEnterprise.getIdRegion());
                    CBranchs branch = branchsDao.findById(dwEnterprise.getIdBranch());
                    CZonas zona = zonaDao.findById(dwEnterprise.getIdZona());

                    if(distributor != null){
                        row.createCell(1).setCellValue(distributor.getDistributorName());
                    }

                    if(region != null){
                        row.createCell(2).setCellValue(region.getRegionName());
                    }

                    if(branch != null){
                        row.createCell(10).setCellValue(branch.getBranchShort());
                    }

                    if (zona != null){
                        row.createCell(3).setCellValue(zona.getName());
                    }
                }
            }

            if(dwEmployee.getIdRole() != null){
                CRoles role = cRolesDao.findById(dwEmployee.getIdRole());
                if (role != null){
                    row.createCell(6).setCellValue(role.getRoleName());
                }
            }

            if(dwEmployee.getIdEmployee() != null){
                Employees employee = employeesDao.findById(dwEmployee.getIdEmployee());
                List<EmployeesAccounts> employeeAccountList = employee.getEmployeesAccountsList();

                if (!employeeAccountList.isEmpty()) {
                    Accounts account = employeeAccountList.get(0).getAccount();
                    if (account != null) {
                        row.createCell(8).setCellValue(account.getAccountNumber());
                        row.createCell(9).setCellValue(account.getAccountClabe());
                        CBanks bank = account.getBank();
                        if (bank != null) {
                            row.createCell(7).setCellValue(bank.getAcronyms());
                        }
                    }
                }

                if (employee.getJoinDate() != null) {
                    Date joinDate = Date.from(employee.getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                    row.createCell(13);
                    row.getCell(13).setCellValue(joinDate);
                    row.getCell(13).setCellStyle(cellDateStyle);
                }

                // Create a cell and put a value in it.

                row.createCell(0).setCellValue(employee.getIdEmployee());
                row.createCell(4).setCellValue(employee.getFullName().replace("_", " "));
                row.createCell(5).setCellValue(employee.getClaveSap());
                row.createCell(11).setCellValue(employee.getRfc());
                row.createCell(12).setCellValue(employee.getCurp());
                row.createCell(14).setCellValue((employee.getSalary().floatValue())/2);
            }

            aux++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);

        wb.write(outputStream);
    }

    @Override
    public void changeEmployeeStatus(Integer idDwEmployee, Users user) {
        DwEmployees dwEmployee = dwEmployeesDao.findById(idDwEmployee);

        if(dwEmployee.getRole() != null){
            if (dwEmployee.getRole().getIdRole() == 81){
                if (dwEmployee.getEmployee() != null){
                    List<MultilevelEmployee> multilevelEmployeeList = multilevelEmployeeDao.findByIdEmployeeMultilevel(dwEmployee.getEmployee().getIdEmployee());
                    if (!multilevelEmployeeList.isEmpty()){
                        for (MultilevelEmployee multilevelEmployee : multilevelEmployeeList){
                            multilevelEmployeeDao.delete(multilevelEmployee);
                        }
                    }
                }
            }else if(dwEmployee.getRole().getIdRole() == 64){
                if (dwEmployee.getEmployee() != null){
                    MultilevelEmployee multilevelEmployee = multilevelEmployeeDao.findByEmployee(dwEmployee.getEmployee().getIdEmployee());
                    if (multilevelEmployee != null){
                        multilevelEmployeeDao.delete(multilevelEmployee);
                    }
                }
            }
        }

        Employees employee = dwEmployee.getEmployee();
        employee.setStatus(0);
        employeesDao.update(employee);

        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());

        CActionTypes actionType = CActionTypes.BAJA;
        employeesHistoryService.save(dwEmployee, actionType, employeesAccounts.getAccount(), user);
        DwEmployees dw=dwEmployee;
        dwEmployeesDao.delete(dwEmployee);

        if(dw.getRole().getIdRole()==63 ||dw.getRole().getIdRole()==80) {
            EmailTemplates emailTemplate = emailTemplatesService.findByName("sucursal_notification");
            emailTemplate.addProperty("dwEmployee", dw);
            emailDeliveryService.deliverEmail(emailTemplate);
        }else if(dw.getRole().getIdRole()==64){
            EmailTemplates emailTemplate = emailTemplatesService.findByName("promotor_notification");
            emailTemplate.addProperty("dwEmployee", dw);
            emailDeliveryService.deliverEmail(emailTemplate);
        }else {
            EmailTemplates emailTemplate = emailTemplatesService.findByName("corporativo_notification");
            emailTemplate.addProperty("dwEmployee", dw);
            emailDeliveryService.deliverEmail(emailTemplate);
        }
    }

    @Override
    public DwEmployees findByIdDw(Integer idDwEnterprise) {
        return dwEmployeesDao.findByEmployee(idDwEnterprise);
    }

    @Override
    public DwEmployees update(String data, Users user) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        JsonNode jsonNode = mapper.readTree(data);
        JsonNode employeeNode = jsonNode.get("dwEmployee").get("employee");

        DwEmployees dwEmployee = dwEmployeesDao.findById(jsonNode.get("dwEmployee").get("idDwEmployee").asInt());

        List<DwEnterprises> dwEnterprisesList;

        if(jsonNode.get("dwEmployee").get("dwEnterprise").get("distributor").get("idDistributor").asInt() == 2 || jsonNode.get("dwEmployee").get("dwEnterprise").get("distributor").get("idDistributor").asInt() == 3){

            if( jsonNode.get("dwEmployee").get("dwEnterprise").get("zona").get("idZonas").asInt() == 0 && jsonNode.get("dwEmployee").get("dwEnterprise").get("branch").get("idBranch").asInt() == 0){

                dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("region").get("idRegion").asInt(),0,0,
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("area").get("idArea").asInt());

            } else if (jsonNode.get("dwEmployee").get("dwEnterprise").get("branch").get("idBranch").asInt() == 0){

                dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("region").get("idRegion").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("zona").get("idZonas").asInt(),0,
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("area").get("idArea").asInt());

            } else {

                dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("region").get("idRegion").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("zona").get("idZonas").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("branch").get("idBranch").asInt(),
                        jsonNode.get("dwEmployee").get("dwEnterprise").get("area").get("idArea").asInt());
            }
        } else {
            dwEnterprisesList = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(jsonNode.get("dwEmployee").get("dwEnterprise").get("distributor").get("idDistributor").asInt(),
                    null,null,jsonNode.get("dwEmployee").get("dwEnterprise").get("branch").get("idBranch").asInt(),
                    jsonNode.get("dwEmployee").get("dwEnterprise").get("area").get("idArea").asInt());
        }

        CRoles role = cRolesDao.findById(jsonNode.get("dwEmployee").get("role").get("idRole").asInt());
        Employees employee = employeesDao.findById(employeeNode.get("idEmployee").asInt());

        LocalDateTime joinDate = LocalDateTime.parse(jsonNode.get("dwEmployee").get("employee").get("joinDate").asText() + " 00:00",formatter);
        LocalDate birthday = LocalDateTime.parse(jsonNode.get("dwEmployee").get("employee").get("birthday").asText() + " 00:00",formatter).toLocalDate();

        employee.setStreet(employeeNode.get("street").asText());
        employee.setBirthday(birthday);
        employee.setCellPhone(employeeNode.get("cellPhone").asText());
        employee.setContractType(mapper.treeToValue(employeeNode.get("contractType"),CContractType.class));
        employee.setBirthplace(employeeNode.get("birthPlace").asText());
        employee.setColonia(employeeNode.get("colonia").asText());
        employee.setCity(employeeNode.get("city").asText());
        employee.setClaveSap(employeeNode.get("claveSap").asText());
        employee.setCurp(employeeNode.get("curp").asText());
        employee.setRfc(employeeNode.get("rfc").asText());
        employee.setMotherName(employeeNode.get("motherName").asText());
        employee.setFatherName(employeeNode.get("fatherName").asText());
        employee.setFirstName(employeeNode.get("firstName").asText());
        employee.setMiddleName(employeeNode.get("middleName").asText());
        employee.setParentalLast(employeeNode.get("parentalLast").asText());
        employee.setMotherLast(employeeNode.get("motherLast").asText());
        employee.setPostcode(employeeNode.get("postcode").asText());
        employee.setEducation(mapper.treeToValue(employeeNode.get("education"),CEducation.class));
        employee.setEmployeeType(mapper.treeToValue(employeeNode.get("employeeType"),CEmployeeType.class));
        employee.setSize(mapper.treeToValue(employeeNode.get("size"),CSizes.class));
        employee.setSizeNumber(employeeNode.get("sizeNumber").asInt());
        employee.setState(employeeNode.get("state").asText());
        employee.setExteriorNumber(employeeNode.get("exteriorNumber").asText());
        employee.setInteriorNumber(employeeNode.get("interiorNumber").asText());
        employee.setGender(mapper.treeToValue(employeeNode.get("gender"),CGenders.class));
        employee.setHomePhone(employeeNode.get("homePhone").asText());
        employee.setImss(employeeNode.get("imss").asText());
        employee.setInfonavitNumber(employeeNode.get("infonavitNumber").asText());
        employee.setStatusMarital(mapper.treeToValue(employeeNode.get("statusMarital"),CStatusMarital.class));
        employee.setSalary(new BigDecimal(employeeNode.get("salary").asInt()));
        employee.setStatus(1);
        employee.setMail(employeeNode.get("mail").asText());
        employee.setJoinDate(joinDate);

        if (employeeNode.has("sistarh")) {
            employee.setSistarh(employeeNode.get("sistarh").asText());
        }

        dwEmployee.setDwEnterprise(dwEnterprisesList.get(0));
        dwEmployee.setRole(role);
        dwEmployee.setEmployee(employee);

        dwEmployee = dwEmployeesDao.update(dwEmployee);

        Accounts currentAccount = accountsDao.findById(jsonNode.get("employeeAccount").get("account").get("idAccount").asInt());

        ObjectNode accountNode = (ObjectNode) jsonNode.get("employeeAccount").get("account");
        accountNode.remove("employeesAccountsList");
        accountNode.remove("providersAccountsList");
        accountNode.remove("priceEstimationsList");
        accountNode.remove("idAccount");
        accountNode.remove("deleteDay");
        accountNode.remove("idBank");
        accountNode.remove("idAccountType");
        accountNode.remove("idCurrency");
        accountNode.remove("_id");

        Accounts account = mapper.treeToValue(jsonNode.get("employeeAccount").get("account"), Accounts.class);

        EmployeesAccounts employeesAccount = employeesAccountsDao.findByIdEmployee(employee.getIdEmployee());

        if (!account.equals(currentAccount)) {
            currentAccount.setDeleteDay(LocalDateTime.now());
            accountsDao.update(currentAccount);
            account = accountsDao.save(account);

            employeesAccount.setAccount(account);

            employeesAccountsDao.save(employeesAccount);
        }

        employeesHistoryService.save(dwEmployee, CActionTypes.MODIFICAION, employeesAccount.getAccount(), user);

        return dwEmployee;
    }

    @Override
    public boolean delete(DwEmployees dwEmployees) {
        dwEmployeesDao.delete(dwEmployees);
        return true;
    }

    @Override
    public boolean validateExistRole(Integer idDwEnterprise, Integer idRole) {
        boolean exist = false;
        List<DwEmployees> dwEmployeesList = dwEmployeesDao.findDwEmployeeByDwEnterpirseAndRole(idDwEnterprise, idRole);

        if (dwEmployeesList.size()>0){
            exist = true;
        }

        return exist;
    }

    @Override
    public void createReportCompanys(List<EmployeesHistory> employeesHistories, OutputStream outputStream) throws IOException {
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

        row.createCell(0).setCellValue("ID DE EMPLEADO");
        row.createCell(1).setCellValue("EMPRESA");
        row.createCell(2).setCellValue("NOMBRE DEL EMPLEADO");
        row.createCell(3).setCellValue("AREA");
        row.createCell(4).setCellValue("PUESTO");
        row.createCell(5).setCellValue("BANCO");
        row.createCell(6).setCellValue("N. CUENTA");
        row.createCell(7).setCellValue("CLABE");
        row.createCell(8).setCellValue("RFC");
        row.createCell(9).setCellValue("CURP");
        row.createCell(10).setCellValue("FECHA DE INGRESO");
        row.createCell(11).setCellValue("SUELDO QUINCENAL");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (EmployeesHistory eH : employeesHistories) {

            row = hoja.createRow(aux);


            if(eH.getIdEmployee() != null) {
                Employees employee = employeesDao.findById(eH.getIdEmployee());
                EmployeesAccounts employeeAccount = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());
            }
            if(eH.getIdDwEnterprise() != null){
                DwEnterprises dwEnterprise = dwEnterprisesDao.findById(eH.getIdDwEnterprise());
                CDistributors distributor = distributorsDao.findById(eH.getIdDistributor());
                CAreas area = areasDao.findById(eH.getIdArea());

                if(distributor != null){
                    row.createCell(1).setCellValue(distributor.getDistributorName());
                }

                if (area != null){
                    row.createCell(3).setCellValue(area.getAreaName());
                }
            }

            if(eH.getIdRole() != null){
                CRoles role = cRolesDao.findById(eH.getIdRole());
                if (role != null){
                    row.createCell(4).setCellValue(role.getRoleName());
                }
            }

            if(eH.getIdEmployee() != null){
                Employees employee = employeesDao.findById(eH.getIdEmployee());
                EmployeesAccounts employeeAccount = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());

                if (employeeAccount != null) {
                    Accounts accounts = employeeAccount.getAccount();
                    if (accounts != null) {
                        row.createCell(6).setCellValue(accounts.getAccountNumber());
                        row.createCell(7).setCellValue(accounts.getAccountClabe());
                        CBanks bank = accounts.getBank();
                        if (bank != null) {
                            row.createCell(5).setCellValue(bank.getAcronyms());
                        }
                    }
                }

                if (employee.getJoinDate() != null) {
                    Date joinDate = Date.from(employee.getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                    row.createCell(10);
                    row.getCell(10).setCellValue(joinDate);
                    row.getCell(10).setCellStyle(cellDateStyle);
                }

                // Create a cell and put a value in it.
                row.createCell(0).setCellValue(employee.getIdEmployee());
                row.createCell(2).setCellValue(employee.getFullName().replace("_", " "));
                row.createCell(8).setCellValue(employee.getRfc());
                row.createCell(9).setCellValue(employee.getCurp());
                row.createCell(11).setCellValue((employee.getSalary().floatValue())/2);
            }

            aux++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);

        wb.write(outputStream);
    }

    @Override
    public void createReportBpo(List<EmployeesHistory> employeesHistorys, OutputStream outputStream) throws IOException {
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

        row.createCell(0).setCellValue("ID DE EMPLEADO");
        row.createCell(1).setCellValue("CURP");
        row.createCell(2).setCellValue("CLAVE BANCO");
        row.createCell(3).setCellValue("APELLIDO PATERNO");
        row.createCell(4).setCellValue("APELLIDO MATERNO");
        row.createCell(5).setCellValue("NOMBRES");
        row.createCell(6).setCellValue("IMSS");
        row.createCell(7).setCellValue("RFC");
        row.createCell(8).setCellValue("FECHA DE INGRESO");
        row.createCell(9).setCellValue("PUESTO");
        row.createCell(10).setCellValue("FECHA DE NACIMIENTO");
        row.createCell(11).setCellValue("LUGAR DE NACIMIENTO");
        row.createCell(12).setCellValue("SEXO");
        row.createCell(13).setCellValue("ESTADO CIVIL");
        row.createCell(14).setCellValue("CALLE Y NUMERO");
        row.createCell(15).setCellValue("COLONIA");
        row.createCell(16).setCellValue("C.P.");
        row.createCell(17).setCellValue("ESTADO");
        row.createCell(18).setCellValue("CIUDAD");
        row.createCell(19).setCellValue("TELEFONO");
        row.createCell(20).setCellValue("ESCOLARIDAD");
        row.createCell(21).setCellValue("CUENTA");
        row.createCell(22).setCellValue("CLABE");
        row.createCell(23).setCellValue("BANCO");
        row.createCell(24).setCellValue("SUELDO MENSUAL");
        row.createCell(25).setCellValue("EMPRESA");
        row.createCell(26).setCellValue("AREA");
        row.createCell(27).setCellValue("ZONA");
        row.createCell(28).setCellValue("SUCURSAL");
        row.createCell(29).setCellValue("CORREO PERSONAL");
        row.createCell(30).setCellValue("FECHA DE BAJA");
        row.createCell(31).setCellValue("FECHA DE CAMBIO O PROMOCION");


        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;
        String direccion = "";

        for (EmployeesHistory eHistorys : employeesHistorys) {

            row = hoja.createRow(aux);

            if(eHistorys.getIdRole() != null){
                CRoles role = cRolesDao.findById(eHistorys.getIdRole());
                if (role != null){
                    row.createCell(9).setCellValue(role.getRoleName());
                }
            }

            if(eHistorys.getIdEmployee() != null){
                Employees employee = employeesDao.findById(eHistorys.getIdEmployee());
                EmployeesAccounts employeeAccount = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());

                if (employeeAccount != null) {
                    Accounts accounts = employeeAccount.getAccount();
                    if (accounts != null) {
                        row.createCell(21).setCellValue(accounts.getAccountNumber());
                        row.createCell(22).setCellValue(accounts.getAccountClabe());
                        CBanks bank = accounts.getBank();
                        if (bank != null) {
                            row.createCell(23).setCellValue(bank.getAcronyms());
                            row.createCell(2).setCellValue(bank.getClave());
                        }
                    }
                }

                if (employee.getJoinDate() != null) {
                    Date joinDate = Date.from(employee.getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                    row.createCell(8);
                    row.getCell(8).setCellValue(joinDate);
                    row.getCell(8).setCellStyle(cellDateStyle);
                }

                if (employee.getBirthday() != null) {
                    Date birthDate = Date.from(employee.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    row.createCell(10);
                    row.getCell(10).setCellValue(birthDate);
                    row.getCell(10).setCellStyle(cellDateStyle);
                }

                if (employee.getIdGender() != null) {
                    CGenders gender = gendersDao.findById(employee.getIdGender());
                    if (gender != null){
                        row.createCell(12).setCellValue(gender.getGenderName());
                    }
                }

                if (employee.getIdStatusMarital() != null) {
                    CStatusMarital statusMarital = statusMaritalDao.findById(employee.getIdStatusMarital());
                    if (statusMarital != null){
                        row.createCell(13).setCellValue(statusMarital.getMaritalName());
                    }
                }

                if (employee.getStreet() != null){
                    direccion = direccion + employee.getStreet().replace("_", " ");
                }

                if (employee.getExteriorNumber() != null){
                    direccion = direccion + " No. Ext. "+ employee.getExteriorNumber().replace("_", " ");
                }

                if (employee.getInteriorNumber() != null){
                    direccion = direccion + " No. Int. " + employee.getInteriorNumber().replace("_", " ");
                }

                row.createCell(14).setCellValue(direccion);
                direccion = "";

                if (employee.getColonia() != null){
                    row.createCell(15).setCellValue(employee.getColonia());
                }

                if (employee.getPostcode() != null){
                    row.createCell(16).setCellValue(employee.getPostcode());
                }

                if (employee.getState() != null){
                    row.createCell(17).setCellValue(employee.getState());
                }

                if (employee.getCity() != null){
                    row.createCell(18).setCellValue(employee.getCity());
                }

                if (employee.getCellPhone() != null){
                    row.createCell(19).setCellValue(employee.getCellPhone());
                }

                if (employee.getIdEducation() != null){
                    CEducation education = cEducationDao.findById(employee.getIdEducation());
                    if (education != null){
                        row.createCell(20).setCellValue(education.getEducationName());
                    }
                }

                // Create a cell and put a value in it.
                row.createCell(0).setCellValue(employee.getIdEmployee());
                row.createCell(7).setCellValue(employee.getRfc());
                row.createCell(1).setCellValue(employee.getCurp());
                row.createCell(3).setCellValue(employee.getParentalLast().replace("_", " "));
                row.createCell(4).setCellValue(employee.getMotherLast().replace("_", " "));
                row.createCell(5).setCellValue(employee.getFirstName().replace("_", " ")+" "+employee.getMiddleName().replace("_", " "));
                row.createCell(6).setCellValue(employee.getImss());
                row.createCell(11).setCellValue(employee.getBirthplace());

                if (employee.getSalary() != null){
                    row.createCell(24).setCellValue(employee.getSalary().doubleValue());
                }

                if (employee.getMail() != null){
                    row.createCell(29).setCellValue(employee.getMail());
                }
            }

            if (eHistorys.getIdDwEnterprise() != null){
                DwEnterprises dwEnterprise = dwEnterprisesDao.findById(eHistorys.getIdDwEnterprise());
                if (dwEnterprise != null){
                    if (dwEnterprise.getDistributor() != null){
                        row.createCell(25).setCellValue(dwEnterprise.getDistributor().getAcronyms());
                    }
                    if (dwEnterprise.getArea() != null){
                        row.createCell(26).setCellValue(dwEnterprise.getArea().getAreaName());
                    }
                    if (dwEnterprise.getZona() != null){
                        row.createCell(27).setCellValue(dwEnterprise.getZona().getName());
                    }
                    if (dwEnterprise.getBranch() != null){
                        row.createCell(28).setCellValue(dwEnterprise.getBranch().getBranchShort());
                    }
                }
            }

            if(eHistorys.getIdActionType() != null){
                if (eHistorys.getIdActionType() == 2){
                    Date lowDate = Date.from(eHistorys.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());
                    row.createCell(30);
                    row.getCell(30).setCellValue(lowDate);
                    row.getCell(30).setCellStyle(cellDateStyle);
                }else {
                    Date modificationDate = Date.from(eHistorys.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());
                    row.createCell(31);
                    row.getCell(31).setCellValue(modificationDate);
                    row.getCell(31).setCellStyle(cellDateStyle);
                }
            }

            aux++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);

        wb.write(outputStream);
    }

    @Override
    public List<DwEmployees> findDwEmployeeByDwEnterpirseAndRoleAdvisers(Integer idDwEnterprise, List<MultilevelEmployee> multilevelEmployeeList) {
        return dwEmployeesDao.findDwEmployeeByDwEnterpirseAndRoleAdvisers(idDwEnterprise, multilevelEmployeeList);
    }
}

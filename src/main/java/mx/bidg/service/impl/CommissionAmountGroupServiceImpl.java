package mx.bidg.service.impl;

import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.service.CommissionAmountGroupBackupService;
import mx.bidg.service.CommissionAmountGroupService;
import mx.bidg.service.DwBranchsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by josueolvera on 2/09/16.
 */
@Service
@Transactional
public class CommissionAmountGroupServiceImpl implements CommissionAmountGroupService {

    @Autowired
    CommissionAmountGroupDao commissionAmountGroupDao;

    @Autowired
    CBranchsDao cBranchsDao;

    @Autowired
    CZonaDao cZonaDao;

    @Autowired
    CRegionsDao cRegionsDao;

    @Autowired
    CDistributorsDao cDistributorsDao;

    @Autowired
    BonusCommisionableEmployeeDao bonusCommisionableEmployeeDao;

    @Autowired
    DwEmployeesDao dwEmployeesDao;

    @Autowired
    EmployeesAccountsDao employeesAccountsDao;

    @Autowired
    CommissionAmountGroupBackupDao commissionAmountGroupBackupDao;

    @Autowired
    CommissionAmountGroupBackupService commissionAmountGroupBackupService;

    @Autowired
    EmployeesHistoryDao employeesHistoryDao;

    @Autowired
    EmployeesDao employeesDao;

    @Autowired
    CRolesDao cRolesDao;

    @Autowired
    PerceptionsDeductionsDao perceptionsDeductionsDao;

    @Autowired
    DwBranchsDao dwBranchsDao;

    @Autowired
    CsbPayCommissionDao csbPayCommissionDao;

    @Override
    public CommissionAmountGroup save(CommissionAmountGroup commissionAmountGroup) {
        return commissionAmountGroupDao.save(commissionAmountGroup);
    }

    @Override
    public CommissionAmountGroup update(CommissionAmountGroup commissionAmountGroup) {
        return commissionAmountGroupDao.update(commissionAmountGroup);
    }

    @Override
    public CommissionAmountGroup findById(Integer idCommissionAmount) {
        return commissionAmountGroupDao.findById(idCommissionAmount);
    }

    @Override
    public boolean delete(CommissionAmountGroup commissionAmountGroup) {
        commissionAmountGroupDao.delete(commissionAmountGroup);
        return true;
    }

    @Override
    public List<CommissionAmountGroup> findAll() {
        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyGroup(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {

        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            BigDecimal numRequest = new BigDecimal(projection[1].toString());
            String claveSap = (String) projection[0];
            BigDecimal amount = (BigDecimal) projection[2];
            Integer idEmployee = (Integer) projection[3];
            Integer idRole = (Integer) projection[4];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setClaveSap(claveSap);
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroup.setIdEmployee(idEmployee);
            commissionAmountGroup.setIdRole(idRole);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyAuxiliarGroup(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idBranch = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            BigDecimal numRequest = new BigDecimal(projection[2].toString());

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdBranch(idBranch);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyBranch(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idBranch = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            BigDecimal numRequest = new BigDecimal(projection[2].toString());
            Integer idRegion = (Integer) projection[3];
            Integer idZonas = (Integer) projection[4];
            Integer idDistributor = (Integer) projection[5];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdBranch(idBranch);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroup.setIdRegion(idRegion);
            commissionAmountGroup.setIdDistributor(idDistributor);
            commissionAmountGroup.setIdZona(idZonas);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyZona(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idZonas = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            BigDecimal numRequest = new BigDecimal(projection[2].toString());
            Integer idDistributor = (Integer) projection[3];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdZona(idZonas);
            commissionAmountGroup.setIdDistributor(idDistributor);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyRegion(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idRegion = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            BigDecimal numRequest = new BigDecimal(projection[2].toString());
            Integer idDistributor = (Integer) projection[3];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdRegion(idRegion);
            commissionAmountGroup.setIdDistributor(idDistributor);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyDistributor(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idDistributor = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            BigDecimal numRequest = new BigDecimal(projection[2].toString());

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdDistributor(idDistributor);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List findByOnlyClaveSap() {
        return commissionAmountGroupDao.findOnlyByClaveSap();
    }

    @Override
    public void comissionByReport(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException {

        List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupDao.findAllByClaveSap();

        List<List<CommissionAmountGroup>> commissionAmountGroupStreamList = new ArrayList<>();

        List<String> claveSapList = commissionAmountGroupDao.findOnlyByClaveSap();


        for (String claveSap : claveSapList) {
            List<CommissionAmountGroup> commissionAmountGroupStream =
                    commissionAmountGroupList.stream()
                            .filter(commissionAmountGroup -> commissionAmountGroup.getClaveSap().equals(claveSap))
                            .collect(Collectors.toList());

            commissionAmountGroupStreamList.add(commissionAmountGroupStream);
        }

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

        Sheet hoja = wb.createSheet("Promotor");

        //Se crea la fila que contiene la cabecera
        Row row = hoja.createRow(0);

        row.createCell(0).setCellValue("EMPRESA");
        row.createCell(1).setCellValue("REGION");
        row.createCell(2).setCellValue("ZONA");
        row.createCell(3).setCellValue("NOMBRE");
        row.createCell(4).setCellValue("CLAVE SAP");
        row.createCell(5).setCellValue("PUESTO");
        row.createCell(6).setCellValue("BANCO");
        row.createCell(7).setCellValue("NÚMERO DE  CUENTA");
        row.createCell(8).setCellValue("CLABE");
        row.createCell(9).setCellValue("SUCURSAL");
        row.createCell(10).setCellValue("RFC");
        row.createCell(11).setCellValue("CURP");
        row.createCell(12).setCellValue("FECHA INGRESO");
        row.createCell(13).setCellValue("No. SOL. GOBIERNO");
        row.createCell(14).setCellValue("MONTO GOBIERNO");
        row.createCell(15).setCellValue("COMISION GOBIERNO");
        row.createCell(16).setCellValue("No. SOL. MAGISTERIO");
        row.createCell(17).setCellValue("MONTO MAGISTERIO");
        row.createCell(18).setCellValue("COMISION MAGISTERIO");
        row.createCell(19).setCellValue("No. SOL. PEMEX");
        row.createCell(20).setCellValue("MONTO PEMEX");
        row.createCell(21).setCellValue("COMISION PEMEX");
        row.createCell(22).setCellValue("No. SOL. SALUD");
        row.createCell(23).setCellValue("MONTO SALUD");
        row.createCell(24).setCellValue("COMISION SALUD");
        row.createCell(25).setCellValue("No. SOL. SALUD-CI");
        row.createCell(26).setCellValue("MONTO SALUD-CI");
        row.createCell(27).setCellValue("COMISION SALUD-CI");
        row.createCell(28).setCellValue("No. SOL. IEEPO");
        row.createCell(29).setCellValue("MONTO IEEPO");
        row.createCell(30).setCellValue("COMISION IEEPO");
        row.createCell(31).setCellValue("No. SOL. TOTAL");
        row.createCell(32).setCellValue("MONTO TOTAL");
        row.createCell(33).setCellValue("BONO CUMPLIMIENTO");
        row.createCell(34).setCellValue("BONO POR NUEVO INGRESO");
        row.createCell(35).setCellValue("APOYO A PASAJE");
        row.createCell(36).setCellValue("COMISION TOTAL REAL");
        row.createCell(37).setCellValue("COMISION PAGADA CSB");
        row.createCell(38).setCellValue("COMISION TOTAL FINAL");
        row.createCell(39).setCellValue("PAGO");
        row.createCell(40).setCellValue("ESTATUS");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (List listGeneric : commissionAmountGroupStreamList){
            row = hoja.createRow(aux);
            BigDecimal totalComission = new BigDecimal(0);
            BigDecimal commssionT = new BigDecimal(0);

            for (Object object: listGeneric){
                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;

                row.createCell(0).setCellValue(commissionAmountGroup.getClaveSap());

                if (commissionAmountGroup.getIdEmployee() != null){
                    EmployeesHistory employeesHistory = employeesHistoryDao.findByIdEmployeeAndLastRegister(commissionAmountGroup.getIdEmployee());
                    if (employeesHistory != null){
                        Employees employees = employeesDao.findById(employeesHistory.getIdEmployee());
                        if(employees != null){
                            if (employees.getStatus() == 1){
                                row.createCell(40).setCellValue("ALTA");
                            }else if (employees.getStatus() == 0){
                                row.createCell(40).setCellValue("BAJA");
                            }

                            CDistributors cDistributor = cDistributorsDao.findById(employeesHistory.getIdDistributor());

                            if (cDistributor != null){
                                row.createCell(0).setCellValue(cDistributor.getDistributorName());
                            }
                            CRegions cRegions = cRegionsDao.findById(employeesHistory.getIdRegion());

                            if (cRegions != null){
                                row.createCell(1).setCellValue(cRegions.getRegionName());
                            }
                            CZonas cZonas = cZonaDao.findById(employeesHistory.getIdZona());

                            if (cZonas != null){
                                row.createCell(2).setCellValue(cZonas.getName());
                            }

                            row.createCell(3).setCellValue(employees.getFullName());
                            row.createCell(4).setCellValue(employees.getClaveSap());

                            CRoles cRoles = cRolesDao.findById(employeesHistory.getIdRole());

                            if (cRoles != null){
                                row.createCell(5).setCellValue(cRoles.getRoleName());
                            }

                            EmployeesAccounts employeesAccounts = employeesAccountsDao.findByIdEmployee(employees.getIdEmployee());

                            if (employeesAccounts != null){
                                row.createCell(6).setCellValue(employeesAccounts.getAccount().getBank().getAcronyms());
                                row.createCell(7).setCellValue(employeesAccounts.getAccount().getAccountNumber());
                                row.createCell(8).setCellValue(employeesAccounts.getAccount().getAccountClabe());
                            }

                            CBranchs cBranchs = cBranchsDao.findById(employeesHistory.getIdBranch());

                            if(cBranchs != null){
                                row.createCell(9).setCellValue(cBranchs.getBranchShort());
                            }

                            row.createCell(10).setCellValue(employees.getRfc());
                            row.createCell(11).setCellValue(employees.getCurp());

                            if (employees.getJoinDate() != null){
                                Date joinDate = Date.from(employees.getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                                row.createCell(12);
                                row.getCell(12).setCellValue(joinDate);
                                row.getCell(12).setCellStyle(cellDateStyle);
                            }
                        }
                    }
                }

                if(commissionAmountGroup.getIdAg() == 13){
                    row.createCell(13).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(14).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(15).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                    commssionT = commssionT.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 14){
                    row.createCell(16).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(17).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(18).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                    commssionT = commssionT.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 15){
                    row.createCell(19).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(20).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(21).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                    commssionT = commssionT.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 16){
                    row.createCell(22).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(23).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(24).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                    commssionT = commssionT.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 17){
                    row.createCell(25).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(26).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(27).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                    commssionT = commssionT.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 29){
                    row.createCell(28).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(29).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(30).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                    commssionT = commssionT.add(commissionAmountGroup.getCommission());
                }

                BigDecimal sumPerceptions = new BigDecimal(0);
                if(commissionAmountGroup.getIdAg() == 19){
                    row.createCell(31).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(32).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    commissionAmountGroup.setCommission(commssionT);
                    commissionAmountGroupDao.update(commissionAmountGroup);

                    List<PerceptionsDeductions> perceptionsDeductionsList = perceptionsDeductionsDao.findByIdEmployeeAndApplicationDate(commissionAmountGroup.getIdEmployee(),fromDate,toDate);
                    if (!perceptionsDeductionsList.isEmpty()){
                        for (PerceptionsDeductions perceptionsDeductions : perceptionsDeductionsList){
                            if (perceptionsDeductions.getIdCPd() == 9){
                                row.createCell(33).setCellValue(perceptionsDeductions.getAmount().doubleValue());
                                totalComission = totalComission.add(perceptionsDeductions.getAmount());
                                sumPerceptions = sumPerceptions.add(perceptionsDeductions.getAmount());
                            }else if (perceptionsDeductions.getIdCPd() == 10){
                                row.createCell(35).setCellValue(perceptionsDeductions.getAmount().doubleValue());
                                totalComission = totalComission.add(perceptionsDeductions.getAmount());
                                sumPerceptions = sumPerceptions.add(perceptionsDeductions.getAmount());
                            }else if (perceptionsDeductions.getIdCPd() == 11){
                                row.createCell(34).setCellValue(perceptionsDeductions.getAmount().doubleValue());
                                totalComission = totalComission.add(perceptionsDeductions.getAmount());
                                sumPerceptions = sumPerceptions.add(perceptionsDeductions.getAmount());
                            }
                        }
                    }

//                    List<BonusCommisionableEmployee> bonusCommisionableEmployees = bonusCommisionableEmployeeDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
//                    if (!bonusCommisionableEmployees.isEmpty()){
//                        for (BonusCommisionableEmployee bonusCommisionableEmployee : bonusCommisionableEmployees){
//                            if (bonusCommisionableEmployee.getIdCommissionBonus() == 1){
//                                row.createCell(33).setCellValue(bonusCommisionableEmployee.getBonusAmount().doubleValue());
//                                totalComission = totalComission.add(bonusCommisionableEmployee.getBonusAmount());
//                            }else if (bonusCommisionableEmployee.getIdCommissionBonus() == 2){
//                                row.createCell(34).setCellValue(bonusCommisionableEmployee.getBonusAmount().doubleValue());
//                                totalComission = totalComission.add(bonusCommisionableEmployee.getBonusAmount());
//                            }else if (bonusCommisionableEmployee.getIdCommissionBonus() == 3){
//                                row.createCell(35).setCellValue(bonusCommisionableEmployee.getBonusAmount().doubleValue());
//                                totalComission = totalComission.add(bonusCommisionableEmployee.getBonusAmount());
//                            }
//                        }
//                    }
                }
                CommissionAmountGroup amountGroup = commissionAmountGroupDao.getOnlyDataOfGroupNineTeen(commissionAmountGroup.getIdEmployee());

                LocalDate initialDate = fromDate.toLocalDate();
                LocalDate finalDate = toDate.toLocalDate();

                BigDecimal amountTotal = new BigDecimal(0);

                BigDecimal csbCommission = csbPayCommissionDao.sumAmountByDateAndClaveSap(commissionAmountGroup.getClaveSap(),initialDate, finalDate);
                if (amountGroup != null){

                    if (csbCommission != null){
                        row.createCell(37).setCellValue(csbCommission.doubleValue());
                        amountGroup.setCsbCommission(csbCommission);

                        BigDecimal realCommission = commssionT.subtract(csbCommission).setScale(2, BigDecimal.ROUND_HALF_UP);

                        amountTotal = realCommission.add(sumPerceptions).setScale(2, BigDecimal.ROUND_HALF_UP);

                        amountGroup.setTotalCommission(realCommission);
                        row.createCell(38).setCellValue(realCommission.doubleValue());
                    }else {
                        amountTotal = commssionT.add(sumPerceptions).setScale(2, BigDecimal.ROUND_HALF_UP);
                        amountGroup.setTotalCommission(commssionT);
                    }
                    amountGroup.setCommission(commssionT);
                    commissionAmountGroupDao.update(amountGroup);
                }

                row.createCell(36).setCellValue(commssionT.doubleValue());
                row.createCell(39).setCellValue(amountTotal.doubleValue());
            }
            aux++;
        }

//        Sheet hoja2 = wb.createSheet("Auxiliar");
//
//        //Se crea la fila que contiene la cabecera
//        Row row2 = hoja2.createRow(0);
//
//        row2.createCell(0).setCellValue("SUCURSAL");
//        row2.createCell(1).setCellValue("MONTO");
//        row2.createCell(2).setCellValue("COMISION");
//
//        //Implementacion del estilo
//        for (Cell celda : row2) {
//            celda.setCellStyle(style);
//        }
//
//        List<CommissionAmountGroup> auxiliarList = commissionAmountGroupDao.findByGroupAuxiliarAndBranch();
//
//        int aux1 = 1;
//
//        for (CommissionAmountGroup auxiliar: auxiliarList){
//            row2 = hoja2.createRow(aux1);
//
//            CBranchs branchs = cBranchsDao.findById(auxiliar.getIdBranch());
//
//            if (branchs != null){
//                row2.createCell(0).setCellValue(branchs.getBranchShort());
//            }
//
//            row2.createCell(1).setCellValue(auxiliar.getAmount().doubleValue());
//            row2.createCell(2).setCellValue(auxiliar.getCommission().doubleValue());
//
//            aux1++;
//        }
//
//        List<CommissionAmountGroup> commissionAmountBranchGroupList = commissionAmountGroupDao.findAllByBranchs();
//
//        List<List<CommissionAmountGroup>> commissionAmountBranchGroupStreamList = new ArrayList<>();
//
//        List<Integer> idBranchList = commissionAmountGroupDao.findOnlyByIdBranch();
//
//
//        for (Integer idBranch : idBranchList) {
//            List<CommissionAmountGroup> commissionAmountGroupStream =
//                    commissionAmountBranchGroupList.stream()
//                            .filter(commissionAmountGroup -> commissionAmountGroup.getIdBranch().equals(idBranch))
//                            .collect(Collectors.toList());
//
//            commissionAmountBranchGroupStreamList.add(commissionAmountGroupStream);
//        }
//
//        Sheet hoja6 = wb.createSheet("Gerente de sucursal");
//
//        //Se crea la fila que contiene la cabecera
//        Row row6 = hoja6.createRow(0);
//
//        row6.createCell(0).setCellValue("ID SUCURSAL");
//        row6.createCell(1).setCellValue("NOMBRE SUCURSAL");
//        row6.createCell(2).setCellValue("META");
//        row6.createCell(3).setCellValue("MONTO");
//        row6.createCell(4).setCellValue("ALCANCE");
//        row6.createCell(5).setCellValue("TABULADOR");
//        row6.createCell(6).setCellValue("COMISION GERENTE");
//        row6.createCell(7).setCellValue("MONTO PEMEX");
//        row6.createCell(8).setCellValue("COMISION PEMEX");
//        row6.createCell(9).setCellValue("MONTO MENOS PEMEX");
//        row6.createCell(10).setCellValue("TAB OTORGADO");
//
//        //Implementacion del estilo
//        for (Cell celda : row6) {
//            celda.setCellStyle(style);
//        }
//
//        int aux6 = 1;
//
//        for (List listGeneric : commissionAmountBranchGroupStreamList){
//            row6 = hoja6.createRow(aux6);
//
//            for (Object object: listGeneric){
//                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;
//
//                row6.createCell(0).setCellValue(commissionAmountGroup.getIdBranch());
//
//                if(commissionAmountGroup.getIdBranch() != null){
//                    CBranchs branchs = cBranchsDao.findById(commissionAmountGroup.getIdBranch());
//
//                    if (branchs != null){
//                        row6.createCell(1).setCellValue(branchs.getBranchName());
//                    }
//                }
//
//                if(commissionAmountGroup.getIdAg() == 20){
//                    row6.createCell(2).setCellValue(commissionAmountGroup.getGoal().doubleValue());
//                    row6.createCell(3).setCellValue(commissionAmountGroup.getAmount().doubleValue());
//                    BigDecimal divisor = new BigDecimal(100);
//                    BigDecimal scope = commissionAmountGroup.getScope().divide(divisor);
//                    row6.createCell(4).setCellValue(scope.doubleValue());
//                    row6.createCell(5).setCellValue(commissionAmountGroup.getTabulator().doubleValue());
//                    row6.createCell(6).setCellValue(commissionAmountGroup.getCommission().doubleValue());
//                }
//
//                if(commissionAmountGroup.getIdAg() == 21){
//                    row6.createCell(7).setCellValue(commissionAmountGroup.getAmount().doubleValue());
//                    row6.createCell(8).setCellValue(commissionAmountGroup.getCommission().doubleValue());
//                    if (row6.getCell(3) != null){
//                        BigDecimal TotalAmount = new BigDecimal(row6.getCell(3).getNumericCellValue());
//                        if (commissionAmountGroup.getAmount().equals(TotalAmount)){
//                            row6.getCell(6).setCellValue(commissionAmountGroup.getCommission().doubleValue());
//                        }else{
//                            BigDecimal amountNotPemex = TotalAmount.subtract(commissionAmountGroup.getAmount());
//                            row6.createCell(9).setCellValue(amountNotPemex.doubleValue());
//                            BigDecimal tabulador = new BigDecimal(row6.getCell(5).getNumericCellValue());
//                            BigDecimal divisor = new BigDecimal(100);
//                            BigDecimal tabuladorNotPemex = tabulador.divide(divisor);
//                            BigDecimal commisionNotPemex = amountNotPemex.multiply(tabuladorNotPemex);
//                            row6.createCell(10).setCellValue(commisionNotPemex.doubleValue());
//                            BigDecimal commissionTotal = commisionNotPemex.add(commissionAmountGroup.getCommission());
//                            row6.getCell(6).setCellValue(commissionTotal.doubleValue());
//                        }
//                    }
//                }
//            }
//            aux6++;
//        }
//
//        Sheet hoja3 = wb.createSheet("Gerente Zonal");
//
//        //Se crea la fila que contiene la cabecera
//        Row row3 = hoja3.createRow(0);
//
////        row3.createCell(0).setCellValue("DISTRIBUIDOR");
//        row3.createCell(0).setCellValue("ZONA");
//        row3.createCell(1).setCellValue("MONTO");
//        row3.createCell(2).setCellValue("TABULADOR");
//        row3.createCell(3).setCellValue("COMISION");
//
//        //Implementacion del estilo
//        for (Cell celda : row3) {
//            celda.setCellStyle(style);
//        }
//
//        List<CommissionAmountGroup> zonalList = commissionAmountGroupDao.findByGroupZonalAndZona();
//
//        int aux2 = 1;
//
//        for (CommissionAmountGroup zonal: zonalList){
//            row3 = hoja3.createRow(aux2);
//
////            CDistributors distributors = cDistributorsDao.findById(zonal.getIdDistributor());
////
////            if (distributors != null){
////                row3.createCell(0).setCellValue(distributors.getDistributorName());
////            }
//
//            CZonas zonas = cZonaDao.findById(zonal.getIdZona());
//
//            if (zonas != null){
//                row3.createCell(0).setCellValue(zonas.getName());
//            }
//
//            row3.createCell(1).setCellValue(zonal.getAmount().doubleValue());
//            row3.createCell(2).setCellValue(zonal.getTabulator().doubleValue());
//            row3.createCell(3).setCellValue(zonal.getCommission().doubleValue());
//
//            aux2++;
//        }
//
//        Sheet hoja4 = wb.createSheet("Gerente Regional");
//
//        //Se crea la fila que contiene la cabecera
//        Row row4 = hoja4.createRow(0);
//
////        row4.createCell(0).setCellValue("DISTRIBUCION");
//        row4.createCell(0).setCellValue("REGION");
//        row4.createCell(1).setCellValue("MONTO");
//        row4.createCell(2).setCellValue("TABULADOR");
//        row4.createCell(3).setCellValue("COMISION");
//
//        //Implementacion del estilo
//        for (Cell celda : row4) {
//            celda.setCellStyle(style);
//        }
//
//        List<CommissionAmountGroup> regionList = commissionAmountGroupDao.findByGroupRegionslAndRegion();
//
//        int aux3 = 1;
//
//        for (CommissionAmountGroup region : regionList){
//            row4 = hoja4.createRow(aux3);
//
////            CDistributors distributor = cDistributorsDao.findById(region.getIdDistributor());
////
////            if (distributor != null){
////                row4.createCell(0).setCellValue(distributor.getDistributorName());
////            }
//
//            CRegions regions = cRegionsDao.findById(region.getIdRegion());
//
//            if (regions != null){
//                row4.createCell(0).setCellValue(regions.getRegionName());
//            }
//
//            row4.createCell(1).setCellValue(region.getAmount().doubleValue());
//            row4.createCell(2).setCellValue(region.getTabulator().doubleValue());
//            row4.createCell(3).setCellValue(region.getCommission().doubleValue());
//
//            aux3++;
//        }
//
//        Sheet hoja5 = wb.createSheet("Gerente Comercial");
//
//        //Se crea la fila que contiene la cabecera
//        Row row5 = hoja5.createRow(0);
//
//        row5.createCell(0).setCellValue("EMPRESA");
//        row5.createCell(1).setCellValue("MONTO");
//        row5.createCell(2).setCellValue("COMISION");
//
//        //Implementacion del estilo
//        for (Cell celda : row5) {
//            celda.setCellStyle(style);
//        }
//
//        List<CommissionAmountGroup> distributorsList = commissionAmountGroupDao.findByGroupComercialAndDistributor();
//
//        int aux4 = 1;
//
//        for (CommissionAmountGroup comercial: distributorsList){
//            row5 = hoja5.createRow(aux4);
//
//            CDistributors distributor =  cDistributorsDao.findById(comercial.getIdDistributor());
//
//            if (distributor != null){
//                row5.createCell(0).setCellValue(distributor.getDistributorName());
//            }
//
//            row5.createCell(1).setCellValue(comercial.getAmount().doubleValue());
//            row5.createCell(2).setCellValue(comercial.getCommission().doubleValue());
//
//            aux4++;
//        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);
        hoja.autoSizeColumn(3);
        hoja.autoSizeColumn(4);
        hoja.autoSizeColumn(5);
        hoja.autoSizeColumn(6);
        hoja.autoSizeColumn(7);
        hoja.autoSizeColumn(8);
        hoja.autoSizeColumn(9);
        hoja.autoSizeColumn(10);
        hoja.autoSizeColumn(11);
        hoja.autoSizeColumn(12);
        hoja.autoSizeColumn(13);
        hoja.autoSizeColumn(14);
        hoja.autoSizeColumn(15);
        hoja.autoSizeColumn(16);
        hoja.autoSizeColumn(17);
        hoja.autoSizeColumn(18);
//        hoja2.autoSizeColumn(0);
//        hoja2.autoSizeColumn(1);
//        hoja2.autoSizeColumn(2);
//        hoja3.autoSizeColumn(0);
//        hoja3.autoSizeColumn(1);
//        hoja3.autoSizeColumn(2);
//        hoja4.autoSizeColumn(0);
//        hoja4.autoSizeColumn(1);
//        hoja4.autoSizeColumn(2);
//        hoja5.autoSizeColumn(0);
//        hoja5.autoSizeColumn(1);
//        hoja5.autoSizeColumn(2);
//        hoja6.autoSizeColumn(0);
//        hoja6.autoSizeColumn(1);
//        hoja6.autoSizeColumn(2);
//        hoja6.autoSizeColumn(3);
//        hoja6.autoSizeColumn(4);
//        hoja6.autoSizeColumn(5);
//        hoja6.autoSizeColumn(6);
//        hoja6.autoSizeColumn(7);
//        hoja6.autoSizeColumn(8);
//        hoja6.autoSizeColumn(9);
//        hoja6.autoSizeColumn(10);

        wb.write(stream);
    }

    @Override
    public List<CommissionAmountGroup> obtainBranchManager() {
        return commissionAmountGroupDao.findByGroupBranchAndBranch();
    }

    @Override
    public List<CommissionAmountGroup> obtainAuxiliar() {
        return commissionAmountGroupDao.findByGroupAuxiliarAndBranch();
    }

    @Override
    public void reportMonthlyCommissions(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException {
        List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupDao.findAllByAdvisersAndCleaning();

        List<List<CommissionAmountGroup>> commissionAmountGroupStreamList = new ArrayList<>();

        List<String> claveSapList = commissionAmountGroupDao.findOnlyByClaveSapAndAdvisersAndCleaning();

        for (String claveSap : claveSapList) {
            List<CommissionAmountGroup> commissionAmountGroupStream =
                    commissionAmountGroupList.stream()
                            .filter(commissionAmountGroup -> commissionAmountGroup.getClaveSap().equals(claveSap))
                            .collect(Collectors.toList());

            commissionAmountGroupStreamList.add(commissionAmountGroupStream);
        }

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

        Sheet hoja = wb.createSheet("Promotor");

        //Se crea la fila que contiene la cabecera
        Row row = hoja.createRow(0);

        row.createCell(0).setCellValue("EMPRESA");
        row.createCell(1).setCellValue("REGION");
        row.createCell(2).setCellValue("ZONA");
        row.createCell(3).setCellValue("NOMBRE");
        row.createCell(4).setCellValue("CLAVE SAP");
        row.createCell(5).setCellValue("PUESTO");
        row.createCell(6).setCellValue("BANCO");
        row.createCell(7).setCellValue("NÚMERO DE  CUENTA");
        row.createCell(8).setCellValue("CLABE");
        row.createCell(9).setCellValue("SUCURSAL");
        row.createCell(10).setCellValue("RFC");
        row.createCell(11).setCellValue("CURP");
        row.createCell(12).setCellValue("FECHA INGRESO");
        row.createCell(13).setCellValue("No. SOL. GOBIERNO");
        row.createCell(14).setCellValue("MONTO GOBIERNO");
        row.createCell(15).setCellValue("COMISION GOBIERNO");
        row.createCell(16).setCellValue("No. SOL. SALUD");
        row.createCell(17).setCellValue("MONTO SALUD");
        row.createCell(18).setCellValue("COMISION SALUD");
        row.createCell(19).setCellValue("No. SOL. SALUD-CI");
        row.createCell(20).setCellValue("MONTO SALUD-CI");
        row.createCell(21).setCellValue("COMISION SALUD-CI");
        row.createCell(22).setCellValue("COMISION ACUMULADA GOBIERNO");
        row.createCell(23).setCellValue("COMISION ACUMULADA SALUD");
        row.createCell(24).setCellValue("COMISION ACUMULADA SALUD-CI");
        row.createCell(25).setCellValue("AJUSTE COMISIÓN GOBIERNO");
        row.createCell(26).setCellValue("AJUSTE COMISIÓN SALUD");
        row.createCell(27).setCellValue("AJUSTE COMISIÓN SALUD-CI");
        row.createCell(28).setCellValue("BONO SALUD");
        row.createCell(29).setCellValue("BONO SALUD-CI");
        row.createCell(30).setCellValue("MONTO A PAGAR");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (List listGeneric : commissionAmountGroupStreamList){
            row = hoja.createRow(aux);
            BigDecimal totalComission = new BigDecimal(0);

            for (Object object: listGeneric){
                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;

                if (commissionAmountGroup.getIdRole() != null){
                    if (commissionAmountGroup.getIdRole() == 64 || commissionAmountGroup.getIdRole() == 82 || commissionAmountGroup.getIdRole() == 80){
                        row.createCell(0).setCellValue(commissionAmountGroup.getClaveSap());

                        if (commissionAmountGroup.getIdEmployee() != null){
                            DwEmployees dwEmployees = dwEmployeesDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
                            if (dwEmployees != null){
                                row.createCell(0).setCellValue(dwEmployees.getDwEnterprise().getDistributor().getDistributorName());
                                row.createCell(1).setCellValue(dwEmployees.getDwEnterprise().getRegion().getRegionName());
                                row.createCell(2).setCellValue(dwEmployees.getDwEnterprise().getZona().getName());
                                row.createCell(3).setCellValue(dwEmployees.getEmployee().getFullName());
                                row.createCell(4).setCellValue(dwEmployees.getEmployee().getClaveSap());
                                row.createCell(5).setCellValue(dwEmployees.getRole().getRoleName());

                                EmployeesAccounts employeesAccounts = employeesAccountsDao.findByIdEmployee(dwEmployees.getIdEmployee());

                                if (employeesAccounts != null){
                                    row.createCell(6).setCellValue(employeesAccounts.getAccount().getBank().getAcronyms());
                                    row.createCell(7).setCellValue(employeesAccounts.getAccount().getAccountNumber());
                                    row.createCell(8).setCellValue(employeesAccounts.getAccount().getAccountClabe());
                                }
                                row.createCell(9).setCellValue(dwEmployees.getDwEnterprise().getBranch().getBranchShort());
                                row.createCell(10).setCellValue(dwEmployees.getEmployee().getRfc());
                                row.createCell(11).setCellValue(dwEmployees.getEmployee().getCurp());

                                if (dwEmployees.getEmployee().getJoinDate() != null){
                                    Date joinDate = Date.from(dwEmployees.getEmployee().getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                                    row.createCell(12);
                                    row.getCell(12).setCellValue(joinDate);
                                    row.getCell(12).setCellStyle(cellDateStyle);
                                }
                            }
                        }

                        if(commissionAmountGroup.getIdAg() == 13){
                            row.createCell(13).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                            row.createCell(14).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                            row.createCell(15).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                            List listaGobierno = commissionAmountGroupBackupDao.findTotalAmountGroupGobierno(fromDate, toDate);
                            for (Object data : listaGobierno){
                                Object[] projection = (Object[]) data;
                                String claveSap = (String) projection[0];
                                BigDecimal commissionableAmount = (BigDecimal) projection[1];
                                BigDecimal numRequest = new BigDecimal(projection[2].toString());
                                BigDecimal comission = new BigDecimal(projection[3].toString());
                                Integer idAg = (Integer) projection[4];

                                if (claveSap.equals(commissionAmountGroup.getClaveSap())){
                                    row.createCell(22).setCellValue(comission.doubleValue());
                                    BigDecimal finalCommission = commissionAmountGroup.getCommission().subtract(comission);
                                    if (finalCommission.signum() == -1){
                                        row.createCell(25).setCellValue(0);
                                        totalComission = totalComission.add(new BigDecimal(0));
                                        commissionAmountGroup.setAdjustment(new BigDecimal(0));
                                        commissionAmountGroupDao.update(commissionAmountGroup);
                                    }else {
                                        row.createCell(25).setCellValue(finalCommission.doubleValue());
                                        totalComission = totalComission.add(finalCommission);
                                        commissionAmountGroup.setAdjustment(finalCommission);
                                        commissionAmountGroupDao.update(commissionAmountGroup);
                                    }
                                }
                            }
                        }

                        if(commissionAmountGroup.getIdAg() == 16){
                            row.createCell(16).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                            row.createCell(17).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                            row.createCell(18).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                            List listaSalud = commissionAmountGroupBackupDao.findTotalAmountGroupSalud(fromDate, toDate);
                            for (Object data : listaSalud){
                                Object[] projection = (Object[]) data;
                                String claveSap = (String) projection[0];
                                BigDecimal commissionableAmount = (BigDecimal) projection[1];
                                BigDecimal numRequest = new BigDecimal(projection[2].toString());
                                BigDecimal comission = new BigDecimal(projection[3].toString());
                                Integer idAg = (Integer) projection[4];

                                if (claveSap.equals(commissionAmountGroup.getClaveSap())){
                                    row.createCell(23).setCellValue(comission.doubleValue());
                                    BigDecimal finalCommission = commissionAmountGroup.getCommission().subtract(comission);
                                    if (finalCommission.signum() == -1){
                                        row.createCell(26).setCellValue(0);
                                        totalComission = totalComission.add(new BigDecimal(0));
                                        commissionAmountGroup.setAdjustment(new BigDecimal(0));
                                        commissionAmountGroupDao.update(commissionAmountGroup);
                                    }else {
                                        row.createCell(26).setCellValue(finalCommission.doubleValue());
                                        totalComission = totalComission.add(finalCommission);
                                        commissionAmountGroup.setAdjustment(finalCommission);
                                        commissionAmountGroupDao.update(commissionAmountGroup);
                                    }
                                    if(commissionAmountGroup.getBonusCommissionableAmount() != null){
                                        row.createCell(28).setCellValue(commissionAmountGroup.getBonusCommissionableAmount().doubleValue());
                                        totalComission = totalComission.add(commissionAmountGroup.getBonusCommissionableAmount());
                                    }
/*                                    if (commissionAmountGroup.getAmount().doubleValue() >= 300000){
                                        row.createCell(24).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                                    }else {
                                        row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
                                    }
*/
                                }
                            }
                        }

                        if(commissionAmountGroup.getIdAg() == 17){
                            row.createCell(19).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                            row.createCell(20).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                            row.createCell(21).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                            List listaSaludCI = commissionAmountGroupBackupDao.findTotalAmountGroupSaludCI(fromDate, toDate);
                            for (Object data : listaSaludCI){
                                CommissionAmountGroup commissionAmountGroups;
                                Object[] projection = (Object[]) data;
                                String claveSap = (String) projection[0];
                                BigDecimal commissionableAmount = (BigDecimal) projection[1];
                                BigDecimal numRequest = new BigDecimal(projection[2].toString());
                                BigDecimal comission = new BigDecimal(projection[3].toString());
                                Integer idAg = (Integer) projection[4];

                                if (claveSap.equals(commissionAmountGroup.getClaveSap())){
                                    row.createCell(24).setCellValue(comission.doubleValue());
                                    BigDecimal finalCommission = commissionAmountGroup.getCommission().subtract(comission);
                                    if (finalCommission.signum() == -1){
                                        row.createCell(27).setCellValue(0);
                                        totalComission = totalComission.add(new BigDecimal(0));
                                        commissionAmountGroup.setAdjustment(new BigDecimal(0));
                                        commissionAmountGroupDao.update(commissionAmountGroup);
                                    }else {
                                        row.createCell(27).setCellValue(finalCommission.doubleValue());
                                        totalComission = totalComission.add(finalCommission);
                                        commissionAmountGroup.setAdjustment(finalCommission);
                                        commissionAmountGroupDao.update(commissionAmountGroup);
                                    }
                                    if(commissionAmountGroup.getBonusCommissionableAmount() != null){
                                        row.createCell(29).setCellValue(commissionAmountGroup.getBonusCommissionableAmount().doubleValue());
                                        totalComission = totalComission.add(commissionAmountGroup.getBonusCommissionableAmount());
                                    }
/*                                    if (commissionAmountGroup.getAmount().doubleValue() >= 300000){
                                        row.createCell(25).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                                    }else {
                                        row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
                                    }
*/
                                }
                            }
                        }
                        row.createCell(30).setCellValue(totalComission.doubleValue());
                    }
                } else {
                    row.createCell(0).setCellValue(commissionAmountGroup.getClaveSap());

                    if (commissionAmountGroup.getIdEmployee() != null){
                        DwEmployees dwEmployees = dwEmployeesDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
                        if (dwEmployees != null){
                            row.createCell(0).setCellValue(dwEmployees.getDwEnterprise().getDistributor().getDistributorName());
                            row.createCell(1).setCellValue(dwEmployees.getDwEnterprise().getRegion().getRegionName());
                            row.createCell(2).setCellValue(dwEmployees.getDwEnterprise().getZona().getName());
                            row.createCell(3).setCellValue(dwEmployees.getEmployee().getFullName());
                            row.createCell(4).setCellValue(dwEmployees.getEmployee().getClaveSap());
                            row.createCell(5).setCellValue(dwEmployees.getRole().getRoleName());

                            EmployeesAccounts employeesAccounts = employeesAccountsDao.findByIdEmployee(dwEmployees.getIdEmployee());

                            if (employeesAccounts != null){
                                row.createCell(6).setCellValue(employeesAccounts.getAccount().getBank().getAcronyms());
                                row.createCell(7).setCellValue(employeesAccounts.getAccount().getAccountNumber());
                                row.createCell(8).setCellValue(employeesAccounts.getAccount().getAccountClabe());
                            }
                            row.createCell(9).setCellValue(dwEmployees.getDwEnterprise().getBranch().getBranchShort());
                            row.createCell(10).setCellValue(dwEmployees.getEmployee().getRfc());
                            row.createCell(11).setCellValue(dwEmployees.getEmployee().getCurp());

                            if (dwEmployees.getEmployee().getJoinDate() != null){
                                Date joinDate = Date.from(dwEmployees.getEmployee().getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                                row.createCell(12);
                                row.getCell(12).setCellValue(joinDate);
                                row.getCell(12).setCellStyle(cellDateStyle);
                            }
                        }
                    }

                    if(commissionAmountGroup.getIdAg() == 13){
                        row.createCell(13).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                        row.createCell(14).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                        row.createCell(15).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                        List listaGobierno = commissionAmountGroupBackupDao.findTotalAmountGroupGobierno(fromDate, toDate);
                        for (Object data : listaGobierno){
                            Object[] projection = (Object[]) data;
                            String claveSap = (String) projection[0];
                            BigDecimal commissionableAmount = (BigDecimal) projection[1];
                            BigDecimal numRequest = new BigDecimal(projection[2].toString());
                            BigDecimal comission = new BigDecimal(projection[3].toString());
                            Integer idAg = (Integer) projection[4];

                            if (claveSap.equals(commissionAmountGroup.getClaveSap())){
                                row.createCell(22).setCellValue(comission.doubleValue());
                                BigDecimal finalCommission = commissionAmountGroup.getCommission().subtract(comission);
                                if (finalCommission.signum() == -1){
                                    row.createCell(25).setCellValue(0);
                                    totalComission = totalComission.add(new BigDecimal(0));
                                    commissionAmountGroup.setAdjustment(new BigDecimal(0));
                                    commissionAmountGroupDao.update(commissionAmountGroup);
                                }else {
                                    row.createCell(25).setCellValue(finalCommission.doubleValue());
                                    totalComission = totalComission.add(finalCommission);
                                    commissionAmountGroup.setAdjustment(finalCommission);
                                    commissionAmountGroupDao.update(commissionAmountGroup);
                                }
                            }
                        }
                    }

                    if(commissionAmountGroup.getIdAg() == 16){
                        row.createCell(16).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                        row.createCell(17).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                        row.createCell(18).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                        List listaSalud = commissionAmountGroupBackupDao.findTotalAmountGroupSalud(fromDate, toDate);
                        for (Object data : listaSalud){
                            Object[] projection = (Object[]) data;
                            String claveSap = (String) projection[0];
                            BigDecimal commissionableAmount = (BigDecimal) projection[1];
                            BigDecimal numRequest = new BigDecimal(projection[2].toString());
                            BigDecimal comission = new BigDecimal(projection[3].toString());
                            Integer idAg = (Integer) projection[4];

                            if (claveSap.equals(commissionAmountGroup.getClaveSap())){
                                row.createCell(23).setCellValue(comission.doubleValue());
                                BigDecimal finalCommission = commissionAmountGroup.getCommission().subtract(comission);
                                if (finalCommission.signum() == -1){
                                    row.createCell(26).setCellValue(0);
                                    totalComission = totalComission.add(new BigDecimal(0));
                                    commissionAmountGroup.setAdjustment(new BigDecimal(0));
                                    commissionAmountGroupDao.update(commissionAmountGroup);
                                }else {
                                    row.createCell(26).setCellValue(finalCommission.doubleValue());
                                    totalComission = totalComission.add(finalCommission);
                                    commissionAmountGroup.setAdjustment(finalCommission);
                                    commissionAmountGroupDao.update(commissionAmountGroup);
                                }
                                if(commissionAmountGroup.getBonusCommissionableAmount() != null){
                                    row.createCell(28).setCellValue(commissionAmountGroup.getBonusCommissionableAmount().doubleValue());
                                    totalComission = totalComission.add(commissionAmountGroup.getBonusCommissionableAmount());
                                }
//                                if (commissionAmountGroup.getAmount().doubleValue() >= 300000){
//                                    row.createCell(24).setCellValue(commissionAmountGroup.getCommission().doubleValue());
//                                }else {
//                                    row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
//                                }
                            }
                        }
                    }

                    if(commissionAmountGroup.getIdAg() == 17){
                        row.createCell(19).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                        row.createCell(20).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                        row.createCell(21).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                        List listaSaludCI = commissionAmountGroupBackupDao.findTotalAmountGroupSaludCI(fromDate, toDate);
                        for (Object data : listaSaludCI){
                            CommissionAmountGroup commissionAmountGroups;
                            Object[] projection = (Object[]) data;
                            String claveSap = (String) projection[0];
                            BigDecimal commissionableAmount = (BigDecimal) projection[1];
                            BigDecimal numRequest = new BigDecimal(projection[2].toString());
                            BigDecimal comission = new BigDecimal(projection[3].toString());
                            Integer idAg = (Integer) projection[4];

                            if (claveSap.equals(commissionAmountGroup.getClaveSap())){
                                row.createCell(24).setCellValue(comission.doubleValue());
                                BigDecimal finalCommission = commissionAmountGroup.getCommission().subtract(comission);
                                if (finalCommission.signum() == -1){
                                    row.createCell(27).setCellValue(0);
                                    totalComission = totalComission.add(new BigDecimal(0));
                                    commissionAmountGroup.setAdjustment(new BigDecimal(0));
                                    commissionAmountGroupDao.update(commissionAmountGroup);
                                }else {
                                    row.createCell(27).setCellValue(finalCommission.doubleValue());
                                    totalComission = totalComission.add(finalCommission);
                                    commissionAmountGroup.setAdjustment(finalCommission);
                                    commissionAmountGroupDao.update(commissionAmountGroup);
                                }
                                if(commissionAmountGroup.getBonusCommissionableAmount() != null){
                                    row.createCell(29).setCellValue(commissionAmountGroup.getBonusCommissionableAmount().doubleValue());
                                    totalComission = totalComission.add(commissionAmountGroup.getBonusCommissionableAmount());
                                }
//                                if (commissionAmountGroup.getAmount().doubleValue() >= 300000){
//                                    row.createCell(25).setCellValue(commissionAmountGroup.getCommission().doubleValue());
//                                }else {
//                                    row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
//                                }
                            }
                        }
                    }
                    row.createCell(30).setCellValue(totalComission.doubleValue());
                }
            }
            aux++;
        }

        Sheet hoja2 = wb.createSheet("Auxiliar");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("SUCURSAL");
        row2.createCell(1).setCellValue("MONTO");
        row2.createCell(2).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> auxiliarList = commissionAmountGroupDao.findByGroupAuxiliarAndBranch();

        int aux1 = 1;

        for (CommissionAmountGroup auxiliar: auxiliarList){
            row2 = hoja2.createRow(aux1);

            CBranchs branchs = cBranchsDao.findById(auxiliar.getIdBranch());

            if (branchs != null){
                row2.createCell(0).setCellValue(branchs.getBranchShort());
            }

            row2.createCell(1).setCellValue(auxiliar.getAmount().doubleValue());
            row2.createCell(2).setCellValue(auxiliar.getCommission().doubleValue());

            aux1++;
        }

        List<CommissionAmountGroup> commissionAmountBranchGroupList = commissionAmountGroupDao.findAllByBranchs();

        List<List<CommissionAmountGroup>> commissionAmountBranchGroupStreamList = new ArrayList<>();

        List<Integer> idBranchList = commissionAmountGroupDao.findOnlyByIdBranch();


        for (Integer idBranch : idBranchList) {
            List<CommissionAmountGroup> commissionAmountGroupStream =
                    commissionAmountBranchGroupList.stream()
                            .filter(commissionAmountGroup -> commissionAmountGroup.getIdBranch().equals(idBranch))
                            .collect(Collectors.toList());

            commissionAmountBranchGroupStreamList.add(commissionAmountGroupStream);
        }

        Sheet hoja6 = wb.createSheet("Gerente de sucursal");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);

        row6.createCell(0).setCellValue("ID SUCURSAL");
        row6.createCell(1).setCellValue("EMPRESA");
        row6.createCell(2).setCellValue("REGION");
        row6.createCell(3).setCellValue("ZONA");
        row6.createCell(4).setCellValue("NOMBRE SUCURSAL");
        row6.createCell(5).setCellValue("MONTO CONVENIOS");
        row6.createCell(6).setCellValue("MONTO PEMEX");
        row6.createCell(7).setCellValue("MONTO TOTAL");
        row6.createCell(8).setCellValue("%MONTO TOTAL");
        row6.createCell(9).setCellValue("META");
        row6.createCell(10).setCellValue("%PROYECCION");
        row6.createCell(11).setCellValue("COMISION CONVENIOS");
        row6.createCell(12).setCellValue("COMISION PEMEX");
        row6.createCell(13).setCellValue("COMISION TOTAL");

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        int aux6 = 1;

        for (List listGeneric : commissionAmountBranchGroupStreamList){
            row6 = hoja6.createRow(aux6);
            Double totalAmount = 0.00;
            Double totalCommission = 0.00;

            for (Object object: listGeneric){
                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;

                row6.createCell(0).setCellValue(commissionAmountGroup.getIdBranch());

                if (commissionAmountGroup.getIdDistributor() != null){
                    CDistributors distributor = cDistributorsDao.findById(commissionAmountGroup.getIdDistributor());

                    if (distributor != null){
                        row6.createCell(1).setCellValue(distributor.getAcronyms());
                    }
                }

                if (commissionAmountGroup.getIdRegion() != null){
                    CRegions region = cRegionsDao.findById(commissionAmountGroup.getIdRegion());

                    if (region != null){
                        row6.createCell(2).setCellValue(region.getRegionName());
                    }
                }

                if (commissionAmountGroup.getIdZona() != null){
                    CZonas zonas = cZonaDao.findById(commissionAmountGroup.getIdZona());

                    if (zonas != null){
                        row6.createCell(3).setCellValue(zonas.getName());
                    }
                }

                if(commissionAmountGroup.getIdBranch() != null){
                    CBranchs branchs = cBranchsDao.findById(commissionAmountGroup.getIdBranch());

                    if (branchs != null){
                        row6.createCell(4).setCellValue(branchs.getBranchName());
                    }

                    DwBranchs dwBranchs = dwBranchsDao.findById(commissionAmountGroup.getIdBranch());

                    if (dwBranchs != null){
                        row6.createCell(10).setCellValue(dwBranchs.getScope().doubleValue());
                    }

                }

                if(commissionAmountGroup.getIdAg() == 20){
                    row6.createCell(5).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    totalAmount += commissionAmountGroup.getAmount().doubleValue();
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal scope = commissionAmountGroup.getScope().divide(divisor);
                    row6.createCell(8).setCellValue(scope.doubleValue());
                    row6.createCell(9).setCellValue(commissionAmountGroup.getGoal().doubleValue());
                    row6.createCell(11).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalCommission += commissionAmountGroup.getCommission().doubleValue();
                }

                if(commissionAmountGroup.getIdAg() == 21){
                    row6.createCell(6).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    totalAmount += commissionAmountGroup.getAmount().doubleValue();
                    row6.createCell(12).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalCommission += commissionAmountGroup.getCommission().doubleValue();
                }

                row6.createCell(7).setCellValue(totalAmount);
                row6.createCell(13).setCellValue(totalCommission);
            }
            aux6++;
        }

//        Sheet hoja6 = wb.createSheet("Gerente de sucursal");
//
//        //Se crea la fila que contiene la cabecera
//        Row row6 = hoja6.createRow(0);
//
//        row6.createCell(0).setCellValue("ID SUCURSAL");
//        row6.createCell(1).setCellValue("NOMBRE SUCURSAL");
//        row6.createCell(2).setCellValue("META");
//        row6.createCell(3).setCellValue("MONTO");
//        row6.createCell(4).setCellValue("ALCANCE");
//        row6.createCell(5).setCellValue("TABULADOR");
//        row6.createCell(6).setCellValue("COMISION GERENTE");
//
//        //Implementacion del estilo
//        for (Cell celda : row6) {
//            celda.setCellStyle(style);
//        }
//
//        int aux6 = 1;
//
//        for (List listGeneric : commissionAmountBranchGroupStreamList){
//            row6 = hoja6.createRow(aux6);
//
//            for (Object object: listGeneric){
//                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;
//
//                row6.createCell(0).setCellValue(commissionAmountGroup.getIdBranch());
//
//                if(commissionAmountGroup.getIdBranch() != null){
//                    CBranchs branchs = cBranchsDao.findById(commissionAmountGroup.getIdBranch());
//
//                    if (branchs != null){
//                        row6.createCell(1).setCellValue(branchs.getBranchName());
//                    }
//                }
//
//                if(commissionAmountGroup.getIdAg() == 20){
//                    row6.createCell(2).setCellValue(commissionAmountGroup.getGoal().doubleValue());
//                    row6.createCell(3).setCellValue(commissionAmountGroup.getAmount().doubleValue());
//                    BigDecimal divisor = new BigDecimal(100);
//                    BigDecimal scope = commissionAmountGroup.getScope().divide(divisor);
//                    row6.createCell(4).setCellValue(scope.doubleValue());
//                    row6.createCell(5).setCellValue(commissionAmountGroup.getTabulator().doubleValue());
//                    row6.createCell(6).setCellValue(commissionAmountGroup.getCommission().doubleValue());
//                }
//            }
//            aux6++;
//        }


        Sheet hoja3 = wb.createSheet("Gerente Zonal");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("EMPRESA");
        row3.createCell(1).setCellValue("ZONA");
        row3.createCell(2).setCellValue("SUCURSALES");
        row3.createCell(3).setCellValue("MONTO CONVENIOS");
        row3.createCell(4).setCellValue("MONTO PEMEX");
        row3.createCell(5).setCellValue("MONTO TOTAL");
        row3.createCell(6).setCellValue("TABULADOR CONVENIOS");
        row3.createCell(7).setCellValue("TABULADOR PEMEX");
        row3.createCell(8).setCellValue("COMISION CONVENIOS");
        row3.createCell(9).setCellValue("COMISION PEMEX");
        row3.createCell(10).setCellValue("COMISION TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<Integer> idZonas = commissionAmountGroupDao.getOnlyIdsZonasFromGroupZona();

        int aux2 = 1;

        for (Integer idZona : idZonas){

            if (idZona != null){
                CommissionAmountGroup zonaAmer = commissionAmountGroupDao.findZonaByBranchAndDistributorInGroupZonaGT(2, idZona);
                Double totalMontoZonaAmer = 0.00;
                Double comissionMontoZonaAmer = 0.00;

                CommissionAmountGroup zonaAmermedia = commissionAmountGroupDao.findZonaByBranchAndDistributorInGroupZonaGT(3, idZona);
                Double totalMontoZonaAmermedia = 0.00;
                Double comissionMontoZonaAmermedia = 0.00;

                if (zonaAmer != null){
                    row3 = hoja3.createRow(aux2);

                    CDistributors distributor = cDistributorsDao.findById(zonaAmer.getIdDistributor());

                    if (distributor != null){
                        row3.createCell(0).setCellValue(distributor.getAcronyms());
                    }

                    CZonas zonas = cZonaDao.findById(zonaAmer.getIdZona());

                    if (zonas != null){
                        row3.createCell(1).setCellValue(zonas.getName());
                    }

                    List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.findAllBranchsByZonaAndDistributor(2, idZona);

                    Integer numBranches = branchsList.size();

                    if (numBranches != null){
                        row3.createCell(2).setCellValue(numBranches);
                    }

                    if (zonaAmer.getAmount() != null){
                        row3.createCell(3).setCellValue(zonaAmer.getAmount().doubleValue());
                        totalMontoZonaAmer += zonaAmer.getAmount().doubleValue();
                    }

                    if(zonaAmer.getTabulator() != null){
                        row3.createCell(6).setCellValue(zonaAmer.getTabulator().doubleValue());
                    }

                    if (zonaAmer.getCommission() != null){
                        row3.createCell(8).setCellValue(zonaAmer.getCommission().doubleValue());
                        comissionMontoZonaAmer += zonaAmer.getCommission().doubleValue();
                    }

                    CommissionAmountGroup zonaAmerPemex = commissionAmountGroupDao.findZonaByBranchAndDistributorInGroupZonaP(2, idZona);

                    if (zonaAmerPemex != null){

                        if (zonaAmerPemex.getTabulator() != null){
                            if (zonaAmerPemex.getAmount() != null) {
                                row3.createCell(4).setCellValue(zonaAmerPemex.getAmount().doubleValue());
                                totalMontoZonaAmer += zonaAmerPemex.getAmount().doubleValue();
                            }

                            if (zonaAmerPemex.getTabulator() != null) {
                                row3.createCell(7).setCellValue(zonaAmerPemex.getTabulator().doubleValue());
                            }

                            if (zonaAmerPemex.getCommission() != null) {
                                row3.createCell(9).setCellValue(zonaAmerPemex.getCommission().doubleValue());
                                comissionMontoZonaAmer += zonaAmerPemex.getCommission().doubleValue();
                            }
                        }
                    }

                    row3.createCell(5).setCellValue(totalMontoZonaAmer);
                    row3.createCell(10).setCellValue(comissionMontoZonaAmer);

                    aux2++;
                }

                if (zonaAmermedia != null){
                    row3 = hoja3.createRow(aux2);

                    CDistributors distributor = cDistributorsDao.findById(zonaAmermedia.getIdDistributor());

                    if (distributor != null){
                        row3.createCell(0).setCellValue(distributor.getAcronyms());
                    }

                    CZonas zonas = cZonaDao.findById(zonaAmermedia.getIdZona());

                    if (zonas != null){
                        row3.createCell(1).setCellValue(zonas.getName());
                    }

                    List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.findAllBranchsByZonaAndDistributor(3, idZona);

                    Integer numBranches = branchsList.size();

                    if (numBranches != null){
                        row3.createCell(2).setCellValue(numBranches);
                    }

                    if (zonaAmermedia.getAmount() != null) {
                        row3.createCell(3).setCellValue(zonaAmermedia.getAmount().doubleValue());
                        totalMontoZonaAmermedia += zonaAmermedia.getAmount().doubleValue();
                    }

                    if (zonaAmermedia.getTabulator() != null) {
                        row3.createCell(6).setCellValue(zonaAmermedia.getTabulator().doubleValue());
                    }

                    if (zonaAmermedia.getCommission() != null) {
                        row3.createCell(8).setCellValue(zonaAmermedia.getCommission().doubleValue());
                        comissionMontoZonaAmermedia += zonaAmermedia.getCommission().doubleValue();
                    }

                    CommissionAmountGroup zonaAmermediaPemex = commissionAmountGroupDao.findZonaByBranchAndDistributorInGroupZonaP(3, idZona);

                    if (zonaAmermediaPemex != null){
                        if (zonaAmermediaPemex.getTabulator() != null){
                            if (zonaAmermediaPemex.getAmount() != null) {
                                row3.createCell(4).setCellValue(zonaAmermediaPemex.getAmount().doubleValue());
                                totalMontoZonaAmermedia += zonaAmermediaPemex.getAmount().doubleValue();
                            }

                            if (zonaAmermediaPemex.getTabulator() != null) {
                                row3.createCell(7).setCellValue(zonaAmermediaPemex.getTabulator().doubleValue());
                            }

                            if (zonaAmermediaPemex.getCommission() != null) {
                                row3.createCell(9).setCellValue(zonaAmermediaPemex.getCommission().doubleValue());
                                comissionMontoZonaAmermedia += zonaAmermediaPemex.getCommission().doubleValue();
                            }
                        }
                    }

                    row3.createCell(5).setCellValue(totalMontoZonaAmermedia);
                    row3.createCell(10).setCellValue(comissionMontoZonaAmermedia);

                    aux2++;
                }
            }
        }



//        Sheet hoja3 = wb.createSheet("Gerente Zonal");
//
//        //Se crea la fila que contiene la cabecera
//        Row row3 = hoja3.createRow(0);
//
////        row3.createCell(0).setCellValue("DISTRIBUIDOR");
//        row3.createCell(0).setCellValue("ZONA");
//        row3.createCell(1).setCellValue("MONTO");
//        row3.createCell(2).setCellValue("TABULADOR");
//        row3.createCell(3).setCellValue("COMISION");
//
//        //Implementacion del estilo
//        for (Cell celda : row3) {
//            celda.setCellStyle(style);
//        }
//
//        List<CommissionAmountGroup> zonalList = commissionAmountGroupDao.findByGroupZonalAndZona();
//
//        int aux2 = 1;
//
//        for (CommissionAmountGroup zonal: zonalList){
//            row3 = hoja3.createRow(aux2);
//
////            CDistributors distributors = cDistributorsDao.findById(zonal.getIdDistributor());
////
////            if (distributors != null){
////                row3.createCell(0).setCellValue(distributors.getDistributorName());
////            }
//
//            CZonas zonas = cZonaDao.findById(zonal.getIdZona());
//
//            if (zonas != null){
//                row3.createCell(0).setCellValue(zonas.getName());
//            }
//
//            row3.createCell(1).setCellValue(zonal.getAmount().doubleValue());
//            if (zonal.getTabulator() != null){
//                row3.createCell(2).setCellValue(zonal.getTabulator().doubleValue());
//            }
//            row3.createCell(3).setCellValue(zonal.getCommission().doubleValue());
//
//            aux2++;
//        }



        Sheet hoja4 = wb.createSheet("Gerente Regional");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("EMPRESA");
        row4.createCell(1).setCellValue("REGION");
        row4.createCell(2).setCellValue("SUCURSALES");
        row4.createCell(3).setCellValue("MONTO CONVENIOS");
        row4.createCell(4).setCellValue("MONTO PEMEX");
        row4.createCell(5).setCellValue("MONTO TOTAL");
        row4.createCell(6).setCellValue("TABULADOR CONVENIOS");
        row4.createCell(7).setCellValue("TABULADOR PEMEX");
        row4.createCell(8).setCellValue("COMISION CONVENIOS");
        row4.createCell(9).setCellValue("COMISION PEMEX");
        row4.createCell(10).setCellValue("COMISION TOTAL");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        List<Integer> idRegions = commissionAmountGroupDao.getOnlyIdsRegionsFromGroupRegion();

        int aux3 = 1;

        for (Integer idRegion : idRegions){

            if (idRegion != null){
                CommissionAmountGroup regionAmer = commissionAmountGroupDao.findRegionByRegionAndDistributorInGroupRegionGT(2, idRegion);
                Double totalMontoRegionAmer = 0.00;
                Double comissionMontoRegionAmer = 0.00;

                CommissionAmountGroup regionAmermedia = commissionAmountGroupDao.findRegionByRegionAndDistributorInGroupRegionGT(3, idRegion);
                Double totalMontoRegionAmermendia = 0.00;
                Double comissionMontoRegionAmermedia = 0.00;

                if (regionAmer != null){
                    row4 = hoja4.createRow(aux3);

                    CDistributors distributor = cDistributorsDao.findById(regionAmer.getIdDistributor());

                    if (distributor != null){
                        row4.createCell(0).setCellValue(distributor.getAcronyms());
                    }

                    CRegions region = cRegionsDao.findById(regionAmer.getIdRegion());

                    if (region != null){
                        row4.createCell(1).setCellValue(region.getRegionName());
                    }

                    List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.findAllBranchsByRegionAndDistributor(2, idRegion);

                    Integer numBranches = branchsList.size();

                    if (numBranches != null){
                        row4.createCell(2).setCellValue(numBranches);
                    }

                    if (regionAmer.getAmount() != null) {
                        row4.createCell(3).setCellValue(regionAmer.getAmount().doubleValue());
                        totalMontoRegionAmer += regionAmer.getAmount().doubleValue();
                    }

                    if (regionAmer.getTabulator() != null) {
                        row4.createCell(6).setCellValue(regionAmer.getTabulator().doubleValue());
                    }

                    if (regionAmer.getCommission() != null) {
                        row4.createCell(8).setCellValue(regionAmer.getCommission().doubleValue());
                        comissionMontoRegionAmer += regionAmer.getCommission().doubleValue();
                    }

                    CommissionAmountGroup regionAmerPemex = commissionAmountGroupDao.findRegionByRegionAndDistributorInGroupRegionP(2, idRegion);

                    if (regionAmerPemex != null){
                        if (regionAmerPemex.getTabulator() != null) {
                            if (regionAmerPemex.getAmount() != null) {
                                row4.createCell(4).setCellValue(regionAmerPemex.getAmount().doubleValue());
                                totalMontoRegionAmer += regionAmerPemex.getAmount().doubleValue();
                            }

                            if (regionAmerPemex.getTabulator() != null) {
                                row4.createCell(7).setCellValue(regionAmerPemex.getTabulator().doubleValue());
                            }

                            if (regionAmerPemex.getCommission() != null) {
                                row4.createCell(9).setCellValue(regionAmerPemex.getCommission().doubleValue());
                                comissionMontoRegionAmer += regionAmerPemex.getCommission().doubleValue();
                            }
                        }
                    }

                    row4.createCell(5).setCellValue(totalMontoRegionAmer);
                    row4.createCell(10).setCellValue(comissionMontoRegionAmer);

                    aux3++;
                }



                if (regionAmermedia != null){
                    row4 = hoja4.createRow(aux3);

                    CDistributors distributor = cDistributorsDao.findById(regionAmermedia.getIdDistributor());

                    if (distributor != null){
                        row4.createCell(0).setCellValue(distributor.getAcronyms());
                    }

                    CRegions region = cRegionsDao.findById(regionAmermedia.getIdRegion());

                    if (region != null){
                        row4.createCell(1).setCellValue(region.getRegionName());
                    }

                    List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.findAllBranchsByRegionAndDistributor(3, idRegion);

                    Integer numBranches = branchsList.size();

                    if (numBranches != null){
                        row4.createCell(2).setCellValue(numBranches);
                    }

                    if (regionAmermedia.getAmount() != null) {
                        row4.createCell(3).setCellValue(regionAmermedia.getAmount().doubleValue());
                        totalMontoRegionAmermendia += regionAmermedia.getAmount().doubleValue();
                    }

                    if (regionAmermedia.getTabulator() != null) {
                        row4.createCell(6).setCellValue(regionAmermedia.getTabulator().doubleValue());
                    }

                    if (regionAmermedia.getCommission() != null) {
                        row4.createCell(8).setCellValue(regionAmermedia.getCommission().doubleValue());
                        comissionMontoRegionAmermedia += regionAmermedia.getCommission().doubleValue();
                    }

                    CommissionAmountGroup regionAmermediaPemex = commissionAmountGroupDao.findRegionByRegionAndDistributorInGroupRegionP(3, idRegion);

                    if (regionAmermediaPemex != null){
                        if (regionAmermediaPemex.getTabulator() != null) {
                            if (regionAmermediaPemex.getAmount() != null) {
                                row4.createCell(4).setCellValue(regionAmermediaPemex.getAmount().doubleValue());
                                totalMontoRegionAmermendia += regionAmermediaPemex.getAmount().doubleValue();
                            }

                            if (regionAmermediaPemex.getTabulator() != null) {
                                row4.createCell(7).setCellValue(regionAmermediaPemex.getTabulator().doubleValue());
                            }

                            if (regionAmermediaPemex.getCommission() != null) {
                                row4.createCell(9).setCellValue(regionAmermediaPemex.getCommission().doubleValue());
                                comissionMontoRegionAmermedia += regionAmermediaPemex.getCommission().doubleValue();
                            }
                        }
                    }

                    row4.createCell(5).setCellValue(totalMontoRegionAmermendia);
                    row4.createCell(10).setCellValue(comissionMontoRegionAmermedia);

                    aux3++;
                }

            }
        }




//        Sheet hoja4 = wb.createSheet("Gerente Regional");
//
//        //Se crea la fila que contiene la cabecera
//        Row row4 = hoja4.createRow(0);
//
////        row4.createCell(0).setCellValue("DISTRIBUCION");
//        row4.createCell(0).setCellValue("REGION");
//        row4.createCell(1).setCellValue("MONTO");
//        row4.createCell(2).setCellValue("TABULADOR");
//        row4.createCell(3).setCellValue("COMISION");
//
//        //Implementacion del estilo
//        for (Cell celda : row4) {
//            celda.setCellStyle(style);
//        }
//
//        List<CommissionAmountGroup> regionList = commissionAmountGroupDao.findByGroupRegionslAndRegion();
//
//        int aux3 = 1;
//
//        for (CommissionAmountGroup region : regionList){
//            row4 = hoja4.createRow(aux3);
//
////            CDistributors distributor = cDistributorsDao.findById(region.getIdDistributor());
////
////            if (distributor != null){
////                row4.createCell(0).setCellValue(distributor.getDistributorName());
////            }
//
//            CRegions regions = cRegionsDao.findById(region.getIdRegion());
//
//            if (regions != null){
//                row4.createCell(0).setCellValue(regions.getRegionName());
//            }
//
//            row4.createCell(1).setCellValue(region.getAmount().doubleValue());
//            if (region.getTabulator() != null){
//                row4.createCell(2).setCellValue(region.getTabulator().doubleValue());
//            }
//            row4.createCell(3).setCellValue(region.getCommission().doubleValue());
//
//            aux3++;
//        }

        Sheet hoja5 = wb.createSheet("Gerente Comercial");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("EMPRESA");
        row5.createCell(1).setCellValue("TABULADOR");
        row5.createCell(2).setCellValue("MONTO");
        row5.createCell(3).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> distributorsList = commissionAmountGroupDao.findByGroupComercialAndDistributor();

        int aux4 = 1;

        for (CommissionAmountGroup comercial: distributorsList){
            row5 = hoja5.createRow(aux4);

            CDistributors distributor =  cDistributorsDao.findById(comercial.getIdDistributor());

            if (distributor != null){
                row5.createCell(0).setCellValue(distributor.getDistributorName());
            }

            row5.createCell(1).setCellValue(comercial.getTabulator().doubleValue());
            row5.createCell(2).setCellValue(comercial.getAmount().doubleValue());
            row5.createCell(3).setCellValue(comercial.getCommission().doubleValue());

            aux4++;
        }

        List<CommissionAmountGroup> commissionAmountGroupsList = commissionAmountGroupDao.findSupervisorByBuildingReport();

        List<List<CommissionAmountGroup>> commissionSupervisorAmountGroupStreamList = new ArrayList<>();

        List<String> claveSapSupervisorList = commissionAmountGroupDao.findSupervisorOnlyClaveSapeByBuildingReport();

        for (String claveSap : claveSapSupervisorList) {
            List<CommissionAmountGroup> commissionAmountGroupStream =
                    commissionAmountGroupsList.stream()
                            .filter(commissionAmountGroup -> commissionAmountGroup.getClaveSap().equals(claveSap))
                            .collect(Collectors.toList());

            commissionSupervisorAmountGroupStreamList.add(commissionAmountGroupStream);
        }

        Sheet hoja7 = wb.createSheet("Supervisor");

        //Se crea la fila que contiene la cabecera
        Row row7 = hoja7.createRow(0);

        row7.createCell(0).setCellValue("EMPRESA");
        row7.createCell(1).setCellValue("REGION");
        row7.createCell(2).setCellValue("ZONA");
        row7.createCell(3).setCellValue("NOMBRE");
        row7.createCell(4).setCellValue("CLAVE SAP");
        row7.createCell(5).setCellValue("PUESTO");
        row7.createCell(6).setCellValue("BANCO");
        row7.createCell(7).setCellValue("NÚMERO DE  CUENTA");
        row7.createCell(8).setCellValue("CLABE");
        row7.createCell(9).setCellValue("SUCURSAL");
        row7.createCell(10).setCellValue("RFC");
        row7.createCell(11).setCellValue("CURP");
        row7.createCell(12).setCellValue("FECHA INGRESO");
//        row7.createCell(13).setCellValue("No. SOL. GOBIERNO");
//        row7.createCell(14).setCellValue("MONTO GOBIERNO");
//        row7.createCell(15).setCellValue("COMISION GOBIERNO");
//        row7.createCell(16).setCellValue("No. SOL. MAGISTERIO");
//        row7.createCell(17).setCellValue("MONTO MAGISTERIO");
//        row7.createCell(18).setCellValue("COMISION MAGISTERIO");
//        row7.createCell(19).setCellValue("No. SOL. PEMEX");
//        row7.createCell(20).setCellValue("MONTO PEMEX");
//        row7.createCell(21).setCellValue("COMISION PEMEX");
//        row7.createCell(22).setCellValue("No. SOL. SALUD");
//        row7.createCell(23).setCellValue("MONTO SALUD");
//        row7.createCell(24).setCellValue("COMISION SALUD");
//        row7.createCell(25).setCellValue("No. SOL. SALUD-CI");
//        row7.createCell(26).setCellValue("MONTO SALUD-CI");
//        row7.createCell(27).setCellValue("COMISION SALUD-CI");
//        row7.createCell(28).setCellValue("No. SOL. IEEPO");
//        row7.createCell(29).setCellValue("MONTO IEEPO");
//        row7.createCell(30).setCellValue("COMISION IEEPO");
        row7.createCell(13).setCellValue("No. SOL. TOTAL");
        row7.createCell(14).setCellValue("MONTO TOTAL COMO PROMOTOR");
        row7.createCell(15).setCellValue("COMISION TOTAL COMO PROMOTOR");
        row7.createCell(16).setCellValue("MONTO ACUMULADO CELULA");
        row7.createCell(17).setCellValue("ALCANCE");
        row7.createCell(18).setCellValue("TABULADOR");
        row7.createCell(19).setCellValue("COMISIÒN COMO SUPERVISOR");

        //Implementacion del estilo
        for (Cell celda : row7) {
            celda.setCellStyle(style);
        }

        int aux7 = 1;

        for (List listGeneric : commissionSupervisorAmountGroupStreamList){
            row7 = hoja7.createRow(aux7);
            BigDecimal totalComission = new BigDecimal(0);
            BigDecimal totalAmount = new BigDecimal(0);
            BigDecimal totalRequestNum = new BigDecimal(0);

            for (Object object: listGeneric){
                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;

                row7.createCell(0).setCellValue(commissionAmountGroup.getClaveSap());

                if (commissionAmountGroup.getIdEmployee() != null){
                    DwEmployees dwEmployees = dwEmployeesDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
                    row7.createCell(0).setCellValue(dwEmployees.getDwEnterprise().getDistributor().getDistributorName());
                    row7.createCell(1).setCellValue(dwEmployees.getDwEnterprise().getRegion().getRegionName());
                    row7.createCell(2).setCellValue(dwEmployees.getDwEnterprise().getZona().getName());
                    row7.createCell(3).setCellValue(dwEmployees.getEmployee().getFullName());
                    row7.createCell(4).setCellValue(dwEmployees.getEmployee().getClaveSap());
                    row7.createCell(5).setCellValue(dwEmployees.getRole().getRoleName());

                    EmployeesAccounts employeesAccounts = employeesAccountsDao.findByIdEmployee(dwEmployees.getIdEmployee());

                    if (employeesAccounts != null){
                        row7.createCell(6).setCellValue(employeesAccounts.getAccount().getBank().getAcronyms());
                        row7.createCell(7).setCellValue(employeesAccounts.getAccount().getAccountNumber());
                        row7.createCell(8).setCellValue(employeesAccounts.getAccount().getAccountClabe());
                    }
                    row7.createCell(9).setCellValue(dwEmployees.getDwEnterprise().getBranch().getBranchShort());
                    row7.createCell(10).setCellValue(dwEmployees.getEmployee().getRfc());
                    row7.createCell(11).setCellValue(dwEmployees.getEmployee().getCurp());

                    if (dwEmployees.getEmployee().getJoinDate() != null){
                        Date joinDate = Date.from(dwEmployees.getEmployee().getJoinDate().atZone(ZoneId.systemDefault()).toInstant());
                        row7.createCell(12);
                        row7.getCell(12).setCellValue(joinDate);
                        row7.getCell(12).setCellStyle(cellDateStyle);
                    }
                }

                List<CommissionAmountGroupBackup> commissionAmountGroupBackupList = commissionAmountGroupBackupService.findByAcumulateEmployee(commissionAmountGroup.getIdEmployee(), fromDate, toDate);

                for (CommissionAmountGroupBackup commissionAmountGroupBackup :  commissionAmountGroupBackupList){
                    if(commissionAmountGroupBackup.getIdAg() ==  13){
//                        row7.createCell(13).setCellValue(commissionAmountGroupBackup.getApplicationsNumber().doubleValue());
//                        row7.createCell(14).setCellValue(commissionAmountGroupBackup.getAmount().doubleValue());
//                        row7.createCell(15).setCellValue(commissionAmountGroupBackup.getCommission().doubleValue());
//                        commissionAmountGroup.setAmount(commissionAmountGroupBackup.getAmount());
//                        commissionAmountGroup.setCommission(commissionAmountGroupBackup.getCommission());
//                        commissionAmountGroup.setApplicationsNumber(commissionAmountGroupBackup.getApplicationsNumber());
//                        commissionAmountGroupDao.update(commissionAmountGroup);
                        totalComission = totalComission.add(commissionAmountGroupBackup.getCommission());
                        totalAmount = totalAmount.add(commissionAmountGroupBackup.getAmount());
                        totalRequestNum = totalRequestNum.add(commissionAmountGroupBackup.getApplicationsNumber());
                    }

                    if(commissionAmountGroupBackup.getIdAg() ==  14){
//                        row7.createCell(16).setCellValue(commissionAmountGroupBackup.getApplicationsNumber().doubleValue());
//                        row7.createCell(17).setCellValue(commissionAmountGroupBackup.getAmount().doubleValue());
//                        row7.createCell(18).setCellValue(commissionAmountGroupBackup.getCommission().doubleValue());
//                        commissionAmountGroup.setAmount(commissionAmountGroupBackup.getAmount());
//                        commissionAmountGroup.setCommission(commissionAmountGroupBackup.getCommission());
//                        commissionAmountGroup.setApplicationsNumber(commissionAmountGroupBackup.getApplicationsNumber());
//                        commissionAmountGroupDao.update(commissionAmountGroup);
                        totalComission = totalComission.add(commissionAmountGroupBackup.getCommission());
                        totalAmount = totalAmount.add(commissionAmountGroupBackup.getAmount());
                        totalRequestNum = totalRequestNum.add(commissionAmountGroupBackup.getApplicationsNumber());
                    }

                    if(commissionAmountGroupBackup.getIdAg() ==  15){
//                        row7.createCell(19).setCellValue(commissionAmountGroupBackup.getApplicationsNumber().doubleValue());
//                        row7.createCell(20).setCellValue(commissionAmountGroupBackup.getAmount().doubleValue());
//                        row7.createCell(21).setCellValue(commissionAmountGroupBackup.getCommission().doubleValue());
//                        commissionAmountGroup.setAmount(commissionAmountGroupBackup.getAmount());
//                        commissionAmountGroup.setCommission(commissionAmountGroupBackup.getCommission());
//                        commissionAmountGroup.setApplicationsNumber(commissionAmountGroupBackup.getApplicationsNumber());
//                        commissionAmountGroupDao.update(commissionAmountGroup);
                        totalComission = totalComission.add(commissionAmountGroupBackup.getCommission());
                        totalAmount = totalAmount.add(commissionAmountGroupBackup.getAmount());
                        totalRequestNum = totalRequestNum.add(commissionAmountGroupBackup.getApplicationsNumber());
                    }

                    if(commissionAmountGroupBackup.getIdAg() ==  16){
//                        row7.createCell(22).setCellValue(commissionAmountGroupBackup.getApplicationsNumber().doubleValue());
//                        row7.createCell(23).setCellValue(commissionAmountGroupBackup.getAmount().doubleValue());
//                        row7.createCell(24).setCellValue(commissionAmountGroupBackup.getCommission().doubleValue());
//                        commissionAmountGroup.setAmount(commissionAmountGroupBackup.getAmount());
//                        commissionAmountGroup.setCommission(commissionAmountGroupBackup.getCommission());
//                        commissionAmountGroup.setApplicationsNumber(commissionAmountGroupBackup.getApplicationsNumber());
//                        commissionAmountGroupDao.update(commissionAmountGroup);
                        totalComission = totalComission.add(commissionAmountGroupBackup.getCommission());
                        totalAmount = totalAmount.add(commissionAmountGroupBackup.getAmount());
                        totalRequestNum = totalRequestNum.add(commissionAmountGroupBackup.getApplicationsNumber());
                    }

                    if(commissionAmountGroupBackup.getIdAg() ==  17){
//                        row7.createCell(25).setCellValue(commissionAmountGroupBackup.getApplicationsNumber().doubleValue());
//                        row7.createCell(26).setCellValue(commissionAmountGroupBackup.getAmount().doubleValue());
//                        row7.createCell(27).setCellValue(commissionAmountGroupBackup.getCommission().doubleValue());
//                        commissionAmountGroup.setAmount(commissionAmountGroupBackup.getAmount());
//                        commissionAmountGroup.setCommission(commissionAmountGroupBackup.getCommission());
//                        commissionAmountGroup.setApplicationsNumber(commissionAmountGroupBackup.getApplicationsNumber());
//                        commissionAmountGroupDao.update(commissionAmountGroup);
                        totalComission = totalComission.add(commissionAmountGroupBackup.getCommission());
                        totalAmount = totalAmount.add(commissionAmountGroupBackup.getAmount());
                        totalRequestNum = totalRequestNum.add(commissionAmountGroupBackup.getApplicationsNumber());
                    }

                    if(commissionAmountGroupBackup.getIdAg() ==  29){
//                        row7.createCell(28).setCellValue(commissionAmountGroupBackup.getApplicationsNumber().doubleValue());
//                        row7.createCell(29).setCellValue(commissionAmountGroupBackup.getAmount().doubleValue());
//                        row7.createCell(30).setCellValue(commissionAmountGroupBackup.getCommission().doubleValue());
//                        commissionAmountGroup.setAmount(commissionAmountGroupBackup.getAmount());
//                        commissionAmountGroup.setCommission(commissionAmountGroupBackup.getCommission());
//                        commissionAmountGroup.setApplicationsNumber(commissionAmountGroupBackup.getApplicationsNumber());
//                        commissionAmountGroupDao.update(commissionAmountGroup);
                        totalComission = totalComission.add(commissionAmountGroupBackup.getCommission());
                        totalAmount = totalAmount.add(commissionAmountGroupBackup.getAmount());
                        totalRequestNum = totalRequestNum.add(commissionAmountGroupBackup.getApplicationsNumber());
                    }
                }

                row7.createCell(13).setCellValue(totalRequestNum.doubleValue());
                row7.createCell(14).setCellValue(totalAmount.doubleValue());
                row7.createCell(15).setCellValue(totalComission.doubleValue());

                if(commissionAmountGroup.getIdAg() == 30){
                    row7.createCell(16).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    if(commissionAmountGroup.getScope() != null){
                        row7.createCell(17).setCellValue(commissionAmountGroup.getScope().doubleValue());
                    }
                    if (commissionAmountGroup.getTabulator() != null){
                        row7.createCell(18).setCellValue(commissionAmountGroup.getTabulator().doubleValue());
                    }
                    if(commissionAmountGroup.getCommission() != null){
                        row7.createCell(19).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    }
                }
            }
            aux7++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);
        hoja.autoSizeColumn(3);
        hoja.autoSizeColumn(4);
        hoja.autoSizeColumn(5);
        hoja.autoSizeColumn(6);
        hoja.autoSizeColumn(7);
        hoja.autoSizeColumn(8);
        hoja.autoSizeColumn(9);
        hoja.autoSizeColumn(10);
        hoja.autoSizeColumn(11);
        hoja.autoSizeColumn(12);
        hoja.autoSizeColumn(13);
        hoja.autoSizeColumn(14);
        hoja.autoSizeColumn(15);
        hoja.autoSizeColumn(16);
        hoja.autoSizeColumn(17);
        hoja.autoSizeColumn(18);
        hoja2.autoSizeColumn(0);
        hoja2.autoSizeColumn(1);
        hoja2.autoSizeColumn(2);
        hoja3.autoSizeColumn(0);
        hoja3.autoSizeColumn(1);
        hoja3.autoSizeColumn(2);
        hoja4.autoSizeColumn(0);
        hoja4.autoSizeColumn(1);
        hoja4.autoSizeColumn(2);
        hoja5.autoSizeColumn(0);
        hoja5.autoSizeColumn(1);
        hoja5.autoSizeColumn(2);
        hoja6.autoSizeColumn(0);
        hoja6.autoSizeColumn(1);
        hoja6.autoSizeColumn(2);
        hoja6.autoSizeColumn(3);
        hoja6.autoSizeColumn(4);
        hoja6.autoSizeColumn(5);
        hoja6.autoSizeColumn(6);
        hoja6.autoSizeColumn(7);
        hoja6.autoSizeColumn(8);
        hoja6.autoSizeColumn(9);
        hoja6.autoSizeColumn(10);

        wb.write(stream);
    }

    @Override
    public List<CommissionAmountGroup> getBranchWithScopeGoal() {
        return commissionAmountGroupDao.getBranchWithScopeGoal();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbySupervisor(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {

        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            BigDecimal numRequest = new BigDecimal(projection[2].toString());
            String claveSap = (String) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            Integer idEmployee = (Integer) projection[3];
            Integer idRole = (Integer) projection[4];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setClaveSap(claveSap);
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroup.setIdEmployee(idEmployee);
            commissionAmountGroup.setIdRole(idRole);
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> getBranchsWithScopeGoalAndTabulator() {
        return commissionAmountGroupDao.getBranchsWithScopeGoalAndTabulator();
    }
}

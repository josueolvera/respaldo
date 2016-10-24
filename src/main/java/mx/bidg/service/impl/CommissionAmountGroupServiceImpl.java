package mx.bidg.service.impl;

import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.service.CommissionAmountGroupService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
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
//            Integer idDistributor = (Integer) projection[3];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdZona(idZonas);
//            commissionAmountGroup.setIdDistributor(idDistributor);
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
    public List<CommissionAmountGroup> obtainAmountsbyRegion(List list, CAgreementsGroups agreementsGroups, LocalDateTime fromDate, LocalDateTime toDate) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idRegion = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];
            BigDecimal numRequest = new BigDecimal(projection[2].toString());
//            Integer idDistributor = (Integer) projection[3];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdRegion(idRegion);
//            commissionAmountGroup.setIdDistributor(idDistributor);
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
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
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
    public void comissionByReport(OutputStream stream) throws IOException {

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
        row.createCell(35).setCellValue("COMISION TOTAL");

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

                row.createCell(0).setCellValue(commissionAmountGroup.getClaveSap());

                if (commissionAmountGroup.getIdEmployee() != null){
                    DwEmployees dwEmployees = dwEmployeesDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
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

                if(commissionAmountGroup.getIdAg() == 13){
                    row.createCell(13).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(14).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(15).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 14){
                    row.createCell(16).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(17).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(18).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 15){
                    row.createCell(19).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(20).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(21).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 16){
                    row.createCell(22).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(23).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(24).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 17){
                    row.createCell(25).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(26).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(27).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 29){
                    row.createCell(28).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(29).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(30).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 19){
                    row.createCell(31).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(32).setCellValue(commissionAmountGroup.getAmount().doubleValue());

                    List<BonusCommisionableEmployee> bonusCommisionableEmployees = bonusCommisionableEmployeeDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
                    if (!bonusCommisionableEmployees.isEmpty()){
                        for (BonusCommisionableEmployee bonusCommisionableEmployee : bonusCommisionableEmployees){
                            if (bonusCommisionableEmployee.getIdCommissionBonus() == 1){
                                row.createCell(33).setCellValue(bonusCommisionableEmployee.getBonusAmount().doubleValue());
                                totalComission = totalComission.add(bonusCommisionableEmployee.getBonusAmount());
                            }else{
                                row.createCell(34).setCellValue(bonusCommisionableEmployee.getBonusAmount().doubleValue());
                                totalComission = totalComission.add(bonusCommisionableEmployee.getBonusAmount());
                            }
                        }
                    }
                }

                row.createCell(35).setCellValue(totalComission.doubleValue());
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
        Sheet hoja3 = wb.createSheet("Gerente Zonal");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

//        row3.createCell(0).setCellValue("DISTRIBUIDOR");
        row3.createCell(0).setCellValue("ZONA");
        row3.createCell(1).setCellValue("MONTO");
        row3.createCell(2).setCellValue("TABULADOR");
        row3.createCell(3).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> zonalList = commissionAmountGroupDao.findByGroupZonalAndZona();

        int aux2 = 1;

        for (CommissionAmountGroup zonal: zonalList){
            row3 = hoja3.createRow(aux2);

//            CDistributors distributors = cDistributorsDao.findById(zonal.getIdDistributor());
//
//            if (distributors != null){
//                row3.createCell(0).setCellValue(distributors.getDistributorName());
//            }

            CZonas zonas = cZonaDao.findById(zonal.getIdZona());

            if (zonas != null){
                row3.createCell(0).setCellValue(zonas.getName());
            }

            row3.createCell(1).setCellValue(zonal.getAmount().doubleValue());
            row3.createCell(2).setCellValue(zonal.getTabulator().doubleValue());
            row3.createCell(3).setCellValue(zonal.getCommission().doubleValue());

            aux2++;
        }
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
        List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupDao.findAllByClaveSap();

        List<List<CommissionAmountGroup>> commissionAmountGroupStreamList = new ArrayList<>();

        List<String> claveSapList = commissionAmountGroupDao.findOnlyByClaveSap();

        List<CommissionAmountGroupBackup> amountGobiernoBackup = commissionAmountGroupBackupDao.findTotalAmountGroupGobierno(fromDate, toDate);
        List<CommissionAmountGroupBackup> amountSaludBackup = commissionAmountGroupBackupDao.findTotalAmountGroupSalud(fromDate, toDate);
        List<CommissionAmountGroupBackup> amountSaludCIBackup = commissionAmountGroupBackupDao.findTotalAmountGroupSaludCI(fromDate, toDate);


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
        row.createCell(22).setCellValue("COMISION ACUMULADA");
        row.createCell(23).setCellValue("ALCANCE FINAL");
        row.createCell(24).setCellValue("BONO SALUD");
        row.createCell(25).setCellValue("BONO SALUD-CI");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (List listGeneric : commissionAmountGroupStreamList){
            row = hoja.createRow(aux);

            for (Object object: listGeneric){
                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;

                row.createCell(0).setCellValue(commissionAmountGroup.getClaveSap());

                if (commissionAmountGroup.getIdEmployee() != null){
                    DwEmployees dwEmployees = dwEmployeesDao.findByIdEmployee(commissionAmountGroup.getIdEmployee());
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
                            row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
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
                            row.createCell(22).setCellValue(comission.doubleValue());
                            if (commissionAmountGroup.getAmount().doubleValue() >= 300000){
                                row.createCell(24).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                            }else {
                                row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
                            }
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
                            row.createCell(22).setCellValue(comission.doubleValue());
                            if (commissionAmountGroup.getAmount().doubleValue() >= 300000){
                                row.createCell(25).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                            }else {
                                row.createCell(23).setCellValue(commissionAmountGroup.getCommission().subtract(comission).doubleValue());
                            }
                        }
                    }
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
        row6.createCell(1).setCellValue("NOMBRE SUCURSAL");
        row6.createCell(2).setCellValue("META");
        row6.createCell(3).setCellValue("MONTO");
        row6.createCell(4).setCellValue("ALCANCE");
        row6.createCell(5).setCellValue("TABULADOR");
        row6.createCell(6).setCellValue("COMISION GERENTE");

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        int aux6 = 1;

        for (List listGeneric : commissionAmountBranchGroupStreamList){
            row6 = hoja6.createRow(aux6);

            for (Object object: listGeneric){
                CommissionAmountGroup commissionAmountGroup = (CommissionAmountGroup) object;

                row6.createCell(0).setCellValue(commissionAmountGroup.getIdBranch());

                if(commissionAmountGroup.getIdBranch() != null){
                    CBranchs branchs = cBranchsDao.findById(commissionAmountGroup.getIdBranch());

                    if (branchs != null){
                        row6.createCell(1).setCellValue(branchs.getBranchName());
                    }
                }

                if(commissionAmountGroup.getIdAg() == 20){
                    row6.createCell(2).setCellValue(commissionAmountGroup.getGoal().doubleValue());
                    row6.createCell(3).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal scope = commissionAmountGroup.getScope().divide(divisor);
                    row6.createCell(4).setCellValue(scope.doubleValue());
                    row6.createCell(5).setCellValue(commissionAmountGroup.getTabulator().doubleValue());
                    row6.createCell(6).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                }
            }
            aux6++;
        }

        Sheet hoja3 = wb.createSheet("Gerente Zonal");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

//        row3.createCell(0).setCellValue("DISTRIBUIDOR");
        row3.createCell(0).setCellValue("ZONA");
        row3.createCell(1).setCellValue("MONTO");
        row3.createCell(2).setCellValue("TABULADOR");
        row3.createCell(3).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> zonalList = commissionAmountGroupDao.findByGroupZonalAndZona();

        int aux2 = 1;

        for (CommissionAmountGroup zonal: zonalList){
            row3 = hoja3.createRow(aux2);

//            CDistributors distributors = cDistributorsDao.findById(zonal.getIdDistributor());
//
//            if (distributors != null){
//                row3.createCell(0).setCellValue(distributors.getDistributorName());
//            }

            CZonas zonas = cZonaDao.findById(zonal.getIdZona());

            if (zonas != null){
                row3.createCell(0).setCellValue(zonas.getName());
            }

            row3.createCell(1).setCellValue(zonal.getAmount().doubleValue());
            row3.createCell(2).setCellValue(zonal.getTabulator().doubleValue());
            row3.createCell(3).setCellValue(zonal.getCommission().doubleValue());

            aux2++;
        }

        Sheet hoja4 = wb.createSheet("Gerente Regional");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

//        row4.createCell(0).setCellValue("DISTRIBUCION");
        row4.createCell(0).setCellValue("REGION");
        row4.createCell(1).setCellValue("MONTO");
        row4.createCell(2).setCellValue("TABULADOR");
        row4.createCell(3).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> regionList = commissionAmountGroupDao.findByGroupRegionslAndRegion();

        int aux3 = 1;

        for (CommissionAmountGroup region : regionList){
            row4 = hoja4.createRow(aux3);

//            CDistributors distributor = cDistributorsDao.findById(region.getIdDistributor());
//
//            if (distributor != null){
//                row4.createCell(0).setCellValue(distributor.getDistributorName());
//            }

            CRegions regions = cRegionsDao.findById(region.getIdRegion());

            if (regions != null){
                row4.createCell(0).setCellValue(regions.getRegionName());
            }

            row4.createCell(1).setCellValue(region.getAmount().doubleValue());
            row4.createCell(2).setCellValue(region.getTabulator().doubleValue());
            row4.createCell(3).setCellValue(region.getCommission().doubleValue());

            aux3++;
        }

        Sheet hoja5 = wb.createSheet("Gerente Comercial");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("EMPRESA");
        row5.createCell(1).setCellValue("MONTO");
        row5.createCell(2).setCellValue("COMISION");

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

            row5.createCell(1).setCellValue(comercial.getAmount().doubleValue());
            row5.createCell(2).setCellValue(comercial.getCommission().doubleValue());

            aux4++;
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
}

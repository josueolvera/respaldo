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

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setClaveSap(claveSap);
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
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
            Integer idDistributor = (Integer) projection[3];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdRegion(idRegion);
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

        row.createCell(0).setCellValue("CLAVE SAP");
        row.createCell(1).setCellValue("No. SOL. GOBIERNO");
        row.createCell(2).setCellValue("MONTO GOBIERNO");
        row.createCell(3).setCellValue("COMISION GOBIERNO");
        row.createCell(4).setCellValue("No. SOL. MAGISTERIO");
        row.createCell(5).setCellValue("MONTO MAGISTERIO");
        row.createCell(6).setCellValue("COMISION MAGISTERIO");
        row.createCell(7).setCellValue("No. SOL. PEMEX");
        row.createCell(8).setCellValue("MONTO PEMEX");
        row.createCell(9).setCellValue("COMISION PEMEX");
        row.createCell(10).setCellValue("No. SOL. SALUD");
        row.createCell(11).setCellValue("MONTO SALUD");
        row.createCell(12).setCellValue("COMISION SALUD");
        row.createCell(13).setCellValue("No. SOL. SALUD-CI");
        row.createCell(14).setCellValue("MONTO SALUD-CI");
        row.createCell(15).setCellValue("COMISION SALUD-CI");
        row.createCell(16).setCellValue("No. SOL. IEEPO");
        row.createCell(17).setCellValue("MONTO IEEPO");
        row.createCell(18).setCellValue("COMISION IEEPO");
        row.createCell(19).setCellValue("No. SOL. TOTAL");
        row.createCell(20).setCellValue("MONTO TOTAL");
        row.createCell(21).setCellValue("BONO CUMPLIMIENTO");
        row.createCell(22).setCellValue("COMISION TOTAL");

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

                if(commissionAmountGroup.getIdAg() == 13){
                    row.createCell(1).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(2).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(3).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 14){
                    row.createCell(4).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(5).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(6).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 15){
                    row.createCell(7).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(8).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(9).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 16){
                    row.createCell(10).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(11).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(12).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 17){
                    row.createCell(13).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(14).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(15).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 29){
                    row.createCell(16).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(17).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(18).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    totalComission = totalComission.add(commissionAmountGroup.getCommission());
                }

                if(commissionAmountGroup.getIdAg() == 19){
                    row.createCell(19).setCellValue(commissionAmountGroup.getApplicationsNumber().doubleValue());
                    row.createCell(20).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row.createCell(21).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                }

                row.createCell(22).setCellValue(totalComission.doubleValue());
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
        row6.createCell(7).setCellValue("MONTO PEMEX");
        row6.createCell(8).setCellValue("COMISION PEMEX");
        row6.createCell(9).setCellValue("MONTO MENOS PEMEX");
        row6.createCell(10).setCellValue("TAB OTORGADO");

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

                if(commissionAmountGroup.getIdAg() == 21){
                    row6.createCell(7).setCellValue(commissionAmountGroup.getAmount().doubleValue());
                    row6.createCell(8).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                    if (row6.getCell(3) != null){
                        BigDecimal TotalAmount = new BigDecimal(row6.getCell(3).getNumericCellValue());
                        if (commissionAmountGroup.getAmount().equals(TotalAmount)){
                            row6.getCell(6).setCellValue(commissionAmountGroup.getCommission().doubleValue());
                        }else{
                            BigDecimal amountNotPemex = TotalAmount.subtract(commissionAmountGroup.getAmount());
                            row6.createCell(9).setCellValue(amountNotPemex.doubleValue());
                            BigDecimal tabulador = new BigDecimal(row6.getCell(5).getNumericCellValue());
                            BigDecimal divisor = new BigDecimal(100);
                            BigDecimal tabuladorNotPemex = tabulador.divide(divisor);
                            BigDecimal commisionNotPemex = amountNotPemex.multiply(tabuladorNotPemex);
                            row6.createCell(10).setCellValue(commisionNotPemex.doubleValue());
                            BigDecimal commissionTotal = commisionNotPemex.add(commissionAmountGroup.getCommission());
                            row6.getCell(6).setCellValue(commissionTotal.doubleValue());
                        }
                    }
                }
            }
            aux6++;
        }

        Sheet hoja3 = wb.createSheet("Gerente Zonal");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DISTRIBUIDOR");
        row3.createCell(1).setCellValue("ZONA");
        row3.createCell(2).setCellValue("MONTO");
        row3.createCell(3).setCellValue("TABULADOR");
        row3.createCell(4).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> zonalList = commissionAmountGroupDao.findByGroupZonalAndZona();

        int aux2 = 1;

        for (CommissionAmountGroup zonal: zonalList){
            row3 = hoja3.createRow(aux2);

            CDistributors distributors = cDistributorsDao.findById(zonal.getIdDistributor());

            if (distributors != null){
                row3.createCell(0).setCellValue(distributors.getDistributorName());
            }

            CZonas zonas = cZonaDao.findById(zonal.getIdZona());

            if (zonas != null){
                row3.createCell(1).setCellValue(zonas.getName());
            }

            row3.createCell(2).setCellValue(zonal.getAmount().doubleValue());
            row3.createCell(3).setCellValue(zonal.getTabulator().doubleValue());
            row3.createCell(4).setCellValue(zonal.getCommission().doubleValue());

            aux2++;
        }

        Sheet hoja4 = wb.createSheet("Gerente Regional");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("DISTRIBUCION");
        row4.createCell(1).setCellValue("REGION");
        row4.createCell(2).setCellValue("MONTO");
        row4.createCell(3).setCellValue("TABULADOR");
        row4.createCell(4).setCellValue("COMISION");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        List<CommissionAmountGroup> regionList = commissionAmountGroupDao.findByGroupRegionslAndRegion();

        int aux3 = 1;

        for (CommissionAmountGroup region : regionList){
            row4 = hoja4.createRow(aux3);

            CDistributors distributor = cDistributorsDao.findById(region.getIdDistributor());

            if (distributor != null){
                row4.createCell(0).setCellValue(distributor.getDistributorName());
            }

            CRegions regions = cRegionsDao.findById(region.getIdRegion());

            if (regions != null){
                row4.createCell(1).setCellValue(regions.getRegionName());
            }

            row4.createCell(2).setCellValue(region.getAmount().doubleValue());
            row4.createCell(3).setCellValue(region.getTabulator().doubleValue());
            row4.createCell(4).setCellValue(region.getCommission().doubleValue());

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

    @Override
    public List<CommissionAmountGroup> obtainBranchManager() {
        return commissionAmountGroupDao.findByGroupBranchAndBranch();
    }

    @Override
    public List<CommissionAmountGroup> obtainAuxiliar() {
        return commissionAmountGroupDao.findByGroupAuxiliarAndBranch();
    }
}

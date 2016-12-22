package mx.bidg.service.impl;

import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.dao.OutsourcingDao;
import mx.bidg.dao.PayrollDao;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Outsourcing;
import mx.bidg.model.Payroll;
import mx.bidg.service.PayrollService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Desarrollador on 19/11/2016.
 */
@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    PayrollDao payrollDao;

    @Autowired
    DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    OutsourcingDao outsourcingDao;

    @Override
    public Payroll save(Payroll payroll) {
        return payrollDao.save(payroll);
    }

    @Override
    public Payroll update(Payroll payroll) {
        return payrollDao.update(payroll);
    }

    @Override
    public Payroll findById(Integer idPayroll) {
        return payrollDao.findById(idPayroll);
    }

    @Override
    public List<Payroll> findAll() {
        return payrollDao.findAll();
    }

    @Override
    public boolean delete(Payroll payroll) {
        return payrollDao.delete(payroll);
    }

    @Override
    public void reportCorporate(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException {

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

        Sheet hoja1 = wb.createSheet("CALCULO");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("NOMBRE");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("PLAZA");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("PUESTO");
        row1.createCell(8).setCellValue("RFC");
        row1.createCell(9).setCellValue("CURP");
        row1.createCell(10).setCellValue("FECHA DE INGRESO");
        row1.createCell(11).setCellValue("SUELDO REAL QUINCENAL");
        row1.createCell(12).setCellValue("MONTO RETARDO");
        row1.createCell(13).setCellValue("DESCUENTO");
        row1.createCell(14).setCellValue("BONO");
        row1.createCell(15).setCellValue("AJUSTE");
        row1.createCell(16).setCellValue("PRIMA VACACIONAL");
        row1.createCell(17).setCellValue("EFECTIVO");
        row1.createCell(18).setCellValue("EFECTIVO EDMON");
        row1.createCell(19).setCellValue("COMISIONES EMCOFIN");
        row1.createCell(20).setCellValue("RHMAS PAGO");
        row1.createCell(21).setCellValue("RHMAS TOTAL A FACTURAR");
        row1.createCell(22).setCellValue("PERCEPCIONES");
        row1.createCell(23).setCellValue("DEDUCCIONES");
        row1.createCell(24).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row1.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row1.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(7).setCellValue(payroll.getPuesto());
            }
            if (payroll.getRfc() != null){
                row1.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(9).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(10);
                row1.getCell(10).setCellValue(joinDate);
                row1.getCell(10).setCellStyle(cellDateStyle);
            }
            if (payroll.getSueldo() != null){
                row1.createCell(11).setCellValue(payroll.getSueldo().doubleValue());
            }
            if (payroll.getMontoRetardo() != null){
                row1.createCell(12).setCellValue(payroll.getMontoRetardo().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(13).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(15).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(14).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getPrimaVacacional() != null){
                row1.createCell(16).setCellValue(payroll.getPrimaVacacional().doubleValue());
            }
            if (payroll.getEfectivo() != null){
                row1.createCell(17).setCellValue(payroll.getEfectivo().doubleValue());
            }
            if (payroll.getEfectivoEdmon() != null){
                row1.createCell(18).setCellValue(payroll.getEfectivoEdmon().doubleValue());
            }
            if (payroll.getComisionEmcofin() != null){
                row1.createCell(19).setCellValue(payroll.getComisionEmcofin().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(20).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(21).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(22).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(23).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(24).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findOnlyCorporate();
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux2++;
        }

        Sheet hoja3 = wb.createSheet("GMT NEC");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("N");
        row3.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row3.createCell(2).setCellValue("BANCO");
        row3.createCell(3).setCellValue("N. DE CUENTA");
        row3.createCell(4).setCellValue("CLABE");
        row3.createCell(5).setCellValue("SUCURSAL");
        row3.createCell(6).setCellValue("AREA");
        row3.createCell(7).setCellValue("RFC");
        row3.createCell(8).setCellValue("CURP");
        row3.createCell(9).setCellValue("MONTO A PAGAR");
        row3.createCell(10).setCellValue("11.50%");
        row3.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        int aux3 = 1;

        for(Payroll payroll : payrollList){
            row3 = hoja3.createRow(aux3);

            if (payroll.getNumeroDeEmpleado() != null){
                row3.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row3.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row3.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row3.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row3.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row3.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row3.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row3.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row3.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row3.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row3.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row3.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux3 ++;

        }

        Sheet hoja4 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        BigDecimal gmtNec = (BigDecimal) payrollDao.sumGmtNec();
        BigDecimal efectivoEdmon = (BigDecimal) payrollDao.sumEfectivoEdmon();
        BigDecimal rhmas = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        Double totalDeTotales = 0.00;
        if(gmtNec != null){
            totalDeTotales+=gmtNec.doubleValue();
            Row rowGmt = hoja4.createRow(3);

            rowGmt.createCell(1).setCellValue("GMT NEC");
            rowGmt.createCell(2).setCellValue(gmtNec.doubleValue());
        }
        if (efectivoEdmon != null){
            totalDeTotales+=efectivoEdmon.doubleValue();
            Row rowEfectivoEdmon = hoja4.createRow(5);

            rowEfectivoEdmon.createCell(1).setCellValue("EFECTIVO EDMON");
            rowEfectivoEdmon.createCell(2).setCellValue(efectivoEdmon.doubleValue());
        }
        if (rhmas != null){
            totalDeTotales+=rhmas.doubleValue();
            Row rowRhmas = hoja4.createRow(4);

            rowRhmas.createCell(1).setCellValue("RHMAS SEG, MSJ y GMT");
            rowRhmas.createCell(2).setCellValue(rhmas.doubleValue());
        }


        Row rowTotal = hoja4.createRow(6);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void corporateFortyName(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException {

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

        Sheet hoja1 = wb.createSheet("MATRIZ");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("REGION");
        row1.createCell(3).setCellValue("ZONA");
        row1.createCell(4).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(5).setCellValue("CLAVE SAP");
        row1.createCell(6).setCellValue("PUESTO");
        row1.createCell(7).setCellValue("BANCO");
        row1.createCell(8).setCellValue("CUENTA");
        row1.createCell(9).setCellValue("CLABE");
        row1.createCell(10).setCellValue("SUCURSAL");
        row1.createCell(11).setCellValue("RFC");
        row1.createCell(12).setCellValue("CURP");
        row1.createCell(13).setCellValue("FECHA DE INGRESO");
        row1.createCell(14).setCellValue("SUELDO QUINCENAL");
        row1.createCell(15).setCellValue("PRIMA VACACIONAL");
        row1.createCell(16).setCellValue("BONO");
        row1.createCell(17).setCellValue("AJUSTE");
        row1.createCell(18).setCellValue("IMMS RHMAS PAGO");
        row1.createCell(19).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(20).setCellValue("DESCUENTO");
        row1.createCell(21).setCellValue("PERCEPCIONES");
        row1.createCell(22).setCellValue("DEDUCCIONES");
        row1.createCell(23).setCellValue("DEPOSITOS ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null){
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null){
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null){
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null){
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null){
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null){
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null){
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getSueldo() != null){
                row1.createCell(14).setCellValue(payroll.getSueldo().doubleValue());
            }
            if (payroll.getPrimaVacacional() != null){
                row1.createCell(15).setCellValue(payroll.getPrimaVacacional().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(16).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(17).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(18).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(19).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(20).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(21).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(22).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(23).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS AMER");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux2++;
        }

        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DEPARTAMENTO");
        row3.createCell(1).setCellValue("CODIGO");
        row3.createCell(2).setCellValue("NOMBRE");
        row3.createCell(3).setCellValue("SUELDO");
        row3.createCell(4).setCellValue("SUBSIDIO");
        row3.createCell(5).setCellValue("IMSS EMPLEADO");
        row3.createCell(6).setCellValue("ISR");
        row3.createCell(7).setCellValue("AJUSTE AL NETO");
        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row3.createCell(10).setCellValue("IMSS");
        row3.createCell(11).setCellValue("RCV");
        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row3.createCell(15).setCellValue("COMISIÓN");
        row3.createCell(16).setCellValue("SUBTOTAL");
        row3.createCell(17).setCellValue("IVA");
        row3.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        int aux3 = 1;

        for (Outsourcing outsourcing : outsourcings){
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux3++;
        }

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("BANCO");
        row4.createCell(3).setCellValue("N. DE CUENTA");
        row4.createCell(4).setCellValue("CLABE");
        row4.createCell(5).setCellValue("SUCURSAL");
        row4.createCell(6).setCellValue("AREA");
        row4.createCell(7).setCellValue("RFC");
        row4.createCell(8).setCellValue("CURP");
        row4.createCell(9).setCellValue("MONTO A PAGAR");
        row4.createCell(10).setCellValue("11.50%");
        row4.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        for(Payroll payroll : amerNec){
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null){
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row4.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row4.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row4.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row4.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row4.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row4.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row4.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row4.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row4.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row4.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux4 ++;

        }

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("BANCO");
        row5.createCell(3).setCellValue("N. DE CUENTA");
        row5.createCell(4).setCellValue("CLABE");
        row5.createCell(5).setCellValue("SUCURSAL");
        row5.createCell(6).setCellValue("AREA");
        row5.createCell(7).setCellValue("RFC");
        row5.createCell(8).setCellValue("CURP");
        row5.createCell(9).setCellValue("MONTO A PAGAR");
        row5.createCell(10).setCellValue("11.50%");
        row5.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        for(Payroll payroll : amermediaNec){
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null){
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row5.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row5.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row5.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row5.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row5.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row5.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row5.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row5.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row5.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row5.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux5 ++;

        }

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        BigDecimal rhmasAmermedia  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        Double totalDeTotales = 0.00;

        if(amerNecSum != null){
            totalDeTotales+=amerNecSum.doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.doubleValue());
        }
        if(amermediaNecSum != null){
            totalDeTotales+=amermediaNecSum.doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.doubleValue());
        }
        if (rhmasAmer != null){
            totalDeTotales+=rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null){
            totalDeTotales+=rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }


        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void reportWeeklyPay(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException {
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

        Sheet hoja1 = wb.createSheet("MATRIZ");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("REGION");
        row1.createCell(3).setCellValue("ZONA");
        row1.createCell(4).setCellValue("NOMBRE");
        row1.createCell(5).setCellValue("CLAVE SAP");
        row1.createCell(6).setCellValue("PUESTO");
        row1.createCell(7).setCellValue("BANCO");
        row1.createCell(8).setCellValue("CUENTA");
        row1.createCell(9).setCellValue("CLABE");
        row1.createCell(10).setCellValue("SUCURSAL");
        row1.createCell(11).setCellValue("RFC");
        row1.createCell(12).setCellValue("CURP");
        row1.createCell(13).setCellValue("FECHA DE INGRESO");
        row1.createCell(14).setCellValue("APOYO 375");
        row1.createCell(15).setCellValue("MONTO_PROMOTOR");
        row1.createCell(16).setCellValue("COMISIÓN");
        row1.createCell(17).setCellValue("BONO CUMPLIMIENTO");
        row1.createCell(18).setCellValue("AJUSTES");
        row1.createCell(19).setCellValue("APOYO PASAJES");
        row1.createCell(20).setCellValue("DESCUENTOS");
        row1.createCell(21).setCellValue("IMMS RHMAS PAGO");
        row1.createCell(22).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(23).setCellValue("PERCEPCIONES");
        row1.createCell(24).setCellValue("DEDUCCIONES");
        row1.createCell(25).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null){
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null){
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null){
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null){
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null){
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null){
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null){
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(14).setCellValue(payroll.getApoyo375().doubleValue());
            }
            if (payroll.getMontoPromotor() != null){
                row1.createCell(15).setCellValue(payroll.getMontoPromotor().doubleValue());
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(16).setCellValue(payroll.getComissionPromotor().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(17).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(18).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getApoyoPasajes() != null){
                row1.createCell(19).setCellValue(payroll.getApoyoPasajes().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(20).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(21).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(22).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(23).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(24).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(25).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS AMER");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux2++;
        }

        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DEPARTAMENTO");
        row3.createCell(1).setCellValue("CODIGO");
        row3.createCell(2).setCellValue("NOMBRE");
        row3.createCell(3).setCellValue("SUELDO");
        row3.createCell(4).setCellValue("SUBSIDIO");
        row3.createCell(5).setCellValue("IMSS EMPLEADO");
        row3.createCell(6).setCellValue("ISR");
        row3.createCell(7).setCellValue("AJUSTE AL NETO");
        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row3.createCell(10).setCellValue("IMSS");
        row3.createCell(11).setCellValue("RCV");
        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row3.createCell(15).setCellValue("COMISIÓN");
        row3.createCell(16).setCellValue("SUBTOTAL");
        row3.createCell(17).setCellValue("IVA");
        row3.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        int aux3 = 1;

        for (Outsourcing outsourcing : outsourcings){
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux3++;
        }

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("BANCO");
        row4.createCell(3).setCellValue("N. DE CUENTA");
        row4.createCell(4).setCellValue("CLABE");
        row4.createCell(5).setCellValue("SUCURSAL");
        row4.createCell(6).setCellValue("AREA");
        row4.createCell(7).setCellValue("RFC");
        row4.createCell(8).setCellValue("CURP");
        row4.createCell(9).setCellValue("MONTO A PAGAR");
        row4.createCell(10).setCellValue("11.50%");
        row4.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        for(Payroll payroll : amerNec){
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null){
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row4.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row4.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row4.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row4.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row4.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row4.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row4.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row4.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row4.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row4.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux4 ++;

        }

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("BANCO");
        row5.createCell(3).setCellValue("N. DE CUENTA");
        row5.createCell(4).setCellValue("CLABE");
        row5.createCell(5).setCellValue("SUCURSAL");
        row5.createCell(6).setCellValue("AREA");
        row5.createCell(7).setCellValue("RFC");
        row5.createCell(8).setCellValue("CURP");
        row5.createCell(9).setCellValue("MONTO A PAGAR");
        row5.createCell(10).setCellValue("11.50%");
        row5.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        for(Payroll payroll : amermediaNec){
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null){
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row5.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row5.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row5.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row5.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row5.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row5.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row5.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row5.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row5.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row5.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux5 ++;

        }

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        BigDecimal rhmasAmermedia  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        Double totalDeTotales = 0.00;

        if(amerNecSum != null){
            totalDeTotales+=amerNecSum.doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.doubleValue());
        }
        if(amermediaNecSum != null){
            totalDeTotales+=amermediaNecSum.doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.doubleValue());
        }
        if (rhmasAmer != null){
            totalDeTotales+=rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null){
            totalDeTotales+=rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }


        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void monthlyPayrollReport(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException {
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

        Sheet hoja1 = wb.createSheet("MATRIZ");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("REGION");
        row1.createCell(3).setCellValue("ZONA");
        row1.createCell(4).setCellValue("NOMBRE");
        row1.createCell(5).setCellValue("CLAVE SAP");
        row1.createCell(6).setCellValue("PUESTO");
        row1.createCell(7).setCellValue("BANCO");
        row1.createCell(8).setCellValue("CUENTA");
        row1.createCell(9).setCellValue("CLABE");
        row1.createCell(10).setCellValue("SUCURSAL");
        row1.createCell(11).setCellValue("RFC");
        row1.createCell(12).setCellValue("CURP");
        row1.createCell(13).setCellValue("FECHA DE INGRESO");
        row1.createCell(14).setCellValue("APOYO 375");
        row1.createCell(15).setCellValue("MONTO_PROMOTOR");
        row1.createCell(16).setCellValue("COMISIÓN SEMANAL PROMOTOR");
        row1.createCell(17).setCellValue("BONO CUMPLIMIENTO");
        row1.createCell(18).setCellValue("AJUSTES");
        row1.createCell(19).setCellValue("APOYO PASAJES");
        row1.createCell(20).setCellValue("MONTO MENSUAL");
        row1.createCell(21).setCellValue("COMISIÓN MENSUAL");
        row1.createCell(22).setCellValue("DESCUENTOS");
        row1.createCell(23).setCellValue("IMMS RHMAS PAGO");
        row1.createCell(24).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(25).setCellValue("PERCEPCIONES");
        row1.createCell(26).setCellValue("DEDUCCIONES");
        row1.createCell(27).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null){
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null){
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null){
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null){
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null){
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null){
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null){
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(14).setCellValue(payroll.getApoyo375().doubleValue());
            }
            if (payroll.getMontoPromotor() != null){
                row1.createCell(15).setCellValue(payroll.getMontoPromotor().doubleValue());
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(16).setCellValue(payroll.getComissionPromotor().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(17).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(18).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getApoyoPasajes() != null){
                row1.createCell(19).setCellValue(payroll.getApoyoPasajes().doubleValue());
            }
            if (payroll.getMontoMensual() != null){
                row1.createCell(20).setCellValue(payroll.getMontoMensual().doubleValue());
            }
            if (payroll.getComisionMensual() != null){
                row1.createCell(21).setCellValue(payroll.getComisionMensual().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(22).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(23).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(24).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(25).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(26).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(27).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS AMER");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux2++;
        }

        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DEPARTAMENTO");
        row3.createCell(1).setCellValue("CODIGO");
        row3.createCell(2).setCellValue("NOMBRE");
        row3.createCell(3).setCellValue("SUELDO");
        row3.createCell(4).setCellValue("SUBSIDIO");
        row3.createCell(5).setCellValue("IMSS EMPLEADO");
        row3.createCell(6).setCellValue("ISR");
        row3.createCell(7).setCellValue("AJUSTE AL NETO");
        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row3.createCell(10).setCellValue("IMSS");
        row3.createCell(11).setCellValue("RCV");
        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row3.createCell(15).setCellValue("COMISIÓN");
        row3.createCell(16).setCellValue("SUBTOTAL");
        row3.createCell(17).setCellValue("IVA");
        row3.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        int aux3 = 1;

        for (Outsourcing outsourcing : outsourcings){
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
            }
            if(outsourcing.getSubsidy() != null){
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
            }
            if (outsourcing.getImssEmployee() != null){
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
            }
            if (outsourcing.getIsr() != null){
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
            }
            if (outsourcing.getAdjustment() != null){
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
            }
            if (outsourcing.getTotalDeductions() != null){
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
            }
            if (outsourcing.getNetAssetTax() != null){
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
            }
            if (outsourcing.getImss() != null){
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
            }
            if (outsourcing.getRcv() != null){
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
            }
            if (outsourcing.getPayrollTax() != null){
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
            }
            if (outsourcing.getCommission() != null){
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
            }
            if (outsourcing.getSubtotal() != null){
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
            }
            if (outsourcing.getIva() != null){
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
            }
            if (outsourcing.getTotal() != null){
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
            }

            aux3++;
        }

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("BANCO");
        row4.createCell(3).setCellValue("N. DE CUENTA");
        row4.createCell(4).setCellValue("CLABE");
        row4.createCell(5).setCellValue("SUCURSAL");
        row4.createCell(6).setCellValue("AREA");
        row4.createCell(7).setCellValue("RFC");
        row4.createCell(8).setCellValue("CURP");
        row4.createCell(9).setCellValue("MONTO A PAGAR");
        row4.createCell(10).setCellValue("11.50%");
        row4.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        for(Payroll payroll : amerNec){
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null){
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row4.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row4.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row4.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row4.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row4.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row4.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row4.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row4.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row4.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row4.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux4 ++;

        }

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("BANCO");
        row5.createCell(3).setCellValue("N. DE CUENTA");
        row5.createCell(4).setCellValue("CLABE");
        row5.createCell(5).setCellValue("SUCURSAL");
        row5.createCell(6).setCellValue("AREA");
        row5.createCell(7).setCellValue("RFC");
        row5.createCell(8).setCellValue("CURP");
        row5.createCell(9).setCellValue("MONTO A PAGAR");
        row5.createCell(10).setCellValue("11.50%");
        row5.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        for(Payroll payroll : amermediaNec){
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null){
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row5.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row5.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row5.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row5.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row5.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row5.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row5.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row5.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row5.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row5.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux5 ++;

        }

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        BigDecimal rhmasAmermedia  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        Double totalDeTotales = 0.00;

        if(amerNecSum != null){
            totalDeTotales+=amerNecSum.doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.doubleValue());
        }
        if(amermediaNecSum != null){
            totalDeTotales+=amermediaNecSum.doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.doubleValue());
        }
        if (rhmasAmer != null){
            totalDeTotales+=rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null){
            totalDeTotales+=rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }


        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void reportCorporateNec(FileOutputStream fileOutputStream) throws IOException {

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

        Sheet hoja1 = wb.createSheet("GMT NEC");

        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("N");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("N. DE CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("SUCURSAL");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("RFC");
        row1.createCell(8).setCellValue("CURP");
        row1.createCell(9).setCellValue("MONTO A PAGAR");
        row1.createCell(10).setCellValue("11.50%");
        row1.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        List<Payroll> payrollList = payrollDao.findAll();

        int aux1 = 1;

        for(Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row1.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row1.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row1.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row1.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row1.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row1.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux1 ++;

        }

        hoja1.autoSizeColumn(0);

        wb.write(fileOutputStream);
    }

    @Override
    public void reportDistributionNec(FileOutputStream fileOutputStream) throws IOException {

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

        Sheet hoja1 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("N");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("N. DE CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("SUCURSAL");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("RFC");
        row1.createCell(8).setCellValue("CURP");
        row1.createCell(9).setCellValue("MONTO A PAGAR");
        row1.createCell(10).setCellValue("11.50%");
        row1.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        for(Payroll payroll : amerNec){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row1.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row1.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row1.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row1.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row1.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row1.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux1 ++;

        }

        Sheet hoja2 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("N");
        row2.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row2.createCell(2).setCellValue("BANCO");
        row2.createCell(3).setCellValue("N. DE CUENTA");
        row2.createCell(4).setCellValue("CLABE");
        row2.createCell(5).setCellValue("SUCURSAL");
        row2.createCell(6).setCellValue("AREA");
        row2.createCell(7).setCellValue("RFC");
        row2.createCell(8).setCellValue("CURP");
        row2.createCell(9).setCellValue("MONTO A PAGAR");
        row2.createCell(10).setCellValue("11.50%");
        row2.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        int aux2 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        for(Payroll payroll : amermediaNec){
            row2 = hoja2.createRow(aux2);

            if (payroll.getNumeroDeEmpleado() != null){
                row2.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row2.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row2.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row2.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row2.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row2.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row2.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row2.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row2.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row2.createCell(9).setCellValue(payroll.getPago().doubleValue());
            }
            if (payroll.getComisionNec() != null){
                row2.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
            }
            if (payroll.getTotalFacturar() != null){
                row2.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
            }

            aux2 ++;

        }

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);

        wb.write(fileOutputStream);
    }
}

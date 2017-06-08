package mx.bidg.service.impl;

import mx.bidg.dao.*;
import mx.bidg.model.*;
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
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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

    @Autowired
    SapSaleDao sapSaleDao;

    @Autowired
    EmployeesHistoryDao employeesHistoryDao;

    @Autowired
    CRolesDao cRolesDao;

    @Autowired
    AccountsDao accountsDao;

    @Autowired
    CBranchsDao cBranchsDao;

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
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("NOMBRE");
        row1.createCell(3).setCellValue("BANCO");
        row1.createCell(4).setCellValue("CUENTA");
        row1.createCell(5).setCellValue("CLABE");
        row1.createCell(6).setCellValue("PLAZA");
        row1.createCell(7).setCellValue("AREA");
        row1.createCell(8).setCellValue("PUESTO");
        row1.createCell(9).setCellValue("RFC");
        row1.createCell(10).setCellValue("CURP");
        row1.createCell(11).setCellValue("FECHA DE INGRESO");
        row1.createCell(12).setCellValue("SUELDO REAL QUINCENAL");
        row1.createCell(13).setCellValue("MONTO RETARDO");
        row1.createCell(14).setCellValue("DESCUENTO");
        row1.createCell(15).setCellValue("AJUSTE");
        row1.createCell(16).setCellValue("BONO");
        row1.createCell(17).setCellValue("PRIMA VACACIONAL");
        row1.createCell(18).setCellValue("EFECTIVO");
        row1.createCell(19).setCellValue("EFECTIVO EDMON");
        row1.createCell(20).setCellValue("COMISIONES EMCOFIN");
        row1.createCell(21).setCellValue("RHMAS PAGO");
        row1.createCell(22).setCellValue("RHMAS TOTAL A FACTURAR");
        row1.createCell(23).setCellValue("PERCEPCIONES");
        row1.createCell(24).setCellValue("DEDUCCIONES");
        row1.createCell(25).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        Double sum1 = 0.00;
        Double sum2 = 0.00;
        Double sum3 = 0.00;
        Double sum4 = 0.00;
        Double sum5 = 0.00;
        Double sum6 = 0.00;
        Double sum7 = 0.00;
        Double sum8 = 0.00;
        Double sum9 = 0.00;
        Double sum10 = 0.00;
        Double sum11 = 0.00;
        Double sum12 = 0.00;
        Double sum13 = 0.00;
        Double sum14 = 0.00;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList) {
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null) {
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null) {
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getNombre() != null) {
                row1.createCell(2).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null) {
                row1.createCell(3).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row1.createCell(4).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row1.createCell(5).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row1.createCell(6).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null) {
                row1.createCell(7).setCellValue(payroll.getArea());
            }
            if (payroll.getPuesto() != null) {
                row1.createCell(8).setCellValue(payroll.getPuesto());
            }
            if (payroll.getRfc() != null) {
                row1.createCell(9).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row1.createCell(10).setCellValue(payroll.getCurp());
            }
            if (payroll.getFechaIngreso() != null) {
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(11);
                row1.getCell(11).setCellValue(joinDate);
                row1.getCell(11).setCellStyle(cellDateStyle);
            }
            if (payroll.getSueldo() != null) {
                if (payroll.getSueldo().signum() == -1) {
                    row1.createCell(12).setCellValue(0.0);
                    sum1 += 0.0;
                } else {
                    row1.createCell(12).setCellValue(payroll.getSueldo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum1 += payroll.getSueldo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getMontoRetardo() != null) {
                if (payroll.getMontoRetardo().signum() == -1) {
                    row1.createCell(13).setCellValue(0.0);
                    sum2 += 0.0;
                } else {
                    row1.createCell(13).setCellValue(payroll.getMontoRetardo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum2 += payroll.getMontoRetardo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getDescuento() != null) {
                if (payroll.getDescuento().signum() == -1) {
                    row1.createCell(14).setCellValue(0.0);
                    sum3 += 0.0;
                } else {
                    row1.createCell(14).setCellValue(payroll.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum3 += payroll.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getAjuste() != null) {
                if (payroll.getAjuste().signum() == -1) {
                    row1.createCell(15).setCellValue(0.0);
                    sum4 += 0.0;
                } else {
                    row1.createCell(15).setCellValue(payroll.getAjuste().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum4 += payroll.getAjuste().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getBono() != null) {
                if (payroll.getBono().signum() == -1) {
                    row1.createCell(16).setCellValue(0.0);
                    sum5 += 0.0;
                } else {
                    row1.createCell(16).setCellValue(payroll.getBono().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum5 += payroll.getBono().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getPrimaVacacional() != null) {
                if (payroll.getPrimaVacacional().signum() == -1) {
                    row1.createCell(17).setCellValue(0.0);
                    sum6 += 0.0;
                } else {
                    row1.createCell(17).setCellValue(payroll.getPrimaVacacional().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum6 += payroll.getPrimaVacacional().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getEfectivo() != null) {
                if (payroll.getEfectivo().signum() == -1) {
                    row1.createCell(18).setCellValue(0.0);
                    sum7 += 0.0;
                } else {
                    row1.createCell(18).setCellValue(payroll.getEfectivo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum7 += payroll.getEfectivo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getEfectivoEdmon() != null) {
                if (payroll.getEfectivoEdmon().signum() == -1) {
                    row1.createCell(19).setCellValue(0.0);
                    sum8 += 0.0;
                } else {
                    row1.createCell(19).setCellValue(payroll.getEfectivoEdmon().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum8 += payroll.getEfectivoEdmon().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getComisionEmcofin() != null) {
                if (payroll.getComisionEmcofin().signum() == -1) {
                    row1.createCell(20).setCellValue(0.0);
                    sum9 += 0.0;
                } else {
                    row1.createCell(20).setCellValue(payroll.getComisionEmcofin().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum9 += payroll.getComisionEmcofin().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getRhmasPago() != null) {
                if (payroll.getRhmasPago().signum() == -1) {
                    row1.createCell(21).setCellValue(0.0);
                    sum10 += 0.0;
                } else {
                    row1.createCell(21).setCellValue(payroll.getRhmasPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum10 += payroll.getRhmasPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getRhmasTotalFacturar() != null) {
                if (payroll.getRhmasTotalFacturar().signum() == -1) {
                    row1.createCell(22).setCellValue(0.0);
                    sum11 += 0.0;
                } else {
                    row1.createCell(22).setCellValue(payroll.getRhmasTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum11 += payroll.getRhmasTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getPercepcion() != null) {
                if (payroll.getPercepcion().signum() == -1) {
                    row1.createCell(23).setCellValue(0.0);
                    sum12 += 0.0;
                } else {
                    row1.createCell(23).setCellValue(payroll.getPercepcion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum12 += payroll.getPercepcion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getDeduccion() != null) {
                if (payroll.getDeduccion().signum() == -1) {
                    row1.createCell(24).setCellValue(0.0);
                    sum13 += 0.0;
                } else {
                    row1.createCell(24).setCellValue(payroll.getDeduccion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum13 += payroll.getDeduccion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            if (payroll.getPago() != null) {
                if (payroll.getPago().signum() == -1) {
                    row1.createCell(25).setCellValue(0.0);
                    sum14 += 0.0;
                } else {
                    row1.createCell(25).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum14 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }

            aux1++;
        }

        row1 = hoja1.createRow(aux1 + 2);

        row1.createCell(11).setCellValue("TOTALES");
        row1.createCell(12).setCellValue(sum1);
        row1.createCell(13).setCellValue(sum2);
        row1.createCell(14).setCellValue(sum3);
        row1.createCell(15).setCellValue(sum4);
        row1.createCell(16).setCellValue(sum5);
        row1.createCell(17).setCellValue(sum6);
        row1.createCell(18).setCellValue(sum7);
        row1.createCell(19).setCellValue(sum8);
        row1.createCell(20).setCellValue(sum9);
        row1.createCell(21).setCellValue(sum10);
        row1.createCell(22).setCellValue(sum11);
        row1.createCell(23).setCellValue(sum12);
        row1.createCell(24).setCellValue(sum13);
        row1.createCell(25).setCellValue(sum14);

        Sheet hoja2 = wb.createSheet("RHMAS GMT E ");

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
        List<Outsourcing> outsourcingEList = outsourcingDao.findByType(1, applicationDateEnd, applicationDateEnd);

        int aux2 = 1;

        Double sum15 = 0.0;
        Double sum16 = 0.0;
        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;

        for (Outsourcing outsourcing : outsourcingEList) {
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum15 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum16 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum17 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum18 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum19 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum20 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum21 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum22 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum23 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum24 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum25 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum26 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum27 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum28 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum29 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum30 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2 + 2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum15);
        row2.createCell(4).setCellValue(sum16);
        row2.createCell(5).setCellValue(sum17);
        row2.createCell(6).setCellValue(sum18);
        row2.createCell(7).setCellValue(sum19);
        row2.createCell(8).setCellValue(sum20);
        row2.createCell(9).setCellValue(sum21);
        row2.createCell(10).setCellValue(sum22);
        row2.createCell(11).setCellValue(sum23);
        row2.createCell(12).setCellValue(sum24);
        row2.createCell(13).setCellValue(sum25);
        row2.createCell(14).setCellValue(sum26);
        row2.createCell(15).setCellValue(sum27);
        row2.createCell(16).setCellValue(sum28);
        row2.createCell(17).setCellValue(sum29);
        row2.createCell(18).setCellValue(sum30);

        Sheet hoja5 = wb.createSheet("RHMAS GMT C ");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("DEPARTAMENTO");
        row5.createCell(1).setCellValue("CODIGO");
        row5.createCell(2).setCellValue("NOMBRE");
        row5.createCell(3).setCellValue("SUELDO");
        row5.createCell(4).setCellValue("SUBSIDIO");
        row5.createCell(5).setCellValue("IMSS EMPLEADO");
        row5.createCell(6).setCellValue("ISR");
        row5.createCell(7).setCellValue("AJUSTE AL NETO");
        row5.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row5.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row5.createCell(10).setCellValue("IMSS");
        row5.createCell(11).setCellValue("RCV");
        row5.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row5.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row5.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row5.createCell(15).setCellValue("COMISIÓN");
        row5.createCell(16).setCellValue("SUBTOTAL");
        row5.createCell(17).setCellValue("IVA");
        row5.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        List<Outsourcing> outsourcingCList = outsourcingDao.findByType(2, applicationDateEnd, applicationDateEnd);

        int aux5 = 1;

        Double sum31 = 0.0;
        Double sum32 = 0.0;
        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;
        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;
        Double sum39 = 0.0;
        Double sum40 = 0.0;
        Double sum41 = 0.0;
        Double sum42 = 0.0;
        Double sum43 = 0.0;
        Double sum44 = 0.0;
        Double sum45 = 0.0;
        Double sum46 = 0.0;

        for (Outsourcing outsourcing : outsourcingCList) {
            row5 = hoja5.createRow(aux5);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row5.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row5.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row5.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row5.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum31 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row5.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum32 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row5.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum33 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row5.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum34 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row5.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum35 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row5.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum36 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row5.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum37 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row5.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum38 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row5.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum39 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row5.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum40 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row5.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum41 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row5.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum42 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row5.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum43 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row5.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum44 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row5.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum45 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row5.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum46 += outsourcing.getTotal().doubleValue();
            }

            aux5++;
        }

        row5 = hoja5.createRow(aux5 + 2);

        row5.createCell(2).setCellValue("TOTALES");
        row5.createCell(3).setCellValue(sum31);
        row5.createCell(4).setCellValue(sum32);
        row5.createCell(5).setCellValue(sum33);
        row5.createCell(6).setCellValue(sum34);
        row5.createCell(7).setCellValue(sum35);
        row5.createCell(8).setCellValue(sum36);
        row5.createCell(9).setCellValue(sum37);
        row5.createCell(10).setCellValue(sum38);
        row5.createCell(11).setCellValue(sum39);
        row5.createCell(12).setCellValue(sum40);
        row5.createCell(13).setCellValue(sum41);
        row5.createCell(14).setCellValue(sum42);
        row5.createCell(15).setCellValue(sum43);
        row5.createCell(16).setCellValue(sum44);
        row5.createCell(17).setCellValue(sum45);
        row5.createCell(18).setCellValue(sum46);

        Sheet hoja6 = wb.createSheet("RHMAS GMT BID ENERGY");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);

        row6.createCell(0).setCellValue("DEPARTAMENTO");
        row6.createCell(1).setCellValue("CODIGO");
        row6.createCell(2).setCellValue("NOMBRE");
        row6.createCell(3).setCellValue("SUELDO");
        row6.createCell(4).setCellValue("SUBSIDIO");
        row6.createCell(5).setCellValue("IMSS EMPLEADO");
        row6.createCell(6).setCellValue("ISR");
        row6.createCell(7).setCellValue("AJUSTE AL NETO");
        row6.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row6.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row6.createCell(10).setCellValue("IMSS");
        row6.createCell(11).setCellValue("RCV");
        row6.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row6.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row6.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row6.createCell(15).setCellValue("COMISIÓN");
        row6.createCell(16).setCellValue("SUBTOTAL");
        row6.createCell(17).setCellValue("IVA");
        row6.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        List<Outsourcing> outsourcingBIDList = outsourcingDao.findByType(3, applicationDateEnd, applicationDateEnd);

        int aux6 = 1;

        Double sum47 = 0.0;
        Double sum48 = 0.0;
        Double sum49 = 0.0;
        Double sum50 = 0.0;
        Double sum51 = 0.0;
        Double sum52 = 0.0;
        Double sum53 = 0.0;
        Double sum54 = 0.0;
        Double sum55 = 0.0;
        Double sum56 = 0.0;
        Double sum57 = 0.0;
        Double sum58 = 0.0;
        Double sum59 = 0.0;
        Double sum60 = 0.0;
        Double sum61 = 0.0;
        Double sum62 = 0.0;

        for (Outsourcing outsourcing : outsourcingBIDList) {
            row6 = hoja6.createRow(aux6);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row6.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row6.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row6.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row6.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum47 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row6.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum48 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row6.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum49 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row6.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum50 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row6.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum51 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row6.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum52 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row6.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum53 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row6.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum54 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row6.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum55 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row6.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum56 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row6.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum57 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row6.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum58 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row6.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum59 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row6.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum60 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row6.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum61 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row6.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum62 += outsourcing.getTotal().doubleValue();
            }

            aux6++;
        }

        row6 = hoja6.createRow(aux6 + 2);

        row6.createCell(2).setCellValue("TOTALES");
        row6.createCell(3).setCellValue(sum47);
        row6.createCell(4).setCellValue(sum48);
        row6.createCell(5).setCellValue(sum49);
        row6.createCell(6).setCellValue(sum50);
        row6.createCell(7).setCellValue(sum51);
        row6.createCell(8).setCellValue(sum52);
        row6.createCell(9).setCellValue(sum53);
        row6.createCell(10).setCellValue(sum54);
        row6.createCell(11).setCellValue(sum55);
        row6.createCell(12).setCellValue(sum56);
        row6.createCell(13).setCellValue(sum57);
        row6.createCell(14).setCellValue(sum58);
        row6.createCell(15).setCellValue(sum59);
        row6.createCell(16).setCellValue(sum60);
        row6.createCell(17).setCellValue(sum61);
        row6.createCell(18).setCellValue(sum62);

        Sheet hoja8 = wb.createSheet("RHMAS MVO");

        //Se crea la fila que contiene la cabecera
        Row row8 = hoja8.createRow(0);

        row8.createCell(0).setCellValue("DEPARTAMENTO");
        row8.createCell(1).setCellValue("CODIGO");
        row8.createCell(2).setCellValue("NOMBRE");
        row8.createCell(3).setCellValue("SUELDO");
        row8.createCell(4).setCellValue("SUBSIDIO");
        row8.createCell(5).setCellValue("IMSS EMPLEADO");
        row8.createCell(6).setCellValue("ISR");
        row8.createCell(7).setCellValue("AJUSTE AL NETO");
        row8.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row8.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row8.createCell(10).setCellValue("IMSS");
        row8.createCell(11).setCellValue("RCV");
        row8.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row8.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row8.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row8.createCell(15).setCellValue("COMISIÓN");
        row8.createCell(16).setCellValue("SUBTOTAL");
        row8.createCell(17).setCellValue("IVA");
        row8.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row8) {
            celda.setCellStyle(style);
        }

        List<Outsourcing> outsourcingMBOList = outsourcingDao.findByType(4, applicationDateEnd, applicationDateEnd);

        int aux8 = 1;

        Double sum69 = 0.0;
        Double sum70 = 0.0;
        Double sum71 = 0.0;
        Double sum72 = 0.0;
        Double sum73 = 0.0;
        Double sum74 = 0.0;
        Double sum75 = 0.0;
        Double sum76 = 0.0;
        Double sum77 = 0.0;
        Double sum78 = 0.0;
        Double sum79 = 0.0;
        Double sum80 = 0.0;
        Double sum81 = 0.0;
        Double sum82 = 0.0;
        Double sum83 = 0.0;
        Double sum84 = 0.0;

        for (Outsourcing outsourcing : outsourcingMBOList) {
            row8 = hoja8.createRow(aux8);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row8.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row8.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row8.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row8.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum69 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row8.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum70 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row8.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum71 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row8.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum72 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row8.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum73 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row8.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum74 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row8.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum75 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row8.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum76 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row8.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum77 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row8.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum78 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row8.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum79 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row6.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum80 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row8.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum81 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row8.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum82 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row8.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum83 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row8.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum84 += outsourcing.getTotal().doubleValue();
            }

            aux8++;
        }

        row8 = hoja8.createRow(aux8 + 2);

        row8.createCell(2).setCellValue("TOTALES");
        row8.createCell(3).setCellValue(sum69);
        row8.createCell(4).setCellValue(sum70);
        row8.createCell(5).setCellValue(sum71);
        row8.createCell(6).setCellValue(sum72);
        row8.createCell(7).setCellValue(sum73);
        row8.createCell(8).setCellValue(sum74);
        row8.createCell(9).setCellValue(sum75);
        row8.createCell(10).setCellValue(sum76);
        row8.createCell(11).setCellValue(sum77);
        row8.createCell(12).setCellValue(sum78);
        row8.createCell(13).setCellValue(sum79);
        row8.createCell(14).setCellValue(sum80);
        row8.createCell(15).setCellValue(sum81);
        row8.createCell(16).setCellValue(sum82);
        row8.createCell(17).setCellValue(sum83);
        row8.createCell(18).setCellValue(sum84);

        Sheet hoja3 = wb.createSheet("GMT NEC");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(9);

        row3.createCell(0).setCellValue("N");
        row3.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row3.createCell(2).setCellValue("TIPO DE SOCIO");
        row3.createCell(3).setCellValue("PUESTO");
        row3.createCell(4).setCellValue("BANCO");
        row3.createCell(5).setCellValue("N. DE CUENTA");
        row3.createCell(6).setCellValue("CLABE");
        row3.createCell(7).setCellValue("SUCURSAL");
        row3.createCell(8).setCellValue("RFC");
        row3.createCell(9).setCellValue("CURP");
        row3.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row3.createCell(11).setCellValue("11.50%");
        row3.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        int aux3 = 10;

        List<Integer> idsEmployeesE = outsourcingDao.getExtranjerosOnly();
        List<Payroll> payrolls = payrollDao.findAllByAmountPositivesNotExtranjeros(idsEmployeesE);

        Double sum63 = 0.0;
        Double sum64 = 0.0;
        Double sum65 = 0.0;

        for (Payroll payroll : payrolls) {
            row3 = hoja3.createRow(aux3);

            if (payroll.getNumeroDeEmpleado() != 3220) {
                if (payroll.getNumeroDeEmpleado() != null) {
                    row3.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
                }
                if (payroll.getNombre() != null) {
                    row3.createCell(1).setCellValue(payroll.getNombre());
                }
                row3.createCell(2).setCellValue("AS");
                if (payroll.getPuesto() != null) {
                    row3.createCell(3).setCellValue(payroll.getPuesto());
                }
                if (payroll.getBanco() != null) {
                    row3.createCell(4).setCellValue(payroll.getBanco());
                }
                if (payroll.getNumeroDeCuenta() != null) {
                    row3.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
                }
                if (payroll.getCuentaClabe() != null) {
                    row3.createCell(6).setCellValue(payroll.getCuentaClabe());
                }
                if (payroll.getSucursal() != null) {
                    row3.createCell(7).setCellValue(payroll.getSucursal());
                }
                if (payroll.getRfc() != null) {
                    row3.createCell(8).setCellValue(payroll.getRfc());
                }
                if (payroll.getCurp() != null) {
                    row3.createCell(9).setCellValue(payroll.getCurp());
                }
                if (payroll.getPago() != null) {
                    row3.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum63 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                if (payroll.getComisionNec() != null) {
                    row3.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum64 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                if (payroll.getTotalFacturar() != null) {
                    row3.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum65 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }

                aux3++;
            }
        }

        row3 = hoja3.createRow(aux3 + 2);

        row3.createCell(9).setCellValue("TOTALES");
        row3.createCell(10).setCellValue(sum63);
        row3.createCell(11).setCellValue(sum64);
        row3.createCell(12).setCellValue(sum65);

        Sheet hoja4 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        BigDecimal gmtNec = (BigDecimal) payrollDao.sumGmtNecNotExtranjeros(idsEmployeesE);
        BigDecimal efectivoEdmon = (BigDecimal) payrollDao.sumEfectivoEdmon();
        BigDecimal rhmasE = (BigDecimal) outsourcingDao.sumRhmasByDwEnterpriseAndType(dwEnterprisesList, 1, applicationDateEnd, applicationDateEnd);
        BigDecimal rhmasC = (BigDecimal) outsourcingDao.sumRhmasByDwEnterpriseAndType(dwEnterprisesList, 2, applicationDateEnd, applicationDateEnd);
        BigDecimal rhmasBIDE = (BigDecimal) outsourcingDao.sumRhmasByDwEnterpriseAndType(dwEnterprisesList, 3, applicationDateEnd, applicationDateEnd);
        BigDecimal rhmasMBO = (BigDecimal) outsourcingDao.sumRhmasByDwEnterpriseAndType(dwEnterprisesList, 4, applicationDateEnd, applicationDateEnd);
        BigDecimal grupoBaal = (BigDecimal) outsourcingDao.sumRhmasByDwEnterpriseAndType(dwEnterprisesList, 5, applicationDateEnd, applicationDateEnd);
        
        Double rhmasAsimilados = 0.00;
        if(!idsEmployeesE.isEmpty()){
            for(Integer idEmployee : idsEmployeesE){
                if (idEmployee != 2580) {
                EmployeesHistory eH = employeesHistoryDao.findByIdEmployeeAndLastRegister(idEmployee);
                if(eH.getSalary() != null){
                    Outsourcing o = outsourcingDao.finfByidEmployee(idEmployee, applicationDateEnd);
                    BigDecimal salary = eH.getSalary().divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal totalAmount = salary.subtract(o.getNetAssetTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal percentage = new BigDecimal(0.1);
                    BigDecimal commission = totalAmount.multiply(percentage).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal total = totalAmount.add(commission);
                    
                    rhmasAsimilados += total.doubleValue();
                }
                }
            }
        }
        EmployeesHistory backOfficeT = employeesHistoryDao.findByIdEmployeeAndLastRegister(3220);
        Double totalDeTotales = 0.00;
        if (gmtNec != null) {
            totalDeTotales += gmtNec.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowGmt = hoja4.createRow(3);

            rowGmt.createCell(1).setCellValue("GMT NEC");
            rowGmt.createCell(2).setCellValue(gmtNec.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (efectivoEdmon != null) {
            totalDeTotales += efectivoEdmon.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowEfectivoEdmon = hoja4.createRow(9);

            rowEfectivoEdmon.createCell(1).setCellValue("EFECTIVO EDMON");
            rowEfectivoEdmon.createCell(2).setCellValue(efectivoEdmon.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasE != null) {
            totalDeTotales += rhmasE.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmas = hoja4.createRow(4);

            rowRhmas.createCell(1).setCellValue("RHMAS GMT E");
            rowRhmas.createCell(2).setCellValue(rhmasE.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasC != null) {
            totalDeTotales += rhmasC.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmas = hoja4.createRow(5);

            rowRhmas.createCell(1).setCellValue("RHMAS GMT C");
            rowRhmas.createCell(2).setCellValue(rhmasC.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasBIDE != null) {
            totalDeTotales += rhmasBIDE.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmas = hoja4.createRow(6);

            rowRhmas.createCell(1).setCellValue("RHMAS GMT BID ENERGY");
            rowRhmas.createCell(2).setCellValue(rhmasBIDE.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasMBO != null) {
            totalDeTotales += rhmasMBO.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmas = hoja4.createRow(7);

            rowRhmas.createCell(1).setCellValue("RHMAS MVO");
            rowRhmas.createCell(2).setCellValue(rhmasMBO.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (grupoBaal != null) {
            totalDeTotales += grupoBaal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmas = hoja4.createRow(8);

            rowRhmas.createCell(1).setCellValue("NOMINA IMSS EXTRANJEROS");
            rowRhmas.createCell(2).setCellValue(grupoBaal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (backOfficeT != null && rhmasAsimilados != null) {
            if (backOfficeT.getSalary() != null) {
                BigDecimal salary = backOfficeT.getSalary().divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal percentage = new BigDecimal(0.1);
                BigDecimal commission = salary.multiply(percentage).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal total = salary.add(commission);
                
                BigDecimal sumRhMasA = total.add(new BigDecimal(rhmasAsimilados));

                totalDeTotales += sumRhMasA.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                Row rowBackOffice = hoja4.createRow(10);

                rowBackOffice.createCell(1).setCellValue("RHMAS ASIMILABLE");
                rowBackOffice.createCell(2).setCellValue(sumRhMasA.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }

        Row rowTotal = hoja4.createRow(11);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        Sheet hoja7 = wb.createSheet("RHMAS ASIMILABLE");

        //Se crea la fila que contiene la cabecera
        Row row7 = hoja7.createRow(0);

        row7.createCell(0).setCellValue("N");
        row7.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row7.createCell(2).setCellValue("TIPO DE SOCIO");
        row7.createCell(3).setCellValue("PUESTO");
        row7.createCell(4).setCellValue("BANCO");
        row7.createCell(5).setCellValue("N. DE CUENTA");
        row7.createCell(6).setCellValue("CLABE");
        row7.createCell(7).setCellValue("SUCURSAL");
        row7.createCell(8).setCellValue("RFC");
        row7.createCell(9).setCellValue("CURP");
        row7.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row7.createCell(11).setCellValue("10.00%");
        row7.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row7) {
            celda.setCellStyle(style);
        }

        int aux7 = 1;

        EmployeesHistory backOffice = employeesHistoryDao.findByIdEmployeeAndLastRegister(3220);

        List<Integer> idsEmployeeExtranjeros = outsourcingDao.getExtranjerosOnly();

        Double sum66 = 0.0;
        Double sum67 = 0.0;
        Double sum68 = 0.0;

        row7 = hoja7.createRow(aux7);

        if (backOffice.getIdEmployee() != null) {
            row7.createCell(0).setCellValue(backOffice.getIdEmployee());
        }
        if (backOffice.getFullName() != null) {
            row7.createCell(1).setCellValue(backOffice.getFullName());
        }
        row7.createCell(2).setCellValue("AS");
        CRoles role = cRolesDao.findByIdRole(backOffice.getIdRole());
        if (role != null) {
            row7.createCell(3).setCellValue(role.getRoleName());
        }
        Accounts accounts = accountsDao.findById(backOffice.getIdAccount());
        if (accounts != null) {
            if (accounts.getBank() != null) {
                row7.createCell(4).setCellValue(accounts.getBank().getBankName());
            }
            if (accounts.getAccountNumber() != null) {
                row7.createCell(5).setCellValue(accounts.getAccountNumber());
            }
            if (accounts.getAccountClabe() != null) {
                row7.createCell(6).setCellValue(accounts.getAccountClabe());
            }
        }
        CBranchs cBranchs = cBranchsDao.findById(backOffice.getIdBranch());
        if (cBranchs != null) {
            if (cBranchs.getBranchShort() != null) {
                row7.createCell(7).setCellValue(cBranchs.getBranchShort());
            }
        }
        if (backOffice.getRfc() != null) {
            row7.createCell(8).setCellValue(backOffice.getRfc());
        }
        if (backOffice.getCurp() != null) {
            row7.createCell(9).setCellValue(backOffice.getCurp());
        }
        if (backOffice.getSalary() != null) {
            BigDecimal salary = backOffice.getSalary().divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);

            row7.createCell(10).setCellValue(salary.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            sum66 += salary.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            BigDecimal percentage = new BigDecimal(0.1);
            BigDecimal commission = salary.multiply(percentage).setScale(2, BigDecimal.ROUND_HALF_UP);

            row7.createCell(11).setCellValue(commission.doubleValue());
            sum67 += commission.doubleValue();

            BigDecimal total = salary.add(commission);

            row7.createCell(12).setCellValue(total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            sum68 += total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        if (!idsEmployeeExtranjeros.isEmpty()) {
            aux7 = 2;
            for (Integer idEmployee : idsEmployeeExtranjeros) {
                if (idEmployee != 2580) {
                    row7 = hoja7.createRow(aux7);
                    
                    EmployeesHistory eH = employeesHistoryDao.findByIdEmployeeAndLastRegister(idEmployee);

                    if (eH.getIdEmployee() != null) {
                        row7.createCell(0).setCellValue(eH.getIdEmployee());
                    }
                    if (eH.getFullName() != null) {
                        row7.createCell(1).setCellValue(eH.getFullName());
                    }
                    row7.createCell(2).setCellValue("AS");
                    CRoles roleE = cRolesDao.findByIdRole(eH.getIdRole());
                    if (roleE != null) {
                        row7.createCell(3).setCellValue(roleE.getRoleName());
                    }
                    Accounts accountsE = accountsDao.findById(eH.getIdAccount());
                    if (accountsE != null) {
                        if (accountsE.getBank() != null) {
                            row7.createCell(4).setCellValue(accountsE.getBank().getBankName());
                        }
                        if (accountsE.getAccountNumber() != null) {
                            row7.createCell(5).setCellValue(accountsE.getAccountNumber());
                        }
                        if (accountsE.getAccountClabe() != null) {
                            row7.createCell(6).setCellValue(accountsE.getAccountClabe());
                        }
                    }
                    CBranchs cBranchsE = cBranchsDao.findById(eH.getIdBranch());
                    if (cBranchsE != null) {
                        if (cBranchsE.getBranchShort() != null) {
                            row7.createCell(7).setCellValue(cBranchsE.getBranchShort());
                        }
                    }
                    if (eH.getRfc() != null) {
                        row7.createCell(8).setCellValue(eH.getRfc());
                    }
                    if (eH.getCurp() != null) {
                        row7.createCell(9).setCellValue(eH.getCurp());
                    }
                    if (eH.getSalary() != null) {
                        BigDecimal salary = eH.getSalary().divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
                        
                        Outsourcing outsourcing = outsourcingDao.finfByidEmployee(idEmployee, applicationDateEnd);
                        
                        BigDecimal totalAmount = salary.subtract(outsourcing.getNetAssetTax());

                        row7.createCell(10).setCellValue(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        sum66 += totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                        BigDecimal percentage = new BigDecimal(0.1);
                        BigDecimal commission = totalAmount.multiply(percentage).setScale(2, BigDecimal.ROUND_HALF_UP);

                        row7.createCell(11).setCellValue(commission.doubleValue());
                        sum67 += commission.doubleValue();

                        BigDecimal total = totalAmount.add(commission);

                        row7.createCell(12).setCellValue(total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        sum68 += total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    
                    aux7++;
                }
            }
        }

        row7 = hoja7.createRow(aux7 + 2);

        row7.createCell(9).setCellValue("TOTALES");
        row7.createCell(10).setCellValue(sum66);
        row7.createCell(11).setCellValue(sum67);
        row7.createCell(12).setCellValue(sum68);

        Sheet hoja9 = wb.createSheet("NOMINA IMSS EXTRANJEROS");

        //Se crea la fila que contiene la cabecera
        Row row9 = hoja9.createRow(0);

        row9.createCell(0).setCellValue("DEPARTAMENTO");
        row9.createCell(1).setCellValue("CODIGO");
        row9.createCell(2).setCellValue("NOMBRE");
        row9.createCell(3).setCellValue("SUELDO");
        row9.createCell(4).setCellValue("SUBSIDIO");
        row9.createCell(5).setCellValue("IMSS EMPLEADO");
        row9.createCell(6).setCellValue("ISR");
        row9.createCell(7).setCellValue("AJUSTE AL NETO");
        row9.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row9.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row9.createCell(10).setCellValue("IMSS");
        row9.createCell(11).setCellValue("RCV");
        row9.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row9.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row9.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row9.createCell(15).setCellValue("COMISIÓN");
        row9.createCell(16).setCellValue("SUBTOTAL");
        row9.createCell(17).setCellValue("IVA");
        row9.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row9) {
            celda.setCellStyle(style);
        }

        List<Outsourcing> outsourcingGrupoBaalList = outsourcingDao.findByType(5, applicationDateEnd, applicationDateEnd);

        int aux9 = 1;

        Double sum85 = 0.0;
        Double sum86 = 0.0;
        Double sum87 = 0.0;
        Double sum88 = 0.0;
        Double sum89 = 0.0;
        Double sum90 = 0.0;
        Double sum91 = 0.0;
        Double sum92 = 0.0;
        Double sum93 = 0.0;
        Double sum94 = 0.0;
        Double sum95 = 0.0;
        Double sum96 = 0.0;
        Double sum97 = 0.0;
        Double sum98 = 0.0;
        Double sum99 = 0.0;
        Double sum100 = 0.0;

        for (Outsourcing outsourcing : outsourcingGrupoBaalList) {
            row9 = hoja9.createRow(aux9);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row9.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row9.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row9.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row9.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum85 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row9.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum86 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row9.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum87 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row9.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum88 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row9.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum89 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row9.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum90 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row9.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum91 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row9.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum92 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row9.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum93 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row9.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum94 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row9.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum95 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row9.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum96 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row9.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum97 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row9.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum98 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row9.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum99 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row9.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum100 += outsourcing.getTotal().doubleValue();
            }

            aux9++;
        }

        row9 = hoja9.createRow(aux9 + 2);

        row9.createCell(2).setCellValue("TOTALES");
        row9.createCell(3).setCellValue(sum85);
        row9.createCell(4).setCellValue(sum86);
        row9.createCell(5).setCellValue(sum87);
        row9.createCell(6).setCellValue(sum88);
        row9.createCell(7).setCellValue(sum89);
        row9.createCell(8).setCellValue(sum90);
        row9.createCell(9).setCellValue(sum91);
        row9.createCell(10).setCellValue(sum92);
        row9.createCell(11).setCellValue(sum93);
        row9.createCell(12).setCellValue(sum94);
        row9.createCell(13).setCellValue(sum95);
        row9.createCell(14).setCellValue(sum96);
        row9.createCell(15).setCellValue(sum97);
        row9.createCell(16).setCellValue(sum98);
        row9.createCell(17).setCellValue(sum99);
        row9.createCell(18).setCellValue(sum100);

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

        for (Payroll payroll : payrollList) {
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null) {
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null) {
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null) {
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null) {
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null) {
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null) {
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null) {
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if (payroll.getFechaIngreso() != null) {
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getSueldo() != null) {
                if (payroll.getSueldo().signum() == -1) {
                    row1.createCell(14).setCellValue(0.0);
                } else {
                    row1.createCell(14).setCellValue(payroll.getSueldo().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getPrimaVacacional() != null) {
                if (payroll.getPrimaVacacional().signum() == -1) {
                    row1.createCell(15).setCellValue(0.0);
                } else {
                    row1.createCell(15).setCellValue(payroll.getPrimaVacacional().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getBono() != null) {
                if (payroll.getBono().signum() == -1) {
                    row1.createCell(16).setCellValue(0.0);
                } else {
                    row1.createCell(16).setCellValue(payroll.getBono().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getAjuste() != null) {
                if (payroll.getAjuste().signum() == -1) {
                    row1.createCell(17).setCellValue(0.0);
                } else {
                    row1.createCell(17).setCellValue(payroll.getAjuste().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getRhmasPago() != null) {
                if (payroll.getRhmasPago().signum() == -1) {
                    row1.createCell(18).setCellValue(0.0);
                } else {
                    row1.createCell(18).setCellValue(payroll.getRhmasPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getRhmasTotalFacturar() != null) {
                if (payroll.getRhmasTotalFacturar().signum() == -1) {
                    row1.createCell(19).setCellValue(0.0);
                } else {
                    row1.createCell(19).setCellValue(payroll.getRhmasTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getDescuento() != null) {
                if (payroll.getDescuento().signum() == -1) {
                    row1.createCell(20).setCellValue(0.0);
                } else {
                    row1.createCell(20).setCellValue(payroll.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getPercepcion() != null) {
                if (payroll.getPercepcion().signum() == -1) {
                    row1.createCell(21).setCellValue(0.0);
                } else {
                    row1.createCell(21).setCellValue(payroll.getPercepcion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getDeduccion() != null) {
                if (payroll.getDeduccion().signum() == -1) {
                    row1.createCell(22).setCellValue(0.0);
                } else {
                    row1.createCell(22).setCellValue(payroll.getDeduccion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getPago() != null) {
                if (payroll.getPago().signum() == -1) {
                    row1.createCell(23).setCellValue(0.0);
                } else {
                    row1.createCell(23).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
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
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList, applicationDateEnd, applicationDateEnd);

        int aux2 = 1;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;
        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;
        Double sum7 = 0.0;
        Double sum8 = 0.0;
        Double sum9 = 0.0;
        Double sum10 = 0.0;
        Double sum11 = 0.0;
        Double sum12 = 0.0;
        Double sum13 = 0.0;
        Double sum14 = 0.0;
        Double sum15 = 0.0;
        Double sum16 = 0.0;

        for (Outsourcing outsourcing : outsourcingList) {
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum1 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum2 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum3 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum4 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum5 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum6 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum7 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum8 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum9 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum10 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum11 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum12 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum13 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum14 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum15 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum16 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2 + 2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum1);
        row2.createCell(4).setCellValue(sum2);
        row2.createCell(5).setCellValue(sum3);
        row2.createCell(6).setCellValue(sum4);
        row2.createCell(7).setCellValue(sum5);
        row2.createCell(8).setCellValue(sum6);
        row2.createCell(9).setCellValue(sum7);
        row2.createCell(10).setCellValue(sum8);
        row2.createCell(11).setCellValue(sum9);
        row2.createCell(12).setCellValue(sum10);
        row2.createCell(13).setCellValue(sum11);
        row2.createCell(14).setCellValue(sum12);
        row2.createCell(15).setCellValue(sum13);
        row2.createCell(16).setCellValue(sum14);
        row2.createCell(17).setCellValue(sum15);
        row2.createCell(18).setCellValue(sum16);

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
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList, applicationDateEnd, applicationDateEnd);

        int aux3 = 1;

        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;
        Double sum31 = 0.0;
        Double sum32 = 0.0;

        for (Outsourcing outsourcing : outsourcings) {
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum17 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum18 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum19 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum20 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum21 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum22 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum23 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum24 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum25 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum26 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum27 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum28 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum29 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum30 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum31 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum32 += outsourcing.getTotal().doubleValue();
            }

            aux3++;
        }

        row3 = hoja3.createRow(aux3 + 2);

        row3.createCell(2).setCellValue("TOTALES");
        row3.createCell(3).setCellValue(sum17);
        row3.createCell(4).setCellValue(sum18);
        row3.createCell(5).setCellValue(sum19);
        row3.createCell(6).setCellValue(sum20);
        row3.createCell(7).setCellValue(sum21);
        row3.createCell(8).setCellValue(sum22);
        row3.createCell(9).setCellValue(sum23);
        row3.createCell(10).setCellValue(sum24);
        row3.createCell(11).setCellValue(sum25);
        row3.createCell(12).setCellValue(sum26);
        row3.createCell(13).setCellValue(sum27);
        row3.createCell(14).setCellValue(sum28);
        row3.createCell(15).setCellValue(sum29);
        row3.createCell(16).setCellValue(sum30);
        row3.createCell(17).setCellValue(sum31);
        row3.createCell(18).setCellValue(sum32);

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(9);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("TIPO DE SOCIO");
        row4.createCell(3).setCellValue("PUESTO");
        row4.createCell(4).setCellValue("BANCO");
        row4.createCell(5).setCellValue("N. DE CUENTA");
        row4.createCell(6).setCellValue("CLABE");
        row4.createCell(7).setCellValue("SUCURSAL");
        row4.createCell(8).setCellValue("RFC");
        row4.createCell(9).setCellValue("CURP");
        row4.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row4.createCell(11).setCellValue("11.50%");
        row4.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 10;

        List<Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;

        for (Payroll payroll : amerNec) {
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null) {
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            row4.createCell(2).setCellValue("AS");
            if (payroll.getPuesto() != null) {
                row4.createCell(3).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row4.createCell(4).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row4.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row4.createCell(6).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row4.createCell(7).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row4.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row4.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row4.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum33 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row4.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum34 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row4.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum35 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux4++;

        }

        row4 = hoja4.createRow(aux4 + 2);

        row4.createCell(9).setCellValue("TOTALES");
        row4.createCell(10).setCellValue(sum33);
        row4.createCell(11).setCellValue(sum34);
        row4.createCell(12).setCellValue(sum35);

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(9);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("TIPO DE SOCIO");
        row5.createCell(3).setCellValue("PUESTO");
        row5.createCell(4).setCellValue("BANCO");
        row5.createCell(5).setCellValue("N. DE CUENTA");
        row5.createCell(6).setCellValue("CLABE");
        row5.createCell(7).setCellValue("SUCURSAL");
        row5.createCell(8).setCellValue("RFC");
        row5.createCell(9).setCellValue("CURP");
        row5.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row5.createCell(11).setCellValue("11.50%");
        row5.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 10;

        List<Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;

        for (Payroll payroll : amermediaNec) {
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null) {
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            row5.createCell(2).setCellValue("AS");
            if (payroll.getPuesto() != null) {
                row5.createCell(3).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row5.createCell(4).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row5.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row5.createCell(6).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row5.createCell(7).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row5.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row5.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row5.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum36 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row5.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum37 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row5.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum38 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux5++;

        }

        row5 = hoja5.createRow(aux5 + 2);

        row5.createCell(9).setCellValue("TOTALES");
        row5.createCell(10).setCellValue(sum36);
        row5.createCell(11).setCellValue(sum37);
        row5.createCell(12).setCellValue(sum38);

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList, applicationDateEnd, applicationDateEnd);
        BigDecimal rhmasAmermedia = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList, applicationDateEnd, applicationDateEnd);

        Double totalDeTotales = 0.00;

        if (amerNecSum != null) {
            totalDeTotales += amerNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (amermediaNecSum != null) {
            totalDeTotales += amermediaNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasAmer != null) {
            totalDeTotales += rhmasAmer.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasAmermedia != null) {
            totalDeTotales += rhmasAmermedia.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
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
    public void reportWeeklyPay(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream, List queryResult) throws IOException {
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

        for (Payroll payroll : payrollList) {
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null) {
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null) {
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null) {
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null) {
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null) {
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null) {
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null) {
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if (payroll.getFechaIngreso() != null) {
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getComissionPromotor() != null) {
                if (payroll.getComissionPromotor().signum() == -1) {
                    row1.createCell(14).setCellValue(0.0);
                } else {
                    row1.createCell(14).setCellValue(payroll.getApoyo375().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getMontoPromotor() != null) {
                if (payroll.getMontoPromotor().signum() == -1) {
                    row1.createCell(15).setCellValue(0.0);
                } else {
                    row1.createCell(15).setCellValue(payroll.getMontoPromotor().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getComissionPromotor() != null) {
                if (payroll.getComissionPromotor().signum() == -1) {
                    row1.createCell(16).setCellValue(0.0);
                } else {
                    row1.createCell(16).setCellValue(payroll.getComissionPromotor().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getBono() != null) {
                if (payroll.getBono().signum() == -1) {
                    row1.createCell(17).setCellValue(0.0);
                } else {
                    row1.createCell(17).setCellValue(payroll.getBono().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getAjuste() != null) {
                if (payroll.getAjuste().signum() == -1) {
                    row1.createCell(18).setCellValue(0.0);
                } else {
                    row1.createCell(18).setCellValue(payroll.getAjuste().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getApoyoPasajes() != null) {
                if (payroll.getApoyoPasajes().signum() == -1) {
                    row1.createCell(19).setCellValue(0.0);
                } else {
                    row1.createCell(19).setCellValue(payroll.getApoyoPasajes().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getDescuento() != null) {
                if (payroll.getDescuento().signum() == -1) {
                    row1.createCell(20).setCellValue(0.0);
                } else {
                    row1.createCell(20).setCellValue(payroll.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getRhmasPago() != null) {
                if (payroll.getRhmasPago().signum() == -1) {
                    row1.createCell(21).setCellValue(0.0);
                } else {
                    row1.createCell(21).setCellValue(payroll.getRhmasPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getRhmasTotalFacturar() != null) {
                if (payroll.getRhmasTotalFacturar().signum() == -1) {
                    row1.createCell(22).setCellValue(0.0);
                } else {
                    row1.createCell(22).setCellValue(payroll.getRhmasTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getPercepcion() != null) {
                if (payroll.getPercepcion().signum() == -1) {
                    row1.createCell(23).setCellValue(0.0);
                } else {
                    row1.createCell(23).setCellValue(payroll.getPercepcion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getDeduccion() != null) {
                if (payroll.getDeduccion().signum() == -1) {
                    row1.createCell(24).setCellValue(0.0);
                } else {
                    row1.createCell(24).setCellValue(payroll.getDeduccion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getPago() != null) {
                if (payroll.getPago().signum() == -1) {
                    row1.createCell(25).setCellValue(0.0);
                } else {
                    row1.createCell(25).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
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
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList, applicationDateEnd, applicationDateEnd);

        int aux2 = 1;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;
        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;
        Double sum7 = 0.0;
        Double sum8 = 0.0;
        Double sum9 = 0.0;
        Double sum10 = 0.0;
        Double sum11 = 0.0;
        Double sum12 = 0.0;
        Double sum13 = 0.0;
        Double sum14 = 0.0;
        Double sum15 = 0.0;
        Double sum16 = 0.0;

        for (Outsourcing outsourcing : outsourcingList) {
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum1 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum2 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum3 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum4 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum5 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum6 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum7 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum8 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum9 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum10 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum11 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum12 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum13 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum14 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum15 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum16 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2 + 2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum1);
        row2.createCell(4).setCellValue(sum2);
        row2.createCell(5).setCellValue(sum3);
        row2.createCell(6).setCellValue(sum4);
        row2.createCell(7).setCellValue(sum5);
        row2.createCell(8).setCellValue(sum6);
        row2.createCell(9).setCellValue(sum7);
        row2.createCell(10).setCellValue(sum8);
        row2.createCell(11).setCellValue(sum9);
        row2.createCell(12).setCellValue(sum10);
        row2.createCell(13).setCellValue(sum11);
        row2.createCell(14).setCellValue(sum12);
        row2.createCell(15).setCellValue(sum13);
        row2.createCell(16).setCellValue(sum14);
        row2.createCell(17).setCellValue(sum15);
        row2.createCell(18).setCellValue(sum16);

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
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList, applicationDateEnd, applicationDateEnd);

        int aux3 = 1;

        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;
        Double sum31 = 0.0;
        Double sum32 = 0.0;

        for (Outsourcing outsourcing : outsourcings) {
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null) {
                if (outsourcing.getDwEnterprises().getArea() != null) {
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if (outsourcing.getEmployee() != null) {
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if (outsourcing.getEmployee() != null) {
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if (outsourcing.getSalary() != null) {
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum17 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getSubsidy() != null) {
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum18 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null) {
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum19 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null) {
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum20 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null) {
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum21 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null) {
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum22 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null) {
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum23 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null) {
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum24 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null) {
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum25 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null) {
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum26 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null) {
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum27 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null) {
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum28 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null) {
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum29 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null) {
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum30 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null) {
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum31 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null) {
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum32 += outsourcing.getTotal().doubleValue();
            }

            aux3++;
        }

        row3 = hoja3.createRow(aux3 + 2);

        row3.createCell(2).setCellValue("TOTALES");
        row3.createCell(3).setCellValue(sum17);
        row3.createCell(4).setCellValue(sum18);
        row3.createCell(5).setCellValue(sum19);
        row3.createCell(6).setCellValue(sum20);
        row3.createCell(7).setCellValue(sum21);
        row3.createCell(8).setCellValue(sum22);
        row3.createCell(9).setCellValue(sum23);
        row3.createCell(10).setCellValue(sum24);
        row3.createCell(11).setCellValue(sum25);
        row3.createCell(12).setCellValue(sum26);
        row3.createCell(13).setCellValue(sum27);
        row3.createCell(14).setCellValue(sum28);
        row3.createCell(15).setCellValue(sum29);
        row3.createCell(16).setCellValue(sum30);
        row3.createCell(17).setCellValue(sum31);
        row3.createCell(18).setCellValue(sum32);

        Sheet hoja4 = wb.createSheet("AMER ENZO");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(9);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("TIPO DE SOCIO");
        row4.createCell(3).setCellValue("PUESTO");
        row4.createCell(4).setCellValue("BANCO");
        row4.createCell(5).setCellValue("N. DE CUENTA");
        row4.createCell(6).setCellValue("CLABE");
        row4.createCell(7).setCellValue("SUCURSAL");
        row4.createCell(8).setCellValue("RFC");
        row4.createCell(9).setCellValue("CURP");
        row4.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row4.createCell(11).setCellValue("11.50%");
        row4.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 10;

        List<Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;

        for (Payroll payroll : amerNec) {
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null) {
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            row4.createCell(2).setCellValue("AS");
            if (payroll.getPuesto() != null) {
                row4.createCell(3).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row4.createCell(4).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row4.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row4.createCell(6).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row4.createCell(7).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row4.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row4.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row4.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum33 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row4.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum34 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row4.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum35 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux4++;

        }

        row4 = hoja4.createRow(aux4 + 2);

        row4.createCell(9).setCellValue("TOTALES");
        row4.createCell(10).setCellValue(sum33);
        row4.createCell(11).setCellValue(sum34);
        row4.createCell(12).setCellValue(sum35);

        Sheet hoja5 = wb.createSheet("AMERMEDIA ENZO");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(9);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("TIPO DE SOCIO");
        row5.createCell(3).setCellValue("PUESTO");
        row5.createCell(4).setCellValue("BANCO");
        row5.createCell(5).setCellValue("N. DE CUENTA");
        row5.createCell(6).setCellValue("CLABE");
        row5.createCell(7).setCellValue("SUCURSAL");
        row5.createCell(8).setCellValue("RFC");
        row5.createCell(9).setCellValue("CURP");
        row5.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row5.createCell(11).setCellValue("11.50%");
        row5.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 10;

        List<Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;

        for (Payroll payroll : amermediaNec) {
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null) {
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            row5.createCell(2).setCellValue("AS");
            if (payroll.getPuesto() != null) {
                row5.createCell(3).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row5.createCell(4).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row5.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row5.createCell(6).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row5.createCell(7).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row5.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row5.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row5.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum36 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row5.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum37 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row5.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum38 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux5++;

        }

        row5 = hoja5.createRow(aux5 + 2);

        row5.createCell(9).setCellValue("TOTALES");
        row5.createCell(10).setCellValue(sum36);
        row5.createCell(11).setCellValue(sum37);
        row5.createCell(12).setCellValue(sum38);

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList, applicationDateEnd, applicationDateEnd);
        BigDecimal rhmasAmermedia = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList, applicationDateEnd, applicationDateEnd);

        Double totalDeTotales = 0.00;

        if (amerNecSum != null) {
            totalDeTotales += amerNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER ENZO");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (amermediaNecSum != null) {
            totalDeTotales += amermediaNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA ENZO");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasAmer != null) {
            totalDeTotales += rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null) {
            totalDeTotales += rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }

        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        List<Object[]> results = queryResult;
        List<Object[]> amerResults = new ArrayList<>();
        List<Object[]> amermediaResults = new ArrayList<>();

        for (Object[] o : results) {
            if (o[3] != null) {
                if (Integer.parseInt(String.valueOf(o[3])) == 2) {
                    amerResults.add(o);
                } else if (Integer.parseInt(String.valueOf(o[3])) == 3) {
                    amermediaResults.add(o);
                }
            }
        }

        Sheet hoja7 = wb.createSheet("CUADRO ADMON AMER");

        //Se crea la fila que contiene la cabecera
        Row row7 = hoja7.createRow(0);

        row7.createCell(0).setCellValue("CONCEPTO");
        row7.createCell(1).setCellValue("VENTAS");
        row7.createCell(2).setCellValue("% CONTRA VENTA");

        //Implementacion del estilo
        for (Cell celda : row7) {
            celda.setCellStyle(style);
        }

        int aux7 = 1;

        Double sum100 = 0.00;
        Double sum200 = 0.00;

        for (Object[] admonReport : amerResults) {
            row7 = hoja7.createRow(aux7);

            if (admonReport[3] != null) {
                if (Integer.parseInt(String.valueOf(admonReport[3])) == 2) {
                    if (admonReport[0] != null) {
                        row7.createCell(0).setCellValue(String.valueOf(admonReport[0]));
                    }
                    if (admonReport[1] != null) {
                        row7.createCell(1).setCellValue(Double.parseDouble(String.valueOf(admonReport[1])));
                        sum100 += Double.parseDouble(String.valueOf(admonReport[1]));
                    }
                    if (admonReport[2] != null) {
                        String cadena = String.valueOf(admonReport[2]);
                        if (!cadena.isEmpty()) {
                            row7.createCell(2).setCellValue(Double.parseDouble(cadena));
                            sum200 += Double.parseDouble(cadena);
                        }
                    }
                }
            }

            aux7++;

        }

        BigDecimal totalAmountSap = (BigDecimal) sapSaleDao.sumTotalAmuntBeteween(2, applicatioDateStart, applicationDateEnd);

        row7 = hoja7.createRow(aux7 + 1);
        row7.createCell(0).setCellValue("TOTAL PROMOTOR");
        row7.createCell(1).setCellValue(sum100 - totalAmountSap.doubleValue());
        row7.createCell(2).setCellValue(sum200);

        Sheet hoja8 = wb.createSheet("CUADRO ADMON AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row8 = hoja8.createRow(0);

        row8.createCell(0).setCellValue("CONCEPTO");
        row8.createCell(1).setCellValue("VENTAS");
        row8.createCell(2).setCellValue("% CONTRA VENTA");

        //Implementacion del estilo
        for (Cell celda : row8) {
            celda.setCellStyle(style);
        }

        int aux8 = 1;

        Double sum300 = 0.00;
        Double sum400 = 0.00;

        for (Object[] admonReport : amermediaResults) {
            row8 = hoja8.createRow(aux8);

            if (admonReport[3] != null) {
                if (Integer.parseInt(String.valueOf(admonReport[3])) == 3) {
                    if (admonReport[0] != null) {
                        row8.createCell(0).setCellValue(String.valueOf(admonReport[0]));
                    }
                    if (admonReport[1] != null) {
                        row8.createCell(1).setCellValue(Double.parseDouble(String.valueOf(admonReport[1])));
                        sum300 += Double.parseDouble(String.valueOf(admonReport[1]));
                    }
                    if (admonReport[2] != null) {
                        String cadena = String.valueOf(admonReport[2]);
                        if (!cadena.isEmpty()) {
                            row8.createCell(2).setCellValue(Double.parseDouble(cadena));
                            sum400 += Double.parseDouble(cadena);
                        }
                    }
                }
            }

            aux8++;

        }

        BigDecimal totalAmountSapAmermedia = (BigDecimal) sapSaleDao.sumTotalAmuntBeteween(3, applicatioDateStart, applicationDateEnd);

        row8 = hoja8.createRow(aux8 + 1);
        row8.createCell(0).setCellValue("TOTAL PROMOTOR");
        row8.createCell(1).setCellValue(sum300 - totalAmountSapAmermedia.doubleValue());
        row8.createCell(2).setCellValue(sum400);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);
        hoja7.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void monthlyPayrollReport(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream, List queryResult) throws IOException {
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
//        row1.createCell(14).setCellValue("APOYO 375");
        row1.createCell(14).setCellValue("MONTO PROMOTOR");
        row1.createCell(15).setCellValue("COMISIÓN PROMOTOR");
        row1.createCell(16).setCellValue("BONO");
        row1.createCell(17).setCellValue("AJUSTES");
//        row1.createCell(19).setCellValue("APOYO PASAJES");
        row1.createCell(18).setCellValue("MONTO MENSUAL");
        row1.createCell(19).setCellValue("COMISIÓN MENSUAL");
        row1.createCell(20).setCellValue("DESCUENTOS");
//        row1.createCell(21).setCellValue("IMMS RHMAS PAGO");
//        row1.createCell(22).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(21).setCellValue("PERCEPCIONES");
        row1.createCell(22).setCellValue("DEDUCCIONES");
        row1.createCell(23).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList) {
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null) {
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null) {
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null) {
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null) {
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null) {
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null) {
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null) {
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if (payroll.getFechaIngreso() != null) {
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
//            if (payroll.getApoyo375() != null){
//                if (payroll.getApoyo375().signum() == -1){
//                    row1.createCell(14).setCellValue(0.0);
//                }else {
//                    row1.createCell(14).setCellValue(payroll.getApoyo375().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//                }
//            }
            if (payroll.getMontoPromotor() != null) {
                if (payroll.getMontoPromotor().signum() == -1) {
                    row1.createCell(14).setCellValue(0.0);
                } else {
                    row1.createCell(14).setCellValue(payroll.getMontoPromotor().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getComissionPromotor() != null) {
                if (payroll.getComissionPromotor().signum() == -1) {
                    row1.createCell(15).setCellValue(0.0);
                } else {
                    row1.createCell(15).setCellValue(payroll.getComissionPromotor().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getBono() != null) {
                if (payroll.getBono().signum() == -1) {
                    row1.createCell(16).setCellValue(0.0);
                } else {
                    row1.createCell(16).setCellValue(payroll.getBono().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getAjuste() != null) {
                if (payroll.getAjuste().signum() == -1) {
                    row1.createCell(17).setCellValue(0.0);
                } else {
                    row1.createCell(17).setCellValue(payroll.getAjuste().doubleValue());
                }
            }
//            if (payroll.getApoyoPasajes() != null){
//                if (payroll.getApoyoPasajes().signum() == -1){
//                    row1.createCell(19).setCellValue(0.0);
//                }else {
//                    row1.createCell(19).setCellValue(payroll.getApoyoPasajes().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//                }
//            }
            if (payroll.getMontoMensual() != null) {
                if (payroll.getMontoMensual().signum() == -1) {
                    row1.createCell(18).setCellValue(0.0);
                } else {
                    row1.createCell(18).setCellValue(payroll.getMontoMensual().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getComisionMensual() != null) {
                if (payroll.getComisionMensual().signum() == -1) {
                    row1.createCell(19).setCellValue(0.0);
                } else {
                    row1.createCell(19).setCellValue(payroll.getComisionMensual().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getDescuento() != null) {
                if (payroll.getDescuento().signum() == -1) {
                    row1.createCell(20).setCellValue(0.0);
                } else {
                    row1.createCell(20).setCellValue(payroll.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
//            if (payroll.getRhmasPago() != null){
//                if (payroll.getRhmasPago().signum() == -1){
//                    row1.createCell(21).setCellValue(0.0);
//                }else {
//                    row1.createCell(21).setCellValue(payroll.getRhmasPago().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//                }
//            }
//            if (payroll.getRhmasTotalFacturar() != null){
//                if (payroll.getRhmasTotalFacturar().signum() == -1){
//                    row1.createCell(24).setCellValue(0.0);
//                }else {
//                    row1.createCell(24).setCellValue(payroll.getRhmasTotalFacturar().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//                }
//            }
            if (payroll.getPercepcion() != null) {
                if (payroll.getPercepcion().signum() == -1) {
                    row1.createCell(21).setCellValue(0.0);
                } else {
                    row1.createCell(21).setCellValue(payroll.getPercepcion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getDeduccion() != null) {
                if (payroll.getDeduccion().signum() == -1) {
                    row1.createCell(22).setCellValue(0.0);
                } else {
                    row1.createCell(22).setCellValue(payroll.getDeduccion().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
            if (payroll.getPago() != null) {
                if (payroll.getPago().signum() == -1) {
                    row1.createCell(23).setCellValue(0.0);
                } else {
                    row1.createCell(23).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }

            aux1++;
        }

//        Sheet hoja2 = wb.createSheet("RHMAS AMER");
//
//        //Se crea la fila que contiene la cabecera
//        Row row2 = hoja2.createRow(0);
//
//        row2.createCell(0).setCellValue("DEPARTAMENTO");
//        row2.createCell(1).setCellValue("CODIGO");
//        row2.createCell(2).setCellValue("NOMBRE");
//        row2.createCell(3).setCellValue("SUELDO");
//        row2.createCell(4).setCellValue("SUBSIDIO");
//        row2.createCell(5).setCellValue("IMSS EMPLEADO");
//        row2.createCell(6).setCellValue("ISR");
//        row2.createCell(7).setCellValue("AJUSTE AL NETO");
//        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
//        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
//        row2.createCell(10).setCellValue("IMSS");
//        row2.createCell(11).setCellValue("RCV");
//        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
//        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
//        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
//        row2.createCell(15).setCellValue("COMISIÓN");
//        row2.createCell(16).setCellValue("SUBTOTAL");
//        row2.createCell(17).setCellValue("IVA");
//        row2.createCell(18).setCellValue("TOTAL");
//
//        //Implementacion del estilo
//        for (Cell celda : row2) {
//            celda.setCellStyle(style);
//        }
//
        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
//        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicationDateEnd,applicationDateEnd);
//
//        int aux2 = 1;
//
//        Double sum1 = 0.0;
//        Double sum2 = 0.0;
//        Double sum3 = 0.0;
//        Double sum4 = 0.0;
//        Double sum5 = 0.0;
//        Double sum6 = 0.0;
//        Double sum7 = 0.0;
//        Double sum8 = 0.0;
//        Double sum9 = 0.0;
//        Double sum10 = 0.0;
//        Double sum11 = 0.0;
//        Double sum12 = 0.0;
//        Double sum13 = 0.0;
//        Double sum14 = 0.0;
//        Double sum15 = 0.0;
//        Double sum16 = 0.0;
//
//        for (Outsourcing outsourcing : outsourcingList){
//            row2 = hoja2.createRow(aux2);
//
//            if (outsourcing.getDwEnterprises() != null){
//                if (outsourcing.getDwEnterprises().getArea() != null){
//                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
//                }
//            }
//            if(outsourcing.getEmployee() != null){
//                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
//            }
//            if(outsourcing.getEmployee() != null){
//                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
//            }
//            if(outsourcing.getSalary() != null){
//                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
//                sum1 += outsourcing.getSalary().doubleValue();
//            }
//            if(outsourcing.getSubsidy() != null){
//                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
//                sum2 += outsourcing.getSubsidy().doubleValue();
//            }
//            if (outsourcing.getImssEmployee() != null){
//                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
//                sum3 += outsourcing.getImssEmployee().doubleValue();
//            }
//            if (outsourcing.getIsr() != null){
//                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
//                sum4 += outsourcing.getIsr().doubleValue();
//            }
//            if (outsourcing.getAdjustment() != null){
//                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
//                sum5 += outsourcing.getAdjustment().doubleValue();
//            }
//            if (outsourcing.getTotalDeductions() != null){
//                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
//                sum6 += outsourcing.getTotalDeductions().doubleValue();
//            }
//            if (outsourcing.getNetAssetTax() != null){
//                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
//                sum7 += outsourcing.getNetAssetTax().doubleValue();
//            }
//            if (outsourcing.getImss() != null){
//                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
//                sum8 += outsourcing.getImss().doubleValue();
//            }
//            if (outsourcing.getRcv() != null){
//                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
//                sum9 += outsourcing.getRcv().doubleValue();
//            }
//            if (outsourcing.getEnterpriseInfonavit() != null){
//                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
//                sum10 += outsourcing.getEnterpriseInfonavit().doubleValue();
//            }
//            if (outsourcing.getPayrollTax() != null){
//                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
//                sum11 += outsourcing.getPayrollTax().doubleValue();
//            }
//            if (outsourcing.getTotalSocialSecurity() != null){
//                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
//                sum12 += outsourcing.getTotalSocialSecurity().doubleValue();
//            }
//            if (outsourcing.getCommission() != null){
//                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
//                sum13 += outsourcing.getCommission().doubleValue();
//            }
//            if (outsourcing.getSubtotal() != null){
//                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
//                sum14 += outsourcing.getSubtotal().doubleValue();
//            }
//            if (outsourcing.getIva() != null){
//                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
//                sum15 += outsourcing.getIva().doubleValue();
//            }
//            if (outsourcing.getTotal() != null){
//                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
//                sum16 += outsourcing.getTotal().doubleValue();
//            }
//
//            aux2++;
//        }
//
//        row2 = hoja2.createRow(aux2+2);
//
//        row2.createCell(2).setCellValue("TOTALES");
//        row2.createCell(3).setCellValue(sum1);
//        row2.createCell(4).setCellValue(sum2);
//        row2.createCell(5).setCellValue(sum3);
//        row2.createCell(6).setCellValue(sum4);
//        row2.createCell(7).setCellValue(sum5);
//        row2.createCell(8).setCellValue(sum6);
//        row2.createCell(9).setCellValue(sum7);
//        row2.createCell(10).setCellValue(sum8);
//        row2.createCell(11).setCellValue(sum9);
//        row2.createCell(12).setCellValue(sum10);
//        row2.createCell(13).setCellValue(sum11);
//        row2.createCell(14).setCellValue(sum12);
//        row2.createCell(15).setCellValue(sum13);
//        row2.createCell(16).setCellValue(sum14);
//        row2.createCell(17).setCellValue(sum15);
//        row2.createCell(18).setCellValue(sum16);
//
//        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");
//
//        //Se crea la fila que contiene la cabecera
//        Row row3 = hoja3.createRow(0);
//
//        row3.createCell(0).setCellValue("DEPARTAMENTO");
//        row3.createCell(1).setCellValue("CODIGO");
//        row3.createCell(2).setCellValue("NOMBRE");
//        row3.createCell(3).setCellValue("SUELDO");
//        row3.createCell(4).setCellValue("SUBSIDIO");
//        row3.createCell(5).setCellValue("IMSS EMPLEADO");
//        row3.createCell(6).setCellValue("ISR");
//        row3.createCell(7).setCellValue("AJUSTE AL NETO");
//        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
//        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
//        row3.createCell(10).setCellValue("IMSS");
//        row3.createCell(11).setCellValue("RCV");
//        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
//        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
//        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
//        row3.createCell(15).setCellValue("COMISIÓN");
//        row3.createCell(16).setCellValue("SUBTOTAL");
//        row3.createCell(17).setCellValue("IVA");
//        row3.createCell(18).setCellValue("TOTAL");
//
//        //Implementacion del estilo
//        for (Cell celda : row3) {
//            celda.setCellStyle(style);
//        }
//
        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
//        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicationDateEnd,applicationDateEnd);
//
//        int aux3 = 1;
//
//        Double sum17 = 0.0;
//        Double sum18 = 0.0;
//        Double sum19 = 0.0;
//        Double sum20 = 0.0;
//        Double sum21 = 0.0;
//        Double sum22 = 0.0;
//        Double sum23 = 0.0;
//        Double sum24 = 0.0;
//        Double sum25 = 0.0;
//        Double sum26 = 0.0;
//        Double sum27 = 0.0;
//        Double sum28 = 0.0;
//        Double sum29 = 0.0;
//        Double sum30 = 0.0;
//        Double sum31 = 0.0;
//        Double sum32 = 0.0;
//
//        for (Outsourcing outsourcing : outsourcings){
//            row3 = hoja3.createRow(aux3);
//
//            if (outsourcing.getDwEnterprises() != null){
//                if (outsourcing.getDwEnterprises().getArea() != null){
//                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
//                }
//            }
//            if(outsourcing.getEmployee() != null){
//                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
//            }
//            if(outsourcing.getEmployee() != null){
//                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
//            }
//            if(outsourcing.getSalary() != null){
//                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
//                sum17 += outsourcing.getSalary().doubleValue();
//            }
//            if(outsourcing.getSubsidy() != null){
//                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
//                sum18 += outsourcing.getSubsidy().doubleValue();
//            }
//            if (outsourcing.getImssEmployee() != null){
//                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
//                sum19 += outsourcing.getImssEmployee().doubleValue();
//            }
//            if (outsourcing.getIsr() != null){
//                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
//                sum20 += outsourcing.getIsr().doubleValue();
//            }
//            if (outsourcing.getAdjustment() != null){
//                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
//                sum21 += outsourcing.getAdjustment().doubleValue();
//            }
//            if (outsourcing.getTotalDeductions() != null){
//                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
//                sum22 += outsourcing.getTotalDeductions().doubleValue();
//            }
//            if (outsourcing.getNetAssetTax() != null){
//                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
//                sum23 += outsourcing.getNetAssetTax().doubleValue();
//            }
//            if (outsourcing.getImss() != null){
//                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
//                sum24 += outsourcing.getImss().doubleValue();
//            }
//            if (outsourcing.getRcv() != null){
//                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
//                sum25 += outsourcing.getRcv().doubleValue();
//            }
//            if (outsourcing.getEnterpriseInfonavit() != null){
//                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
//                sum26 += outsourcing.getEnterpriseInfonavit().doubleValue();
//            }
//            if (outsourcing.getPayrollTax() != null){
//                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
//                sum27 += outsourcing.getPayrollTax().doubleValue();
//            }
//            if (outsourcing.getTotalSocialSecurity() != null){
//                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
//                sum28 += outsourcing.getTotalSocialSecurity().doubleValue();
//            }
//            if (outsourcing.getCommission() != null){
//                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
//                sum29 += outsourcing.getCommission().doubleValue();
//            }
//            if (outsourcing.getSubtotal() != null){
//                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
//                sum30 += outsourcing.getSubtotal().doubleValue();
//            }
//            if (outsourcing.getIva() != null){
//                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
//                sum31 += outsourcing.getIva().doubleValue();
//            }
//            if (outsourcing.getTotal() != null){
//                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
//                sum32 += outsourcing.getTotal().doubleValue();
//            }
//
//            aux3++;
//        }
//
//        row3 = hoja3.createRow(aux3+2);
//
//        row3.createCell(2).setCellValue("TOTALES");
//        row3.createCell(3).setCellValue(sum17);
//        row3.createCell(4).setCellValue(sum18);
//        row3.createCell(5).setCellValue(sum19);
//        row3.createCell(6).setCellValue(sum20);
//        row3.createCell(7).setCellValue(sum21);
//        row3.createCell(8).setCellValue(sum22);
//        row3.createCell(9).setCellValue(sum23);
//        row3.createCell(10).setCellValue(sum24);
//        row3.createCell(11).setCellValue(sum25);
//        row3.createCell(12).setCellValue(sum26);
//        row3.createCell(13).setCellValue(sum27);
//        row3.createCell(14).setCellValue(sum28);
//        row3.createCell(15).setCellValue(sum29);
//        row3.createCell(16).setCellValue(sum30);
//        row3.createCell(17).setCellValue(sum31);
//        row3.createCell(18).setCellValue(sum32);

        Sheet hoja4 = wb.createSheet("AMER ENZO");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(9);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("TIPO DE SOCIO");
        row4.createCell(3).setCellValue("PUESTO");
        row4.createCell(4).setCellValue("BANCO");
        row4.createCell(5).setCellValue("N. DE CUENTA");
        row4.createCell(6).setCellValue("CLABE");
        row4.createCell(7).setCellValue("SUCURSAL");
        row4.createCell(8).setCellValue("RFC");
        row4.createCell(9).setCellValue("CURP");
        row4.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row4.createCell(11).setCellValue("11.50%");
        row4.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 10;

        List<Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;

        for (Payroll payroll : amerNec) {
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null) {
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            row4.createCell(2).setCellValue("AS");
            if (payroll.getPuesto() != null) {
                row4.createCell(3).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row4.createCell(4).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row4.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row4.createCell(6).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row4.createCell(7).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row4.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row4.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row4.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum33 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row4.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum34 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row4.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum35 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux4++;

        }

        row4 = hoja4.createRow(aux4 + 2);

        row4.createCell(9).setCellValue("TOTALES");
        row4.createCell(10).setCellValue(sum33);
        row4.createCell(11).setCellValue(sum34);
        row4.createCell(12).setCellValue(sum35);

        Sheet hoja5 = wb.createSheet("AMERMEDIA ENZO");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(9);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("TIPO DE SOCIO");
        row5.createCell(3).setCellValue("PUESTO");
        row5.createCell(4).setCellValue("BANCO");
        row5.createCell(5).setCellValue("N. DE CUENTA");
        row5.createCell(6).setCellValue("CLABE");
        row5.createCell(7).setCellValue("SUCURSAL");
        row5.createCell(8).setCellValue("RFC");
        row5.createCell(9).setCellValue("CURP");
        row5.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row5.createCell(11).setCellValue("11.50%");
        row5.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 10;

        List<Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;

        for (Payroll payroll : amermediaNec) {
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null) {
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            row5.createCell(2).setCellValue("AS");
            if (payroll.getPuesto() != null) {
                row5.createCell(3).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null) {
                row5.createCell(4).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row5.createCell(5).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row5.createCell(6).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row5.createCell(7).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null) {
                row5.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row5.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row5.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum36 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row5.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum37 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row5.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum38 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux5++;

        }

        row5 = hoja5.createRow(aux5 + 2);

        row5.createCell(9).setCellValue("TOTALES");
        row5.createCell(10).setCellValue(sum36);
        row5.createCell(11).setCellValue(sum37);
        row5.createCell(12).setCellValue(sum38);

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList, applicationDateEnd, applicationDateEnd);
        BigDecimal rhmasAmermedia = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList, applicationDateEnd, applicationDateEnd);

        Double totalDeTotales = 0.00;

        if (amerNecSum != null) {
            totalDeTotales += amerNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER ENZO");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (amermediaNecSum != null) {
            totalDeTotales += amermediaNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA ENZO");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasAmer != null) {
            totalDeTotales += rhmasAmer.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (rhmasAmermedia != null) {
            totalDeTotales += rhmasAmermedia.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        List<Object[]> results = queryResult;
        List<Object[]> amerResults = new ArrayList<>();
        List<Object[]> amermediaResults = new ArrayList<>();

        for (Object[] o : results) {
            if (o[3] != null) {
                if (Integer.parseInt(String.valueOf(o[3])) == 2) {
                    amerResults.add(o);
                } else if (Integer.parseInt(String.valueOf(o[3])) == 3) {
                    amermediaResults.add(o);
                }
            }
        }

        Sheet hoja7 = wb.createSheet("CUADRO ADMON AMER");

        //Se crea la fila que contiene la cabecera
        Row row7 = hoja7.createRow(0);

        row7.createCell(0).setCellValue("CONCEPTO");
        row7.createCell(1).setCellValue("VENTAS");
        row7.createCell(2).setCellValue("% CONTRA VENTA");

        //Implementacion del estilo
        for (Cell celda : row7) {
            celda.setCellStyle(style);
        }

        int aux7 = 1;

        Double sum100 = 0.00;
        Double sum200 = 0.00;

        for (Object[] admonReport : amerResults) {
            row7 = hoja7.createRow(aux7);

            if (admonReport[3] != null) {
                if (Integer.parseInt(String.valueOf(admonReport[3])) == 2) {
                    if (admonReport[0] != null) {
                        row7.createCell(0).setCellValue(String.valueOf(admonReport[0]));
                    }
                    if (admonReport[1] != null) {
                        row7.createCell(1).setCellValue(Double.parseDouble(String.valueOf(admonReport[1])));
                        sum100 += Double.parseDouble(String.valueOf(admonReport[1]));
                    }
                    if (admonReport[2] != null) {
                        String cadena = String.valueOf(admonReport[2]);
                        if (!cadena.isEmpty()) {
                            row7.createCell(2).setCellValue(Double.parseDouble(cadena));
                            sum200 += Double.parseDouble(cadena);
                        }
                    }
                }
            }

            aux7++;

        }

        BigDecimal totalAmountSap = (BigDecimal) sapSaleDao.sumTotalAmuntBeteween(2, applicatioDateStart, applicationDateEnd);

        row7 = hoja7.createRow(aux7 + 2);
        row7.createCell(0).setCellValue("SUBTOTAL");
        row7.createCell(1).setCellValue(sum100 - totalAmountSap.doubleValue());
        row7.createCell(2).setCellValue(sum200);

        Sheet hoja8 = wb.createSheet("CUADRO ADMON AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row8 = hoja8.createRow(0);

        row8.createCell(0).setCellValue("CONCEPTO");
        row8.createCell(1).setCellValue("VENTAS");
        row8.createCell(2).setCellValue("% CONTRA VENTA");

        //Implementacion del estilo
        for (Cell celda : row8) {
            celda.setCellStyle(style);
        }

        int aux8 = 1;

        Double sum300 = 0.00;
        Double sum400 = 0.00;

        for (Object[] admonReport : amermediaResults) {
            row8 = hoja8.createRow(aux8);

            if (admonReport[3] != null) {
                if (Integer.parseInt(String.valueOf(admonReport[3])) == 3) {
                    if (admonReport[0] != null) {
                        row8.createCell(0).setCellValue(String.valueOf(admonReport[0]));
                    }
                    if (admonReport[1] != null) {
                        row8.createCell(1).setCellValue(Double.parseDouble(String.valueOf(admonReport[1])));
                        sum300 += Double.parseDouble(String.valueOf(admonReport[1]));
                    }
                    if (admonReport[2] != null) {
                        String cadena = String.valueOf(admonReport[2]);
                        if (!cadena.isEmpty()) {
                            row8.createCell(2).setCellValue(Double.parseDouble(cadena));
                            sum400 += Double.parseDouble(cadena);
                        }
                    }
                }
            }

            aux8++;

        }

        BigDecimal totalAmountSapAmermedia = (BigDecimal) sapSaleDao.sumTotalAmuntBeteween(3, applicatioDateStart, applicationDateEnd);

        row8 = hoja8.createRow(aux8 + 2);
        row8.createCell(0).setCellValue("SUBTOTAL");
        row8.createCell(1).setCellValue(sum300 - totalAmountSapAmermedia.doubleValue());
        row8.createCell(2).setCellValue(sum400);

        hoja1.autoSizeColumn(0);
//        hoja2.autoSizeColumn(0);
//        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);
        hoja7.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void reportCost(OutputStream outputStream, List queryResult) throws IOException {

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

        Sheet hoja1 = wb.createSheet("NEC");

        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("No");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("N. DE CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("SUCURSAL");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("RFC");
        row1.createCell(8).setCellValue("CURP");
        row1.createCell(9).setCellValue("MONTO A PAGAR");
        row1.createCell(10).setCellValue("COSTO");
        row1.createCell(11).setCellValue("TOTAL A FACTURAR");
        row1.createCell(12).setCellValue("PAGADOR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Object[]> results = queryResult;

        for (Object[] costReportPojo : results) {
            row1 = hoja1.createRow(aux1);

            if (costReportPojo[0] != null) {
                row1.createCell(0).setCellValue(Integer.parseInt(String.valueOf(costReportPojo[0])));
            }
            if (costReportPojo[1] != null) {
                row1.createCell(1).setCellValue(String.valueOf(costReportPojo[1]));
            }
            if (costReportPojo[2] != null) {
                row1.createCell(2).setCellValue(String.valueOf(costReportPojo[2]));
            }
            if (costReportPojo[3] != null) {
                row1.createCell(3).setCellValue(String.valueOf(costReportPojo[3]));
            }
            if (costReportPojo[4] != null) {
                row1.createCell(4).setCellValue(String.valueOf(costReportPojo[4]));
            }
            if (costReportPojo[6] != null) {
                row1.createCell(5).setCellValue(String.valueOf(costReportPojo[6]));
            }
            if (costReportPojo[8] != null) {
                row1.createCell(6).setCellValue(String.valueOf(costReportPojo[8]));
            }
            if (costReportPojo[9] != null) {
                row1.createCell(7).setCellValue(String.valueOf(costReportPojo[9]));
            }
            if (costReportPojo[10] != null) {
                row1.createCell(8).setCellValue(String.valueOf(costReportPojo[10]));
            }
            if (costReportPojo[11] != null) {
                row1.createCell(9).setCellValue(Double.parseDouble(String.valueOf(costReportPojo[11])));
            }
            if (costReportPojo[12] != null) {
                row1.createCell(10).setCellValue(Double.parseDouble(String.valueOf(costReportPojo[12])));
            }
            if (costReportPojo[13] != null) {
                row1.createCell(11).setCellValue(Double.parseDouble(String.valueOf(costReportPojo[13])));
            }

            if (costReportPojo[14] != null) {
                row1.createCell(12).setCellValue(String.valueOf(costReportPojo[14]));
            }

            aux1++;

        }

        hoja1.autoSizeColumn(0);

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
        Row row1 = hoja1.createRow(9);

        row1.createCell(0).setCellValue("N");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("TIPO DE SOCIO");
//        row1.createCell(3).setCellValue("PUESTO");
        row1.createCell(3).setCellValue("BANCO");
        row1.createCell(4).setCellValue("N. DE CUENTA");
        row1.createCell(5).setCellValue("CLABE");
        row1.createCell(6).setCellValue("SUCURSAL");
        row1.createCell(7).setCellValue("AREA");
        row1.createCell(8).setCellValue("RFC");
        row1.createCell(9).setCellValue("CURP");
        row1.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row1.createCell(11).setCellValue("11.50%");
        row1.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        List<Integer> idsEmployeesE = outsourcingDao.getExtranjerosOnly();
        List<Payroll> payrollList = payrollDao.findAllByAmountPositivesNotExtranjeros(idsEmployeesE);

        int aux1 = 10;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;

        for (Payroll payroll : payrollList) {
            if (payroll.getNumeroDeEmpleado() != 3220) {
                row1 = hoja1.createRow(aux1);

                if (payroll.getNumeroDeEmpleado() != null) {
                    row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
                }
                if (payroll.getNombre() != null) {
                    row1.createCell(1).setCellValue(payroll.getNombre());
                }
                row1.createCell(2).setCellValue("AS");
                if (payroll.getBanco() != null) {
                    row1.createCell(3).setCellValue(payroll.getBanco());
                }
                if (payroll.getNumeroDeCuenta() != null) {
                    row1.createCell(4).setCellValue(payroll.getNumeroDeCuenta());
                }
                if (payroll.getCuentaClabe() != null) {
                    row1.createCell(5).setCellValue(payroll.getCuentaClabe());
                }
                if (payroll.getSucursal() != null) {
                    row1.createCell(6).setCellValue(payroll.getSucursal());
                }
                if (payroll.getArea() != null) {
                    row1.createCell(7).setCellValue(payroll.getArea());
                }
                if (payroll.getRfc() != null) {
                    row1.createCell(8).setCellValue(payroll.getRfc());
                }
                if (payroll.getCurp() != null) {
                    row1.createCell(9).setCellValue(payroll.getCurp());
                }
                if (payroll.getPago() != null) {
                    row1.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum1 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                if (payroll.getComisionNec() != null) {
                    row1.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum2 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                if (payroll.getTotalFacturar() != null) {
                    row1.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sum3 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }

                aux1++;
            }

        }

        row1 = hoja1.createRow(aux1 + 2);
        row1.createCell(9).setCellValue("TOTALES");
        row1.createCell(10).setCellValue(sum1);
        row1.createCell(11).setCellValue(sum2);
        row1.createCell(12).setCellValue(sum3);

        Sheet hoja2 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(4);

        row2.createCell(0).setCellValue(" ");
        row2.createCell(1).setCellValue(" ");
        row2.createCell(2).setCellValue(" ");
        row2.createCell(3).setCellValue("TOTAL A FACTURAR");

        BigDecimal gmtPago = (BigDecimal) payrollDao.sumPagoGmtNotExtranjeros(idsEmployeesE);
        BigDecimal gmtComission = (BigDecimal) payrollDao.sumCommissionGmtNotExtranjeros(idsEmployeesE);
        BigDecimal gmtTotal = (BigDecimal) payrollDao.sumGmtNecNotExtranjeros(idsEmployeesE);

        Row rowGmt = hoja2.createRow(5);

        rowGmt.createCell(0).setCellValue("GMT NEC");
        rowGmt.createCell(1).setCellValue(gmtPago.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        rowGmt.createCell(2).setCellValue(gmtComission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        rowGmt.createCell(3).setCellValue(gmtTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        Row rowTotales = hoja2.createRow(7);

        rowTotales.createCell(0).setCellValue("TOTAL");
        rowTotales.createCell(1).setCellValue(gmtPago.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        rowTotales.createCell(2).setCellValue(gmtComission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        rowTotales.createCell(3).setCellValue(gmtTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

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
        Row row1 = hoja1.createRow(9);

        row1.createCell(0).setCellValue("N");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("TIPO DE SOCIO");
        row1.createCell(3).setCellValue("BANCO");
        row1.createCell(4).setCellValue("N. DE CUENTA");
        row1.createCell(5).setCellValue("CLABE");
        row1.createCell(6).setCellValue("SUCURSAL");
        row1.createCell(7).setCellValue("AREA");
        row1.createCell(8).setCellValue("RFC");
        row1.createCell(9).setCellValue("CURP");
        row1.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row1.createCell(11).setCellValue("11.50%");
        row1.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 10;

        List<Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;

        for (Payroll payroll : amerNec) {
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null) {
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            row1.createCell(2).setCellValue("AS");
            if (payroll.getBanco() != null) {
                row1.createCell(3).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row1.createCell(4).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row1.createCell(5).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row1.createCell(6).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null) {
                row1.createCell(7).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null) {
                row1.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row1.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row1.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum1 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row1.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum2 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row1.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum3 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux1++;

        }

        row1 = hoja1.createRow(aux1 + 2);
        row1.createCell(9).setCellValue("TOTALES");
        row1.createCell(10).setCellValue(sum1);
        row1.createCell(11).setCellValue(sum2);
        row1.createCell(12).setCellValue(sum3);

        Sheet hoja2 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(9);

        row2.createCell(0).setCellValue("N");
        row2.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row2.createCell(2).setCellValue("TIPO DE SOCIO");
        row2.createCell(3).setCellValue("BANCO");
        row2.createCell(4).setCellValue("N. DE CUENTA");
        row2.createCell(5).setCellValue("CLABE");
        row2.createCell(6).setCellValue("SUCURSAL");
        row2.createCell(7).setCellValue("AREA");
        row2.createCell(8).setCellValue("RFC");
        row2.createCell(9).setCellValue("CURP");
        row2.createCell(10).setCellValue("MONTO A DEPOSITAR");
        row2.createCell(11).setCellValue("11.50%");
        row2.createCell(12).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        int aux2 = 10;

        List<Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;

        for (Payroll payroll : amermediaNec) {
            row2 = hoja2.createRow(aux2);

            if (payroll.getNumeroDeEmpleado() != null) {
                row2.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null) {
                row2.createCell(1).setCellValue(payroll.getNombre());
            }
            row2.createCell(2).setCellValue("AS");
            if (payroll.getBanco() != null) {
                row2.createCell(3).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null) {
                row2.createCell(4).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null) {
                row2.createCell(5).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null) {
                row2.createCell(6).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null) {
                row2.createCell(7).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null) {
                row2.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null) {
                row2.createCell(9).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null) {
                row2.createCell(10).setCellValue(payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum4 += payroll.getPago().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getComisionNec() != null) {
                row2.createCell(11).setCellValue(payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum5 += payroll.getComisionNec().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (payroll.getTotalFacturar() != null) {
                row2.createCell(12).setCellValue(payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                sum6 += payroll.getTotalFacturar().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            aux2++;

        }

        row2 = hoja2.createRow(aux2 + 2);
        row2.createCell(9).setCellValue("TOTALES");
        row2.createCell(10).setCellValue(sum4);
        row2.createCell(11).setCellValue(sum5);
        row2.createCell(12).setCellValue(sum6);

        Sheet hoja3 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(4);

        row3.createCell(0).setCellValue(" ");
        row3.createCell(1).setCellValue(" ");
        row3.createCell(2).setCellValue(" ");
        row3.createCell(3).setCellValue("TOTAL A FACTURAR");

        BigDecimal amerPago = (BigDecimal) payrollDao.sumPagoDistributorNec(2);
        BigDecimal amerComission = (BigDecimal) payrollDao.sumCommissionDistributorNec(2);
        BigDecimal amerTotal = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaPago = (BigDecimal) payrollDao.sumPagoDistributorNec(3);
        BigDecimal amermediaComission = (BigDecimal) payrollDao.sumCommissionDistributorNec(3);
        BigDecimal amermediaTotal = (BigDecimal) payrollDao.sumDistributorNec(3);

        Double totalPago = 0.00;
        Double totalComission = 0.00;
        Double totalTFacturar = 0.00;

        Row rowAmer = hoja3.createRow(5);

        rowAmer.createCell(0).setCellValue("AMER NEC");
        rowAmer.createCell(1).setCellValue(amerPago.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (amerPago != null) {
            totalPago += amerPago.doubleValue();
        }
        rowAmer.createCell(2).setCellValue(amerComission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (amerComission != null) {
            totalComission += amerComission.doubleValue();
        }
        rowAmer.createCell(3).setCellValue(amerTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (amerTotal != null) {
            totalTFacturar += amerTotal.doubleValue();
        }

        Row rowAmermedia = hoja3.createRow(6);

        rowAmermedia.createCell(0).setCellValue("AMERMEDIA NEC");
        rowAmermedia.createCell(1).setCellValue(amermediaPago.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (amermediaPago != null) {
            totalPago += amermediaPago.doubleValue();
        }
        rowAmermedia.createCell(2).setCellValue(amermediaComission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (amermediaComission != null) {
            totalComission += amermediaComission.doubleValue();
        }
        rowAmermedia.createCell(3).setCellValue(amermediaTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        if (amermediaTotal != null) {
            totalTFacturar += amermediaTotal.doubleValue();
        }

        Row rowTotales = hoja3.createRow(9);

        rowTotales.createCell(0).setCellValue("TOTAL");
        rowTotales.createCell(1).setCellValue(totalPago);
        rowTotales.createCell(2).setCellValue(totalComission);
        rowTotales.createCell(3).setCellValue(totalTFacturar);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);

        wb.write(fileOutputStream);
    }
}

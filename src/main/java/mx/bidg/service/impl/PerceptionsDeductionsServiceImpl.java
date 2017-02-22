package mx.bidg.service.impl;

import mx.bidg.dao.CPerceptionsDeductionsDao;
import mx.bidg.dao.PerceptionsDeductionsDao;
import mx.bidg.dao.SqlQueriesDao;
import mx.bidg.model.*;
import mx.bidg.service.PerceptionsDeductionsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.exolab.castor.types.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Service
@Transactional
public class PerceptionsDeductionsServiceImpl implements PerceptionsDeductionsService {

    @Autowired
    PerceptionsDeductionsDao perceptionsDeductionsDao;

    @Autowired
    SqlQueriesDao sqlQueriesDao;

    @Autowired
    private CPerceptionsDeductionsDao cPerceptionsDeductionsDao;

    @Override
    public PerceptionsDeductions save(PerceptionsDeductions perceptionsDeductions) {
        return perceptionsDeductionsDao.save(perceptionsDeductions);
    }

    @Override
    public PerceptionsDeductions update(PerceptionsDeductions perceptionsDeductions) {
        return perceptionsDeductionsDao.update(perceptionsDeductions);
    }

    @Override
    public PerceptionsDeductions findById(Integer idPerceptionsDeductions) {
        return perceptionsDeductionsDao.findById(idPerceptionsDeductions);
    }

    @Override
    public List<PerceptionsDeductions> findAll() {
        return perceptionsDeductionsDao.findAll();
    }

    @Override
    public boolean delete(PerceptionsDeductions perceptionsDeductions) {
        perceptionsDeductionsDao.delete(perceptionsDeductions);
        return true;
    }

    @Override
    public List<PerceptionsDeductions> findAllActives() {
        return perceptionsDeductionsDao.findAllWithStatus();
    }

    @Override
    public List<PerceptionsDeductions> calculateBonus(Users user, String ofDate, String untilDate) {
        SqlQueries sqlQuery = sqlQueriesDao.findQuery(3);
        perceptionsDeductionsDao.calculateBonus(sqlQuery,user,ofDate,untilDate);
        return perceptionsDeductionsDao.findAll();
    }

    @Override
    public List<PerceptionsDeductions> findByAllEmployeesAndInitialDateAndFinalDate(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate) {
        return perceptionsDeductionsDao.findByAllEmployeesAndInitialDateAndFinalDate(employeesHistoryList, initialDate, finalDate);
    }

    @Override
    public List<PerceptionsDeductions> findByIdEmployee(Integer idEmployee) {
        return perceptionsDeductionsDao.findByIdEmployee(idEmployee);
    }

    @Override
    public List<PerceptionsDeductions> findByStartDateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        return perceptionsDeductionsDao.findByStartDateEndDate(startDate,endDate);
    }

    @Override
    public List<PerceptionsDeductions> findByIdEmployeeAndStartDateEndDate(Integer idEmployee, LocalDateTime startDate, LocalDateTime endDate) {
        return perceptionsDeductionsDao.findByIdEmployeeAndApplicationDateAll(idEmployee,startDate,endDate);
    }

    @Override
    public void reporUnfold(List<EmployeesHistory> reportData, OutputStream outputStream) throws IOException {
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
        Sheet hoja1 = wb.createSheet("Reporte desgloce");
        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);
        row1.createCell(0).setCellValue("No Empleado");
        row1.createCell(1).setCellValue("RFC");
        row1.createCell(2).setCellValue("NOMBRE");
        row1.createCell(3).setCellValue("TIPO");
        row1.createCell(4).setCellValue("MONTO");
        row1.createCell(5).setCellValue("MOTIVO");
        row1.createCell(6).setCellValue("FECHA APLICACION");
        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }
        int aux1 = 1;
        for(EmployeesHistory eh:reportData){
            row1 = hoja1.createRow(aux1);
            if(eh.getIdEmployee()!=null){
                row1.createCell(0).setCellValue(eh.getIdEmployee());
            }
            if(eh.getRfc()!=null){
                row1.createCell(1).setCellValue(eh.getRfc());
            }
            if(eh.getFullName()!=null){
                row1.createCell(2).setCellValue(eh.getFullName());
            }
            List<PerceptionsDeductions> p =  perceptionsDeductionsDao.findIdEmployeeAndActives(eh.getIdEmployee());
            if (!p.isEmpty()){
                for (PerceptionsDeductions perceptionsDeductions : p){
                    CPerceptionsDeductions cp = cPerceptionsDeductionsDao.findById(perceptionsDeductions.getIdCPd());
                    if(cp.getNamePD()!=null){
                        row1.createCell(3).setCellValue(cp.getNamePD());
                    }
                    if(perceptionsDeductions.getAmount()!=null){
                        row1.createCell(4).setCellValue(perceptionsDeductions.getAmount().doubleValue());
                    }
                    if(perceptionsDeductions.getReason()!=null){
                        row1.createCell(5).setCellValue(perceptionsDeductions.getReason());
                    }
                    if(perceptionsDeductions.getApplicationDate()!=null){
                        row1.createCell(6).setCellValue(String.valueOf(perceptionsDeductions.getApplicationDate()));
                    }
                }
            }
            aux1++;
        }
        hoja1.autoSizeColumn(0);

        wb.write(outputStream);
    }

    @Override
    public void reportPD(OutputStream outputStream, List queryResult) throws IOException {
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
        Sheet hoja1 = wb.createSheet("Percepciones y Deducciones");
        Row row1 = hoja1.createRow(0);
        row1.createCell(0).setCellValue("No Empleado");
        row1.createCell(1).setCellValue("RFC");
        row1.createCell(2).setCellValue("NOMBRE");
        row1.createCell(3).setCellValue("RETARDO");
        row1.createCell(4).setCellValue("DESCUENTO");
        row1.createCell(5).setCellValue("EFECTIVO");
        row1.createCell(6).setCellValue("BONO");
        row1.createCell(7).setCellValue("SUELDO LIMPIEZA");
        row1.createCell(8).setCellValue("AJUSTE");
        row1.createCell(9).setCellValue("PRIMA VACACIONAL");
        row1.createCell(10).setCellValue("COMISIONES EMCOFIN");
        row1.createCell(11).setCellValue("BONO CUMPLIMIENTO");
        row1.createCell(12).setCellValue("APOYO PASAJES");
        row1.createCell(13).setCellValue("APOYO 375");
        row1.createCell(14).setCellValue("PERCEPCION");
        row1.createCell(15).setCellValue("DEDUCCION");
        row1.createCell(16).setCellValue("TOTAL PAGO");
        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }
        int aux1 = 1;
        Double totalRetardo=0.0;
        Double totalDescuento=0.0;
        Double totalEfectivo=0.0;
        Double totalBono=0.0;
        Double totalLimpieza=0.0;
        Double totalAjuste=0.0;
        Double totalVacacional=0.0;
        Double totalEmcofin=0.0;
        Double totalCumplimiento=0.0;
        Double totalPasajes=0.0;
        Double total375=0.0;
        Double totalPercepcion=0.0;
        Double totalDeduccion=0.0;
        Double totalTotales=0.0;
        List<Object[]> results = queryResult;
        for(Object[] reportPd : results) {
            row1 = hoja1.createRow(aux1);
            if(reportPd[0]!=null){
                row1.createCell(0).setCellValue(String.valueOf(reportPd[0]));
            }
            if(reportPd[1]!=null){
                row1.createCell(1).setCellValue(String.valueOf(reportPd[1]));
            }
            if(reportPd[2]!=null){
                row1.createCell(2).setCellValue(String.valueOf(reportPd[2]));
            }
            if(reportPd[3]!=null){
                row1.createCell(3).setCellValue(Double.parseDouble(String.valueOf(reportPd[3])));
                totalRetardo += Double.parseDouble(String.valueOf(reportPd[3]));
            }
            if(reportPd[4]!=null){
                row1.createCell(4).setCellValue(Double.parseDouble(String.valueOf(reportPd[4])));
                totalDescuento += Double.parseDouble(String.valueOf(reportPd[4]));
            }
            if(reportPd[5]!=null){
                row1.createCell(5).setCellValue(Double.parseDouble(String.valueOf(reportPd[5])));
                totalEfectivo += Double.parseDouble(String.valueOf(reportPd[5]));
            }
            if(reportPd[6]!=null){
                row1.createCell(6).setCellValue(Double.parseDouble(String.valueOf(reportPd[6])));
                totalBono += Double.parseDouble(String.valueOf(reportPd[6]));
            }
            if(reportPd[7]!=null){
                row1.createCell(7).setCellValue(Double.parseDouble(String.valueOf(reportPd[7])));
                totalLimpieza += Double.parseDouble(String.valueOf(reportPd[7]));
            }
            if(reportPd[8]!=null){
                row1.createCell(8).setCellValue(Double.parseDouble(String.valueOf(reportPd[8])));
                totalAjuste += Double.parseDouble(String.valueOf(reportPd[8]));
            }
            if(reportPd[9]!=null){
                row1.createCell(9).setCellValue(Double.parseDouble(String.valueOf(reportPd[9])));
                totalVacacional += Double.parseDouble(String.valueOf(reportPd[9]));
            }
            if(reportPd[10]!=null){
                row1.createCell(10).setCellValue(Double.parseDouble(String.valueOf(reportPd[10])));
                totalEmcofin += Double.parseDouble(String.valueOf(reportPd[10]));
            }
            if(reportPd[11]!=null){
                row1.createCell(11).setCellValue(Double.parseDouble(String.valueOf(reportPd[11])));
                totalCumplimiento += Double.parseDouble(String.valueOf(reportPd[11]));
            }
            if(reportPd[12]!=null){
                row1.createCell(12).setCellValue(Double.parseDouble(String.valueOf(reportPd[12])));
                totalPasajes += Double.parseDouble(String.valueOf(reportPd[12]));
            }
            if(reportPd[13]!=null){
                row1.createCell(13).setCellValue(Double.parseDouble(String.valueOf(reportPd[13])));
                total375 += Double.parseDouble(String.valueOf(reportPd[13]));
            }
            if(reportPd[14]!=null){
                row1.createCell(14).setCellValue(Double.parseDouble(String.valueOf(reportPd[14])));
                totalPercepcion += Double.parseDouble(String.valueOf(reportPd[14]));
            }
            if(reportPd[15]!=null){
                row1.createCell(15).setCellValue(Double.parseDouble(String.valueOf(reportPd[15])));
                totalDeduccion += Double.parseDouble(String.valueOf(reportPd[15]));
            }
            if(reportPd[16]!=null){
                row1.createCell(16).setCellValue(Double.parseDouble(String.valueOf(reportPd[16])));
                totalTotales += Double.parseDouble(String.valueOf(reportPd[16]));
            }
            aux1 ++;
        }
        row1= hoja1.createRow(aux1+1);
        row1.createCell(0).setCellValue("TOTALES");
        row1.createCell(3).setCellValue(totalRetardo);
        row1.createCell(4).setCellValue(totalDescuento);
        row1.createCell(5).setCellValue(totalEfectivo);
        row1.createCell(6).setCellValue(totalBono);
        row1.createCell(7).setCellValue(totalLimpieza);
        row1.createCell(8).setCellValue(totalAjuste);
        row1.createCell(9).setCellValue(totalVacacional);
        row1.createCell(10).setCellValue(totalEmcofin);
        row1.createCell(11).setCellValue(totalCumplimiento);
        row1.createCell(12).setCellValue(totalPasajes);
        row1.createCell(13).setCellValue(total375);
        row1.createCell(14).setCellValue(totalPercepcion);
        row1.createCell(15).setCellValue(totalDeduccion);
        row1.createCell(16).setCellValue(totalTotales);
        hoja1.autoSizeColumn(0);
        wb.write(outputStream);
    }

    @Override
    public List findByAllEmployeesAndInitialDateAndFinalDatePerception(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate, List<CPerceptionsDeductions> cPerceptionsDeductionsList) {
        return perceptionsDeductionsDao.findByAllEmployeesAndInitialDateAndFinalDatePerception(employeesHistoryList,initialDate,finalDate,cPerceptionsDeductionsList);
    }

    @Override
    public void reportPDSD(OutputStream outputStream, List queryResult) throws IOException {
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
        Sheet hoja1 = wb.createSheet("Percepciones y Deducciones");
        Row row1 = hoja1.createRow(0);
        row1.createCell(0).setCellValue("No Empleado");
        row1.createCell(1).setCellValue("NOMBRE");
        row1.createCell(2).setCellValue("RFC");
        row1.createCell(3).setCellValue("TIPO");
        row1.createCell(4).setCellValue("MONTO");
        row1.createCell(5).setCellValue("MOTIVO");
        row1.createCell(6).setCellValue("FECHA APLICACION");
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }
        int aux1 = 1;
        List<Object[]> results = queryResult;
        for(Object[] reportPd : results) {
            row1 = hoja1.createRow(aux1);
            if(reportPd[0]!=null){
                row1.createCell(0).setCellValue(String.valueOf(reportPd[0]));
            }
            if(reportPd[1]!=null){
                row1.createCell(1).setCellValue(String.valueOf(reportPd[1]));
            }
            if(reportPd[2]!=null){
                row1.createCell(2).setCellValue(String.valueOf(reportPd[2]));
            }
            if(reportPd[3]!=null){
                row1.createCell(3).setCellValue(String.valueOf(reportPd[3]));
            }
            if(reportPd[4]!=null){
                row1.createCell(4).setCellValue(Double.parseDouble(String.valueOf(reportPd[4])));
            }
            if(reportPd[5]!=null){
                row1.createCell(5).setCellValue(String.valueOf(reportPd[5]));
            }
            if(reportPd[6]!=null){
                row1.createCell(6).setCellValue(String.valueOf(reportPd[6]));
            }
            aux1 ++;
        }
        hoja1.autoSizeColumn(0);
        wb.write(outputStream);
    }
}
package mx.bidg.service.impl;

import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.EmployeesHistory;
import mx.bidg.service.EmployeesHistoryService;
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
            row.createCell(5).setCellValue(employeeHistory.getAcronyms());
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
}

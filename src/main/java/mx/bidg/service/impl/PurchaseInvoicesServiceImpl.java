package mx.bidg.service.impl;

import mx.bidg.dao.PurchaseInvoicesDao;
import mx.bidg.model.PurchaseInvoices;
import mx.bidg.service.PurchaseInvoicesService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
@Service
@Transactional
public class PurchaseInvoicesServiceImpl implements PurchaseInvoicesService {

    @Autowired
    PurchaseInvoicesDao purchaseInvoicesDao;

    @Override
    public PurchaseInvoices save(PurchaseInvoices purchaseInvoices) {
        return purchaseInvoicesDao.save(purchaseInvoices);
    }

    @Override
    public PurchaseInvoices update(PurchaseInvoices purchaseInvoices) {
        return purchaseInvoicesDao.update(purchaseInvoices);
    }

    @Override
    public PurchaseInvoices findById(Integer idPurchaseInvoices) {
        return purchaseInvoicesDao.findById(idPurchaseInvoices);
    }

    @Override
    public List<PurchaseInvoices> findAll() {
        return purchaseInvoicesDao.findAll();
    }

    @Override
    public boolean delete(PurchaseInvoices purchaseInvoices) {
        return purchaseInvoicesDao.delete(purchaseInvoices);
    }

    @Override
    public PurchaseInvoices findByIdRequest(Integer idRequest) {
        return purchaseInvoicesDao.findByIdRequest(idRequest);
    }

    @Override
    public List<PurchaseInvoices> findByRequestTypeAndCatgory(Integer idRequestCategory, Integer idRequestType, Integer idRequestStatus) {
        return purchaseInvoicesDao.findByRequestTypeAndCatgory(idRequestCategory, idRequestType, idRequestStatus);
    }

    @Override
    public List<PurchaseInvoices> findFolio(String folio) {
        return purchaseInvoicesDao.findFolio(folio);
    }


    @Override
    public void payablesRequests(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        List<PurchaseInvoices> payables = purchaseInvoicesDao.findBetweenDates(fromDate, toDate);

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = workbook.createCellStyle();
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

        CellStyle cellDateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja = workbook.createSheet("Cuentas_Por_Pagar");
        Row row = hoja.createRow(0);

        row.createCell(0).setCellValue("FOLIO");
        row.createCell(1).setCellValue("CONCEPTO");
        row.createCell(2).setCellValue("PROVEEDOR");
        row.createCell(3).setCellValue("RFC");
        row.createCell(4).setCellValue("DIAS DE CREDITO");
        row.createCell(5).setCellValue("TOTAL");
        row.createCell(6).setCellValue("CATEGORIA");
        row.createCell(7).setCellValue("ESTATUS");
        row.createCell(8).setCellValue("SOLICITANTE");
        row.createCell(9).setCellValue("FECHA DE SOLICITUD");

        for (Cell celda : row) {
            celda.setCellStyle(style);
        }
        int aux = 1;

        for (PurchaseInvoices request : payables){
            row = hoja.createRow(aux);

            row.createCell(0).setCellValue(request.getRequest().getFolio());
            row.createCell(1).setCellValue(request.getConceptName());
            row.createCell(2).setCellValue(request.getProvider().getProviderName());
            row.createCell(3).setCellValue(request.getProvider().getRfc());
            row.createCell(4).setCellValue(request.getProvider().getCreditDays());
            row.createCell(5).setCellValue(request.getRequest().getTotalExpended().doubleValue());
            row.createCell(6).setCellValue(request.getRequest().getRequestCategory().getRequestCategoryName());
            row.createCell(7).setCellValue(request.getRequest().getRequestStatus().getRequestStatus());
            row.createCell(8).setCellValue(request.getRequest().getEmployees().getFullName());
            if (request.getCreationDate() != null){
                Date fecha = Date.from(request.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());
                if (fecha != null){
                    row.createCell(9);
                    row.getCell(9).setCellValue(fecha);
                    row.getCell(9).setCellStyle(cellDateStyle);
                }
            }
            aux++;
        }
        workbook.write(stream);
    }
}

package mx.bidg.service.impl;

import mx.bidg.dao.DistributorsDetailBanksDao;
import mx.bidg.model.DistributorsDetailBanks;
import mx.bidg.service.DistributorsDetailBanksService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * Created by Leonardo on 13/06/2017.
 */
@Service
@Transactional
public class DistributorsDetailBanksServiceImpl implements DistributorsDetailBanksService {

    @Autowired
    private DistributorsDetailBanksDao distributorsDetailBanksDao;

    @Override
    public List<DistributorsDetailBanks> findAll() {
        return distributorsDetailBanksDao.findAll();
    }

    @Override
    public DistributorsDetailBanks findById(Integer idDistributorsDetailBanks) {
        return distributorsDetailBanksDao.findById(idDistributorsDetailBanks);
    }

    @Override
    public DistributorsDetailBanks save(DistributorsDetailBanks distributorsDetailBanks) {
        return distributorsDetailBanksDao.save(distributorsDetailBanks);
    }

    @Override
    public DistributorsDetailBanks update(DistributorsDetailBanks distributorsDetailBanks) {
        return distributorsDetailBanksDao.update(distributorsDetailBanks);
    }

    @Override
    public boolean delete(DistributorsDetailBanks distributorsDetailBanks) {
        return distributorsDetailBanksDao.delete(distributorsDetailBanks);
    }

    @Override
    public List<DistributorsDetailBanks> getByDistributor(int id) {
        return distributorsDetailBanksDao.getByDistributor(id);
    }

    @Override
    public DistributorsDetailBanks findByAccountNumber(String accountNumber) {
        return distributorsDetailBanksDao.findByAccountNumber(accountNumber);
    }

    @Override
    public BigDecimal sumByDistributor(Integer idDistributor) {
        return distributorsDetailBanksDao.sumByDistributor(idDistributor);
    }

    @Override
    public DistributorsDetailBanks findLikeAccountNumber(String accountNumber) {
        return distributorsDetailBanksDao.findLikeAccountNumber(accountNumber);
    }

    @Override
    public void exportFile(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle cellDateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hojas = workbook.createSheet("REPORTE DE EMPRESAS ");
        Row row = hojas.createRow(0);

        row.createCell(0).setCellValue("NOMBRE DE LA EMPRESA");
        row.createCell(1).setCellValue("NOMBRE DEL BANCO");
        row.createCell(2).setCellValue("CLABE");
        row.createCell(3).setCellValue("NUMERO DE CUENTA");
        row.createCell(4).setCellValue("TIPO DE MONEDA");
        row.createCell(5).setCellValue("MONTO");
        row.createCell(6).setCellValue("TIPO DE CAMBIO");
        row.createCell(7).setCellValue("TOTAL EN PESOS");
        row.createCell(8).setCellValue("FECHA DE INGRESO");

        for (Cell cell : row) {
            cell.setCellStyle(style);
        }

        List<DistributorsDetailBanks> distributorsDetailBanksList = distributorsDetailBanksDao.findAll();
        int aux = 1;
        for (DistributorsDetailBanks distributorsDetailBanks : distributorsDetailBanksList) {
            row = hojas.createRow(aux);
            row.createCell(0).setCellValue(distributorsDetailBanks.getDistributors().getDistributorName());
            row.createCell(1).setCellValue(distributorsDetailBanks.getBanks().getAcronyms());
            row.createCell(2).setCellValue(distributorsDetailBanks.getAccountClabe());
            row.createCell(3).setCellValue(distributorsDetailBanks.getAccountNumber());
            row.createCell(4).setCellValue(distributorsDetailBanks.getCurrencies().getCurrency());
            row.createCell(5).setCellValue(distributorsDetailBanks.getAmount().doubleValue());
            row.createCell(6).setCellValue(distributorsDetailBanks.getCurrencies().getRate().doubleValue());
            BigDecimal amount = distributorsDetailBanks.getAmount().multiply(distributorsDetailBanks.getCurrencies().getRate());
            row.createCell(7).setCellValue(amount.doubleValue());

            if (distributorsDetailBanks.getCreationDate() != null) {
                Date fecha = Date.from(distributorsDetailBanks.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());

                if (fecha != null) {
                    row.createCell(8);
                    row.getCell(8).setCellValue(fecha);
                    row.getCell(8).setCellStyle(cellDateStyle);
                }
            }
            aux++;
        }
        workbook.write(stream);

    }

}



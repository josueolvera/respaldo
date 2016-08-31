package mx.bidg.service.impl;

import mx.bidg.dao.CAgreementsDao;
import mx.bidg.model.CAgreements;
import mx.bidg.service.CAgreementsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
@Service
@Transactional
public class CAgreementsServiceImpl implements CAgreementsService {

    @Autowired
    CAgreementsDao cAgreementsDao;

    @Override
    public CAgreements save(CAgreements cAgreements) {
        return cAgreementsDao.save(cAgreements);
    }

    @Override
    public CAgreements update(CAgreements cAgreements) {
        cAgreementsDao.update(cAgreements);
        return cAgreements;
    }

    @Override
    public CAgreements findById(Integer idAgreement) {
        return cAgreementsDao.findById(idAgreement);
    }

    @Override
    public List<CAgreements> findAll() {
        return cAgreementsDao.findAll();
    }

    @Override
    public boolean delete(CAgreements cAgreements) {
        return cAgreementsDao.delete(cAgreements);
    }

    @Override
    public boolean diferentAgreement(String agreementName) {

        boolean flag = false;

        if (cAgreementsDao.findByName(agreementName) != null) {
            flag = true;
        }

        return flag;
    }

    @Override
    public void lowDate (Integer idAgreement) {
        CAgreements agreement = cAgreementsDao.findById(idAgreement);
        agreement.setLowDate(LocalDateTime.now());
        agreement.setStatus(0);
        cAgreementsDao.update(agreement);
    }

    @Override
    public List<CAgreements> findActives() {
        return cAgreementsDao.findAgreementsActives();
    }

    @Override
    public void agreementsReport(OutputStream stream) throws IOException {
        List<CAgreements> agreementsList = cAgreementsDao.findAgreementsActives();

        Workbook wb = new HSSFWorkbook();
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

        Sheet hoja = wb.createSheet();

        if(agreementsList.size()>0) {
            //Se crea la fila que contiene la cabecera
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("ID DE CONVENIO");
            row.createCell(1).setCellValue("NOMBRE DEL CONVENIO");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }

            int aux = 1;

            for (CAgreements agreements : agreementsList) {

                row = hoja.createRow(aux);
                // Create a cell and put a value in it.
                row.createCell(0).setCellValue(agreements.getIdAgreement());
                row.createCell(1).setCellValue(agreements.getAgreementName());

                aux++;
            }

            //Autoajustar al contenido
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);

            wb.write(stream);
        }else{
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("NO HAY CONVENIOS");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }
            hoja.autoSizeColumn(0);
            wb.write(stream);
        }
    }
}

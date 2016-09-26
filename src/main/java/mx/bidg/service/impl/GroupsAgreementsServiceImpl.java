/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import mx.bidg.dao.CAgreementsDao;
import mx.bidg.dao.CAgreementsGroupsDao;
import mx.bidg.dao.GroupsAgreementsDao;
import mx.bidg.model.CAgreements;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.service.GroupsAgreementsService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupsAgreementsServiceImpl implements GroupsAgreementsService {
    
    @Autowired
    GroupsAgreementsDao groupsAgreementsDao;

    @Autowired
    CAgreementsDao cAgreementsDao;

    @Autowired
    CAgreementsGroupsDao cAgreementsGroupsDao;

    @Override
    public GroupsAgreements save(GroupsAgreements groupsAgreements) {
        return groupsAgreementsDao.save(groupsAgreements);
    }

    @Override
    public GroupsAgreements update(GroupsAgreements groupsAgreements) {
        return groupsAgreementsDao.update(groupsAgreements);
    }

    @Override
    public GroupsAgreements findById(Integer idGa) {
        return groupsAgreementsDao.findById(idGa);
    }

    @Override
    public List<GroupsAgreements> findAll() {
        return groupsAgreementsDao.findAll();
    }

    @Override
    public boolean delete(GroupsAgreements groupsAgreements) {
        groupsAgreementsDao.delete(groupsAgreements);
        return true;
    }

    @Override
    public void GroupAgreementsReport(OutputStream stream) throws IOException {

        List<CAgreements> agreementsList = cAgreementsDao.findAgreementsActives();

        List<CAgreementsGroups> groupsList = cAgreementsGroupsDao.findGruoupActives();

        List<GroupsAgreements> groupsAgreementsList = groupsAgreementsDao.findGroupsAgreementsActives(groupsList, agreementsList);

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

        if(groupsAgreementsList.size()>0) {
            //Se crea la fila que contiene la cabecera
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("ID DE CONVENIO");
            row.createCell(1).setCellValue("NOMBRE DEL CONVENIO");
            row.createCell(2).setCellValue("GRUPO");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }

            int aux = 1;

            for (GroupsAgreements groupAgreement : groupsAgreementsList) {

                row = hoja.createRow(aux);
                // Create a cell and put a value in it.
                row.createCell(0).setCellValue(groupAgreement.getAgreement().getIdAgreement());
                row.createCell(1).setCellValue(groupAgreement.getAgreement().getAgreementName());
                row.createCell(2).setCellValue(groupAgreement.getAgreementGroup().getAgreementGroupName());

                aux++;
            }

            //Autoajustar al contenido
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);

            wb.write(stream);
        }else{
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("NO HAY CONVENIOS NI GRUPOS ACTIVOS");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }
            hoja.autoSizeColumn(0);
            wb.write(stream);
        }

    }

    @Override
    public List<GroupsAgreements> findGroupsAgreementByAg(Integer idAg) {
        return groupsAgreementsDao.findGroupsAgreementsByAg(idAg);
    }

}

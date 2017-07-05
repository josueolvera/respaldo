/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.io.OutputStream;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import mx.bidg.dao.CDistributorsDao;
import mx.bidg.model.CDistributors;
import mx.bidg.service.CDistributorsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CDistributorsServiceImpl implements CDistributorsService {
    
    @Autowired
    private CDistributorsDao cDistributorsDao;

    @Override
    public List<CDistributors> findAll() {
        return cDistributorsDao.findAll();
    }

    @Override
    public List<CDistributors> findAllForStock() {
        return cDistributorsDao.findAllForStock();
    }

    @Override
    public List<CDistributors> findAllForAgreement() {
        return cDistributorsDao.findAllForAgreement();
    }

    @Override
    public List<CDistributors> getDistributors(Boolean forStock, Boolean forBudget, Boolean forAgreement) {
        return cDistributorsDao.getDistributors(forStock, forBudget, forAgreement);
    }

    @Override
    public List<CDistributors> getDistributorForSaem(Integer idDistributor, Boolean saemFlag) {
        return cDistributorsDao.getDistributorsBySaemReports(idDistributor, saemFlag);
    }

    @Override
    public CDistributors findById(Integer idDistributor) {
        return cDistributorsDao.findById(idDistributor);
    }

    @Override
    public CDistributors save(CDistributors cDistributors) {
        return cDistributorsDao.save(cDistributors);
    }

    @Override
    public CDistributors update(CDistributors cDistributors) {
        return cDistributorsDao.update(cDistributors);
    }

    @Override
    public boolean delete(CDistributors cDistributors) {
        return cDistributorsDao.delete(cDistributors);
    }

    @Override
    public void exportFile(OutputStream stream) throws Exception {

        //Creación de libro
        Workbook workbook = new XSSFWorkbook();

        //creacion de tipo de letra
        Font font = workbook.createFont();
        //tipo de letra
        font.setFontName("Arial");
        //si la queremos negrita o no
        font.setBold(true);
        //tamaño de la letra
        font.setFontHeightInPoints((short) 10);
        //color de letra
        font.setColor(IndexedColors.BLUE.getIndex());

        //Se crea estilo para celda
        CellStyle style = workbook.createCellStyle();
        //Se setea la letra creada arriba o cualquier letra generada
        style.setFont(font);
        //Se alineamiento del texto
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //Se agrega background a la celda
        style.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
        //
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //Se agrega color y tamaño de los bordes
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());


        //Se agrega Estilo para celda con formato de fecha
        CellStyle cellDateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));


        //Se crea la hoja en la cual escribiremos nuestros datos
        Sheet hoja = workbook.createSheet("Empresas");
        //Creamos nuestra fila 1 para nuestros encabezados
        Row row = hoja.createRow(0);
        //seteo de nombre de encabezados
        row.createCell(0).setCellValue("ID_EMPRESA");
        row.createCell(1).setCellValue("NOMBRE DE EMPRESA");
        row.createCell(2).setCellValue("ACRONIMO");
        row.createCell(3).setCellValue("FECHA DE CREACION");

        //seteo de estilo a encabezados
        for (Cell cell : row){
            cell.setCellStyle(style);
        }


        List<CDistributors> cDistributors = cDistributorsDao.findAll();

        int aux = 1;
        //iteramos nuestra lista de objetos para llenar el cuerpo del excel
        for (CDistributors distributor : cDistributors){
            row = hoja.createRow(aux);
            //seteo de datos
            row.createCell(0).setCellValue(distributor.getIdDistributor());
            row.createCell(1).setCellValue(distributor.getDistributorName());
            row.createCell(2).setCellValue(distributor.getAcronyms());


            //formato de fecha a celda
            if (distributor.getCreationDate() != null){
                Date fecha = Date.from(distributor.getCreationDate().atZone(ZoneId.systemDefault()).toInstant());

                if (fecha != null){
                    row.createCell(3);
                    row.getCell(3).setCellValue(fecha);
                    row.getCell(3).setCellStyle(cellDateStyle);

                }
            }
            aux++;
        }

        workbook.write(stream);
    }
}

package mx.bidg.service.impl;

import mx.bidg.dao.DwBranchsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.DwBranchs;
import mx.bidg.service.DwBranchsService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@Transactional
@Service
public class DwBranchsServiceImpl implements DwBranchsService {

    @Autowired
    private DwBranchsDao dwBranchsDao;

    @Override
    public List<DwBranchs> findAll() {
        return dwBranchsDao.findAll();
    }

    @Override
    public DwBranchs findById(Integer id) {
        return dwBranchsDao.findById(id);
    }

    @Override
    public List<DwBranchs> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);
        String[] headersToSkip = {
                "ID SUCURSAL SISCOM", "INDICE REPROCESO","PRODUCTIVIDAD",
                "PROMOTORES CON VENTA","PROMOTORES CON VENTA REAL",
                "META"
        };

        for (int i = 0 ; i < 5 ;i++) {
            if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idBranch = currentRow.getCell(0);
            Cell indexReprocessing = currentRow.getCell(1);
            Cell productivity = currentRow.getCell(2);
            Cell pttoPromVta = currentRow.getCell(3);
            Cell pttoPromReal = currentRow.getCell(4);
            Cell goalBranch = currentRow.getCell(5);

            if (idBranch != null) {
                DwBranchs dwBranchs = dwBranchsDao.findById((int) idBranch.getNumericCellValue());

                if (dwBranchs != null) {

                    if (indexReprocessing != null) {
                        BigDecimal bdIndexReprocessing = new BigDecimal(indexReprocessing.getNumericCellValue());
                        dwBranchs.setIndexReprocessing(bdIndexReprocessing);
                    }
                    if (productivity != null) {
                        BigDecimal bdproductivity = new BigDecimal(productivity.getNumericCellValue());
                        dwBranchs.setProductivity(bdproductivity);
                    }
                    if (pttoPromVta != null) {
                        dwBranchs.setPttoPromVta((int) pttoPromVta.getNumericCellValue());
                    }
                    if (pttoPromReal != null) {
                        dwBranchs.setPttoPromReal((int) pttoPromReal.getNumericCellValue());
                    }
                    if (goalBranch != null) {
                        BigDecimal goal = new BigDecimal(goalBranch.getNumericCellValue());
                        dwBranchs.setProductivity(goal);
                    }

                    dwBranchs.setUploadedDate(LocalDateTime.now());

                    dwBranchsDao.update(dwBranchs);
                } else {
                    
                    DwBranchs newDwBranchs = new DwBranchs();

                    if (indexReprocessing != null) {
                        BigDecimal bdIndexReprocessing = new BigDecimal(indexReprocessing.getNumericCellValue());
                        newDwBranchs.setIndexReprocessing(bdIndexReprocessing);
                    }
                    if (productivity != null) {
                        BigDecimal bdproductivity = new BigDecimal(productivity.getNumericCellValue());
                        newDwBranchs.setProductivity(bdproductivity);
                    }
                    if (pttoPromVta != null) {
                        newDwBranchs.setPttoPromVta((int) pttoPromVta.getNumericCellValue());
                    }
                    if (pttoPromReal != null) {
                        newDwBranchs.setPttoPromReal((int) pttoPromReal.getNumericCellValue());
                    }

                    if (goalBranch != null) {
                        BigDecimal goal = new BigDecimal(goalBranch.getNumericCellValue());
                        newDwBranchs.setProductivity(goal);
                    }

                    newDwBranchs.setUploadedDate(LocalDateTime.now());

                    dwBranchsDao.save(newDwBranchs);
                }
            }
        }

        return dwBranchsDao.findAll();
    }
}

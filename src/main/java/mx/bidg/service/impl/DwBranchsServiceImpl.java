package mx.bidg.service.impl;

import mx.bidg.dao.DwBranchsDao;
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
    public List<DwBranchs> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idBranch = currentRow.getCell(0);

            Cell pttoPromReal = currentRow.getCell(2);
            Cell pttoPromVta = currentRow.getCell(3);
            Cell productivity = currentRow.getCell(4);
            Cell indexReprocessing = currentRow.getCell(5);


            DwBranchs dwBranchs = new DwBranchs();


            if (indexReprocessing != null) {
                BigDecimal bdIndexReprocessing = new BigDecimal(indexReprocessing.getNumericCellValue());
                dwBranchs.setIndexReprocessing(bdIndexReprocessing);
            }


            dwBranchsDao.save(dwBranchs);

        }

        return dwBranchsDao.findAll();
    }

    @Override
    public List<DwBranchs> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        return null;
    }
}

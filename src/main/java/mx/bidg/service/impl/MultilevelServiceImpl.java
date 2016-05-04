package mx.bidg.service.impl;

import mx.bidg.dao.MultilevelDao;
import mx.bidg.model.Multilevel;
import mx.bidg.service.MultilevelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo8 on 28/04/16.
 */
@Transactional
@Service
public class MultilevelServiceImpl implements MultilevelService {

    @Autowired
    private MultilevelDao multilevelDao;

    @Override
    public List<Multilevel> findAll() {
        return multilevelDao.findAll();
    }

    @Override
    public Multilevel findById(Integer id) {
        return multilevelDao.findById(id);
    }

    @Override
    public List<Multilevel> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        List<Multilevel> multilevelList = new ArrayList<Multilevel>();

        for (int i=1;i<sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell sapKey = currentRow.getCell(0);

            Multilevel multilevel = new Multilevel();


            multilevelList.add(multilevel);
        }
        return multilevelDao.findAll();
    }
}

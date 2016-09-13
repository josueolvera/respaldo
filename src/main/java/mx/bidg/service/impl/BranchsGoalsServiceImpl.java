package mx.bidg.service.impl;

import mx.bidg.dao.BranchsGoalsDao;
import mx.bidg.dao.CBranchsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.BranchsGoals;
import mx.bidg.model.CBranchs;
import mx.bidg.service.BranchsGoalsService;
import org.apache.commons.lang3.StringUtils;
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
 * Created by josueolvera on 12/09/16.
 */
@Service
@Transactional
public class BranchsGoalsServiceImpl implements BranchsGoalsService {

    @Autowired
    BranchsGoalsDao branchsGoalsDao;

    @Autowired
    CBranchsDao cBranchsDao;

    @Override
    public BranchsGoals save(BranchsGoals branchsGoals) {
        return branchsGoalsDao.save(branchsGoals);
    }

    @Override
    public BranchsGoals update(BranchsGoals branchsGoals) {
        return branchsGoalsDao.update(branchsGoals);
    }

    @Override
    public BranchsGoals findById(Integer idBranch) {
        return branchsGoalsDao.findById(idBranch);
    }

    @Override
    public List<BranchsGoals> findAll() {
        return branchsGoalsDao.findAll();
    }

    @Override
    public boolean delete(BranchsGoals branchsGoals) {
        return branchsGoalsDao.delete(branchsGoals);
    }

    @Override
    public List<BranchsGoals> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Row headers = sheet.getRow(0);
        String[] headersToSkip = {
                "SUCURSAL", "META MES","META DIARIA",
                "1ER SEMANA", "2DA SEMANA", "3ER SEMANA",
                "4TA SEMANA", "5TA SEMANA"
        };

        for (int i = 0 ; i < 8 ;i++) {
            if (!headers.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell branchName = currentRow.getCell(0);
            Cell monthGoal = currentRow.getCell(1);
            Cell dayGoal = currentRow.getCell(2);
            Cell week1Goal = currentRow.getCell(3);
            Cell week2Goal = currentRow.getCell(4);
            Cell week3Goal = currentRow.getCell(5);
            Cell week4Goal = currentRow.getCell(6);
            Cell week5Goal = currentRow.getCell(7);

            if (branchName != null) {
                String clearBranchName = StringUtils.stripAccents(branchName.getStringCellValue());
                String branchNameClean= clearBranchName.replaceAll("\\W", "").toUpperCase();
                CBranchs branchs = cBranchsDao.findByName(branchNameClean);

                BranchsGoals branchsGoals = null;
                
                if (branchs != null) {
                     branchsGoals = branchsGoalsDao.findById(branchs.getIdBranch());
                }

                if (branchsGoals != null) {

                    if (monthGoal != null) {
                        branchsGoals.setGoalMonth((long) monthGoal.getNumericCellValue());
                    }
                    if (dayGoal != null) {
                        branchsGoals.setGoalDay((long) dayGoal.getNumericCellValue());
                    }
                    if (week1Goal != null) {
                        branchsGoals.setGoalWeek1((long) week1Goal.getNumericCellValue());
                    }
                    if (week2Goal != null) {
                        branchsGoals.setGoalWeek2((long) week2Goal.getNumericCellValue());
                    }
                    if (week3Goal != null) {
                        branchsGoals.setGoalWeek3((long) week3Goal.getNumericCellValue());
                    }
                    if (week4Goal != null) {
                        branchsGoals.setGoalWeek4((long) week4Goal.getNumericCellValue());
                    }
                    if (week5Goal != null) {
                        branchsGoals.setGoalWeek5((long) week5Goal.getNumericCellValue());
                    }

                    branchsGoalsDao.update(branchsGoals);
                } else {
                    
                    if (branchs != null){
                        BranchsGoals newBranchsGoals = new BranchsGoals();

                        newBranchsGoals.setIdBranch(branchs.getIdBranch());

                        if (monthGoal != null) {
                            newBranchsGoals.setGoalMonth((long) monthGoal.getNumericCellValue());
                        }
                        if (dayGoal != null) {
                            newBranchsGoals.setGoalDay((long) dayGoal.getNumericCellValue());
                        }
                        if (week1Goal != null) {
                            newBranchsGoals.setGoalWeek1((long) week1Goal.getNumericCellValue());
                        }
                        if (week2Goal != null) {
                            newBranchsGoals.setGoalWeek2((long) week2Goal.getNumericCellValue());
                        }
                        if (week3Goal != null) {
                            newBranchsGoals.setGoalWeek3((long) week3Goal.getNumericCellValue());
                        }
                        if (week4Goal != null) {
                            newBranchsGoals.setGoalWeek4((long) week4Goal.getNumericCellValue());
                        }
                        if (week5Goal != null) {
                            newBranchsGoals.setGoalWeek5((long) week5Goal.getNumericCellValue());
                        }

                        branchsGoalsDao.save(newBranchsGoals);
                    }
                }
            }
        }

        return branchsGoalsDao.findAll();
    }
}

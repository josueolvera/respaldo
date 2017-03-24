/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.dao.BudgetsDao;
import mx.bidg.model.*;
import mx.bidg.pojos.BudgetCategory;
import mx.bidg.pojos.BudgetSubcategory;
import mx.bidg.service.BudgetsService;
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
public class BudgetsServiceImpl implements BudgetsService {

    @Autowired
    BudgetsDao budgetsDao;

    @Override
    public Budgets saveBudget(Budgets budgets) {
        return budgetsDao.save(budgets);
    }

    @Override
    public Budgets findById(Integer idBudget) {
        return budgetsDao.findById(idBudget);
    }

    @Override
    public Budgets findByCombination(Integer idDistributor, Integer idArea, Integer idAccountingAccount) {
        return budgetsDao.findByCombination(new CDistributors(idDistributor), new CAreas(idArea),
                new AccountingAccounts(idAccountingAccount));
    }

    @Override
    public ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea) {
        return budgetsDao.findByGroupArea(idGroup, idArea);
    }

    @Override
    public List<Budgets> findByDistributorAndArea(Integer idDistributor, Integer idArea) {
        return budgetsDao.findByDistributorAndArea(idDistributor, idArea);
    }

    @Override
    public List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, Integer idBudgetCategory) {
        return budgetsDao.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);
    }

    @Override
    public List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise) {
        return budgetsDao.findByDistributorAreaAndEnterprise(idDistributor, idArea, idDwEnterprise);
    }

    @Override
    public List<Budgets> findByDistributor(Integer idDistributor) {
        return budgetsDao.findByDistributor(idDistributor);
    }

    @Override
    public void createReport(List<BudgetCategory> budgetCategories, CCostCenter costCenter, Integer year, OutputStream outputStream) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Font font1 = wb.createFont();
        font1.setBold(true);
        font1.setFontHeightInPoints((short) 11);
        font1.setFontName("Arial");
        font1.setColor(IndexedColors.WHITE.getIndex());
        Font font2 = wb.createFont();
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 10);
        font2.setFontName("Arial");
        font2.setColor(IndexedColors.BLACK.getIndex());
        CellStyle style1 = wb.createCellStyle();
        style1.setFont(font1);
        style1.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(CellStyle.BORDER_THIN);
        style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderRight(CellStyle.BORDER_THIN);
        style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderLeft(CellStyle.BORDER_THIN);
        style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderTop(CellStyle.BORDER_THIN);
        style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
        CellStyle style2 = wb.createCellStyle();
        style2.setFont(font2);
        style2.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(CellStyle.BORDER_THIN);
        style2.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style2.setBorderRight(CellStyle.BORDER_THIN);
        style2.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style2.setBorderLeft(CellStyle.BORDER_THIN);
        style2.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style2.setBorderTop(CellStyle.BORDER_THIN);
        style2.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        CellStyle style3 = wb.createCellStyle();
        style3.setFont(font2);
        style3.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(CellStyle.BORDER_THIN);
        style3.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style3.setBorderRight(CellStyle.BORDER_THIN);
        style3.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style3.setBorderLeft(CellStyle.BORDER_THIN);
        style3.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style3.setBorderTop(CellStyle.BORDER_THIN);
        style3.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        CellStyle style4 = wb.createCellStyle();
        style4.setFont(font2);
        style4.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style4.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(CellStyle.BORDER_THIN);
        style4.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style4.setBorderRight(CellStyle.BORDER_THIN);
        style4.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style4.setBorderLeft(CellStyle.BORDER_THIN);
        style4.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style4.setBorderTop(CellStyle.BORDER_THIN);
        style4.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());

        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue(costCenter.getName());
        row.createCell(1).setCellValue("ENERO");
        row.createCell(2).setCellValue("FEBRERO");
        row.createCell(3).setCellValue("MARZO");
        row.createCell(4).setCellValue("ABRIL");
        row.createCell(5).setCellValue("MAYO");
        row.createCell(6).setCellValue("JUNIO");
        row.createCell(7).setCellValue("JULIO");
        row.createCell(8).setCellValue("AGOSTO");
        row.createCell(9).setCellValue("SEPTIEMBRE");
        row.createCell(10).setCellValue("OCTUBRE");
        row.createCell(11).setCellValue("NOVIEMBRE");
        row.createCell(12).setCellValue("DICIEMBRE");
        row.createCell(13).setCellValue("ACUMULADO PPTO " + year);
        for (Cell celda : row) {
            celda.setCellStyle(style1);
        }
        int aux = 1;
        for (BudgetCategory budgetCategory : budgetCategories) {
            Row row1 = sheet.createRow(aux);
            row1.createCell(0).setCellValue("    " + budgetCategory.getName());
            /*row1.createCell(1).setCellValue(budgetCategory.getJanuaryCategoryAmount().doubleValue());
            row1.createCell(2).setCellValue(budgetCategory.getFebruaryCategoryAmount().doubleValue());
            row1.createCell(3).setCellValue(budgetCategory.getMarchCategoryAmount().doubleValue());
            row1.createCell(4).setCellValue(budgetCategory.getAprilCategoryAmount().doubleValue());
            row1.createCell(5).setCellValue(budgetCategory.getMayCategoryAmount().doubleValue());
            row1.createCell(6).setCellValue(budgetCategory.getJuneCategoryAmount().doubleValue());
            row1.createCell(7).setCellValue(budgetCategory.getJulyCategoryAmount().doubleValue());
            row1.createCell(8).setCellValue(budgetCategory.getAugustCategoryAmount().doubleValue());
            row1.createCell(9).setCellValue(budgetCategory.getSeptemberCategoryAmount().doubleValue());
            row1.createCell(10).setCellValue(budgetCategory.getOctoberCategoryAmount().doubleValue());
            row1.createCell(11).setCellValue(budgetCategory.getNovemberCategoryAmount().doubleValue());
            row1.createCell(12).setCellValue(budgetCategory.getDecemberCategoryAmount().doubleValue());
            row1.createCell(13).setCellValue(budgetCategory.getTotalCategoryAmount().doubleValue());*/
            for (Cell celda : row1) {
                celda.setCellStyle(style2);
            }
            aux++;
            /*for (RealBudgetSpending realBudgetSpending : budgetCategory.getRealBudgetSpendings()) {
                Row row2 = sheet.createRow(aux);
                //row2.createCell(0).setCellValue("       " + realBudgetSpending.getBudget().getAccountingAccount().getBudgetSubcategory().getBudgetSubcategory());
                row2.createCell(1).setCellValue(realBudgetSpending.getJanuaryBudgetAmount().doubleValue());
                row2.createCell(2).setCellValue(realBudgetSpending.getFebruaryBudgetAmount().doubleValue());
                row2.createCell(3).setCellValue(realBudgetSpending.getMarchBudgetAmount().doubleValue());
                row2.createCell(4).setCellValue(realBudgetSpending.getAprilBudgetAmount().doubleValue());
                row2.createCell(5).setCellValue(realBudgetSpending.getMayBudgetAmount().doubleValue());
                row2.createCell(6).setCellValue(realBudgetSpending.getJuneBudgetAmount().doubleValue());
                row2.createCell(7).setCellValue(realBudgetSpending.getJulyBudgetAmount().doubleValue());
                row2.createCell(8).setCellValue(realBudgetSpending.getAugustBudgetAmount().doubleValue());
                row2.createCell(9).setCellValue(realBudgetSpending.getSeptemberBudgetAmount().doubleValue());
                row2.createCell(10).setCellValue(realBudgetSpending.getOctoberBudgetAmount().doubleValue());
                row2.createCell(11).setCellValue(realBudgetSpending.getNovemberBudgetAmount().doubleValue());
                row2.createCell(12).setCellValue(realBudgetSpending.getDecemberBudgetAmount().doubleValue());
                row2.createCell(13).setCellValue(realBudgetSpending.getTotalBudgetAmount().doubleValue());
                for (Cell celda : row2) {
                    celda.setCellStyle(style3);
                }
                aux++;
                /*for (RealBudgetSpending realBudgetSpendings : budgetCategory.getRealBudgetSpendings()) {
                    Row row3 = sheet.createRow(aux);
                    //row3.createCell(0).setCellValue("       " + realBudgetSpendings.getBudget().getAccountingAccount().getcBudgetSubSubcategories().getName());
                    row3.createCell(1).setCellValue(realBudgetSpendings.getJanuaryBudgetAmount().doubleValue());
                    row3.createCell(2).setCellValue(realBudgetSpendings.getFebruaryBudgetAmount().doubleValue());
                    row3.createCell(3).setCellValue(realBudgetSpendings.getMarchBudgetAmount().doubleValue());
                    row3.createCell(4).setCellValue(realBudgetSpendings.getAprilBudgetAmount().doubleValue());
                    row3.createCell(5).setCellValue(realBudgetSpendings.getMayBudgetAmount().doubleValue());
                    row3.createCell(6).setCellValue(realBudgetSpendings.getJuneBudgetAmount().doubleValue());
                    row3.createCell(7).setCellValue(realBudgetSpendings.getJulyBudgetAmount().doubleValue());
                    row3.createCell(8).setCellValue(realBudgetSpendings.getAugustBudgetAmount().doubleValue());
                    row3.createCell(9).setCellValue(realBudgetSpendings.getSeptemberBudgetAmount().doubleValue());
                    row3.createCell(10).setCellValue(realBudgetSpendings.getOctoberBudgetAmount().doubleValue());
                    row3.createCell(11).setCellValue(realBudgetSpendings.getNovemberBudgetAmount().doubleValue());
                    row3.createCell(12).setCellValue(realBudgetSpendings.getDecemberBudgetAmount().doubleValue());
                    row3.createCell(13).setCellValue(realBudgetSpendings.getTotalBudgetAmount().doubleValue());
                    for (Cell celda : row3) {
                        celda.setCellStyle(style4);
                    }
                    aux++;
                }
            }*/
        }
        /*sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(2, true);
        sheet.autoSizeColumn(3, true);
        sheet.autoSizeColumn(4, true);
        sheet.autoSizeColumn(5, true);
        sheet.autoSizeColumn(6, true);
        sheet.autoSizeColumn(7, true);
        sheet.autoSizeColumn(8, true);
        sheet.autoSizeColumn(9, true);
        sheet.autoSizeColumn(10, true);
        sheet.autoSizeColumn(11, true);
        sheet.autoSizeColumn(12, true);
        sheet.autoSizeColumn(13, true);
        wb.write(outputStream);*/
    }

    @Override
    public ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise) {
        return budgetsDao.findByGroupAreaEnterprise(idGroup, idArea, idDwEnterprise);
    }

    @Override
    public ArrayList<Budgets> findByCostCenter(Integer idCostCenter) {
        return budgetsDao.findByCostCenter(idCostCenter);
    }

    @Override
    public List<Budgets> getBudgetsfindNatureTypeAndCostCenter(Integer idCostCenter,Integer idBudgetType, Integer idBudgetNature) {
        return budgetsDao.getBudgetsfindNatureTypeAndCostCenter(idCostCenter,idBudgetType,idBudgetNature);
    }

    @Override
    public Budgets getBudgetByNatureAndCostAndTypeAndConcept(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, Integer idConceptBudget) {
        return budgetsDao.getBudgetByNatureAndCostAndTypeAndConcept(idCostCenter, idBudgetType, idBudgetNature, idConceptBudget);
    }

    @Override
    public Budgets update(Budgets budgets) {
        return budgetsDao.update(budgets);
    }

    @Override
    public List<Budgets> findByIdDistributorCostCenter(Integer idDistributorCostCenter, Integer idBudgetType, Integer idBudgetNature) {
        return budgetsDao.findByIdDistributorCostCenter(idDistributorCostCenter, idBudgetType, idBudgetNature);
    }

    @Override
    public Budgets findByNatureTypeAndDistributor(Integer idBudgetNature, Integer idBudgetType, Integer idDistributorCostCenter) {
        return budgetsDao.findByNatureTypeAndDistributor(idBudgetNature,idBudgetType,idDistributorCostCenter);
    }

    @Override
    public List<Budgets> findByIdDistributor(Integer idDistributorCostCenter) {
        return budgetsDao.findByIdDistributor(idDistributorCostCenter);
    }

}

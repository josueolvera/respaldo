/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.dao.BudgetsDao;
import mx.bidg.model.*;
import mx.bidg.pojos.*;
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
    public void createReport(List<BussinessLine> bussinessLines, Integer year, FileOutputStream outputStream) throws IOException {
        Workbook wb = new XSSFWorkbook();

        Font font1 = wb.createFont();
        font1.setBold(true);
        font1.setFontHeightInPoints((short) 11);
        font1.setFontName("Arial");
        font1.setColor(IndexedColors.WHITE.getIndex());

        Font font2 = wb.createFont();
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 11);
        font2.setFontName("Arial");
        font2.setColor(IndexedColors.BLACK.getIndex());

        Font font3 = wb.createFont();
        font3.setBold(true);
        font3.setFontHeightInPoints((short) 12);
        font3.setFontName("Arial");
        font3.setColor(IndexedColors.BLACK.getIndex());

        Font font4 = wb.createFont();
        font4.setBold(true);
        font4.setFontHeightInPoints((short) 10);
        font4.setFontName("Arial");
        font4.setColor(IndexedColors.BLACK.getIndex());

        Font font5 = wb.createFont();
        font5.setFontHeightInPoints((short) 10);
        font5.setFontName("Arial");
        font5.setColor(IndexedColors.BLACK.getIndex());

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

        style2.setFont(font1);
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

        style4.setFont(font4);
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


        CellStyle style5 = wb.createCellStyle();

        style5.setFont(font4);
        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style5.setBorderBottom(CellStyle.BORDER_THIN);
        style5.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setBorderRight(CellStyle.BORDER_THIN);
        style5.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setBorderLeft(CellStyle.BORDER_THIN);
        style5.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setBorderTop(CellStyle.BORDER_THIN);
        style5.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        CellStyle style6 = wb.createCellStyle();

        style6.setFont(font4);

        CellStyle style7 = wb.createCellStyle();

        style7.setFont(font5);

        CellStyle style8 = wb.createCellStyle();

        style8.setFont(font3);

        Sheet sheet = wb.createSheet();

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("                             ");
        row.createCell(1).setCellValue("ACUMULADO PPTO AÑO ANTERIOR " + (year-1));
        row.createCell(2).setCellValue("ENERO");
        row.createCell(3).setCellValue("FEBRERO");
        row.createCell(4).setCellValue("MARZO");
        row.createCell(5).setCellValue("ABRIL");
        row.createCell(6).setCellValue("MAYO");
        row.createCell(7).setCellValue("JUNIO");
        row.createCell(8).setCellValue("JULIO");
        row.createCell(9).setCellValue("AGOSTO");
        row.createCell(10).setCellValue("SEPTIEMBRE");
        row.createCell(11).setCellValue("OCTUBRE");
        row.createCell(12).setCellValue("NOVIEMBRE");
        row.createCell(13).setCellValue("DICIEMBRE");
        row.createCell(14).setCellValue("ACUMULADO PPTO " + year);

        for (Cell celda : row) {
            celda.setCellStyle(style8);
        }

        int aux = 1;

        for (BussinessLine bussinessLine : bussinessLines) {
            Row row1 = sheet.createRow(aux);
            row1.createCell(0).setCellValue("    " + bussinessLine.getName());
            row1.createCell(1).setCellValue(bussinessLine.getTotalBudgetAmountLastYear().doubleValue());
            row1.createCell(2).setCellValue(bussinessLine.getJanuaryAmount().doubleValue());
            row1.createCell(3).setCellValue(bussinessLine.getFebruaryAmount().doubleValue());
            row1.createCell(4).setCellValue(bussinessLine.getMarchAmount().doubleValue());
            row1.createCell(5).setCellValue(bussinessLine.getAprilAmount().doubleValue());
            row1.createCell(6).setCellValue(bussinessLine.getMayAmount().doubleValue());
            row1.createCell(7).setCellValue(bussinessLine.getJuneAmount().doubleValue());
            row1.createCell(8).setCellValue(bussinessLine.getJulyAmount().doubleValue());
            row1.createCell(9).setCellValue(bussinessLine.getAugustAmount().doubleValue());
            row1.createCell(10).setCellValue(bussinessLine.getSeptemberAmount().doubleValue());
            row1.createCell(11).setCellValue(bussinessLine.getOctoberAmount().doubleValue());
            row1.createCell(12).setCellValue(bussinessLine.getNovemberAmount().doubleValue());
            row1.createCell(13).setCellValue(bussinessLine.getDecemberAmount().doubleValue());
            row1.createCell(14).setCellValue(bussinessLine.getTotalAmount().doubleValue());
            for (Cell celda : row1) {
                celda.setCellStyle(style1);
            }
            aux++;
            if (!bussinessLine.getDistributorList().isEmpty()){
                for (Distributor distributor : bussinessLine.getDistributorList()) {
                    Row row2 = sheet.createRow(aux);
                    row2.createCell(0).setCellValue("       " + distributor.getName());
                    row2.createCell(1).setCellValue(distributor.getTotalBudgetAmountLastYear().doubleValue());
                    row2.createCell(2).setCellValue(distributor.getJanuaryAmount().doubleValue());
                    row2.createCell(3).setCellValue(distributor.getFebruaryAmount().doubleValue());
                    row2.createCell(4).setCellValue(distributor.getMarchAmount().doubleValue());
                    row2.createCell(5).setCellValue(distributor.getAprilAmount().doubleValue());
                    row2.createCell(6).setCellValue(distributor.getMayAmount().doubleValue());
                    row2.createCell(7).setCellValue(distributor.getJuneAmount().doubleValue());
                    row2.createCell(8).setCellValue(distributor.getJulyAmount().doubleValue());
                    row2.createCell(9).setCellValue(distributor.getAugustAmount().doubleValue());
                    row2.createCell(10).setCellValue(distributor.getSeptemberAmount().doubleValue());
                    row2.createCell(11).setCellValue(distributor.getOctoberAmount().doubleValue());
                    row2.createCell(12).setCellValue(distributor.getNovemberAmount().doubleValue());
                    row2.createCell(13).setCellValue(distributor.getDecemberAmount().doubleValue());
                    row2.createCell(14).setCellValue(distributor.getTotalAmount().doubleValue());
                    for (Cell celda : row2) {
                        celda.setCellStyle(style2);
                    }
                    aux++;
                    if(!distributor.getCostCenterList().isEmpty()){
                        for (CostCenter costCenter : distributor.getCostCenterList()){
                            Row row3 = sheet.createRow(aux);
                            row3.createCell(0).setCellValue("          " + costCenter.getName());
                            row3.createCell(1).setCellValue(costCenter.getTotalBudgetAmountLastYear().doubleValue());
                            row3.createCell(2).setCellValue(costCenter.getJanuaryAmount().doubleValue());
                            row3.createCell(3).setCellValue(costCenter.getFebruaryAmount().doubleValue());
                            row3.createCell(4).setCellValue(costCenter.getMarchAmount().doubleValue());
                            row3.createCell(5).setCellValue(costCenter.getAprilAmount().doubleValue());
                            row3.createCell(6).setCellValue(costCenter.getMayAmount().doubleValue());
                            row3.createCell(7).setCellValue(costCenter.getJuneAmount().doubleValue());
                            row3.createCell(8).setCellValue(costCenter.getJulyAmount().doubleValue());
                            row3.createCell(9).setCellValue(costCenter.getAugustAmount().doubleValue());
                            row3.createCell(10).setCellValue(costCenter.getSeptemberAmount().doubleValue());
                            row3.createCell(11).setCellValue(costCenter.getOctoberAmount().doubleValue());
                            row3.createCell(12).setCellValue(costCenter.getNovemberAmount().doubleValue());
                            row3.createCell(13).setCellValue(costCenter.getDecemberAmount().doubleValue());
                            row3.createCell(14).setCellValue(costCenter.getTotalAmount().doubleValue());
                            for (Cell celda : row3) {
                                celda.setCellStyle(style3);
                            }
                            aux++;
                            if (!costCenter.getBudgetCategories().isEmpty()){
                                for (BudgetCategory budgetCategory : costCenter.getBudgetCategories()){
                                    Row row4 = sheet.createRow(aux);
                                    row4.createCell(0).setCellValue("             " + budgetCategory.getName());
                                    row4.createCell(1).setCellValue(budgetCategory.getTotalBudgetAmountLastYear().doubleValue());
                                    row4.createCell(2).setCellValue(budgetCategory.getJanuaryCategoryAmount().doubleValue());
                                    row4.createCell(3).setCellValue(budgetCategory.getFebruaryCategoryAmount().doubleValue());
                                    row4.createCell(4).setCellValue(budgetCategory.getMarchCategoryAmount().doubleValue());
                                    row4.createCell(5).setCellValue(budgetCategory.getAprilCategoryAmount().doubleValue());
                                    row4.createCell(6).setCellValue(budgetCategory.getMayCategoryAmount().doubleValue());
                                    row4.createCell(7).setCellValue(budgetCategory.getJuneCategoryAmount().doubleValue());
                                    row4.createCell(8).setCellValue(budgetCategory.getJulyCategoryAmount().doubleValue());
                                    row4.createCell(9).setCellValue(budgetCategory.getAugustCategoryAmount().doubleValue());
                                    row4.createCell(10).setCellValue(budgetCategory.getSeptemberCategoryAmount().doubleValue());
                                    row4.createCell(11).setCellValue(budgetCategory.getOctoberCategoryAmount().doubleValue());
                                    row4.createCell(12).setCellValue(budgetCategory.getNovemberCategoryAmount().doubleValue());
                                    row4.createCell(13).setCellValue(budgetCategory.getDecemberCategoryAmount().doubleValue());
                                    row4.createCell(14).setCellValue(budgetCategory.getTotalCategoryAmount().doubleValue());
                                    for (Cell celda : row4) {
                                        celda.setCellStyle(style4);
                                    }
                                    aux++;
                                    if (budgetCategory.getRealBudgetSpendings()!= null){
                                        if (!budgetCategory.getRealBudgetSpendings().isEmpty()){
                                            for (RealBudgetSpending conceptFirstLevel : budgetCategory.getRealBudgetSpendings()){
                                                Row row5 = sheet.createRow(aux);
                                                row5.createCell(0).setCellValue("                  " + conceptFirstLevel.getBudget().getConceptBudget().getNameConcept());
                                                row5.createCell(1).setCellValue(conceptFirstLevel.getTotalLastYearAmount().doubleValue());
                                                row5.createCell(2).setCellValue(conceptFirstLevel.getJanuaryBudgetAmount().doubleValue());
                                                row5.createCell(3).setCellValue(conceptFirstLevel.getFebruaryBudgetAmount().doubleValue());
                                                row5.createCell(4).setCellValue(conceptFirstLevel.getMarchBudgetAmount().doubleValue());
                                                row5.createCell(5).setCellValue(conceptFirstLevel.getAprilBudgetAmount().doubleValue());
                                                row5.createCell(6).setCellValue(conceptFirstLevel.getMayBudgetAmount().doubleValue());
                                                row5.createCell(7).setCellValue(conceptFirstLevel.getJuneBudgetAmount().doubleValue());
                                                row5.createCell(8).setCellValue(conceptFirstLevel.getJulyBudgetAmount().doubleValue());
                                                row5.createCell(9).setCellValue(conceptFirstLevel.getAugustBudgetAmount().doubleValue());
                                                row5.createCell(10).setCellValue(conceptFirstLevel.getSeptemberBudgetAmount().doubleValue());
                                                row5.createCell(11).setCellValue(conceptFirstLevel.getOctoberBudgetAmount().doubleValue());
                                                row5.createCell(12).setCellValue(conceptFirstLevel.getNovemberBudgetAmount().doubleValue());
                                                row5.createCell(13).setCellValue(conceptFirstLevel.getDecemberBudgetAmount().doubleValue());
                                                row5.createCell(14).setCellValue(conceptFirstLevel.getTotalBudgetAmount().doubleValue());
                                                for (Cell celda : row5) {
                                                    celda.setCellStyle(style7);
                                                }
                                                aux++;
                                            }
                                        }
                                        if (budgetCategory.getBudgetSubcategories()!= null){
                                            if (!budgetCategory.getBudgetSubcategories().isEmpty()){
                                                for (BudgetSubcategory budgetSubcategory : budgetCategory.getBudgetSubcategories()){
                                                    Row row6 = sheet.createRow(aux);
                                                    row6.createCell(0).setCellValue("                " + budgetSubcategory.getName());
                                                    row6.createCell(1).setCellValue(budgetSubcategory.getTotalBudgetAmountLastYear().doubleValue());
                                                    row6.createCell(2).setCellValue(budgetSubcategory.getJanuarySubcategoryAmount().doubleValue());
                                                    row6.createCell(3).setCellValue(budgetSubcategory.getFebruarySubcategoryAmount().doubleValue());
                                                    row6.createCell(4).setCellValue(budgetSubcategory.getMarchSubcategoryAmount().doubleValue());
                                                    row6.createCell(5).setCellValue(budgetSubcategory.getAprilSubcategoryAmount().doubleValue());
                                                    row6.createCell(6).setCellValue(budgetSubcategory.getMaySubcategoryAmount().doubleValue());
                                                    row6.createCell(7).setCellValue(budgetSubcategory.getJuneSubcategoryAmount().doubleValue());
                                                    row6.createCell(8).setCellValue(budgetSubcategory.getJulySubcategoryAmount().doubleValue());
                                                    row6.createCell(9).setCellValue(budgetSubcategory.getAugustSubcategoryAmount().doubleValue());
                                                    row6.createCell(10).setCellValue(budgetSubcategory.getSeptemberSubcategoryAmount().doubleValue());
                                                    row6.createCell(11).setCellValue(budgetSubcategory.getOctoberSubcategoryAmount().doubleValue());
                                                    row6.createCell(12).setCellValue(budgetSubcategory.getNovemberSubcategoryAmount().doubleValue());
                                                    row6.createCell(13).setCellValue(budgetSubcategory.getDecemberSubcategoryAmount().doubleValue());
                                                    row6.createCell(14).setCellValue(budgetSubcategory.getTotalSubcategoryAmount().doubleValue());
                                                    for (Cell celda : row6) {
                                                        celda.setCellStyle(style5);
                                                    }
                                                    aux++;
                                                    if (budgetSubcategory.getRealBudgetSpendings() != null){
                                                        if (!budgetSubcategory.getRealBudgetSpendings().isEmpty()){
                                                            for (RealBudgetSpending conceptSecondLevel : budgetSubcategory.getRealBudgetSpendings()){
                                                                Row row7 = sheet.createRow(aux);
                                                                row7.createCell(0).setCellValue("                     " + conceptSecondLevel.getBudget().getConceptBudget().getNameConcept());
                                                                row7.createCell(1).setCellValue(conceptSecondLevel.getTotalLastYearAmount().doubleValue());
                                                                row7.createCell(2).setCellValue(conceptSecondLevel.getJanuaryBudgetAmount().doubleValue());
                                                                row7.createCell(3).setCellValue(conceptSecondLevel.getFebruaryBudgetAmount().doubleValue());
                                                                row7.createCell(4).setCellValue(conceptSecondLevel.getMarchBudgetAmount().doubleValue());
                                                                row7.createCell(5).setCellValue(conceptSecondLevel.getAprilBudgetAmount().doubleValue());
                                                                row7.createCell(6).setCellValue(conceptSecondLevel.getMayBudgetAmount().doubleValue());
                                                                row7.createCell(7).setCellValue(conceptSecondLevel.getJuneBudgetAmount().doubleValue());
                                                                row7.createCell(8).setCellValue(conceptSecondLevel.getJulyBudgetAmount().doubleValue());
                                                                row7.createCell(9).setCellValue(conceptSecondLevel.getAugustBudgetAmount().doubleValue());
                                                                row7.createCell(10).setCellValue(conceptSecondLevel.getSeptemberBudgetAmount().doubleValue());
                                                                row7.createCell(11).setCellValue(conceptSecondLevel.getOctoberBudgetAmount().doubleValue());
                                                                row7.createCell(12).setCellValue(conceptSecondLevel.getNovemberBudgetAmount().doubleValue());
                                                                row7.createCell(13).setCellValue(conceptSecondLevel.getDecemberBudgetAmount().doubleValue());
                                                                row7.createCell(14).setCellValue(conceptSecondLevel.getTotalBudgetAmount().doubleValue());
                                                                for (Cell celda : row7) {
                                                                    celda.setCellStyle(style7);
                                                                }
                                                                aux++;
                                                            }
                                                        }
                                                    }
                                                    if (budgetSubcategory.getBudgetSubSubCategories() != null){
                                                        if (!budgetSubcategory.getBudgetSubSubCategories().isEmpty()){
                                                            for (BudgetSubSubCategory budgetSubSubCategory : budgetSubcategory.getBudgetSubSubCategories()){
                                                                Row row8 = sheet.createRow(aux);
                                                                row8.createCell(0).setCellValue("                   " + budgetSubSubCategory.getName());
                                                                row8.createCell(1).setCellValue(budgetSubSubCategory.getTotalBudgetAmountLastYear().doubleValue());
                                                                row8.createCell(2).setCellValue(budgetSubSubCategory.getJanuarySubSubcategoryAmount().doubleValue());
                                                                row8.createCell(3).setCellValue(budgetSubSubCategory.getFebruarySubSubcategoryAmount().doubleValue());
                                                                row8.createCell(4).setCellValue(budgetSubSubCategory.getMarchSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(5).setCellValue(budgetSubSubCategory.getAprilSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(6).setCellValue(budgetSubSubCategory.getMaySubSubcategoryAmount().doubleValue());
                                                                row8.createCell(7).setCellValue(budgetSubSubCategory.getJuneSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(8).setCellValue(budgetSubSubCategory.getJulySubSubcategoryAmount().doubleValue());
                                                                row8.createCell(9).setCellValue(budgetSubSubCategory.getAugustSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(10).setCellValue(budgetSubSubCategory.getSeptemberSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(11).setCellValue(budgetSubSubCategory.getOctoberSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(12).setCellValue(budgetSubSubCategory.getNovemberSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(13).setCellValue(budgetSubSubCategory.getDecemberSubSubcategoryAmount().doubleValue());
                                                                row8.createCell(14).setCellValue(budgetSubSubCategory.getTotalSubSubcategoryAmount().doubleValue());
                                                                for (Cell celda : row8) {
                                                                    celda.setCellStyle(style6);
                                                                }
                                                                aux++;
                                                                if (budgetSubSubCategory.getRealBudgetSpendingList() != null){
                                                                    if (!budgetSubSubCategory.getRealBudgetSpendingList().isEmpty()){
                                                                        for (RealBudgetSpending conceptThirdLevel : budgetSubSubCategory.getRealBudgetSpendingList()){
                                                                            Row row9 = sheet.createRow(aux);
                                                                            row9.createCell(0).setCellValue("                        " + conceptThirdLevel.getBudget().getConceptBudget().getNameConcept());
                                                                            row9.createCell(1).setCellValue(conceptThirdLevel.getTotalLastYearAmount().doubleValue());
                                                                            row9.createCell(2).setCellValue(conceptThirdLevel.getJanuaryBudgetAmount().doubleValue());
                                                                            row9.createCell(3).setCellValue(conceptThirdLevel.getFebruaryBudgetAmount().doubleValue());
                                                                            row9.createCell(4).setCellValue(conceptThirdLevel.getMarchBudgetAmount().doubleValue());
                                                                            row9.createCell(5).setCellValue(conceptThirdLevel.getAprilBudgetAmount().doubleValue());
                                                                            row9.createCell(6).setCellValue(conceptThirdLevel.getMayBudgetAmount().doubleValue());
                                                                            row9.createCell(7).setCellValue(conceptThirdLevel.getJuneBudgetAmount().doubleValue());
                                                                            row9.createCell(8).setCellValue(conceptThirdLevel.getJulyBudgetAmount().doubleValue());
                                                                            row9.createCell(9).setCellValue(conceptThirdLevel.getAugustBudgetAmount().doubleValue());
                                                                            row9.createCell(10).setCellValue(conceptThirdLevel.getSeptemberBudgetAmount().doubleValue());
                                                                            row9.createCell(11).setCellValue(conceptThirdLevel.getOctoberBudgetAmount().doubleValue());
                                                                            row9.createCell(12).setCellValue(conceptThirdLevel.getNovemberBudgetAmount().doubleValue());
                                                                            row9.createCell(13).setCellValue(conceptThirdLevel.getDecemberBudgetAmount().doubleValue());
                                                                            row9.createCell(14).setCellValue(conceptThirdLevel.getTotalBudgetAmount().doubleValue());
                                                                            for (Cell celda : row9) {
                                                                                celda.setCellStyle(style7);
                                                                            }
                                                                            aux++;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        sheet.autoSizeColumn(0, true);
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
        sheet.autoSizeColumn(14, true);
        wb.write(outputStream);
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

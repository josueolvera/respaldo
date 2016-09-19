/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBranchsServiceImpl implements CBranchsService {
    
    @Autowired
    CBranchsDao cBranchsDao;

    @Autowired
    DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    DwEmployeesDao dwEmployeesDao;

    @Autowired
    EmployeesDao employeesDao;

    @Autowired
    EmployeesAccountsService employeesAccountsService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    EmployeesHistoryService employeesHistoryService;

    @Autowired
    EmailDeliveryService emailDeliveryService;

    @Autowired
    EmailTemplatesService emailTemplatesService;

    @Autowired
    DwEnterprisesService dwEnterprisesService;

    @Autowired
    DwBranchsDao dwBranchsDao;

    @Override
    public List<CBranchs> findAll() {
        return cBranchsDao.findAll();
    }

    @Override
    public CBranchs findById(int idBranch) {
        return cBranchsDao.findById(idBranch);
    }

    @Override
    public boolean delete(int idBranch) {
        return cBranchsDao.delete(cBranchsDao.findById(idBranch));
    }

    @Override
    public CBranchs update(CBranchs cBranchs) {
        return cBranchsDao.update(cBranchs);
    }

    @Override
    public CBranchs save(CBranchs cBranchs, int idDistributor ,int idRegion, int idZona) {
        cBranchs.setUploadedDate(LocalDateTime.now());
        cBranchs.setBranchName(cBranchs.getBranchName().toUpperCase());
        cBranchs.setBranchShort(cBranchs.getBranchShort().toUpperCase());
        String clearBranchName = StringUtils.stripAccents(cBranchs.getBranchName());
        String branchNameClean= clearBranchName.replaceAll("\\W", "").toUpperCase();
        cBranchs.setBranchNameClean(branchNameClean);
        cBranchs.setStatus(true);
        cBranchs.setSaemFlag(1);
        cBranchs = cBranchsDao.save(cBranchs);
        DwEnterprises dwEnterprises = new DwEnterprises();
        dwEnterprises.setBranch(cBranchs);
        dwEnterprises.setDistributor(new CDistributors(idDistributor));
        dwEnterprises.setRegion(new CRegions(idRegion));
        dwEnterprises.setZona(new CZonas(idZona));
        dwEnterprises.setGroup(new CGroups(1));
        dwEnterprises.setArea(new CAreas(5));
        dwEnterprises.setBudgetable(1);
        dwEnterprises.setStatus(true);
        dwEnterprisesDao.save(dwEnterprises);
        DwBranchs dwBranchs = new DwBranchs();
        dwBranchs.setIdBranch(cBranchs.getIdBranch());
        dwBranchs.setStatus(0);
        dwBranchs.setUploadedDate(LocalDateTime.now());
        dwBranchsDao.save(dwBranchs);

        return cBranchs;
    }

    @Override
    public CBranchs changeBranchStatus(int idBranch, Users user) {
        CBranchs branch = cBranchsDao.findById(idBranch);
        List<DwEnterprises> dwEnterprises = branch.getDwEnterprises();
        List<DwEmployees> dwEmployees;
        for (DwEnterprises dwEnterprise : dwEnterprises) {

            if (dwEnterprise.getStatus()) {
                dwEnterprise.setStatus(false);
                dwEnterprisesDao.update(dwEnterprise);
                dwEmployees = dwEmployeesDao.findByDwEnterprise(dwEnterprise.getIdDwEnterprise());
                for (DwEmployees dwEmployee : dwEmployees) {
                    Employees employee = employeesDao.findById(dwEmployee.getIdEmployee());
                    EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());
                    Accounts accounts = employeesAccounts.getAccount();
                    employee.setStatus(0);
                    employeesDao.update(employee);
                    employeesHistoryService.save(dwEmployee, CActionTypes.BAJA, accounts, user);

                    dwEmployeesDao.delete(dwEmployee);

                    EmailTemplates emailTemplate = emailTemplatesService.findByName("employee_low_notification");
                    emailTemplate.addProperty("dwEmployee", dwEmployee);

                    emailDeliveryService.deliverEmail(emailTemplate);
                }
            }
        }

        branch.setStatus(false);
        cBranchsDao.update(branch);
        return branch;
    }

    @Override
    public List<CBranchs> findSaemFlag(Integer idBranch,Integer saemFlag) {
        return cBranchsDao.findBySaemFlag(idBranch,saemFlag);
    }

    @Override
    public void branchDistributorsReport(OutputStream stream) throws IOException {

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesService.findDwEnterprisesByBranchSaem(0,1);

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

        if(dwEnterprisesList.size()>0) {
            //Se crea la fila que contiene la cabecera
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("ID SUCURSAL");
            row.createCell(1).setCellValue("NOMBRE");
            row.createCell(2).setCellValue("NOMBRE CORTO");
            row.createCell(3).setCellValue("DISTRIBUIDOR");
            row.createCell(4).setCellValue("REGION");
            row.createCell(5).setCellValue("ZONA");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }

            int aux = 1;

            for (DwEnterprises dwEnterprises : dwEnterprisesList) {

                row = hoja.createRow(aux);
                // Create a cell and put a value in it.
                row.createCell(0).setCellValue(dwEnterprises.getBranch().getIdBranch());
                row.createCell(1).setCellValue(dwEnterprises.getBranch().getBranchName());
                row.createCell(2).setCellValue(dwEnterprises.getBranch().getBranchShort());
                row.createCell(3).setCellValue(dwEnterprises.getDistributor().getDistributorName());
                row.createCell(4).setCellValue(dwEnterprises.getZona().getName());
                row.createCell(5).setCellValue(dwEnterprises.getRegion().getRegionName());

                aux++;
            }

            //Autoajustar al contenido
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);

            wb.write(stream);
        }else{
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("NO HAY SUCURSALES");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }
            hoja.autoSizeColumn(0);
            wb.write(stream);
        }

    }
}

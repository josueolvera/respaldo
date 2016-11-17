/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mx.bidg.dao.CommissionEmcofinDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CommissionEmcofin;
import mx.bidg.model.Employees;
import mx.bidg.model.Outsourcing;
import mx.bidg.model.Users;
import mx.bidg.service.CommissionEmcofinService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Kevin Salvador
 */

@Service
@Transactional
public class CommissionEmcofinServiceImp implements CommissionEmcofinService{

    @Autowired
    private CommissionEmcofinDao commissionEmcofinDao;
    
    @Autowired
    private EmployeesDao employeesDao;
    
    @Override
    public List<CommissionEmcofin> findAll() {
        return commissionEmcofinDao.findAll();
    }

    @Override
    public CommissionEmcofin findById(Integer id) {
        return commissionEmcofinDao.findById(id);
    }

    @Override
    public List<CommissionEmcofin> saveFromExcel(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        
        Sheet sheet = workbook.getSheetAt(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            
            Row currentRow = sheet.getRow(i);
            Cell idEmployee = currentRow.getCell(0);
            
            Cell commission = currentRow.getCell(2);
            
            CommissionEmcofin c = new CommissionEmcofin();
            if(idEmployee!=null){
                Employees employee = employeesDao.findById((int)idEmployee.getNumericCellValue());
                if(employee!=null){
                    c.setEmployee(employee);
                    
                }
                if(commission!=null){
                    if (commission.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdCommission = new BigDecimal(Integer.parseInt(commission.getStringCellValue()));
                            c.setCommission(bdCommission);
                        } else if (commission.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdCommission = new BigDecimal(commission.getNumericCellValue());
                            c.setCommission(bdCommission);
                        }
                }
            }
            
            c.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
            c.setCreationDate(LocalDateTime.now());
            c.setUsername(user.getUsername());
            c.setStatus(true);
            commissionEmcofinDao.save(c);
        }
        return commissionEmcofinDao.findAll();
    }

    @Override
    public List<CommissionEmcofin> updateFromExcel(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            Cell idEmployee = currentRow.getCell(0);
            Cell commission = currentRow.getCell(2);
            if(idEmployee!=null){
                CommissionEmcofin c = commissionEmcofinDao.finfByidEmployee((int)idEmployee.getNumericCellValue()
                        , LocalDateTime.parse(calculateDate + " 00:00", formatter));
                Employees employee = employeesDao.findById((int)idEmployee.getNumericCellValue());
                if (c != null){
                    if(employee!=null){
                        c.setEmployee(employee);
                    }
                    if(commission!=null){
                        if (commission.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdCommission = new BigDecimal(Integer.parseInt(commission.getStringCellValue()));
                            c.setCommission(bdCommission);
                        } else if (commission.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdCommission = new BigDecimal(commission.getNumericCellValue());
                            c.setCommission(bdCommission);
                        }
                    }
                    c.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
                    c.setCreationDate(LocalDateTime.now());
                    c.setUsername(user.getUsername());
                    c.setStatus(true);
                    commissionEmcofinDao.update(c);
                }
            }
        }
        return commissionEmcofinDao.findAll();
    }

    @Override
    public Boolean existsCommissionRecord(MultipartFile file, String calculateDate) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Row headerRow = sheet.getRow(0);
        String[] headersToSkip = {
            "Codigo", "Nombre", "Comision"
        };

        for (int i = 0; i < 2; i++) {
            if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        boolean existsCommission = false;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow != null) {
                Cell codigo = currentRow.getCell(0);
                CommissionEmcofin saveCommission;

                if (codigo != null) {

                    if (codigo.getCellType() == Cell.CELL_TYPE_STRING) {
                        break;
                    }

                    saveCommission = commissionEmcofinDao.finfByidEmployee(
                            (int) codigo.getNumericCellValue(),
                            LocalDateTime.parse(calculateDate + " 00:00", formatter)
                    );

                    if (saveCommission != null) {
                        existsCommission = true;
                    }
                }
            }
        }

        return existsCommission;
    }
    
}


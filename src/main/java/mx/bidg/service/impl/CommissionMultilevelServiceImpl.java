package mx.bidg.service.impl;

import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.CommissionMultilevelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by PC_YAIR on 17/11/2016.
 */
@Service
@Transactional
public class CommissionMultilevelServiceImpl implements CommissionMultilevelService {

    @Autowired
    private CommissionMultilevelDao commissionMultilevelDao;

    @Autowired
    private EmployeesHistoryDao employeesHistoryDao;

    @Autowired
    private DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    private EmployeesDao employeesDao;

    @Override
    public CommissionMultilevel findById(Integer id) {
        return commissionMultilevelDao.findById(id);
    }

    @Override
    public List<CommissionMultilevel> findAll() {
        return commissionMultilevelDao.findAll();
    }

    @Override
    public List<CommissionMultilevel> updateExcel(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            Cell idEmployee = currentRow.getCell(0);
            Cell rfc = currentRow.getCell(1);
            Cell monto = currentRow.getCell(2);
            if (idEmployee != null) {
                CommissionMultilevel c = commissionMultilevelDao.finfByidEmployee((int) idEmployee.getNumericCellValue()
                        , LocalDateTime.parse(calculateDate + " 00:00", formatter));
                Employees employee = employeesDao.findById((int) idEmployee.getNumericCellValue());

                if (c != null) {
                    if (employee != null) {

                        EmployeesHistory employeesHistory = employeesHistoryDao.findByIdEmployeeAndLastRegister((int) idEmployee.getNumericCellValue());

                        if (employeesHistory != null){
                            DwEnterprises dwEnterprises = dwEnterprisesDao.findById(employeesHistory.getIdDwEnterprise());

                            if (dwEnterprises != null){
                                c.setDwEmployees(dwEnterprises);
                            }

                            c.setEmployee(employee);
                        }
                    }
                    if (monto != null) {
                        if (monto.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdCommission = new BigDecimal(Integer.parseInt(monto.getStringCellValue()));
                            c.setMonto(bdCommission);
                        } else if (monto.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdCommission = new BigDecimal(monto.getNumericCellValue());
                            c.setMonto(bdCommission);
                        }
                    }
                    c.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
                    c.setCreationDate(LocalDateTime.now());
                    c.setUserName(user.getUsername());
                    commissionMultilevelDao.update(c);
                }
            }
        }
        return commissionMultilevelDao.findAll();
    }

    @Override
    public List<CommissionMultilevel> saveExcel(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row currentRow = sheet.getRow(i);
            Cell idEmployee = currentRow.getCell(0);
            Cell rfc = currentRow.getCell(1);
            Cell monto = currentRow.getCell(2);


            CommissionMultilevel c = new CommissionMultilevel();
            if (idEmployee != null) {

                CommissionMultilevel commissionMultilevel = commissionMultilevelDao.finfByidEmployee((int) idEmployee.getNumericCellValue()
                        , LocalDateTime.parse(calculateDate + " 00:00", formatter));

                if (commissionMultilevel == null){
                    Employees employee = employeesDao.findById((int) idEmployee.getNumericCellValue());
                    if (employee != null) {
                        EmployeesHistory employeesHistory = employeesHistoryDao.findByIdEmployeeAndLastRegister((int) idEmployee.getNumericCellValue());

                        if (employeesHistory != null){
                            DwEnterprises dwEnterprises = dwEnterprisesDao.findById(employeesHistory.getIdDwEnterprise());

                            if (dwEnterprises != null){
                                c.setDwEmployees(dwEnterprises);
                            }

                        }

                        c.setEmployee(employee);
                    }
                    if (monto != null) {
                        if (monto.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdCommission = new BigDecimal(Integer.parseInt(monto.getStringCellValue()));
                            c.setMonto(bdCommission);
                        } else if (monto.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdCommission = new BigDecimal(monto.getNumericCellValue());
                            c.setMonto(bdCommission);
                        }
                    }

                    c.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
                    c.setCreationDate(LocalDateTime.now());
                    c.setUserName(user.getUsername());
                    commissionMultilevelDao.save(c);
                }else{

                    EmployeesHistory employeesHistory = employeesHistoryDao.findByIdEmployeeAndLastRegister(commissionMultilevel.getIdEmployee());

                    if (employeesHistory != null){
                        DwEnterprises dwEnterprises = dwEnterprisesDao.findById(employeesHistory.getIdDwEnterprise());

                        if (dwEnterprises != null){
                            commissionMultilevel.setDwEmployees(dwEnterprises);
                        }

                    }
                    if (monto != null) {
                        if (monto.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdCommission = new BigDecimal(Integer.parseInt(monto.getStringCellValue()));
                            commissionMultilevel.setMonto(bdCommission);
                        } else if (monto.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdCommission = new BigDecimal(monto.getNumericCellValue());
                            commissionMultilevel.setMonto(bdCommission);
                        }
                    }

                    commissionMultilevel.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
                    commissionMultilevel.setCreationDate(LocalDateTime.now());
                    commissionMultilevel.setUserName(user.getUsername());
                    commissionMultilevelDao.update(commissionMultilevel);
                }

            }
        }
        return commissionMultilevelDao.findAll();
    }

    @Override
    public Boolean existsMultilevelRecord(MultipartFile file, String calculateDate) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Row headerRow = sheet.getRow(0);
        String[] headersToSkip = {
                "Código", "RFC", "Monto"
        };

        for (int i = 0; i < 3; i++) {
            if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        boolean existsMultilevel = false;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow != null) {
                Cell codigo = currentRow.getCell(0);
                CommissionMultilevel saveCommission;

                if (codigo != null) {

                    if (codigo.getCellType() == Cell.CELL_TYPE_STRING) {
                        break;
                    }


                    saveCommission = commissionMultilevelDao.finfByidEmployee(
                            (int) codigo.getNumericCellValue(),
                            LocalDateTime.parse(calculateDate + " 00:00", formatter)
                    );

                    if (saveCommission != null) {
                        existsMultilevel = true;
                    }
                }
            }
        }

        return existsMultilevel;
    }
}




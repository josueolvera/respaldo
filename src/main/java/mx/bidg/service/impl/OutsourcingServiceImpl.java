package mx.bidg.service.impl;

import mx.bidg.dao.EmployeesDao;
import mx.bidg.dao.OutsourcingDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.Employees;
import mx.bidg.model.Outsourcing;
import mx.bidg.service.OutsourcingService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@Transactional
@Service
public class OutsourcingServiceImpl implements OutsourcingService {

    @Autowired
    private OutsourcingDao outsourcingDao;

    @Autowired
    private EmployeesDao employeesDao;

    @Override
    public List<Outsourcing> findAll() {
        return outsourcingDao.findAll();
    }

    @Override
    public Outsourcing findById(Integer id) {
        return outsourcingDao.findById(id);
    }

    @Override
    public List<Outsourcing> saveFromExcel(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i=8;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);

            Cell departament = currentRow.getCell(0);
            Cell code = currentRow.getCell(1);
            Cell name = currentRow.getCell(2);
            Cell salary = currentRow.getCell(3);
            Cell subsidy = currentRow.getCell(4);
            Cell imssEmployee = currentRow.getCell(5);
            Cell isr = currentRow.getCell(6);
            Cell adjustment = currentRow.getCell(7);
            Cell totalDeduction = currentRow.getCell(8);
            Cell netAssetTax = currentRow.getCell(9);
            Cell imss = currentRow.getCell(10);
            Cell rcv = currentRow.getCell(11);
            Cell enterpriseInfonavit = currentRow.getCell(12);
            Cell payrollTax = currentRow.getCell(13);
            Cell totalSocialSecurity = currentRow.getCell(14);
            Cell commission = currentRow.getCell(15);
            Cell subtotal = currentRow.getCell(16);
            Cell iva = currentRow.getCell(17);
            Cell total = currentRow.getCell(18);



            Outsourcing outsourcing = new Outsourcing();

            if (code != null){
                Employees employee = employeesDao.findById((int) code.getNumericCellValue());

                if (employee != null){
                    outsourcing.setEmployee(employee);
                }
                if (salary != null){
                    BigDecimal bdSalary = new BigDecimal(salary.getNumericCellValue());
                    outsourcing.setSalary(bdSalary);
                }
                if (subsidy != null){
                    BigDecimal bdSubsidy = new BigDecimal(subsidy.getNumericCellValue());
                    outsourcing.setSalary(bdSubsidy);
                }
                if (imssEmployee != null){
                    BigDecimal bdImssEmployee = new BigDecimal(imssEmployee.getNumericCellValue());
                    outsourcing.setSalary(bdImssEmployee);
                }

                if (isr != null){
                    BigDecimal bdIsr = new BigDecimal(isr.getNumericCellValue());
                    outsourcing.setSalary(bdIsr);
                }
                if (adjustment != null){
                    BigDecimal bdAdjustment = new BigDecimal(adjustment.getNumericCellValue());
                    outsourcing.setSalary(bdAdjustment);
                }
                if (totalDeduction != null){
                    BigDecimal bdTotalDeduction = new BigDecimal(totalDeduction.getNumericCellValue());
                    outsourcing.setSalary(bdTotalDeduction);
                }

                if (netAssetTax != null){
                    BigDecimal bdNetAssetTax = new BigDecimal(netAssetTax.getNumericCellValue());
                    outsourcing.setSalary(bdNetAssetTax);
                }

                if (imss != null){
                    BigDecimal bdImss = new BigDecimal(imss.getNumericCellValue());
                    outsourcing.setSalary(bdImss);
                }
                if (rcv != null){
                    BigDecimal bdRcv = new BigDecimal(rcv.getNumericCellValue());
                    outsourcing.setSalary(bdRcv);
                }

                if (enterpriseInfonavit != null){
                    BigDecimal bdEnterpriseInfonavit = new BigDecimal(enterpriseInfonavit.getNumericCellValue());
                    outsourcing.setSalary(bdEnterpriseInfonavit);
                }

                if (payrollTax != null){
                    BigDecimal bdPayrollTax = new BigDecimal(payrollTax.getNumericCellValue());
                    outsourcing.setSalary(bdPayrollTax);
                }

                if (totalSocialSecurity != null){
                    BigDecimal bdTotalSocialitySecurity = new BigDecimal(totalSocialSecurity.getNumericCellValue());
                    outsourcing.setSalary(bdTotalSocialitySecurity);
                }

                if (commission != null){
                    BigDecimal bdCommision = new BigDecimal(commission.getNumericCellValue());
                    outsourcing.setSalary(bdCommision);
                }

                if (subtotal != null){
                    BigDecimal bdSubtotal = new BigDecimal(subtotal.getNumericCellValue());
                    outsourcing.setSalary(bdSubtotal);
                }

                if (iva != null){
                    BigDecimal bdIva = new BigDecimal(iva.getNumericCellValue());
                    outsourcing.setSalary(bdIva);
                }

                if (total != null){
                    BigDecimal bdTotal = new BigDecimal(total.getNumericCellValue());
                    outsourcing.setSalary(bdTotal);
                }


            }
            outsourcing.setApplicationDate(LocalDateTime.parse(calculateDate+" 00:00",formatter));
            outsourcing.setCreationDate(LocalDateTime.now());
            outsourcingDao.save(outsourcing);

        }

        return outsourcingDao.findAll();
    }

    @Override
    public List<Outsourcing> updateFromExcel(MultipartFile file, String calculateDate) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i=8;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell departament = currentRow.getCell(0);
            Cell code = currentRow.getCell(1);
            Cell name = currentRow.getCell(2);
            Cell salary = currentRow.getCell(3);
            Cell subsidy = currentRow.getCell(4);
            Cell imssEmployee = currentRow.getCell(5);
            Cell isr = currentRow.getCell(6);
            Cell adjustment = currentRow.getCell(7);
            Cell totalDeduction = currentRow.getCell(8);
            Cell netAssetTax = currentRow.getCell(9);
            Cell imss = currentRow.getCell(10);
            Cell rcv = currentRow.getCell(11);
            Cell enterpriseInfonavit = currentRow.getCell(12);
            Cell payrollTax = currentRow.getCell(13);
            Cell totalSocialSecurity = currentRow.getCell(14);
            Cell commission = currentRow.getCell(15);
            Cell subtotal = currentRow.getCell(16);
            Cell iva = currentRow.getCell(17);
            Cell total = currentRow.getCell(18);


            if (code != null){

                Outsourcing outsourcing = outsourcingDao.finfByidEmployee(
                        (int) code.getNumericCellValue(),
                        LocalDateTime.parse(calculateDate+" 00:00",formatter)
                );

                if (outsourcing != null){
                    Employees employee = employeesDao.findById((int) code.getNumericCellValue());

                    if (employee != null){
                        outsourcing.setEmployee(employee);
                    }
                    if (salary != null){
                        if (salary.getCellType() == Cell.CELL_TYPE_STRING) {
                            break;
                        }
                        BigDecimal bdSalary = new BigDecimal(salary.getNumericCellValue());
                        outsourcing.setSalary(bdSalary);
                    }
                    if (subsidy != null){
                        BigDecimal bdSubsidy = new BigDecimal(subsidy.getNumericCellValue());
                        outsourcing.setSalary(bdSubsidy);
                    }
                    if (imssEmployee != null){
                        BigDecimal bdImssEmployee = new BigDecimal(imssEmployee.getNumericCellValue());
                        outsourcing.setSalary(bdImssEmployee);
                    }

                    if (isr != null){
                        BigDecimal bdIsr = new BigDecimal(isr.getNumericCellValue());
                        outsourcing.setSalary(bdIsr);
                    }
                    if (adjustment != null){
                        BigDecimal bdAdjustment = new BigDecimal(adjustment.getNumericCellValue());
                        outsourcing.setSalary(bdAdjustment);
                    }
                    if (totalDeduction != null){
                        BigDecimal bdTotalDeduction = new BigDecimal(totalDeduction.getNumericCellValue());
                        outsourcing.setSalary(bdTotalDeduction);
                    }

                    if (netAssetTax != null){
                        BigDecimal bdNetAssetTax = new BigDecimal(netAssetTax.getNumericCellValue());
                        outsourcing.setSalary(bdNetAssetTax);
                    }

                    if (imss != null){
                        BigDecimal bdImss = new BigDecimal(imss.getNumericCellValue());
                        outsourcing.setSalary(bdImss);
                    }
                    if (rcv != null){
                        BigDecimal bdRcv = new BigDecimal(rcv.getNumericCellValue());
                        outsourcing.setSalary(bdRcv);
                    }

                    if (enterpriseInfonavit != null){
                        BigDecimal bdEnterpriseInfonavit = new BigDecimal(enterpriseInfonavit.getNumericCellValue());
                        outsourcing.setSalary(bdEnterpriseInfonavit);
                    }

                    if (payrollTax != null){
                        BigDecimal bdPayrollTax = new BigDecimal(payrollTax.getNumericCellValue());
                        outsourcing.setSalary(bdPayrollTax);
                    }

                    if (totalSocialSecurity != null){
                        BigDecimal bdTotalSocialitySecurity = new BigDecimal(totalSocialSecurity.getNumericCellValue());
                        outsourcing.setSalary(bdTotalSocialitySecurity);
                    }

                    if (commission != null){
                        BigDecimal bdCommision = new BigDecimal(commission.getNumericCellValue());
                        outsourcing.setSalary(bdCommision);
                    }

                    if (subtotal != null){
                        BigDecimal bdSubtotal = new BigDecimal(subtotal.getNumericCellValue());
                        outsourcing.setSalary(bdSubtotal);
                    }

                    if (iva != null){
                        BigDecimal bdIva = new BigDecimal(iva.getNumericCellValue());
                        outsourcing.setSalary(bdIva);
                    }

                    if (total != null){
                        BigDecimal bdTotal = new BigDecimal(total.getNumericCellValue());
                        outsourcing.setSalary(bdTotal);
                    }

                    outsourcing.setApplicationDate(LocalDateTime.parse(calculateDate+" 00:00",formatter));
                    outsourcing.setCreationDate(LocalDateTime.now());


                    outsourcingDao.update(outsourcing);
                }

            }

        }

        return outsourcingDao.findAll();
    }

    @Override
    public Boolean existsOutsourcingRecord(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Row headerRow = sheet.getRow(7);
        String[] headersToSkip = {
                "Departamento", "Codigo","Nombre", "Sueldo",
                "Subsidio","IMSS Empleado","ISR", "Ajuste al neto",
                "TOTAL DEDUCCIONES","NETO SUELDO FISCAL     (A)","IMSS",
                "RCV", "Infonavit empresa",
                "Impuesto sobre nomina", "TOTALPREVISION SOCIAL",
                "Comisión", "Subtotal", "IVA",
                "Total"
        };

        for (int i = 0 ; i < 19 ;i++) {
            if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        boolean existsOutsourcing = false;

        for (int i=8;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell codigo = currentRow.getCell(1);

            Outsourcing savedOutsourcing;

            if (codigo != null) {

                if (codigo.getCellType() == Cell.CELL_TYPE_STRING) {
                    break;
                }

                savedOutsourcing = outsourcingDao.finfByidEmployee(
                        (int) codigo.getNumericCellValue(),
                        LocalDateTime.parse(calculateDate+" 00:00",formatter)
                );

                if (savedOutsourcing != null) {
                    existsOutsourcing = true;
                }
            }
        }

        return existsOutsourcing;
    }
}

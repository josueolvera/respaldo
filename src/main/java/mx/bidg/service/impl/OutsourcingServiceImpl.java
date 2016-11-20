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
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.Users;
import mx.bidg.service.DwEmployeesService;

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
    
    @Autowired
    private DwEmployeesDao dwEmployeesDao;


    @Override
    public List<Outsourcing> findAll() {
        return outsourcingDao.findAll();
    }

    @Override
    public Outsourcing findById(Integer id) {
        return outsourcingDao.findById(id);
    }

    @Override
    public List<Outsourcing> updateFromExcel(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i = 7; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
//            Cell departament = currentRow.getCell(0);
            Cell code = currentRow.getCell(1);
//            Cell name = currentRow.getCell(2);
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

            if (code != null) {

                Outsourcing outsourcing = outsourcingDao.finfByidEmployee(
                        (int) code.getNumericCellValue(),
                        LocalDateTime.parse(calculateDate + " 00:00", formatter)
                );
                DwEmployees dwe= dwEmployeesDao.findByIdEmployee((int)code.getNumericCellValue());

                if (outsourcing != null) {
                    Employees employee = employeesDao.findById((int) code.getNumericCellValue());

                    if (employee != null) {
                        outsourcing.setEmployee(employee);
                        outsourcing.setDwEnterprises(dwe.getDwEnterprise());
                    }

                    if (salary != null) {
                        if (salary.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdSalary = new BigDecimal(Integer.parseInt(salary.getStringCellValue()));
                            outsourcing.setSalary(bdSalary);
                        } else if (salary.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdSalary = new BigDecimal(salary.getNumericCellValue());
                            outsourcing.setSalary(bdSalary);
                        }

                    }
                    if (subsidy != null) {
                        if (subsidy.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdSubsidy = new BigDecimal(Integer.parseInt(subsidy.getStringCellValue()));
                            outsourcing.setSubsidy(bdSubsidy);
                        } else if (subsidy.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdSubsidy = new BigDecimal(subsidy.getNumericCellValue());
                            outsourcing.setSubsidy(bdSubsidy);
                        }

                    }
                    if (imssEmployee != null) {
                        if (imssEmployee.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdImssEmployee = new BigDecimal(Integer.parseInt(imssEmployee.getStringCellValue()));
                            outsourcing.setImssEmployee(bdImssEmployee);
                        } else if (imssEmployee.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdImssEmployee = new BigDecimal(imssEmployee.getNumericCellValue());
                            outsourcing.setImssEmployee(bdImssEmployee);
                        }
                    }

                    if (isr != null) {
                        if (isr.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdIsr = new BigDecimal(Integer.parseInt(isr.getStringCellValue()));
                            outsourcing.setIsr(bdIsr);
                        } else if (isr.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdIsr = new BigDecimal(isr.getNumericCellValue());
                            outsourcing.setIsr(bdIsr);
                        }
                    }
                    if (adjustment != null) {
                        if (adjustment.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdAdjustment = new BigDecimal(Integer.parseInt(adjustment.getStringCellValue()));
                            outsourcing.setAdjustment(bdAdjustment);
                        } else if (adjustment.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdAdjustment = new BigDecimal(adjustment.getNumericCellValue());
                            outsourcing.setAdjustment(bdAdjustment);
                        }
                    }
                    if (totalDeduction != null) {
                        if (totalDeduction.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdTotalDeduction = new BigDecimal(Integer.parseInt(totalDeduction.getStringCellValue()));
                            outsourcing.setTotalDeductions(bdTotalDeduction);
                        } else if (totalDeduction.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdTotalDeduction = new BigDecimal(totalDeduction.getNumericCellValue());
                            outsourcing.setTotalDeductions(bdTotalDeduction);
                        }
                    }

                    if (netAssetTax != null) {
                        if (netAssetTax.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdNetAssetTax = new BigDecimal(Integer.parseInt(netAssetTax.getStringCellValue()));
                            outsourcing.setNetAssetTax(bdNetAssetTax);
                        } else if (netAssetTax.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdNetAssetTax = new BigDecimal(netAssetTax.getNumericCellValue());
                            outsourcing.setNetAssetTax(bdNetAssetTax);
                        }
                    }

                    if (imss != null) {
                        if (imss.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdImss = new BigDecimal(Integer.parseInt(imss.getStringCellValue()));
                            outsourcing.setImss(bdImss);
                        } else if (imss.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdImss = new BigDecimal(imss.getNumericCellValue());
                            outsourcing.setImss(bdImss);
                        }
                    }
                    if (rcv != null) {
                        if (rcv.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdRcv = new BigDecimal(Integer.parseInt(rcv.getStringCellValue()));
                            outsourcing.setRcv(bdRcv);
                        } else if (rcv.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdRcv = new BigDecimal(rcv.getNumericCellValue());
                            outsourcing.setRcv(bdRcv);
                        }
                    }

                    if (enterpriseInfonavit != null) {
                        if (enterpriseInfonavit.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdEnterpriseInfonavit = new BigDecimal(Integer.parseInt(enterpriseInfonavit.getStringCellValue()));
                            outsourcing.setEnterpriseInfonavit(bdEnterpriseInfonavit);
                        } else if (enterpriseInfonavit.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdEnterpriseInfonavit = new BigDecimal(enterpriseInfonavit.getNumericCellValue());
                            outsourcing.setEnterpriseInfonavit(bdEnterpriseInfonavit);
                        }
                    }

                    if (payrollTax != null) {
                        if (payrollTax.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdPayrollTax = new BigDecimal(Integer.parseInt(payrollTax.getStringCellValue()));
                            outsourcing.setPayrollTax(bdPayrollTax);
                        } else if (payrollTax.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdPayrollTax = new BigDecimal(payrollTax.getNumericCellValue());
                            outsourcing.setPayrollTax(bdPayrollTax);
                        }
                    }

                    if (totalSocialSecurity != null) {
                        if (totalSocialSecurity.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdTotalSocialitySecurity = new BigDecimal(Integer.parseInt(totalSocialSecurity.getStringCellValue()));
                            outsourcing.setTotalSocialSecurity(bdTotalSocialitySecurity);
                        } else if (totalSocialSecurity.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdTotalSocialitySecurity = new BigDecimal(totalSocialSecurity.getNumericCellValue());
                            outsourcing.setTotalSocialSecurity(bdTotalSocialitySecurity);
                        }
                    }

                    if (commission != null) {
                        if (commission.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdCommision = new BigDecimal(Integer.parseInt(commission.getStringCellValue()));
                            outsourcing.setCommission(bdCommision);
                        } else if (commission.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdCommision = new BigDecimal(commission.getNumericCellValue());
                            outsourcing.setCommission(bdCommision);
                        }
                    }

                    if (subtotal != null) {
                        if (subtotal.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdSubtotal = new BigDecimal(Integer.parseInt(subtotal.getStringCellValue()));
                            outsourcing.setSubtotal(bdSubtotal);
                        } else if (subtotal.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdSubtotal = new BigDecimal(subtotal.getNumericCellValue());
                            outsourcing.setSubtotal(bdSubtotal);
                        }
                    }

                    if (iva != null) {
                        if (iva.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdIva = new BigDecimal(Integer.parseInt(iva.getStringCellValue()));
                            outsourcing.setIva(bdIva);
                        } else if (iva.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdIva = new BigDecimal(iva.getNumericCellValue());
                            outsourcing.setIva(bdIva);
                        }
                    }

                    if (total != null) {
                        if (total.getCellType() == Cell.CELL_TYPE_STRING) {
                            BigDecimal bdTotal = new BigDecimal(Integer.parseInt(total.getStringCellValue()));
                            outsourcing.setTotal(bdTotal);
                        } else if (total.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            BigDecimal bdTotal = new BigDecimal(total.getNumericCellValue());
                            outsourcing.setTotal(bdTotal);
                        }
                    }

                    if (user != null) {
                        outsourcing.setUsername(user.getUsername());
                    }

                    outsourcing.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
                    outsourcing.setCreationDate(LocalDateTime.now());

                    outsourcingDao.update(outsourcing);
                }

            }

        }

        return outsourcingDao.findAll();
    }

    @Override
    public Boolean existsOutsourcingRecord(MultipartFile file, String calculateDate) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Row headerRow = sheet.getRow(6);
        String[] headersToSkip = {
            "Departamento", "Codigo", "Nombre", "Sueldo",
            "Subsidio", "IMSS Empleado", "ISR", "Ajuste al neto",
            "TOTAL DEDUCCIONES", "NETO SUELDO FISCAL     (A)", "IMSS",
            "RCV", "Infonavit empresa",
            "Impuesto sobre nomina", "TOTALPREVISION SOCIAL",
            "Comisión", "Subtotal", "IVA",
            "Total"
        };

        for (int i = 0; i < 19; i++) {
            if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        boolean existsOutsourcing = false;

        for (int i = 7; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow != null) {
                Cell codigo = currentRow.getCell(1);

                Outsourcing savedOutsourcing;

                if (codigo != null) {

                    if (codigo.getCellType() == Cell.CELL_TYPE_STRING) {
                        break;
                    }

                    savedOutsourcing = outsourcingDao.finfByidEmployee(
                            (int) codigo.getNumericCellValue(),
                            LocalDateTime.parse(calculateDate + " 00:00", formatter)
                    );

                    if (savedOutsourcing != null) {
                        existsOutsourcing = true;
                    }
                }
            }
        }

        return existsOutsourcing;
    }

    @Override
    public List<Outsourcing> saveFromExcel(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i = 7; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);

            //  Cell departament = currentRow.getCell(0);
            Cell code = currentRow.getCell(1);
            //    Cell name = currentRow.getCell(2);
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

            if (code != null) {
                Employees employee = employeesDao.findById((int) code.getNumericCellValue());
                DwEmployees dwe=dwEmployeesDao.findByIdEmployee((int)code.getNumericCellValue());
                outsourcing.setDwEnterprises(dwe.getDwEnterprise());
                if (employee != null) {
                    outsourcing.setEmployee(employee);
                    outsourcing.setDwEnterprises(dwe.getDwEnterprise());
                }
                if (salary != null) {
                    if (salary.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdSalary = new BigDecimal(Integer.parseInt(salary.getStringCellValue()));
                        outsourcing.setSalary(bdSalary);
                    } else if (salary.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdSalary = new BigDecimal(salary.getNumericCellValue());
                        outsourcing.setSalary(bdSalary);
                    }

                }
                if (subsidy != null) {
                    if (subsidy.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdSubsidy = new BigDecimal(Integer.parseInt(subsidy.getStringCellValue()));
                        outsourcing.setSubsidy(bdSubsidy);
                    } else if (subsidy.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdSubsidy = new BigDecimal(subsidy.getNumericCellValue());
                        outsourcing.setSubsidy(bdSubsidy);
                    }

                }
                if (imssEmployee != null) {
                    if (imssEmployee.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdImssEmployee = new BigDecimal(Integer.parseInt(imssEmployee.getStringCellValue()));
                        outsourcing.setImssEmployee(bdImssEmployee);
                    } else if (imssEmployee.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdImssEmployee = new BigDecimal(imssEmployee.getNumericCellValue());
                        outsourcing.setImssEmployee(bdImssEmployee);
                    }
                }

                if (isr != null) {
                    if (isr.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdIsr = new BigDecimal(Integer.parseInt(isr.getStringCellValue()));
                        outsourcing.setIsr(bdIsr);
                    } else if (isr.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdIsr = new BigDecimal(isr.getNumericCellValue());
                        outsourcing.setIsr(bdIsr);
                    }
                }
                if (adjustment != null) {
                    if (adjustment.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdAdjustment = new BigDecimal(Integer.parseInt(adjustment.getStringCellValue()));
                        outsourcing.setAdjustment(bdAdjustment);
                    } else if (adjustment.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdAdjustment = new BigDecimal(adjustment.getNumericCellValue());
                        outsourcing.setAdjustment(bdAdjustment);
                    }
                }
                if (totalDeduction != null) {
                    if (totalDeduction.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdTotalDeduction = new BigDecimal(Integer.parseInt(totalDeduction.getStringCellValue()));
                        outsourcing.setTotalDeductions(bdTotalDeduction);
                    } else if (totalDeduction.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdTotalDeduction = new BigDecimal(totalDeduction.getNumericCellValue());
                        outsourcing.setTotalDeductions(bdTotalDeduction);
                    }
                }

                if (netAssetTax != null) {
                    if (netAssetTax.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdNetAssetTax = new BigDecimal(Integer.parseInt(netAssetTax.getStringCellValue()));
                        outsourcing.setNetAssetTax(bdNetAssetTax);
                    } else if (netAssetTax.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdNetAssetTax = new BigDecimal(netAssetTax.getNumericCellValue());
                        outsourcing.setNetAssetTax(bdNetAssetTax);
                    }
                }

                if (imss != null) {
                    if (imss.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdImss = new BigDecimal(Integer.parseInt(imss.getStringCellValue()));
                        outsourcing.setImss(bdImss);
                    } else if (imss.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdImss = new BigDecimal(imss.getNumericCellValue());
                        outsourcing.setImss(bdImss);
                    }
                }
                if (rcv != null) {
                    if (rcv.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdRcv = new BigDecimal(Integer.parseInt(rcv.getStringCellValue()));
                        outsourcing.setRcv(bdRcv);
                    } else if (rcv.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdRcv = new BigDecimal(rcv.getNumericCellValue());
                        outsourcing.setRcv(bdRcv);
                    }
                }

                if (enterpriseInfonavit != null) {
                    if (enterpriseInfonavit.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdEnterpriseInfonavit = new BigDecimal(Integer.parseInt(enterpriseInfonavit.getStringCellValue()));
                        outsourcing.setEnterpriseInfonavit(bdEnterpriseInfonavit);
                    } else if (enterpriseInfonavit.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdEnterpriseInfonavit = new BigDecimal(enterpriseInfonavit.getNumericCellValue());
                        outsourcing.setEnterpriseInfonavit(bdEnterpriseInfonavit);
                    }
                }

                if (payrollTax != null) {
                    if (payrollTax.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdPayrollTax = new BigDecimal(Integer.parseInt(payrollTax.getStringCellValue()));
                        outsourcing.setPayrollTax(bdPayrollTax);
                    } else if (payrollTax.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdPayrollTax = new BigDecimal(payrollTax.getNumericCellValue());
                        outsourcing.setPayrollTax(bdPayrollTax);
                    }
                }

                if (totalSocialSecurity != null) {
                    if (totalSocialSecurity.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdTotalSocialitySecurity = new BigDecimal(Integer.parseInt(totalSocialSecurity.getStringCellValue()));
                        outsourcing.setTotalSocialSecurity(bdTotalSocialitySecurity);
                    } else if (totalSocialSecurity.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdTotalSocialitySecurity = new BigDecimal(totalSocialSecurity.getNumericCellValue());
                        outsourcing.setTotalSocialSecurity(bdTotalSocialitySecurity);
                    }
                }

                if (commission != null) {
                    if (commission.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdCommision = new BigDecimal(Integer.parseInt(commission.getStringCellValue()));
                        outsourcing.setCommission(bdCommision);
                    } else if (commission.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdCommision = new BigDecimal(commission.getNumericCellValue());
                        outsourcing.setCommission(bdCommision);
                    }
                }

                if (subtotal != null) {
                    if (subtotal.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdSubtotal = new BigDecimal(Integer.parseInt(subtotal.getStringCellValue()));
                        outsourcing.setSubtotal(bdSubtotal);
                    } else if (subtotal.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdSubtotal = new BigDecimal(subtotal.getNumericCellValue());
                        outsourcing.setSubtotal(bdSubtotal);
                    }
                }

                if (iva != null) {
                    if (iva.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdIva = new BigDecimal(Integer.parseInt(iva.getStringCellValue()));
                        outsourcing.setIva(bdIva);
                    } else if (iva.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdIva = new BigDecimal(iva.getNumericCellValue());
                        outsourcing.setIva(bdIva);
                    }
                }

                if (total != null) {
                    if (total.getCellType() == Cell.CELL_TYPE_STRING) {
                        BigDecimal bdTotal = new BigDecimal(Integer.parseInt(total.getStringCellValue()));
                        outsourcing.setTotal(bdTotal);
                    } else if (total.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal bdTotal = new BigDecimal(total.getNumericCellValue());
                        outsourcing.setTotal(bdTotal);
                    }
                }
                if (user != null) {
                    outsourcing.setUsername(user.getUsername());
                }

            }
            outsourcing.setApplicationDate(LocalDateTime.parse(calculateDate + " 00:00", formatter));
            outsourcing.setCreationDate(LocalDateTime.now());
            outsourcingDao.save(outsourcing);

        }

        return outsourcingDao.findAll();

    }

}

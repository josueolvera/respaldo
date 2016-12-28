package mx.bidg.service;

import mx.bidg.model.Payroll;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Desarrollador on 19/11/2016.
 */
public interface PayrollService {

    Payroll save(Payroll payroll);
    Payroll update(Payroll payroll);
    Payroll findById(Integer idPayroll);
    List<Payroll> findAll();
    boolean delete(Payroll payroll);
    void reportCorporate(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException;
    void corporateFortyName(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException;
    void reportWeeklyPay (OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException;
    void monthlyPayrollReport (OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException;
    void reportCorporateNec(FileOutputStream fileOutputStream) throws IOException;
    void reportDistributionNec(FileOutputStream fileOutputStream) throws IOException;
    void reportCost(OutputStream outputStream, List queryResult) throws IOException;
}

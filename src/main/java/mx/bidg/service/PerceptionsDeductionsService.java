package mx.bidg.service;

import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.EmployeesHistory;
import mx.bidg.model.PerceptionsDeductions;
import mx.bidg.model.Users;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface PerceptionsDeductionsService {
    PerceptionsDeductions save (PerceptionsDeductions perceptionsDeductions);
    PerceptionsDeductions update(PerceptionsDeductions perceptionsDeductions);
    PerceptionsDeductions findById(Integer idPerceptionsDeductions);
    List<PerceptionsDeductions> findAll();
    boolean delete(PerceptionsDeductions perceptionsDeductions);
    List<PerceptionsDeductions> findAllActives();
    List<PerceptionsDeductions> calculateBonus(Users user, String ofDate, String untilDate);
    List<PerceptionsDeductions> findByAllEmployeesAndInitialDateAndFinalDate (List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate);
    List<PerceptionsDeductions>findByIdEmployee(Integer idEmployee);
    List<PerceptionsDeductions>findByStartDateEndDate(LocalDateTime startDate, LocalDateTime endDate);
    List<PerceptionsDeductions>findByIdEmployeeAndStartDateEndDate(Integer idEmployee,LocalDateTime startDate, LocalDateTime endDate);
    void reporUnfold(List<EmployeesHistory>reportData, OutputStream outputStream)throws IOException;
    void reportPD(OutputStream outputStream,List queryResult)throws IOException;
    List findByAllEmployeesAndInitialDateAndFinalDatePerception(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate, List<CPerceptionsDeductions> cPerceptionsDeductionsList);
    void reportPDSD(OutputStream outputStream,List queryResult)throws IOException;
}

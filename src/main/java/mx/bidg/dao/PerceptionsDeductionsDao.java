package mx.bidg.dao;

import mx.bidg.model.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface PerceptionsDeductionsDao extends InterfaceDao<PerceptionsDeductions> {
    List<PerceptionsDeductions> findAllWithStatus();
    List calculateBonus(SqlQueries sqlQueries, Users user, String ofDate, String untilDate);
    List<PerceptionsDeductions> findByIdEmployeeAndApplicationDate(Integer idEmployee, LocalDateTime ofDate, LocalDateTime untilDate);
    List<PerceptionsDeductions> findByAllEmployeesAndInitialDateAndFinalDate(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate);
    List<PerceptionsDeductions>findByIdEmployee(Integer idEmployee);
    List<PerceptionsDeductions>findByStartDateEndDate(LocalDateTime startDate, LocalDateTime endDate);
    List<PerceptionsDeductions> findIdEmployeeAndActives(Integer idEmployee);
    List<PerceptionsDeductions> findByIdEmployeeAndApplicationDateAll(Integer idEmployee, LocalDateTime ofDate, LocalDateTime untilDate);
    List findByAllEmployeesAndInitialDateAndFinalDatePerception(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate, List<CPerceptionsDeductions> cPerceptionsDeductionsList);
}

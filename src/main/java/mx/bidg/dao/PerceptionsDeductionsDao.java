package mx.bidg.dao;

import mx.bidg.model.PerceptionsDeductions;
import mx.bidg.model.SqlQueries;
import mx.bidg.model.Users;

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
}

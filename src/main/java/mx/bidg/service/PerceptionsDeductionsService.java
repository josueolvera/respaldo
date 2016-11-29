package mx.bidg.service;

import mx.bidg.model.PerceptionsDeductions;
import mx.bidg.model.Users;

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
}

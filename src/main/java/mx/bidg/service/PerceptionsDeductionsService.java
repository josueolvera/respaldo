package mx.bidg.service;

import mx.bidg.model.PerceptionsDeductions;

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
}

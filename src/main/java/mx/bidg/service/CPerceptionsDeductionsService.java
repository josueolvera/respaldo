package mx.bidg.service;

import mx.bidg.model.CPerceptionsDeductions;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface CPerceptionsDeductionsService {
    CPerceptionsDeductions save(CPerceptionsDeductions cPerceptionsDeductions);
    CPerceptionsDeductions update(CPerceptionsDeductions cPerceptionsDeductions);
    CPerceptionsDeductions findById(Integer idCPd);
    List<CPerceptionsDeductions> findAll();
    boolean delete(CPerceptionsDeductions cPerceptionsDeductions);
}

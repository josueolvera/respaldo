package mx.bidg.service;

import mx.bidg.model.CDateCalculation;

import java.util.List;

/**
 * Created by josueolvera on 6/10/16.
 */
public interface CDateCalculationService {
    CDateCalculation save(CDateCalculation cDateCalculation);
    CDateCalculation update(CDateCalculation cDateCalculation);
    CDateCalculation findById(Integer idDateCalculation);
    List<CDateCalculation> findAll ();
    boolean delete (CDateCalculation cDateCalculation);
    List<CDateCalculation> findByStatus(Integer status);
}

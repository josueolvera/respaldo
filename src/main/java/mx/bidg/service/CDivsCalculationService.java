package mx.bidg.service;

import mx.bidg.model.CDivsCalculation;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface CDivsCalculationService {
    CDivsCalculation save (CDivsCalculation cDivsCalculation);
    CDivsCalculation update (CDivsCalculation cDivsCalculation);
    CDivsCalculation findById (Integer idCDivsCalculation);
    boolean delete (CDivsCalculation cDivsCalculation);
    List<CDivsCalculation> findAll();
}

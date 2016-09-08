package mx.bidg.service;

import mx.bidg.model.RulesCalculation;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface RulesCalculationService {
    RulesCalculation save(RulesCalculation rulesCalculation);
    RulesCalculation update(RulesCalculation rulesCalculation);
    RulesCalculation findById (Integer idRulesCalculation);
    boolean delete(RulesCalculation rulesCalculation);
    List<RulesCalculation> findAll();
}

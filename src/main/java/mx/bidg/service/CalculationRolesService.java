package mx.bidg.service;

import mx.bidg.model.CalculationRoles;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface CalculationRolesService {
    CalculationRoles save(CalculationRoles calculationRoles);
    CalculationRoles update (CalculationRoles calculationRoles);
    CalculationRoles findById(Integer idCalculationRole);
    boolean delete(CalculationRoles calculationRoles);
    List<CalculationRoles> findAll();
}

package mx.bidg.service;

import mx.bidg.model.AuthorizationCostCenter;

import java.util.List;

/**
 * Created by Kevin Salvador on 15/03/2017.
 */
public interface AuthorizationCostCenterService {
    List<AuthorizationCostCenter> findByCostCenter(Integer idCostCenter);
    List<AuthorizationCostCenter>findAll();
    AuthorizationCostCenter save(AuthorizationCostCenter authorizationCostCenter);
    AuthorizationCostCenter update(AuthorizationCostCenter authorizationCostCenter);
    boolean delete(AuthorizationCostCenter authorizationCostCenter);
    AuthorizationCostCenter findByIdCostCenterAndYear(Integer idCostCenter, Integer year);
}

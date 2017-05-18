package mx.bidg.dao;

import mx.bidg.model.AuthorizationCostCenter;

import java.util.List;

/**
 * Created by DONCHINGON on 15/03/2017.
 */
public interface AuthorizationCostCenterDao extends InterfaceDao<AuthorizationCostCenter> {
    List<AuthorizationCostCenter>findByCostCenter(Integer idCostCenter);
    AuthorizationCostCenter findByIdCostCenterAndYear(Integer idCostCenter, Integer year);
    List<Integer> getAllCostCentersRNAByIdsCostCenters(List<Integer> idsCostCenters);
    List<AuthorizationCostCenter> getAllCostCentersByIdsCostCenters(List<Integer> idsCostCenters);
}

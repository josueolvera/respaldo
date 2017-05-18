package mx.bidg.dao;

import mx.bidg.model.RolesCostCenter;

import java.util.List;


/**
 * Created by gerardo8 on 24/08/16.
 */
public interface RolesCostCenterDao extends InterfaceDao<RolesCostCenter> {
    List<RolesCostCenter> findByRole(Integer idRole);
    List<Integer> findOnlyDifferentIdCostCentersByRole(Integer idRole);
}

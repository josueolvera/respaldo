package mx.bidg.service;

import mx.bidg.model.RolesCostCenter;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
public interface RolesCostCenterService {
    List<RolesCostCenter> findAll();
    RolesCostCenter findById(Integer id);
    List<RolesCostCenter> findByRole(Integer idRole);
    RolesCostCenter save(RolesCostCenter userCostCenter);
    RolesCostCenter update(RolesCostCenter userCostCenter);
    Boolean delete(RolesCostCenter userCostCenter);
}

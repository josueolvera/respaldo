package mx.bidg.dao;

import mx.bidg.model.DistributorCostCenter;

import java.util.List;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
public interface DistributorCostCenterDao extends InterfaceDao<DistributorCostCenter> {
    List<DistributorCostCenter> findByCostCenter(Integer idCostCenter);
    DistributorCostCenter findByIdCostCenter(Integer idCostCenter);
    List<DistributorCostCenter>findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine, Integer idDistributor,Integer idCostCenter);
}

package mx.bidg.service;

import mx.bidg.model.DistributorCostCenter;

import java.util.List;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
public interface DistributorCostCenterService {
    List<DistributorCostCenter> findAll();
    DistributorCostCenter findById(Integer id);
    DistributorCostCenter save(DistributorCostCenter distributorCostCenter);
    DistributorCostCenter update(DistributorCostCenter distributorCostCenter);
    boolean delete(DistributorCostCenter distributorCostCenter);
    List<DistributorCostCenter> findByCostCenter(Integer idCostCenter);
    DistributorCostCenter findByIdCostCenter(Integer idCostCenter);
    List<DistributorCostCenter>findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine, Integer idDistributor,Integer idCostCenter);
}

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
    List<DistributorCostCenter> findByCostCenterAndDistributors (Integer idCostCenter, List<Integer> idDistributors);
    List<Integer> getIdsDistributorsByBusinessLine(Integer idBusinessLine);
    List<Integer> getIdsCostCentersByBDistributor(Integer idDistributor, List<Integer> idsBussinessLines);
    List<DistributorCostCenter> getAllByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC);
    List<Integer> getIdsCostCentersByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC);

    List<Integer> getIdsAccountingAccountsByCostCenterAndModuleStatus(Integer idCostCenter, Integer idModuleStatus);
}

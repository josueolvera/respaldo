package mx.bidg.service;

import mx.bidg.model.DistributorCostCenter;

import java.util.List;
import mx.bidg.model.AccountingAccounts;

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
    List<Integer> getIdsDistributorByBusinessLine(Integer idBusinessLine);
    List<Integer> getIdsCostCentersByBDistributor(Integer idDistributor, List<Integer> idsBussinessLines);
    List<DistributorCostCenter> getAllByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC);
    List<Integer> getIdsCostCentersByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC);

    List<AccountingAccounts> getIdsAccountingAccountsByCostCenter(Integer idCostCenter);
    List<Integer> getIdsDCCByDistributor(Integer idDistributor);
    DistributorCostCenter findByCostCenterAndAA(Integer idCostCenter, Integer idAccountingAccounts);
}

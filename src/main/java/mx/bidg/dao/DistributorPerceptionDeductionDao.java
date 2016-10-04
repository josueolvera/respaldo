package mx.bidg.dao;

import mx.bidg.model.DistributorPerceptionDeduction;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface DistributorPerceptionDeductionDao extends InterfaceDao<DistributorPerceptionDeduction> {
    List<DistributorPerceptionDeduction> findByDistributorAndHpd(Integer idDistributor);
    List<DistributorPerceptionDeduction> findByDistributorAll(Integer idDistributor);
}

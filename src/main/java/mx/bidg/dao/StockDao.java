package mx.bidg.dao;

import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 8/12/15.
 */
public interface StockDao extends InterfaceDao<Stocks> {
    List<Stocks> findByDistributorRegionBranchArea(
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch,
            Integer idArea
    );

    List<Stocks> findByDistributorRegionBranch(
            Integer idDistributor,
            Integer idRegion,
            Integer idBranch
    );
    List<Stocks> findByDistributorRegion(Integer idDistributor, Integer idRegion);
    List<Stocks> findByDistributor(Integer idDistributor);
    Stocks updateEntity(Stocks stock);
    Stocks findSimpleById(Integer idStock);
}

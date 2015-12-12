package mx.bidg.dao;

import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 8/12/15.
 */
public interface StockDao extends InterfaceDao<Stocks> {
    List<Stocks> findByDistributor(Integer idDistributor);
}

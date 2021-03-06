package mx.bidg.service;

import mx.bidg.model.Requests;
import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 8/12/15.
 */
public interface StockService {
    Stocks save(Stocks article);
    Stocks findById(int id);
    List<Stocks> findAll();
    List<Stocks> filter(Integer idDistributor, Integer idRegion, Integer idZona, Integer  idBranch, Integer  idArea);
    List<Stocks> addStockArticlesFromRequest(Requests request);
    List<Stocks> findByDistributor(Integer idDistributor);
    Stocks update(Stocks stock);
    Stocks updateEntity(Stocks stock);
    Stocks findSimpleById(int idStock);
}

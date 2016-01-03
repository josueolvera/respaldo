package mx.bidg.service;

import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.model.StockDocuments;
import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
public interface StockDocumentsService {
    List<StockDocuments> findByIdStock(Integer idStock);
    StockDocuments findBy(Stocks stocks, CStockDocumentsTypes documentType);
    StockDocuments save(StockDocuments entity);
    StockDocuments findById(int id);
    List<StockDocuments> findAll();
    StockDocuments update(StockDocuments entity);
    boolean delete(StockDocuments entity);
}

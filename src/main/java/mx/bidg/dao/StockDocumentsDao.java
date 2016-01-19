package mx.bidg.dao;

import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.model.StockDocuments;
import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
public interface StockDocumentsDao extends InterfaceDao<StockDocuments> {
    List<StockDocuments> findByIdStock(Integer idStock);
    StockDocuments findBy(Stocks stocks, CStockDocumentsTypes documentType);
    List<StockDocuments> findRecordBy(Stocks stock);
}

package mx.bidg.service.impl;

import mx.bidg.dao.StockDocumentsDao;
import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.model.StockDocuments;
import mx.bidg.model.Stocks;
import mx.bidg.service.StockDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@Service
@Transactional
public class StockDocumentsServiceImpl implements StockDocumentsService {

    @Autowired
    private StockDocumentsDao stockDocumentsDao;

    @Override
    public List<StockDocuments> findByIdStock(Integer idStock) {
        return stockDocumentsDao.findByIdStock(idStock);
    }

    @Override
    public List<StockDocuments> findRecordBy(Stocks stock) {
        return stockDocumentsDao.findRecordBy(stock);
    }

    @Override
    public StockDocuments findBy(Stocks stocks, CStockDocumentsTypes documentType) {
        return stockDocumentsDao.findBy(stocks, documentType);
    }

    @Override
    public StockDocuments save(StockDocuments entity) {
        return stockDocumentsDao.save(entity);
    }

    @Override
    public StockDocuments findById(int id) {
        return stockDocumentsDao.findById(id);
    }

    @Override
    public List<StockDocuments> findAll() {
        return stockDocumentsDao.findAll();
    }

    @Override
    public StockDocuments update(StockDocuments entity) {
        return stockDocumentsDao.update(entity);
    }

    @Override
    public boolean delete(StockDocuments entity) {
        return stockDocumentsDao.delete(entity);
    }
}

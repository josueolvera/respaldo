package mx.bidg.service.impl;

import mx.bidg.dao.StockDao;
import mx.bidg.model.Requests;
import mx.bidg.model.Stocks;
import mx.bidg.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public Stocks save(Stocks article) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Stocks findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Stocks> findAll() {
        return stockDao.findAll();
    }

    @Override
    public List<Stocks> addStockArticlesFromRequest(Requests request) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

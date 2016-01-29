package mx.bidg.service.impl;

import mx.bidg.dao.StockDao;
import mx.bidg.model.*;
import mx.bidg.service.CArticlesService;
import mx.bidg.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private CArticlesService articlesService;

    @Override
    public Stocks save(Stocks article) {
        stockDao.save(article);
        return article;
    }

    @Override
    public Stocks findById(int id) {
        return stockDao.findById(id);
    }

    @Override
    public Stocks findSimpleById(int idStock) {
        return stockDao.findSimpleById(idStock);
    }

    @Override
    public List<Stocks> findByDistributor(Integer idDistributor) {
        return stockDao.findByDistributor(idDistributor);
    }

    @Override
    public List<Stocks> findAll() {
        return stockDao.findAll();
    }

    @Override
    public List<Stocks> addStockArticlesFromRequest(Requests request) {
        List<Stocks> stocksList = new ArrayList<>();

        for (RequestProducts product : request.getRequestProductsList()) {
            Stocks stockArticle = new Stocks();
            stockArticle.setArticle(articlesService.findByProduct(product.getProduct()));
            stockArticle.setArticleStatus(new CArticleStatus(1));
            stockArticle.setCreationDate(LocalDateTime.now());
            stockArticle.setFolio(request.getFolio());
            this.save(stockArticle);
            stocksList.add(stockArticle);
        }

        return stocksList;
    }

    @Override
    public Stocks update(Stocks stock) {
        stockDao.update(stock);
        return stock;
    }

    @Override
    public Stocks updateEntity(Stocks stock) {
        return stockDao.updateEntity(stock);
    }
}

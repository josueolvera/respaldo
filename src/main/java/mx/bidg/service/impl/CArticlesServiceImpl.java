package mx.bidg.service.impl;

import mx.bidg.dao.CArticlesDao;
import mx.bidg.model.CArticles;
import mx.bidg.model.CProducts;
import mx.bidg.service.CArticlesService;
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
public class CArticlesServiceImpl implements CArticlesService {

    @Autowired
    private CArticlesDao articlesDao;

    @Override
    public CArticles findByProduct(CProducts product) {
        return articlesDao.findByProduct(product);
    }

    @Override
    public List<CArticles> findAll() {
        return articlesDao.findAll();
    }
}

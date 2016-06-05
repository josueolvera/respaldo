package mx.bidg.service.impl;

import mx.bidg.dao.CArticlesCategoriesDao;
import mx.bidg.model.CArticlesCategories;
import mx.bidg.service.CArticlesCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 02/06/16.
 */
@Transactional
@Service
public class CArticlesCategoriesServiceImpl implements CArticlesCategoriesService {

    @Autowired
    private CArticlesCategoriesDao cArticlesCategoriesDao;

    @Override
    public List<CArticlesCategories> findAll() {
        return cArticlesCategoriesDao.findAll();
    }

    @Override
    public CArticlesCategories findById(Integer id) {
        return cArticlesCategoriesDao.findById(id);
    }
}

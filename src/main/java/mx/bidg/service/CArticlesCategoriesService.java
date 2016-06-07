package mx.bidg.service;

import mx.bidg.model.CArticlesCategories;

import java.util.List;

/**
 * Created by gerardo8 on 02/06/16.
 */
public interface CArticlesCategoriesService {
    List<CArticlesCategories> findAll();
    CArticlesCategories findById(Integer id);
}

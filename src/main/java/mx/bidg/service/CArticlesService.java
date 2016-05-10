package mx.bidg.service;

import mx.bidg.model.CArticles;
import mx.bidg.model.CProducts;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
public interface CArticlesService {

    CArticles findByProduct(CProducts product);
    List<CArticles> findAll();
    CArticles findById(Integer id);
}

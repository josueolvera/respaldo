package mx.bidg.service;

import mx.bidg.model.CArticles;
import mx.bidg.model.CProducts;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
public interface CArticlesService {
    CArticles findByProduct(CProducts product);
}

package mx.bidg.dao;

import mx.bidg.model.CArticles;
import mx.bidg.model.CProducts;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
public interface CArticlesDao extends InterfaceDao<CArticles> {
    CArticles findByProduct(CProducts product);
}

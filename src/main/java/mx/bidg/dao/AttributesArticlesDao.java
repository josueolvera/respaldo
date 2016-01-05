package mx.bidg.dao;

import mx.bidg.model.AttributesArticles;
import mx.bidg.model.CArticles;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/01/16.
 */
public interface AttributesArticlesDao extends InterfaceDao<AttributesArticles> {
    List<AttributesArticles> findByArticle(CArticles article);
}

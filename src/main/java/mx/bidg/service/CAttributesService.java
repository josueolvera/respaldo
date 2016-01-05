package mx.bidg.service;

import mx.bidg.model.CArticles;
import mx.bidg.model.CAttributes;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/01/16.
 */
public interface CAttributesService {
    List<CAttributes> findByArticle(CArticles article);
}

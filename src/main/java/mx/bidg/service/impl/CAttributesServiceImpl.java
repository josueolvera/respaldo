package mx.bidg.service.impl;

import mx.bidg.dao.AttributesArticlesDao;
import mx.bidg.dao.CAttributesDao;
import mx.bidg.model.AttributesArticles;
import mx.bidg.model.CArticles;
import mx.bidg.model.CAttributes;
import mx.bidg.service.CAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/01/16.
 */
@Service
@Transactional
public class CAttributesServiceImpl implements CAttributesService {

    @Autowired
    private AttributesArticlesDao attributesArticlesDao;

    @Autowired
    private CAttributesDao attributesDao;

    @Override
    public List<CAttributes> findByArticle(CArticles article) {
        List<AttributesArticles> pivot = attributesArticlesDao.findByArticle(article);
        List<CAttributes> attributes = new ArrayList<>();

        for (AttributesArticles attribute : pivot) {
            attributes.add(attribute.getAttributes());
        }

        return attributes;
    }

    @Override
    public CAttributes findById(int idAttribute) {
        return attributesDao.findById(idAttribute);
    }
}

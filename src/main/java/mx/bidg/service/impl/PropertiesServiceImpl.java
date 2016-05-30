package mx.bidg.service.impl;

import mx.bidg.dao.AttributesArticlesDao;
import mx.bidg.dao.PropertiesDao;
import mx.bidg.model.*;
import mx.bidg.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 5/01/16.
 */
@Service
@Transactional
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesDao propertiesDao;

    @Autowired
    private AttributesArticlesDao attributesArticlesDao;

    @Override
    public List<Properties> getAllFor(Stocks stock) {
        return propertiesDao.getAllFor(stock);
    }

    @Override
    public Properties save(Properties property, CArticles article, CAttributes attribute) {
        AttributesArticles attributesArticles = attributesArticlesDao.findBy(article, attribute);
        property.setAttributesArticles(attributesArticles);
        propertiesDao.save(property);
        return property;
    }

    @Override
    public boolean delete(Properties property) {
        propertiesDao.delete(property);
        return true;
    }

    @Override
    public Properties findById(Integer id) {
        return propertiesDao.findById(id);
    }
}

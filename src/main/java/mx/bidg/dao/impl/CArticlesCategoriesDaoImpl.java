package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CArticlesCategoriesDao;
import mx.bidg.model.CArticlesCategories;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 02/06/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CArticlesCategoriesDaoImpl extends AbstractDao<Integer, CArticlesCategories> implements CArticlesCategoriesDao {
    @Override
    public CArticlesCategories save(CArticlesCategories entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CArticlesCategories findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CArticlesCategories> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CArticlesCategories update(CArticlesCategories entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CArticlesCategories entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

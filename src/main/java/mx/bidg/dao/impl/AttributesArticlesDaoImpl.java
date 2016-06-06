package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AttributesArticlesDao;
import mx.bidg.model.AttributesArticles;
import mx.bidg.model.CArticles;
import mx.bidg.model.CAttributes;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/01/16.
 */
@Repository
public class AttributesArticlesDaoImpl extends AbstractDao<Integer, AttributesArticles> implements AttributesArticlesDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<AttributesArticles> findByArticle(CArticles article) {
        return (List<AttributesArticles>) createEntityCriteria()
                .add(Restrictions.eq("idArticle", article.getIdArticle()))
                .list();
    }

    @Override
    public AttributesArticles findBy(CArticles article, CAttributes attribute) {
        Integer idAttributeArticle = (Integer) createEntityCriteria()
                .setProjection(Projections.property("idAttributeArticle"))
                .add(Restrictions.eq("idArticle", article.getIdArticle()))
                .add(Restrictions.eq("idAttribute", attribute.getIdAttribute()))
                .uniqueResult();
        return new AttributesArticles(idAttributeArticle);
    }

    @Override
    public AttributesArticles save(AttributesArticles entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public AttributesArticles findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<AttributesArticles> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public AttributesArticles update(AttributesArticles entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(AttributesArticles entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

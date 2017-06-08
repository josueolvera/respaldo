package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRequestCategoriesDao;
import mx.bidg.model.CRequestsCategories;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.hibernate.FetchMode;

/**
 * @author Rafael Viveros
 * Created on 19/11/15.
 */
@Repository
public class CRequestCategoriesDaoImpl extends AbstractDao<Integer, CRequestsCategories> implements CRequestCategoriesDao {
    @Override
    public CRequestsCategories save(CRequestsCategories entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CRequestsCategories findById(int id) {
        return (CRequestsCategories) createEntityCriteria()
                .add(Restrictions.eq("idRequestCategorie", id))
                .setFetchMode("view", FetchMode.JOIN)
                .setFetchMode("view.cTasks", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    public List<CRequestsCategories> findAll() {
        return (List<CRequestsCategories>) createEntityCriteria().list();
    }

    @Override
    public CRequestsCategories update(CRequestsCategories entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CRequestsCategories entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CRequestsCategories getById(Integer id) {
        return getByKey(id);
    }
}

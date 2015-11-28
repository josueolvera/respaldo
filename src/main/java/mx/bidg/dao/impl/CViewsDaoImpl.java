package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CViewsDao;
import mx.bidg.model.CViews;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/11/15.
 */
@Repository
public class CViewsDaoImpl extends AbstractDao<Integer, CViews> implements CViewsDao {
    @Override
    public CViews save(CViews entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CViews findById(int id) {
        return (CViews) createEntityCriteria().add(Restrictions.eq("idView", id)).uniqueResult();
    }

    @Override
    public List<CViews> findAll() {
        return (List<CViews>) createEntityCriteria().list();
    }

    @Override
    public CViews update(CViews entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CViews entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

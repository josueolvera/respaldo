package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CProductsDao;
import mx.bidg.model.CProducts;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
@Repository
public class CProductsDaoImpl extends AbstractDao<Integer, CProducts> implements CProductsDao {
    @Override
    public CProducts save(CProducts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CProducts findById(int id) {
        return (CProducts) createEntityCriteria().add(Restrictions.eq("idProduct", id)).uniqueResult();
    }

    @Override
    public List<CProducts> findAll() {
        return (List<CProducts>) createEntityCriteria().list();
    }

    @Override
    public CProducts update(CProducts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CProducts entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

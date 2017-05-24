package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CProductsRequestDao;
import mx.bidg.model.CProductsRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
@Repository
public class CProductsRequestDaoImpl extends AbstractDao<Integer, CProductsRequest> implements CProductsRequestDao{

    @Override
    public CProductsRequest save(CProductsRequest entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CProductsRequest update(CProductsRequest entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CProductsRequest entity) {
        remove(entity);
        return true;
    }

    @Override
    public CProductsRequest findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CProductsRequest> findAll() {
        return (List<CProductsRequest>) createEntityCriteria().list();
    }
}

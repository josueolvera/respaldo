package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProvidersProductsTypesDao;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersProductsTypes;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 30/05/16.
 */
@Repository
public class ProvidersProductsTypesDaoImpl extends AbstractDao <Integer,ProvidersProductsTypes> implements ProvidersProductsTypesDao {
    @Override
    public ProvidersProductsTypes save(ProvidersProductsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ProvidersProductsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<ProvidersProductsTypes> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<ProvidersProductsTypes>) criteria.list();
    }

    @Override
    public ProvidersProductsTypes update(ProvidersProductsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(ProvidersProductsTypes entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<ProvidersProductsTypes> findByProvider(Providers p) {
        Criteria criteria = createEntityCriteria();
        return (List<ProvidersProductsTypes>) criteria.add(Restrictions.eq("provider",p)).list();
    }
}

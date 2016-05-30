package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PropertiesDao;
import mx.bidg.model.CArticles;
import mx.bidg.model.CAttributes;
import mx.bidg.model.Properties;
import mx.bidg.model.Stocks;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 5/01/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class PropertiesDaoImpl extends AbstractDao<Integer, Properties> implements PropertiesDao {
    @Override
    public Properties save(Properties entity) {
        persist(entity);
        return entity;
    }

    @Override
    public List<Properties> getAllFor(Stocks stock) {
        return (List<Properties>) createEntityCriteria()
                .add(Restrictions.eq("idStock", stock.getIdStock()))
                .list();
    }

    @Override
    public Properties findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Properties> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Properties update(Properties entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Properties entity) {
        remove(entity);
        return true;
    }
}

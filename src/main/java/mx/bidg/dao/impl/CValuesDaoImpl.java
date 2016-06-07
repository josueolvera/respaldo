package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CValuesDao;
import mx.bidg.model.CValues;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@SuppressWarnings("unchecked")
@Repository
public class CValuesDaoImpl extends AbstractDao<Integer, CValues> implements CValuesDao {
    @Override
    public CValues save(CValues entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CValues findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CValues> findAll() {
        return (List<CValues>) createEntityCriteria().list();
    }

    @Override
    public CValues update(CValues entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CValues entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CValues> findValuesByAttribute(int idAttribute, int idArticlesCategory) {
        return createEntityCriteria()
                .add(Restrictions.eq("idAttribute",idAttribute))
                .add(Restrictions.eq("idArticlesCategory",idArticlesCategory))
                .list();
    }
}

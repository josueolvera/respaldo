package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAttributesDao;
import mx.bidg.model.CAttributes;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/01/16.
 */
@Repository
public class CAttributesDaoImpl extends AbstractDao<Integer, CAttributes> implements CAttributesDao {
    @Override
    public CAttributes save(CAttributes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAttributes findById(int id) {
        return (CAttributes) createEntityCriteria()
                .add(Restrictions.eq("idAttribute", id))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CAttributes> findAll() {
        return (List<CAttributes>) createEntityCriteria().list();
    }

    @Override
    public CAttributes update(CAttributes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAttributes entity) {
        remove(entity);
        return true;
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTypeSecureDao;
import mx.bidg.model.CTypeSecure;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Desarrollador on 12/01/2017.
 */
@Repository
public class CTypeSecureDaoImpl extends AbstractDao<Integer, CTypeSecure> implements CTypeSecureDao {

    @Override
    public CTypeSecure save(CTypeSecure entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CTypeSecure findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTypeSecure> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CTypeSecure update(CTypeSecure entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CTypeSecure entity) {
        remove(entity);
        return true;
    }

    @Override
    public CTypeSecure findByName(String name) {
        return (CTypeSecure) createEntityCriteriaNoAccessLevel().add(Restrictions.ilike("typeSecure",name,MatchMode.ANYWHERE)).uniqueResult();
    }
}

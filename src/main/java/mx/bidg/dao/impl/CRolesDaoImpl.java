package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRolesDao;
import mx.bidg.model.CRoles;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CRolesDaoImpl extends AbstractDao<Integer,CRoles> implements CRolesDao {

    @Override
    public CRoles findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRoles> findAll() {
        return createEntityCriteria().add(Restrictions.ne("idRole", 0)).list();
    }

    @Override
    public CRoles save(CRoles entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CRoles update(CRoles entity) {
        update(entity);
        return entity;
    }

    @Override
    public boolean delete(CRoles entity) {
        delete(entity);
        return true;
    }

    @Override
    public CRoles findByIdRole(Integer idRole) {
        return (CRoles) createEntityCriteria()
                .add(Restrictions.eq("idRole",idRole))
                .uniqueResult();
    }
}

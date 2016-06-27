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
    public CRoles save(CRoles entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CRoles findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRoles> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CRoles update(CRoles entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CRoles entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CRoles findByIdRole(Integer idRole) {
        return (CRoles) createEntityCriteria()
                .add(Restrictions.eq("idRole",idRole))
                .uniqueResult();
    }
}

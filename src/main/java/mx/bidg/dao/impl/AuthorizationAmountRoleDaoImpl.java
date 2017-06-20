package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationAmountRoleDao;
import mx.bidg.model.AuthorizationAmountRole;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 10/06/17.
 */
@Repository
public class AuthorizationAmountRoleDaoImpl extends AbstractDao<Integer, AuthorizationAmountRole> implements AuthorizationAmountRoleDao {

    @Override
    public AuthorizationAmountRole save(AuthorizationAmountRole entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AuthorizationAmountRole findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<AuthorizationAmountRole> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public AuthorizationAmountRole update(AuthorizationAmountRole entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AuthorizationAmountRole entity) {
        remove(entity);
        return true;
    }

    @Override
    public AuthorizationAmountRole findByRole(Integer idRole) {
        return (AuthorizationAmountRole) createEntityCriteria()
                .add(Restrictions.eq("idRole", idRole))
                .uniqueResult();
    }
}

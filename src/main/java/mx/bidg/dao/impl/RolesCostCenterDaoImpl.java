package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RolesCostCenterDao;
import mx.bidg.model.RolesCostCenter;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class RolesCostCenterDaoImpl extends AbstractDao<Integer, RolesCostCenter> implements RolesCostCenterDao {

    @Override
    public RolesCostCenter save(RolesCostCenter entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RolesCostCenter findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RolesCostCenter> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RolesCostCenter update(RolesCostCenter entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RolesCostCenter entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<RolesCostCenter> findByRole(Integer idRole) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRole", idRole))
                .createCriteria("costCenter")
                .addOrder( Order.asc("name"))
                .list();
    }
}

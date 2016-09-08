package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.FixedOperationRoleDao;
import mx.bidg.model.FixedOperationRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class FixedOperationRoleDaoImpl extends AbstractDao<Integer, FixedOperationRole> implements FixedOperationRoleDao {

    @Override
    public FixedOperationRole save(FixedOperationRole entity) {
        persist(entity);
        return entity;
    }

    @Override
    public FixedOperationRole findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<FixedOperationRole> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public FixedOperationRole update(FixedOperationRole entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(FixedOperationRole entity) {
        remove(entity);
        return true;
    }
}

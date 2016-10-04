package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ApportionmentRoleDao;
import mx.bidg.model.ApportionmentRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
public class ApportionmentRoleDaoImpl extends AbstractDao<Integer, ApportionmentRole> implements ApportionmentRoleDao {

    @Override
    public ApportionmentRole save(ApportionmentRole entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ApportionmentRole findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<ApportionmentRole> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public ApportionmentRole update(ApportionmentRole entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(ApportionmentRole entity) {
        remove(entity);
        return true;
    }
}

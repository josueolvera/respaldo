package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CalculationRolesDao;
import mx.bidg.model.CalculationRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class CalculationRolesDaoImpl extends AbstractDao<Integer,CalculationRoles> implements CalculationRolesDao {

    @Override
    public CalculationRoles save(CalculationRoles entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CalculationRoles findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CalculationRoles> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CalculationRoles update(CalculationRoles entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CalculationRoles entity) {
        remove(entity);
        return true;
    }
}

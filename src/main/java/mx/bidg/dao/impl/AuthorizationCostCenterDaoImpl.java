package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationCostCenterDao;
import mx.bidg.model.AuthorizationCostCenter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 15/03/2017.
 */
@Repository
public class AuthorizationCostCenterDaoImpl extends AbstractDao<Integer,AuthorizationCostCenter> implements AuthorizationCostCenterDao{
    @Override
    public List<AuthorizationCostCenter> findByCostCenter(Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idCostCenter",idCostCenter)).list();
    }

    @Override
    public AuthorizationCostCenter findByIdCostCenterAndYear(Integer idCostCenter, Integer year) {
        Criteria criteria = createEntityCriteria();
        return (AuthorizationCostCenter) criteria.add(Restrictions.eq("idCostCenter",idCostCenter)).
                add(Restrictions.eq("year",year)).uniqueResult();
    }

    @Override
    public AuthorizationCostCenter save(AuthorizationCostCenter entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AuthorizationCostCenter findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<AuthorizationCostCenter> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public AuthorizationCostCenter update(AuthorizationCostCenter entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AuthorizationCostCenter entity) {
        remove(entity);
        return true;
    }
}

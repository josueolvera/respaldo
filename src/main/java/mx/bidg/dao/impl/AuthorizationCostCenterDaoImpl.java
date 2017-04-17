package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationCostCenterDao;
import mx.bidg.model.AuthorizationCostCenter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DONCHINGON on 15/03/2017.
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
    public List<Integer> getAllCostCentersRNAByIdsCostCenters(List<Integer> idsCostCenters) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionCC = Restrictions.disjunction();

        if (!idsCostCenters.isEmpty()){
            for(Integer idCostCenter : idsCostCenters){
                disjunctionCC.add(Restrictions.eq("idCostCenter", idCostCenter));
            }
        }

        return criteria
                .add(Restrictions.disjunction(disjunctionCC))
                .add(Restrictions.eq("idCCostCenterStatus",2))
                .setProjection(Projections.distinct(Projections.property("idCostCenter")))
                .list();
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

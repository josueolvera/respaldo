package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorCostCenterDao;
import mx.bidg.model.DistributorCostCenter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
@Repository
public class DistributorCostCenterDaoImpl extends AbstractDao<Integer, DistributorCostCenter> implements DistributorCostCenterDao{
    @Override
    public DistributorCostCenter save(DistributorCostCenter entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DistributorCostCenter findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DistributorCostCenter> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DistributorCostCenter update(DistributorCostCenter entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DistributorCostCenter entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<DistributorCostCenter> findByCostCenter(Integer idCostCenter) {
        return createEntityCriteria().add(Restrictions.eq("idCostCenter", idCostCenter)).list();
    }

    @Override
    public DistributorCostCenter findByIdCostCenter(Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return (DistributorCostCenter) criteria.add(Restrictions.eq("idCostCenter",idCostCenter)).uniqueResult();
    }

    @Override
    public List<DistributorCostCenter> findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine, Integer idDistributor,Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idBussinessLine",idBussinessLine)).
                add(Restrictions.eq("idDistributor",idDistributor)).
                add(Restrictions.eq("idCostCenter",idCostCenter)).list();
    }
}

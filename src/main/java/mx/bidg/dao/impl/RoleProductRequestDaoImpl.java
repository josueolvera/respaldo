package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RoleProductRequestDao;
import mx.bidg.model.RoleProductRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

/**
 * Created by g_on_ on 22/05/2017.
 */
@Repository
public class RoleProductRequestDaoImpl extends AbstractDao<Integer, RoleProductRequest> implements RoleProductRequestDao{

    @Override
    public RoleProductRequest save(RoleProductRequest entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RoleProductRequest update(RoleProductRequest entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RoleProductRequest entity) {
        remove(entity);
        return true;
    }

    @Override
    public RoleProductRequest findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RoleProductRequest> findAll() {
        return (List<RoleProductRequest>) createEntityCriteria().list();
    }

    @Override
    public List<Integer> getIdsProductsRequestByDistributorCostCenter(Integer idDistributorCostCenter){
        Criteria criteria = createEntityCriteria();

        if (idDistributorCostCenter != null){
            criteria.add(Restrictions.eq("idDistributorCostCenter",idDistributorCostCenter));
        }

        return criteria.setProjection(Projections.distinct(Projections.property("idProductRequest"))).list();
    }

    @Override
    public List<RoleProductRequest> findByDistributorCostCenter(Integer idDistrbutorCostCenter) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDistributorCostCenter", idDistrbutorCostCenter))
                .createCriteria("cProductsRequest")
                .addOrder(Order.asc("productRequestName"))
                .list();
    }
}

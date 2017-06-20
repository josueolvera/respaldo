package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.dao.PolicyTruckdriverDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResizableByteArrayOutputStream;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
@Repository
public class PolicyTruckdriverDaoImpl extends AbstractDao<Integer, PolicyTruckdriver> implements PolicyTruckdriverDao {
    @Override
    public PolicyTruckdriver save(PolicyTruckdriver entity) {
        getSession().persist(entity);
        return entity;
    }

    @Override
    public PolicyTruckdriver findById(int id) {return getByKey(id);}

    @Override
    public List<PolicyTruckdriver> findAll() {
        return (List<PolicyTruckdriver>)createEntityCriteria().list();
    }

    @Override
    public PolicyTruckdriver update(PolicyTruckdriver entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PolicyTruckdriver entity) {
        delete(entity);
        return true;
    }

    @Override
    public List<PolicyTruckdriver> findDStartValidity(LocalDate starDate) {
        return  createEntityCriteria().add(Restrictions.eq("dStartValidity", starDate)).list();
    }

    @Override
    public List<PolicyTruckdriver> findDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(
                Restrictions.between("dStartValidity",starDate, finalDate)).list();
    }

    @Override
    public List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .setProjection(Projections.property("numFolio"))
                .add(Restrictions.between("dStartValidity",startDate, endDate))
                .list();
    }

    @Override
    public PolicyTruckdriver findByFolio(String folio) {
        return (PolicyTruckdriver) createEntityCriteria().add(Restrictions.eq("numFolio", folio)).uniqueResult();
    }

    @Override
    public List findFoliosCommissionIvaByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();
        ProjectionList properties = Projections.projectionList();

        properties.add(Projections.property("numFolio"));
        properties.add(Projections.property("ip.commissionAlterna"));
        properties.add(Projections.property("ip.ivaAlterna"));

        criteria.createAlias("insurancePremium", "ip");
        criteria.setProjection(properties);
        criteria.add(Restrictions.between("dStartValidity",startDate, endDate));
        criteria.addOrder(Order.asc("numFolio"));

        return criteria.list();
    }

    @Override
    public List<String> getNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .setProjection(Projections.property("authorizationNumber"))
                .add(Restrictions.between("dStartValidity",startDate, endDate))
                .list();
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PolicyTruckdriverMonthlyDao;
import mx.bidg.model.PolicyTruckdriverMonthly;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Desarrollador on 18/01/2017.
 */
@Repository
public class PolicyTruckdriverMonthlyDaoImpl extends AbstractDao <Integer, PolicyTruckdriverMonthly> implements PolicyTruckdriverMonthlyDao {

    @Override
    public PolicyTruckdriverMonthly save(PolicyTruckdriverMonthly entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PolicyTruckdriverMonthly findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PolicyTruckdriverMonthly> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PolicyTruckdriverMonthly update(PolicyTruckdriverMonthly entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PolicyTruckdriverMonthly entity) {
        remove(entity);
        return true;
    }

    @Override
    public PolicyTruckdriverMonthly findByLicensePlateUserAndDate(String numLicensePlate, Integer idUser, LocalDate dStartValidity) {
        Criteria criteria = createEntityCriteria();
        return (PolicyTruckdriverMonthly) criteria
                .add(Restrictions.eq("numLicensePlate", numLicensePlate))
                .add(Restrictions.eq("idUser", idUser))
                .add(Restrictions.eq("dStartValidity",dStartValidity))
                .uniqueResult();
    }

    @Override
    public List<PolicyTruckdriverMonthly> findDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
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
    public List findFoliosCommissionIvaByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();
        ProjectionList properties = Projections.projectionList();

        properties.add(Projections.property("numFolio"));
        properties.add(Projections.property("i.commissionAlterna"));
        properties.add(Projections.property("i.ivaAlterna"));

        criteria.createAlias("insurancePremium", "i");
        criteria.setProjection(properties);
        criteria.add(Restrictions.between("dStartValidity",startDate, endDate));
        criteria.addOrder(Order.asc("numFolio"));

        return criteria.list();
    }
}

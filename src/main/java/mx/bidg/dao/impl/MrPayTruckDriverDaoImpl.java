package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.MrPayTruckDriverDao;
import mx.bidg.model.MrPayTruckDriver;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
@Repository
public class MrPayTruckDriverDaoImpl extends AbstractDao<Integer,MrPayTruckDriver>implements MrPayTruckDriverDao{
    @Override
    public MrPayTruckDriver save(MrPayTruckDriver entity) {
        persist(entity);
        return entity;
    }

    @Override
    public MrPayTruckDriver findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<MrPayTruckDriver> findAll() {
        return (List<MrPayTruckDriver>)createEntityCriteria().list();
    }

    @Override
    public MrPayTruckDriver update(MrPayTruckDriver entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(MrPayTruckDriver entity) {
        remove(entity);
        return true;
    }

    @Override
    public MrPayTruckDriver findAuthorizationNumber(String authorizationNumber) {
        Criteria criteria = createEntityCriteria();
        return (MrPayTruckDriver) criteria.add(Restrictions.eq("authorizationNumber",authorizationNumber)).uniqueResult();
    }

    @Override
    public List<MrPayTruckDriver> findByPaymentDate(LocalDate paymentDate) {
        Criteria criteria =  createEntityCriteria();
        return criteria.add(Restrictions.eq("paymentDate", paymentDate)).list();
    }

    @Override
    public List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .setProjection(Projections.property("authorizationNumber"))
                .add(Restrictions.between("paymentDate",startDate, endDate))
                .list();
    }

    @Override
    public List<MrPayTruckDriver> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        Criteria criteria =  createEntityCriteria();
        return criteria.add(Restrictions.between("paymentDate", startDate, endDate)).list();
    }
}

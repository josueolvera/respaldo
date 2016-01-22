/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PeriodicPaymentsDao;
import mx.bidg.model.PeriodicsPayments;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodicPaymentsDaoImpl extends AbstractDao<Integer, PeriodicsPayments> implements PeriodicPaymentsDao {

    @Override
    public PeriodicsPayments save(PeriodicsPayments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PeriodicsPayments findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PeriodicsPayments> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PeriodicsPayments update(PeriodicsPayments entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(PeriodicsPayments entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PeriodicsPayments> findByFolio(String folio) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("folio", folio))
                .setFetchMode("idPeriodicPaymentStatus", FetchMode.JOIN);
        return (List<PeriodicsPayments>) criteria.list();
    }
    
}

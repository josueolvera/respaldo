/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionEmcofinDao;
import mx.bidg.model.CommissionEmcofin;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;



/**
 *
 * @author Cristhian de la cruz
 */
@SuppressWarnings("unchecked")
@Repository
public class CommissionEmcofinDaoImpl extends AbstractDao<Integer, CommissionEmcofin> implements CommissionEmcofinDao{

    @Override
    public CommissionEmcofin save(CommissionEmcofin entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CommissionEmcofin findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CommissionEmcofin> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CommissionEmcofin update(CommissionEmcofin entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CommissionEmcofin entity) {
        remove(entity);
        return true;
    }

    @Override
    public CommissionEmcofin finfByidEmployee(int idEmployee, LocalDateTime applicationDate) {
        return (CommissionEmcofin) createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("applicationDate",applicationDate))
                .uniqueResult();
    }
    
}


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
import mx.bidg.model.Commission_Emcofin;
import mx.bidg.model.Outsourcing;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;



/**
 *
 * @author Cristhian de la cruz
 */
@SuppressWarnings("unchecked")
@Repository
public class CommissionEmcofinDaoImpl extends AbstractDao<Integer, Commission_Emcofin> implements CommissionEmcofinDao{

    @Override
    public Commission_Emcofin save(Commission_Emcofin entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Commission_Emcofin findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Commission_Emcofin> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Commission_Emcofin update(Commission_Emcofin entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Commission_Emcofin entity) {
        remove(entity);
        return true;
    }

    @Override
    public Commission_Emcofin finfByidEmployee(int idEmployee, LocalDateTime applicationDate) {
        return (Commission_Emcofin) createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("applicationDate",applicationDate))
                .uniqueResult();
    }
    
}


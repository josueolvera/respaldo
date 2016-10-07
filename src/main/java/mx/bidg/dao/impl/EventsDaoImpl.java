/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EventsDao;
import mx.bidg.model.Events;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rubens
 */
@Repository
public class EventsDaoImpl extends AbstractDao<Integer, Events> implements EventsDao{

    @Override
    public Events save(Events entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Events findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Events> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<Events>) criteria.addOrder(Order.asc("title")).list();
    }

    @Override
    public Events update(Events entity) {
         modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Events entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<Events> getEvents(Integer idRoom, Integer idUser, String date) {
        Criteria criteria = createEntityCriteria();
        
        if (idRoom != null) {
            criteria.add(Restrictions.eq("idRoom", idRoom));
        }
        
        if (idUser != null) {
            criteria.add(Restrictions.eq("idUser", idUser));
        }
        
        
        return criteria.list();
    }
    
}

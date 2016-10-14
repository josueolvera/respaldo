/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EventsDao;
import mx.bidg.model.Events;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
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
    public List<Events> getEvents(Integer idRoom, Integer idUser, LocalDateTime day) {
        Criteria criteria = createEntityCriteria();
        
        if (idRoom != null) {
            criteria.add(Restrictions.eq("idRoom", idRoom));
        }
        
        if (idUser != null) {
            criteria.add(Restrictions.eq("idUser", idUser));
        }

        if (day != null) {
            
            LocalDateTime startDay = day.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endDay = day.withHour(23).withMinute(59).withSecond(59);
             
            criteria.add(Restrictions.between("start", startDay, endDay));
        }
        criteria.addOrder(Order.asc("start"));
        return criteria.list();
    }

    @Override
    public Events findBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, Integer idRoom) {
        
        Disjunction disjunction = Restrictions.disjunction();
        
        disjunction.add(Restrictions.between("start", start, end))
                .add(Restrictions.between("end", start, end));
        
        return (Events) createEntityCriteria()
               
                .add(Restrictions.eq("idRoom", idRoom))
                .add(disjunction)
                .uniqueResult();
    }
    
}

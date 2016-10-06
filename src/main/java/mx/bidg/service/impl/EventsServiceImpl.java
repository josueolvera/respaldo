/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.EventsDao;
import mx.bidg.model.Events;
import mx.bidg.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rubens
 */
@Service
@Transactional
public class EventsServiceImpl implements EventsService{
    
    @Autowired
    private EventsDao eventsDao;

    @Override
    public List<Events> findAll() {
        return eventsDao.findAll();
        
    }

    @Override
    public Events findById(Integer idevent) {
        return eventsDao.findById(idevent);
    }

    @Override
    public Events save(Events eVents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Events update(Events eVents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean delete(Events eVents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Events> getEvents(Integer idRoom, Integer idUser) {
        return eventsDao.getEvents(idRoom, idUser);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mx.bidg.dao.EventsDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CRooms;
import mx.bidg.model.Events;
import mx.bidg.model.Users;
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
    
    @Autowired
    private UsersDao usersDao;
    
    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Events> findAll() {
        return eventsDao.findAll();
        
    }

    @Override
    public Events findById(Integer idevent) {
        return eventsDao.findById(idevent);
    }

    @Override
    public Events save(String data, Integer idRoom) throws Exception{
        JsonNode node = mapper.readTree(data);
        
        Users user = usersDao.findById(node.get("idUser").asInt());
        
        if (user != null) {
            Events event = new Events();
            
            LocalDateTime end = LocalDateTime.parse(node.get("end").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime start = LocalDateTime.parse(node.get("start").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
           if( start.isBefore(end)) {
               
               if (eventsDao.findBetweenStartAndEnd(start, end, idRoom) == null) {
                    event.setEnd(end);
                    event.setStart(start);
                    event.setTitle(node.get("title").asText());
                    event.setRoom(mapper.treeToValue(node.get("room"), CRooms.class));
                    event.setUser(user);

                    event = eventsDao.save(event);
                    
                    return event;
               } else {
                   throw new ValidationException("TIEMPO RESERVADO", "Ya existe una reser vación para entre estas horas");
               }       
           } else {
               throw new ValidationException("HORAS NO VALIDAS", "La hora final no puede ser antes de la hora inicial");
           }         
        } else {
            throw new ValidationException("USER NOT FOUD","Usuario inexistente");
        }
        
    }

    @Override
    public Events update(Events eVents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean delete(Events eVents) {
        return eventsDao.delete(eVents);
    }

    @Override
    public List<Events> getEvents(Integer idRoom, Integer idUser, String day) {
        
        LocalDateTime dayDateTime = null;
        
        if (day != null) {
            dayDateTime = LocalDateTime.parse(day + "T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        
        return eventsDao.getEvents(idRoom, idUser, dayDateTime);
    }

    
    
}

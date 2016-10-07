/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CRooms;
import mx.bidg.model.Events;



/**
 *
 * @author rubens
 */
public interface EventsService {
    
    
    List<Events> findAll();
    Events findById(Integer idevent);
    Events save(Events eVents);
    Events update(Events eVents);
    Boolean delete(Events eVents);


    List<Events> getEvents(Integer idRoom, Integer idUser, String idDate);
   
            
    
}

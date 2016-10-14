/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.model.Events;

/**
 *
 * @author rubens
 */
public interface EventsDao extends InterfaceDao<Events>{

    List<Events> getEvents(Integer idRoom, Integer idUser, LocalDateTime day);
    
    Events findBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, Integer idRoom);
}

package mx.bidg.service;

import mx.bidg.model.PlaneTickets;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PlaneTicketsService {
    List<PlaneTickets> findAll();
    PlaneTickets findById(Integer id);
    PlaneTickets save(PlaneTickets planeTicket);
    PlaneTickets update(PlaneTickets planeTicket);
    Boolean delete(PlaneTickets planeTicket);
}

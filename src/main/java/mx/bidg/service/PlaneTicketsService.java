package mx.bidg.service;

import mx.bidg.model.CPlaneTicketsTypes;
import mx.bidg.model.PlaneTickets;
import mx.bidg.model.Users;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PlaneTicketsService {
    List<PlaneTickets> findAll();
    PlaneTickets findById(Integer id);
    PlaneTickets save(String data, Users user) throws IOException;
    PlaneTickets update(PlaneTickets planeTicket);
    Boolean delete(PlaneTickets planeTicket);
}

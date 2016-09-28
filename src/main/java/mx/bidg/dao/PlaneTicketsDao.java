package mx.bidg.dao;

import mx.bidg.model.PlaneTickets;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PlaneTicketsDao extends InterfaceDao<PlaneTickets> {
    List<PlaneTickets> getPlaneTickets(Integer idUser);
}
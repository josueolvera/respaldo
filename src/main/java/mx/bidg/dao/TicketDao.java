package mx.bidg.dao;

import mx.bidg.model.CTicketsCategories;
import mx.bidg.model.Ticket;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
public interface TicketDao extends InterfaceDao<Ticket> {
    List<Ticket> findByPriority(Integer idPriority);
    List<Ticket> findByTicketStatus(Integer idTicketStatus);
    List<Ticket> findByTicketStatusPriority(Integer idPriority,Integer idTicketStatus);
    Ticket findByFolio(String folio);
    List<Ticket> findAll(CTicketsCategories category);
    List<Ticket>findStatusOpen(Integer idTicketStatus);
}

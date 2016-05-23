package mx.bidg.service;

import mx.bidg.model.*;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
public interface TicketService {

    Ticket findById(Integer id);
    List<Ticket> findAll();
    Ticket save(Ticket ticket);
    Ticket update(Ticket ticket);
    List<Ticket> findByPriority(Integer idPriority);
    List<Ticket> findByTicketStatus(Integer idTicketStatus);
    List<Ticket> findByTicketStatusPriority(Integer idTicketStatus, Integer idPriority);
    Ticket changeTicketStatus(Integer idTicket, CTicketStatus ticketStatus);
    EmailTemplates sendEmail(Ticket ticket);
    Ticket findByFolio(String folio);
}

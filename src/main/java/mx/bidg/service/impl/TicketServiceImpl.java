package mx.bidg.service.impl;

import mx.bidg.dao.NotificationsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.dao.TicketDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.model.*;
import mx.bidg.service.EmailDeliveryService;
import mx.bidg.service.EmailTemplatesService;
import mx.bidg.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@Transactional
@Service
@PropertySource(value = {"classpath:application.properties"})
public class TicketServiceImpl implements TicketService {

    @Value("${notification.email_design_ticket_template_name}")
    private String EMAIL_DESIGN_TICKET_TEMPLATE_NAME;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private NotificationsDao notificationsDao;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private RequestsDao requestsDao;

    @Override
    public Ticket findById(Integer id) {
        return ticketDao.findById(id);
    }

    @Override
    public List<Ticket> findAll(CTicketsCategories category) {
        return ticketDao.findAll(category);
    }

    @Override
    public Ticket save(Ticket ticket) {
        String correo = ticket.getUser().getMail();

        int begin = correo.indexOf("@");
        int length = correo.length();

        String domain = "";

        for (int i = begin + 1; i <  length; i++) {
            if (correo.charAt(i) != '.') {
                domain += correo.charAt(i);
            } else break;
        }

        ticket.setFechaInicio(LocalDateTime.now());
        long secondsSinceEpoch = ticket.getFechaInicio().toInstant(ZoneOffset.UTC).getEpochSecond();
        String folio = domain.toUpperCase() + "-" + String.valueOf(secondsSinceEpoch);

        ticket.setFolio(folio);

        ticketDao.save(ticket);
        sendEmailNewTicket(ticket);

        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        return ticketDao.update(ticket);
    }

    @Override
    public List<Ticket> findByPriority(Integer idPriority) {
        return ticketDao.findByPriority(idPriority);
    }

    @Override
    public List<Ticket> findByTicketStatus(Integer idTicketStatus) {
        return ticketDao.findByTicketStatus(idTicketStatus);
    }

    @Override
    public List<Ticket> findByTicketStatusPriority(Integer idPriority,Integer idTicketStatus) {
        return ticketDao.findByTicketStatusPriority(idPriority,idTicketStatus);
    }

    @Override
    public Ticket changeTicketStatus(Integer idTicket, CTicketStatus ticketStatus) {
        Ticket ticket = ticketDao.findById(idTicket);
        ticket.setTicketStatus(ticketStatus);

        if (ticketStatus.equals(CTicketStatus.CERRADO)) {
            ticket.setFechaFinal(LocalDateTime.now());
        }

        ticketDao.update(ticket);

        sendEmailStatusTicket(ticket);
        return ticket;
    }

    @Override
    public EmailTemplates sendEmailNewTicket(Ticket ticket) {
        EmailTemplates emailTemplate = emailTemplatesService.findById(ticket.getIncidence().getTicketCategory().getIdEmailTemplate());
        emailTemplate.addProperty("ticket", ticket);
        emailTemplate.addRecipient(new EmailRecipients(ticket.getUser().getMail(), ticket.getUser().getUsername(), EmailRecipients.TO));

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public EmailTemplates sendEmailStatusTicket(Ticket ticket) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("ticket_status_notification");
        emailTemplate.addProperty("ticket", ticket);
        emailTemplate.addRecipient(new EmailRecipients(ticket.getUser().getMail(), ticket.getUser().getUsername(), EmailRecipients.TO));

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public Ticket findByFolio(String folio) {
        return ticketDao.findByFolio(folio);
    }

    @Override
    public List<Ticket> findStatusOpen(Integer idTicketStatus) {
        return ticketDao.findStatusOpen(idTicketStatus);
    }
}

package mx.bidg.service;

import mx.bidg.model.EmailTemplates;
import mx.bidg.model.PlaneTickets;

/**
 * Created by gerardo8 on 19/07/16.
 */
public interface RequestPlaneTicketEmailNotificationService {
    EmailTemplates sendEmailToUser(PlaneTickets planeTicket);
    EmailTemplates sendEmailToAdminAux(PlaneTickets planeTicket);
}

package mx.bidg.service.impl;

import mx.bidg.model.EmailRecipients;
import mx.bidg.model.EmailTemplates;
import mx.bidg.model.PlaneTickets;
import mx.bidg.service.EmailDeliveryService;
import mx.bidg.service.EmailTemplatesService;
import mx.bidg.service.RequestPlaneTicketEmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo8 on 19/07/16.
 */
@Service
public class RequestPlaneTicketEmailNotificationServiceImpl implements RequestPlaneTicketEmailNotificationService {

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Override
    public EmailTemplates sendEmailToUser(PlaneTickets planeTicket) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_plane_ticket_user_notification");

//        if (planeTicket.getIsOutOfDateRequest()) {
//            emailTemplate.addProperty("outOfDateRequestMessage", "Fuera de fechas de solicitud.");
//        }
//        emailTemplate.addProperty("planeTicket", planeTicket);
//        emailTemplate.addRecipient(
//                new EmailRecipients(
//                        planeTicket.getRequest().getUserRequest().getMail(),
//                        planeTicket.getRequest().getUserRequest().getUsername(),
//                        EmailRecipients.TO
//                )
//        );

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public EmailTemplates sendEmailToAdminAux(PlaneTickets planeTicket) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_plane_ticket_admin_aux_notification");

//        if (planeTicket.getIsOutOfDateRequest()) {
//            emailTemplate.addProperty("outOfDateRequestMessage", "Fuera de fechas de solicitud.");
//        }
//
//        emailTemplate.addProperty("planeTicket", planeTicket);

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }
}

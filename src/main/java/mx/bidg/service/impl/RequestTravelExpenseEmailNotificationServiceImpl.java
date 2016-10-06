package mx.bidg.service.impl;

import mx.bidg.model.EmailRecipients;
import mx.bidg.model.EmailTemplates;
import mx.bidg.model.TravelExpenses;
import mx.bidg.service.EmailDeliveryService;
import mx.bidg.service.EmailTemplatesService;
import mx.bidg.service.RequestTravelExpenseEmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Service
public class RequestTravelExpenseEmailNotificationServiceImpl implements RequestTravelExpenseEmailNotificationService{

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Override
    public EmailTemplates sendEmailToUser(TravelExpenses travelExpense) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_travel_expense_user_notification");

        if (travelExpense.getIsOutOfDateRequest()) {
            emailTemplate.addProperty("outOfDateRequestMessage", "Fuera de fechas de solicitud.");
        }
        emailTemplate.addProperty("travelExpense", travelExpense);
        emailTemplate.addRecipient(
                new EmailRecipients(
                        travelExpense.getRequest().getUserRequest().getMail(),
                        travelExpense.getRequest().getUserRequest().getUsername(),
                        EmailRecipients.TO
                )
        );

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public EmailTemplates sendEmailToAdminAux(TravelExpenses travelExpense) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_travel_expense_admin_aux_notification");

        if (travelExpense.getIsOutOfDateRequest()) {
            emailTemplate.addProperty("outOfDateRequestMessage", "Fuera de fechas de solicitud.");
        }

        emailTemplate.addProperty("travelExpense", travelExpense);

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public EmailTemplates sendEmailToUserStatus(TravelExpenses travelExpense) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_travel_expense_user_notification_status");
        emailTemplate.addProperty("travelExpense", travelExpense);
        emailTemplate.addRecipient(new EmailRecipients(travelExpense.getRequest().getUserRequest().getMail(), travelExpense.getRequest().getUserRequest().getUsername(), EmailRecipients.TO));

        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }
}

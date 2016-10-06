package mx.bidg.service;

import mx.bidg.model.EmailTemplates;
import mx.bidg.model.TravelExpenses;

/**
 * Created by gerardo8 on 14/07/16.
 */
public interface RequestTravelExpenseEmailNotificationService {

    EmailTemplates sendEmailToUser(TravelExpenses travelExpense);

    EmailTemplates sendEmailToAdminAux(TravelExpenses travelExpense);

    EmailTemplates sendEmailToUserStatus(TravelExpenses travelExpense);
}

package mx.bidg.service;

import mx.bidg.model.EmailTemplates;
import mx.bidg.model.Users;

/**
 * @author Rafael Viveros
 * Created on 29/02/16.
 */
public interface EmailDeliveryService {
    void deliverEmail(EmailTemplates emailTemplate);
    void deliverEmailWithUser(EmailTemplates emailTemplate, Users users);
    void sendEmailwithAttachmentTruckDriver(EmailTemplates emailTemplate, String filePath);
}

package mx.bidg.service;

import mx.bidg.model.EmailTemplates;

/**
 * @author Rafael Viveros
 * Created on 29/02/16.
 */
public interface EmailDeliveryService {
    void deliverEmail(EmailTemplates emailTemplate);
}

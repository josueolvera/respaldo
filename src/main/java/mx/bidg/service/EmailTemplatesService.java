package mx.bidg.service;

import mx.bidg.model.EmailTemplates;

/**
 * @author Rafael Viveros
 * Created on 1/03/16.
 */
public interface EmailTemplatesService {
    EmailTemplates findByName(String templateName);
}

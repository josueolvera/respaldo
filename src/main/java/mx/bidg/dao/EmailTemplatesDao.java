package mx.bidg.dao;

import mx.bidg.model.EmailTemplates;

/**
 * @author Rafael Viveros
 * Created on 1/03/16.
 */
public interface EmailTemplatesDao extends InterfaceDao<EmailTemplates> {
    EmailTemplates findByName(String templateName);
}

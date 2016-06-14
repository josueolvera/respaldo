package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmailTemplatesDao;
import mx.bidg.model.EmailTemplates;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 1/03/16.
 */
@Repository
public class EmailTemplatesDaoImpl extends AbstractDao<Integer, EmailTemplates> implements EmailTemplatesDao {
    @Override
    public EmailTemplates findByName(String templateName) {
        return (EmailTemplates) createEntityCriteria()
                .setFetchMode("emailRecipientsList", FetchMode.JOIN)
                .add(Restrictions.eq("templateName", templateName))
                .uniqueResult();
    }

    @Override
    public EmailTemplates save(EmailTemplates entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public EmailTemplates findById(int id) {
        return (EmailTemplates) createEntityCriteria()
                .setFetchMode("emailRecipientsList", FetchMode.JOIN)
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<EmailTemplates> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public EmailTemplates update(EmailTemplates entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(EmailTemplates entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

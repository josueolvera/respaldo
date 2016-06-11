package mx.bidg.service.impl;

import mx.bidg.dao.EmailTemplatesDao;
import mx.bidg.model.EmailTemplateFiles;
import mx.bidg.model.EmailTemplates;
import mx.bidg.service.EmailTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Rafael Viveros
 * Created on 1/03/16.
 */
@Service
@Transactional
public class EmailTemplatesServiceImpl implements EmailTemplatesService {

    @Autowired
    private EmailTemplatesDao emailTemplatesDao;

    private Logger logger = Logger.getLogger(EmailTemplatesServiceImpl.class.getName());

    @Override
    public EmailTemplates findByName(String templateName) {
        EmailTemplates emailTemplates = emailTemplatesDao.findByName(templateName);
        List<EmailTemplateFiles> files = emailTemplates.getEmailTemplateFilesList();
        logger.info("Loading email files." + files);
        emailTemplates.setEmailTemplateFilesList(files);
        return emailTemplates;
    }

    @Override
    public EmailTemplates findById(Integer id) {
        EmailTemplates emailTemplates = emailTemplatesDao.findById(id);
        List<EmailTemplateFiles> files = emailTemplates.getEmailTemplateFilesList();
        logger.info("Loading email files." + files);
        emailTemplates.setEmailTemplateFilesList(files);
        return emailTemplates;
    }
}

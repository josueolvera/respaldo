package mx.bidg.service.impl;

import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import mx.bidg.model.EmailRecipients;
import mx.bidg.model.EmailTemplateFiles;
import mx.bidg.model.EmailTemplates;
import mx.bidg.model.Users;
import mx.bidg.service.EmailDeliveryService;
import mx.bidg.service.NotificationsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rafael Viveros
 * Created on 29/02/16.
 */
@Service
@PropertySource(value = {"classpath:application.properties"})
public class EmailDeliveryServiceImpl implements EmailDeliveryService {

    @Value("${email_templates.dir}")
    private String TEMPLATES_DIR;

    @Value("${enable_email_delivery}")
    private Boolean ENABLE_EMAIL_DELIVERY;

    @Async
    public void deliverEmail(final EmailTemplates emailTemplate) {
        if (! ENABLE_EMAIL_DELIVERY) {
            return;
        }
        try {
            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", emailTemplate.getSmtpHost());
            props.put("mail.smtp.port", emailTemplate.getSmtpPort());
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailTemplate.getSmtpUser(), emailTemplate.getSmtpPassword());
                }
            });

            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            message.setFrom(new InternetAddress(emailTemplate.getSenderAddress(), emailTemplate.getSenderName()));

            for (EmailRecipients recipient : emailTemplate.getEmailRecipientsList()) {
                message.addRecipient(
                        getRecipientType(recipient.getRecipientType()),
                        new InternetAddress(recipient.getEmailAddress(), recipient.getRecipientName())
                );
            }

            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("gmorales@bidg.mx", "Gustavo Morales"));
            message.setSentDate(new Date());

            JtwigConfiguration jtwigConf = new JtwigConfiguration();
            JtwigModelMap modelMap = new JtwigModelMap().add(emailTemplate.getProperties());

            JtwigTemplate subjectTemplate = new JtwigTemplate(emailTemplate.getEmailSubject(), jtwigConf);
            String compiledSubject = subjectTemplate.output(modelMap);
            message.setSubject(compiledSubject);

            JtwigTemplate messageTemplate = new JtwigTemplate(emailTemplate.getMessage(), jtwigConf);
            String compiledMessage = messageTemplate.output(modelMap);

            String templateFilePath = TEMPLATES_DIR + emailTemplate.getTemplateFile();
            JtwigTemplate template = new JtwigTemplate(new File(templateFilePath), jtwigConf);
            String content = template.output(
                    new JtwigModelMap()
                            .add("title", emailTemplate.getMessageTitle())
                            .add("body", compiledMessage)
            );
            messageBodyPart.setContent(content, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            for (EmailTemplateFiles templateFile : emailTemplate.getEmailTemplateFilesList()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(TEMPLATES_DIR + templateFile.getFilePath());
                attachPart.setFileName(templateFile.getFileName());
                if (templateFile.getContentId() != null) {
                    attachPart.setHeader("Content-ID", templateFile.getContentId());
                }
                multipart.addBodyPart(attachPart);
            }

            for (EmailTemplateFiles templateFile : emailTemplate.getAdditionalFiles()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(templateFile.getFilePath());
                attachPart.setFileName(templateFile.getFileName());
                if (templateFile.getContentId() != null) {
                    attachPart.setHeader("Content-ID", templateFile.getContentId());
                }
                multipart.addBodyPart(attachPart);
            }

            message.setContent(multipart);
            Logger.getLogger(NotificationsService.class.getName()).log(Level.INFO, "Sending email");
            Transport.send(message);
        } catch (Exception e) {
            Logger.getLogger(NotificationsService.class.getName()).log(Level.WARNING, "Unable to send email", e);
        }
    }

    @Override
    public void deliverEmailWithUser(EmailTemplates emailTemplate, Users users) {
        if (! ENABLE_EMAIL_DELIVERY) {
            return;
        }
        try {
            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", emailTemplate.getSmtpHost());
            props.put("mail.smtp.port", emailTemplate.getSmtpPort());
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailTemplate.getSmtpUser(), emailTemplate.getSmtpPassword());
                }
            });

            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            message.setFrom(new InternetAddress(emailTemplate.getSenderAddress(), emailTemplate.getSenderName()));

            message.addRecipient(
                    getRecipientType(1),
                    new InternetAddress(users.getMail(), users.getUsername())
            );

            for (EmailRecipients recipient : emailTemplate.getEmailRecipientsList()) {
                message.addRecipient(
                        getRecipientType(recipient.getRecipientType()),
                        new InternetAddress(recipient.getEmailAddress(), recipient.getRecipientName())
                );
            }

            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("gmorales@bidg.mx", "Gustavo Morales"));
            message.setSentDate(new Date());

            JtwigConfiguration jtwigConf = new JtwigConfiguration();
            JtwigModelMap modelMap = new JtwigModelMap().add(emailTemplate.getProperties());

            JtwigTemplate subjectTemplate = new JtwigTemplate(emailTemplate.getEmailSubject(), jtwigConf);
            String compiledSubject = subjectTemplate.output(modelMap);
            message.setSubject(compiledSubject);

            JtwigTemplate messageTemplate = new JtwigTemplate(emailTemplate.getMessage(), jtwigConf);
            String compiledMessage = messageTemplate.output(modelMap);

            String templateFilePath = TEMPLATES_DIR + emailTemplate.getTemplateFile();
            JtwigTemplate template = new JtwigTemplate(new File(templateFilePath), jtwigConf);
            String content = template.output(
                    new JtwigModelMap()
                            .add("title", emailTemplate.getMessageTitle())
                            .add("body", compiledMessage)
            );
            messageBodyPart.setContent(content, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            for (EmailTemplateFiles templateFile : emailTemplate.getEmailTemplateFilesList()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(TEMPLATES_DIR + templateFile.getFilePath());
                attachPart.setFileName(templateFile.getFileName());
                if (templateFile.getContentId() != null) {
                    attachPart.setHeader("Content-ID", templateFile.getContentId());
                }
                multipart.addBodyPart(attachPart);
            }

            for (EmailTemplateFiles templateFile : emailTemplate.getAdditionalFiles()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(templateFile.getFilePath());
                attachPart.setFileName(templateFile.getFileName());
                if (templateFile.getContentId() != null) {
                    attachPart.setHeader("Content-ID", templateFile.getContentId());
                }
                multipart.addBodyPart(attachPart);
            }

            message.setContent(multipart);
            Logger.getLogger(NotificationsService.class.getName()).log(Level.INFO, "Sending email");
            Transport.send(message);
        } catch (Exception e) {
            Logger.getLogger(NotificationsService.class.getName()).log(Level.WARNING, "Unable to send email", e);
        }
    }

    @Override
    public void sendEmailwithAttachmentTruckDriver(final EmailTemplates emailTemplate, String filePath) {
        if (! ENABLE_EMAIL_DELIVERY) {
            return;
        }
        try {
            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", emailTemplate.getSmtpHost());
            props.put("mail.smtp.port", emailTemplate.getSmtpPort());
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailTemplate.getSmtpUser(), emailTemplate.getSmtpPassword());
                }
            });

            Message message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            message.setFrom(new InternetAddress(emailTemplate.getSenderAddress(), emailTemplate.getSenderName()));

            for (EmailRecipients recipient : emailTemplate.getEmailRecipientsList()) {
                message.addRecipient(
                        getRecipientType(recipient.getRecipientType()),
                        new InternetAddress(recipient.getEmailAddress(), recipient.getRecipientName())
                );
            }

            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("jolvera@bidg.mx", "Josue Olvera Rojas"));
            message.setSentDate(new Date());

            JtwigConfiguration jtwigConf = new JtwigConfiguration();
            JtwigModelMap modelMap = new JtwigModelMap().add(emailTemplate.getProperties());

            JtwigTemplate subjectTemplate = new JtwigTemplate(emailTemplate.getEmailSubject(), jtwigConf);
            String compiledSubject = subjectTemplate.output(modelMap);
            message.setSubject(compiledSubject);

            JtwigTemplate messageTemplate = new JtwigTemplate(emailTemplate.getMessage(), jtwigConf);
            String compiledMessage = messageTemplate.output(modelMap);

            String templateFilePath = TEMPLATES_DIR + emailTemplate.getTemplateFile();
            JtwigTemplate template = new JtwigTemplate(new File(templateFilePath), jtwigConf);
            String content = template.output(
                    new JtwigModelMap()
                            .add("title", emailTemplate.getMessageTitle())
                            .add("body", compiledMessage)
            );
            messageBodyPart.setContent(content, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            if (filePath.length() > 0) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(filePath);
                multipart.addBodyPart(attachPart);
            }

            message.setContent(multipart);
            Logger.getLogger(NotificationsService.class.getName()).log(Level.INFO, "Sending email");
            Transport.send(message);
        } catch (Exception e) {
            Logger.getLogger(NotificationsService.class.getName()).log(Level.WARNING, "Unable to send email", e);
        }
    }

    private Message.RecipientType getRecipientType(int idType) {
        switch (idType) {
            case 1: return Message.RecipientType.TO;
            case 2: return Message.RecipientType.CC;
            case 3: return Message.RecipientType.BCC;
        }
        return Message.RecipientType.TO;
    }
}

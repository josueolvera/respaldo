package mx.bidg.service.impl;

import mx.bidg.dao.NotificationsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.*;
import mx.bidg.service.EmailDeliveryService;
import mx.bidg.service.EmailTemplatesService;
import mx.bidg.service.NotificationsService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rafael Viveros
 * Created on 10/02/16.
 */
@Service
@Transactional
@PropertySource(value = {"classpath:application.properties"})
public class NotificationsServiceImpl implements NotificationsService {

    @Value("${notification.email_template_name}")
    private String EMAIL_TEMPLATE_NAME;

    @Autowired
    private NotificationsDao notificationsDao;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private RequestsDao requestsDao;

    @Autowired
    private HttpSession session;

    @Override
    public List<Notifications> createNotification(List<Users> users, Requests request) {
        request = requestsDao.findById(request.getIdRequest());
        EmailTemplates emailTemplate = emailTemplatesService.findByName(EMAIL_TEMPLATE_NAME);
        List<Notifications> notifications = new ArrayList<>();
        Set<String> userNames = new HashSet<>(users.size());

        for (Users user : users) {
            if (userNames.add(user.getUsername())) {
                notifications.add(buildAndSave(request, user));
                emailTemplate.addRecipient(new EmailRecipients(user.getMail(), user.getUsername(), EmailRecipients.TO));
            }
        }

        emailDeliveryService.deliverEmail(emailTemplate);
        return notifications;
    }

    @Override
    public List<Notifications> createNotification(List<Users> users, Stocks stock) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Notifications> createNotification(List<Users> users, AccountsPayable accountPayable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Notifications createNotification(Users user, Requests request) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName(EMAIL_TEMPLATE_NAME);
        request = requestsDao.findById(request.getIdRequest());
        emailTemplate.addRecipient(new EmailRecipients(user.getMail(), user.getUsername(), EmailRecipients.TO));
        emailTemplate.addProperty("user", user);
        emailDeliveryService.deliverEmail(emailTemplate);
        return buildAndSave(request, user);
    }

    @Override
    public Notifications createNotification(Users user, Stocks stock) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Notifications createNotification(Users user, AccountsPayable accountPayable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Long countNotificationsForUser(Users user) {
        return notificationsDao.countNotificationsForUser(user);
    }

    @Override
    public Notifications findById(Integer id) {
        return notificationsDao.findById(id);
    }

    @Override
    public List<Notifications> findAllForUser(Users user) {
        return notificationsDao.findAllForUser(user);
    }

    @Override
    public List<Notifications> findArchiveForUser(Users user) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Notifications archive(Integer idNotification) {
        Notifications notification = notificationsDao.findById(idNotification);
        Users user = (Users) session.getAttribute("user");
        if (user.getIdUser().equals(notification.getIdUser())) {
            notification.setNotificationsStatus(new CNotificationsStatus(CNotificationsStatus.ARCHIVADA));
            notificationsDao.update(notification);
        }
        return notification;
    }

    @Override
    public Notifications update(Notifications notification) {
        notificationsDao.update(notification);
        return notification;
    }

    private Notifications buildAndSave(Requests request, Users user) {
        Notifications notification = new Notifications();
        notification.setIdResource(request.getIdRequest());
        notification.setResourcesTasks(request.getRequestTypeProduct().getRequestCategory().getResourcesTasks());
        notification.setTitle("Solicitud: " + request.getRequestTypeProduct().getRequestType().getRequestType());
        notification.setSubtitle(request.getRequestTypeProduct().getProductType().getProductType());
        notification.setText(request.getDescription());
        notification.setUser(user);
        notification.setNotificationTypes(new CNotificationTypes(CNotificationTypes.S));
        notification.setNotificationsStatus(new CNotificationsStatus(CNotificationsStatus.PENDIENTE));
        notification.setCreationDate(LocalDateTime.now());
        notification.setDueDate(LocalDateTime.now());

        return notificationsDao.save(notification);
    }
}

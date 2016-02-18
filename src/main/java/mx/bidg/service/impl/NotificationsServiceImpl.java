package mx.bidg.service.impl;

import mx.bidg.dao.NotificationsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.*;
import mx.bidg.service.NotificationsService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 10/02/16.
 */
@Service
@Transactional
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationsDao notificationsDao;

    @Autowired
    private RequestsDao requestsDao;

    @Override
    public Notifications createNotification(Users user, Requests request) {
        request = requestsDao.findById(request.getIdRequest());

        Notifications notification = new Notifications();
        notification.setIdResource(request.getIdRequest());
        notification.setResourcesTasks(request.getRequestTypeProduct().getRequestCategory().getResourcesTasks());
        notification.setTitle(request.getRequestTypeProduct().getRequestType().getRequestType());
        notification.setSubtitle(request.getRequestTypeProduct().getProductType().getProductType());
        notification.setText(request.getDescription());
        notification.setUser(user);
        notification.setNotificationTypes(new CNotificationTypes(CNotificationTypes.S));
        notification.setNotificationsStatus(new CNotificationsStatus(CNotificationsStatus.PENDIENTE));
        notification.setCreationDate(LocalDateTime.now());
        notification.setDueDate(LocalDateTime.now());

        return notification;
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
    public List<Notifications> findAllForUser(Users user) {
        return notificationsDao.findAllForUser(user);
    }

    @Override
    public List<Notifications> findArchiveForUser(Users user) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Notifications archive(Notifications notification) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Notifications update(Notifications notification) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

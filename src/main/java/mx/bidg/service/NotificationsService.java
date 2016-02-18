package mx.bidg.service;

import mx.bidg.model.*;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 10/02/16.
 */
public interface NotificationsService {
    Notifications createNotification(Users user, Requests request);
    Notifications createNotification(Users user, Stocks stock);
    Notifications createNotification(Users user, AccountsPayable accountPayable);
    Long countNotificationsForUser(Users user);
    List<Notifications> findAllForUser(Users user);
    List<Notifications> findArchiveForUser(Users user);
    Notifications archive(Notifications notification);
    Notifications update(Notifications notification);
}

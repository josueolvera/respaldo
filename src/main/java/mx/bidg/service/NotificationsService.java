package mx.bidg.service;

import mx.bidg.model.*;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 10/02/16.
 */
public interface NotificationsService {
    List<Notifications> createNotification(List<Users> user, Requests request);
    List<Notifications> createNotification(List<Users> users, Stocks stock);
    List<Notifications> createNotification(List<Users> users, AccountsPayable accountPayable);

    Notifications createNotification(Users user, Requests request);
    Notifications createNotification(Users user, Stocks stock);
    Notifications createNotification(Users user, AccountsPayable accountPayable);
    Notifications createForEstimationCreation(Users user, Requests request);
    Notifications createForEstimationAuthorization(Users user, Requests request);
    Notifications createForRequestRejected(Requests request);

    Long countNotificationsForUser(Users user);
    Notifications findById(Integer id);
    List<Notifications> findAllForUser(Users user);
    List<Notifications> findArchiveForUser(Users user);
    Notifications archive(Integer idNotification);
    Notifications update(Notifications notification);
    Boolean delete(Notifications notifications);
}

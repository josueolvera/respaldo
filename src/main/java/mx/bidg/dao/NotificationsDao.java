package mx.bidg.dao;

import mx.bidg.model.Notifications;
import mx.bidg.model.ResourcesTasks;
import mx.bidg.model.Users;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 10/02/16.
 */
public interface NotificationsDao extends InterfaceDao<Notifications> {
    List<Notifications> findAllForUser(Users user);
    Long countNotificationsForUser(Users user);
    Long countForUserResource(Users user, Integer idResource);
}

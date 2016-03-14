package mx.bidg.listeners;

import mx.bidg.events.CreationEvent;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
@Component
public class RequestEventsListener {

    @Autowired
    private NotificationsService notificationsService;

    @EventListener
    public void createNotificationsOnRequestCreatedEvent(CreationEvent<Requests> event) {
        List<Users> users = new ArrayList<>();
        Requests request = event.getResource();
        users.add(request.getUserRequest());
        users.add(request.getUserResponsible());
        notificationsService.createNotification(users, request);
    }

    @EventListener
    public void buildAuthorizationsTreeOnRequestCreatedEvent(CreationEvent<Requests> event) {
    }
}

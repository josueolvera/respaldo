package mx.bidg.listeners;

import mx.bidg.events.authorizations.AuthorizedEvent;
import mx.bidg.events.authorizations.RejectedEvent;
import mx.bidg.events.requests.RequestAuthorizedEvent;
import mx.bidg.model.Authorizations;
import mx.bidg.model.CAuthorizationStatus;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.AuthorizationsService;
import mx.bidg.service.NotificationsService;
import mx.bidg.service.RequestsService;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Rafael Viveros
 * Created on 30/03/16.
 */
@Component
public class AuthorizationsEventsListener {

    @Autowired
    private AuthorizationsService authorizationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    public void notifyNextInAuthorizationsTree(AuthorizedEvent event) {
        Authorizations auth = event.getResource();
        List<Authorizations> authTree = authorizationsService.findByFolioAndStatus(
                auth.getFolio(), CAuthorizationStatus.PENDIENTE
        );
        TreeMap<Integer, Integer> users = new TreeMap<>();
        for (Authorizations nextAuth : authTree) {
            users.put(nextAuth.getAuthorizationOrder(), nextAuth.getIdUser());
        }
        if (users.size() > 0) {
            Map.Entry<Integer, Integer> firstEntry = users.firstEntry();
            Users user = usersService.findById(firstEntry.getValue());
            Requests request = requestsService.findByFolio(auth.getFolio());
            notificationsService.createNotification(user, request);
        } else if (users.size() == 0) {
            eventPublisher.publishEvent(new RequestAuthorizedEvent(requestsService.findByFolio(auth.getFolio())));
        }
    }

    @EventListener
    public void notifyRequestRejected(RejectedEvent event) {
        // TODO: Notificar a usuarios involucrados que la solicitud fue rechazada
    }
}

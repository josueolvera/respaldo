package mx.bidg.listeners;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import mx.bidg.events.CreationEvent;
import mx.bidg.model.*;
import mx.bidg.service.AuthorizationTreeRulesService;
import mx.bidg.service.AuthorizationsService;
import mx.bidg.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RequestEventsListener {

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private AuthorizationTreeRulesService authorizationTreeRulesService;

    @Autowired
    private AuthorizationsService authorizationsService;

    @EventListener
    public void createNotificationsOnRequestCreatedEvent(CreationEvent<Requests> event) {
        List<Users> users = new ArrayList<>();
        Requests request = event.getResource();
        users.add(request.getUserRequest());
        users.add(request.getUserResponsible());
        notificationsService.createNotification(users, request);
    }

    @EventListener
    @SuppressWarnings("unchecked")
    public void buildAuthorizationsTreeOnRequestCreatedEvent(CreationEvent<Requests> event) {
        Requests requests = event.getResource();
        AuthorizationTreeRules rule = authorizationTreeRulesService.findByRuleName("authorizations");
        Binding binding = new Binding();
        binding.setProperty("request", requests);
        GroovyShell shell = new GroovyShell(binding);
        Map<Integer, Integer> users = new HashMap<>(1);
        try {
            users = (Map<Integer, Integer>) shell.evaluate(rule.getRuleCode());
        } catch (RuntimeException e) {
            Logger.getLogger(RequestEventsListener.class.getName())
                    .log(Level.SEVERE, "Error al evaluar la regla del arbol de autorizaciones", e);
            users.put(1, 1);
        } finally {
            for (Map.Entry<Integer, Integer> entry : users.entrySet()) {
                Authorizations auth = new Authorizations();
                auth.setFolio(requests.getFolio());
                auth.setUsers(new Users(entry.getValue()));
                auth.setAuthorizationOrder(entry.getKey());
                auth.setCAuthorizationStatus(new CAuthorizationStatus(CAuthorizationStatus.PENDIENTE));
                auth.setIdAccessLevel(1);
                authorizationsService.save(auth);
            }
        }
    }
}


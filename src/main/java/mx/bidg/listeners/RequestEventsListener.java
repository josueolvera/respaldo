package mx.bidg.listeners;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import mx.bidg.events.CreationEvent;
import mx.bidg.events.requests.PriceEstimationAuthorizedEvent;
import mx.bidg.events.requests.PriceEstimationCreatedEvent;
import mx.bidg.events.requests.RequestCompletedEvent;
import mx.bidg.model.*;
import mx.bidg.service.AuthorizationTreeRulesService;
import mx.bidg.service.AuthorizationsService;
import mx.bidg.service.NotificationsService;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class RequestEventsListener {

    @Value("${request.authorizations.rule_name}")
    private String AUTHORIZATIONS_RULE_NAME;

    @Value("${authorizations.default.user_id}")
    private Integer DEFAULT_USER_ID;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private AuthorizationTreeRulesService authorizationTreeRulesService;

    @Autowired
    private AuthorizationsService authorizationsService;

    @Autowired
    private UsersService usersService;

    @EventListener
    public void createBaseNotifications(CreationEvent<Requests> event) {
        List<Users> users = new ArrayList<>();
        Requests request = event.getResource();
        users.add(request.getUserRequest());
        users.add(request.getUserResponsible());
        notificationsService.createNotification(users, request);
        // TODO: Evaluar regla price_estimations.creation.rule_name
        // TODO: Notificar a responsable de cotizar
        notificationsService.createForEstimationCreation(new Users(188), request);
    }

    @EventListener
    public void buildPriceEstimationsAuthorizationsTree(PriceEstimationCreatedEvent event) {
        Requests request = event.getResource();
        // TODO: Notificar a responsabel de autorizar cotizacion
        notificationsService.createForEstimationAuthorization(new Users(174), request);
    }

    @EventListener
    public void buildPaymentsTree(PriceEstimationAuthorizedEvent event) {
        Requests request = event.getResource();
        // TODO: Notificar a responsabel de adjuntar plan de pago
        notificationsService.createForEstimationAuthorization(new Users(188), request);
    }

    @EventListener
    @SuppressWarnings("unchecked")
    public void buildRequestAuthorizationsTree(RequestCompletedEvent event) {
        Requests requests = event.getResource();
        if (authorizationsService.countByFolio(requests.getFolio()) > 0) {
            return;
        }
        AuthorizationTreeRules rule = authorizationTreeRulesService.findByRuleName(AUTHORIZATIONS_RULE_NAME);
        Binding binding = new Binding();
        binding.setProperty("request", requests);
        GroovyShell shell = new GroovyShell(binding);
        TreeMap<Integer, Integer> users = new TreeMap<>();
        try {
            users = new TreeMap<>((Map<Integer, Integer>) shell.evaluate(rule.getRuleCode()));
        } catch (RuntimeException e) {
            Logger.getLogger(RequestEventsListener.class.getName())
                    .log(Level.SEVERE, "Error al evaluar la regla del arbol de autorizaciones", e);
            users.put(1, DEFAULT_USER_ID);
        } finally {
            Map.Entry<Integer, Integer> firstEntry = users.firstEntry();
            Users user = usersService.findById(firstEntry.getValue());
            notificationsService.createNotification(user, requests);

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


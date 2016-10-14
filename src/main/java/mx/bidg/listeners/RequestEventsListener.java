package mx.bidg.listeners;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import mx.bidg.events.CreationEvent;
import mx.bidg.events.requests.PriceEstimationAuthorizedEvent;
import mx.bidg.events.requests.PriceEstimationCreatedEvent;
import mx.bidg.events.requests.RequestAuthorizedEvent;
import mx.bidg.events.requests.RequestCompletedEvent;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class RequestEventsListener {

    @Value("${request.authorizations.rule_name}")
    private String AUTHORIZATIONS_RULE_NAME;

    @Value("${price_estimations.creation.rule_name}")
    private String ESTIMATION_CREATION_RULE_NAME;

    @Value("${price_estimations.authorization.rule_name}")
    private String ESTIMATION_AUTHORIZATION_RULE_NAME;

    @Value("${payment_plan.creation.rule_name}")
    private String PAYMENT_PLAN_CREATION_RULE_NAME;

    @Value("${authorizations.default.user_id}")
    private Integer DEFAULT_USER_ID;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private AuthorizationTreeRulesService authorizationTreeRulesService;

    @Autowired
    private PriceEstimationsService priceEstimationsService;

    @Autowired
    private AuthorizationsService authorizationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RequestsService requestsService;

    @EventListener
    public void createBaseNotifications(CreationEvent<Requests> event) {
        Requests request = event.getResource();
        notificationsService.createNotification(request.getUserRequest(), request);
        AuthorizationTreeRules rule = authorizationTreeRulesService.findByRuleName(ESTIMATION_CREATION_RULE_NAME);
        Integer idUser = evalRule(rule, request);
        notificationsService.createForEstimationCreation(new Users(idUser), request);
    }

    @EventListener
    public void buildPriceEstimationsAuthorizationsTree(PriceEstimationCreatedEvent event) {
        Requests request = event.getResource();
        AuthorizationTreeRules rule = authorizationTreeRulesService.findByRuleName(ESTIMATION_AUTHORIZATION_RULE_NAME);
        Integer idUser = evalRule(rule, request);
        notificationsService.createForEstimationAuthorization(new Users(idUser), request);
    }

    @EventListener
    public void buildPaymentsTree(PriceEstimationAuthorizedEvent event) {
        Requests request = event.getResource();
        AuthorizationTreeRules rule = authorizationTreeRulesService.findByRuleName(PAYMENT_PLAN_CREATION_RULE_NAME);
        Integer idUser = evalRule(rule, request);
        notificationsService.createForEstimationAuthorization(new Users(idUser), request);
    }

    @EventListener
    @SuppressWarnings("unchecked")
    public void buildRequestAuthorizationsTree(RequestCompletedEvent event) {
        Requests request = event.getResource();
        requestsService.sendEmailForNewRequest(request);
        request.setPriceEstimationsList(priceEstimationsService.findByIdRequest(request.getIdRequest()));
        AuthorizationTreeRules rule = authorizationTreeRulesService.findByRuleName(AUTHORIZATIONS_RULE_NAME);
        Binding binding = new Binding();
        binding.setProperty("request", request);
        GroovyShell shell = new GroovyShell(binding);
        TreeMap<Integer, Integer> users = new TreeMap<>();
        try {
            users = new TreeMap<>((Map<Integer, Integer>) shell.evaluate(rule.getRuleCode()));
        } catch (RuntimeException e) {
            Logger.getLogger(RequestEventsListener.class.getName())
                    .log(Level.SEVERE, "Error al evaluar la regla del arbol de autorizaciones", e);
            users.put(1, DEFAULT_USER_ID);
        } finally {
            authorizationsService.deleteByFolio(request.getFolio());
            Map.Entry<Integer, Integer> firstEntry = users.firstEntry();
            Users user = usersService.findById(firstEntry.getValue());
            requestsService.sendEmailForNewRequestAuthorization(request, user);
            notificationsService.createNotification(user, request);
            for (Map.Entry<Integer, Integer> entry : users.entrySet()) {
                Authorizations auth = new Authorizations();
                auth.setFolio(request.getFolio());
                auth.setUsers(new Users(entry.getValue()));
                auth.setAuthorizationType(CAuthorizationTypes.SOLICITUD);
                auth.setAuthorizationOrder(entry.getKey());
                auth.setCAuthorizationStatus(CAuthorizationStatus.PENDIENTE);
                auth.setIdAccessLevel(1);
                authorizationsService.save(auth);
            }
        }
    }

    @EventListener
    public void finalRequestAuthorization(RequestAuthorizedEvent event) {
        // TODO: Marcar solicitud como aprobada
        Requests request = event.getResource();
        requestsService.authorization(request.getIdRequest());
        // TODO: Notificar a usuario involucrados
        List<Users> users = new ArrayList<>();
        users.add(request.getUserRequest());
        users.add(request.getUserResponsible());
    }

    private Integer evalRule(AuthorizationTreeRules rule, Requests request) {
        Binding binding = new Binding();
        binding.setProperty("request", request);
        GroovyShell shell = new GroovyShell(binding);
        Integer idUser = DEFAULT_USER_ID;
        try {
            idUser = (Integer) shell.evaluate(rule.getRuleCode());
        } catch (RuntimeException e) {
            Logger.getLogger(RequestEventsListener.class.getName())
                    .log(Level.SEVERE, "Error al evaluar la regla " + PAYMENT_PLAN_CREATION_RULE_NAME, e);
        }
        return idUser;
    }
}


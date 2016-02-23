package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import javax.servlet.http.HttpSession;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.AccountsPayableService;
import mx.bidg.service.NotificationsService;
import mx.bidg.service.PeriodicPaymentsService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * created on 18/11/15.
 */
@Controller
@RequestMapping("/requests")
public class RequestsController {
    
    @Autowired
    RequestsService requestsService;

    @Autowired
    AccountsPayableService accountsPayableService;

    @Autowired
    PeriodicPaymentsService periodicPaymentsService;

    @Autowired
    NotificationsService notificationsService;
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveRequest(@RequestBody String data, HttpSession session) 
        throws Exception{
        
        Users user = (Users) session.getAttribute("user");
        Requests request = requestsService.saveData(data, user);
        String response;
        
        if(request != null) {
            List<Users> users = new ArrayList<>();
            users.add(user);
            users.add(request.getUserResponsible());
            notificationsService.createNotification(users, request);
            response = mapper.writeValueAsString(request);
        } else {
            response = "Error al guardar la Solicitud";
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }
    
    
    @RequestMapping(value = "/authorization/{idRequest}", method = RequestMethod.POST, 
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String authorization(@PathVariable Integer idRequest) throws Exception{
        
        Requests request = requestsService.authorization(idRequest);
        return null;
        
    }
    
    
    @RequestMapping(value = "/month-branch-product-type", method = RequestMethod.POST, 
            headers = {"Accept=application/json;charset=UTF-8"}, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetMonthProductType(@RequestBody String data) throws Exception {
        return mapper.writeValueAsString(requestsService.getBudgetMonthProductType(data));
    }


    @RequestMapping(value = "/accounts-payable", method = RequestMethod.POST, headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String saveAccountPayable(@RequestBody String data) throws Exception {
        List<AccountsPayable> accountsPayables = accountsPayableService.saveData(data);
        return mapper.writeValueAsString(accountsPayables);
//        return (accountsPayables.isEmpty())? "Error al guardar el Plan de pago" : "Plan de Pago guardado";
    }


    @RequestMapping(value = "/period-payment", method = RequestMethod.POST, headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String savePeriodicPayment(@RequestBody String data) throws Exception {
        PeriodicsPayments periodicsPayment = periodicPaymentsService.saveData(data);
        return mapper.writeValueAsString(periodicsPayment);
    }
    
}
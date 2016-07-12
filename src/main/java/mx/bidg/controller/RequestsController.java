package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.events.requests.RequestCompletedEvent;
import mx.bidg.events.requests.RequestCreatedEvent;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.AccountsPayableService;
import mx.bidg.service.PeriodicPaymentsService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import mx.bidg.model.EmailTemplates;
import mx.bidg.service.EmailTemplatesService;

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
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private EmailTemplatesService emailTemplatesService; 
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveRequest(@RequestBody String data, HttpSession session) 
        throws Exception{
        
        Users user = (Users) session.getAttribute("user");
        Requests request = requestsService.saveData(data, user);
        String response;
        
        if(request != null) {
            eventPublisher.publishEvent(new RequestCreatedEvent(request));
            response = mapper.writeValueAsString(request);
            
        } else {
            response = "Error al guardar la Solicitud";
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }


    @RequestMapping(value = "/month-branch-product-type", method = RequestMethod.POST, 
            headers = {"Accept=application/json;charset=UTF-8"}, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetMonthProductType(@RequestBody String data) throws Exception {
        return mapper.writeValueAsString(requestsService.getBudgetMonthProductType(data));
    }


    @RequestMapping(value = "/period-payment", method = RequestMethod.POST, headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String savePeriodicPayment(@RequestBody String data) throws Exception {
        PeriodicsPayments periodicsPayment = periodicPaymentsService.saveData(data);
        eventPublisher.publishEvent(new RequestCompletedEvent(requestsService.findByFolio(periodicsPayment.getFolio())));
        return mapper.writeValueAsString(periodicsPayment);
    }
    
    @RequestMapping(value="/{idRequest}", method = RequestMethod.GET, headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String findRequestByID(@PathVariable int idRequest) throws Exception {
        Requests request = requestsService.findById(idRequest);
        return mapper.writeValueAsString(request);
    }

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByFolio(@RequestParam(name = "folio", required = true) String folio) throws IOException{
        Requests requests = requestsService.findByFolio(folio);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class)
                        .writeValueAsString(requests),
                HttpStatus.OK
        );
    }
}

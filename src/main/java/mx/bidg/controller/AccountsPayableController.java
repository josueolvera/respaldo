/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.events.requests.RequestCompletedEvent;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.Requests;
import mx.bidg.model.Transactions;
import mx.bidg.model.Users;
import mx.bidg.service.AccountsPayableService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/accounts-payable")
public class AccountsPayableController {

    @Autowired
    private AccountsPayableService accountsPayableService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(value = "/{idAccountPayable}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findById(@PathVariable int idAccountPayable) throws Exception {
        return mapper.writeValueAsString(accountsPayableService.findById(idAccountPayable));
    }

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findByFolio(@RequestParam(name = "folio", required = true) String folio) throws Exception {
        return mapper.writeValueAsString(accountsPayableService.findByFolio(folio));
    }

    @RequestMapping(value = "/folio", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String update(@RequestParam(name = "folio", required = true) String folio, @RequestBody String data) throws Exception {
        List<AccountsPayable> accountsPayables = accountsPayableService.update(folio, data);
        eventPublisher.publishEvent(new RequestCompletedEvent(requestsService.findByFolio(folio)));
        return mapper.writeValueAsString(accountsPayables);
    }

    @RequestMapping(value = "/periodic", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updatePeriodic(@RequestParam(name = "folio", required = true) String folio,
                                                 @RequestBody String data) throws Exception {
        List<AccountsPayable> accountsPayables = accountsPayableService.updatePeriodic(folio, data);
        eventPublisher.publishEvent(new RequestCompletedEvent(requestsService.findByFolio(folio)));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountsPayables),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<AccountsPayable> accountsPayables = accountsPayableService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountsPayables),
                HttpStatus.OK
        );
    }

    @RequestMapping(value="/now", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllNow() throws IOException{
        List<AccountsPayable> accountsPayables = accountsPayableService.findAccountsNow();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(accountsPayables),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/reschedule", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByReschedule() throws  IOException{
        List <AccountsPayable> accountsPayables = accountsPayableService.findByReschedule();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class)
                        .writeValueAsString(accountsPayables),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/pay-account/{idAccountPayable}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> payAccount(@PathVariable Integer idAccountPayable, @RequestBody String data,HttpSession session) throws IOException{
        Users user = (Users) session.getAttribute("user");
        Transactions transaction = accountsPayableService.payAccount(idAccountPayable, data, user);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(transaction), HttpStatus.OK);
    }

    @RequestMapping(value = "/reschedule/{idAccountPayable}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeDate(@PathVariable Integer idAccountPayable, @RequestBody String data) throws IOException{
        accountsPayableService.changeDate(idAccountPayable,data);
        return new ResponseEntity<>("Cuenta reprogramada", HttpStatus.OK);
    }

    @RequestMapping(value = "/report/accounts-liquidated", method = RequestMethod.GET)
    public ResponseEntity<String> reportPayAccountByDueDate(@RequestParam(name= "fromDate", required=true) String fromDate, @RequestParam(name="toDate", required=true) String toDate , HttpServletResponse response) throws IOException {

        LocalDateTime ofDate = (fromDate == null || fromDate.equals("")) ? null :
                LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (toDate == null || toDate.equals("")) ? null :
                LocalDateTime.parse(toDate, DateTimeFormatter.ISO_DATE_TIME);

        String initialDate = ofDate.toLocalDate().toString();
        String finalDate = untilDate.toLocalDate().toString();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +"Reporte cuentas por pagar de "+initialDate+" a "+finalDate+".xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        accountsPayableService.accountsPayableReport(ofDate, untilDate, outputStream);
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<>("Reporte", HttpStatus.OK);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<String> emailDelivery () throws IOException{
         accountsPayableService.sendEmail();
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }



}

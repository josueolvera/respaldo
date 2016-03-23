/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.Requests;
import mx.bidg.service.AccountsPayableService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    
    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findByFolio(@RequestParam(name = "folio", required = true) String folio) throws Exception {
        return mapper.writeValueAsString(accountsPayableService.findByFolio(folio));
    }

    @RequestMapping(value = "/folio", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String update(@RequestParam(name = "folio", required = true) String folio, @RequestBody String data) throws Exception {
        List<AccountsPayable> accountsPayables = accountsPayableService.update(folio, data);
        return mapper.writeValueAsString(accountsPayables);
    }
}

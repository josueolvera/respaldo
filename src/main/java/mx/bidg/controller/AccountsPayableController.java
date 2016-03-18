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
import mx.bidg.service.AccountsPayableService;
import org.springframework.beans.factory.annotation.Autowired;
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
    AccountsPayableService accountsPayableService;
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String findByFolio(@RequestParam(name = "folio", required = true) String folio) throws Exception {
        return mapper.writeValueAsString(accountsPayableService.findByFolio(folio));
    }

}
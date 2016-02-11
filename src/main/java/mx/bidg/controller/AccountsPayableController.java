/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.model.AccountsPayable;
import mx.bidg.service.AccountsPayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/accounts-payable")
public class AccountsPayableController {
    
    @Autowired
    AccountsPayableService accountsPayableService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.POST, 
            headers = "Accept=application/json; charset=UTF-8")
    public @ResponseBody String saveFromRequest(@PathVariable int idRequest, @RequestBody String data) throws Exception {
        List<AccountsPayable> accountsPayables = accountsPayableService.saveData(data, idRequest);
        return (accountsPayables.isEmpty())? "Error al guardar el Plan de pago" : "Plan de Pago guardado";
    }

}

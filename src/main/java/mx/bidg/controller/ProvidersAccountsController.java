/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import java.util.List;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.service.ProvidersAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author kenneth
 */
@Controller
@RequestMapping("/providers-accounts")
public class ProvidersAccountsController {
    
    @Autowired
    ProvidersAccountsService providersAccountsService;
    
    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET, produces = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<List<ProvidersAccounts>> getByProvider(@PathVariable int idProvider) {
        List<ProvidersAccounts> list = providersAccountsService.findByProvider(new Providers(idProvider));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
}

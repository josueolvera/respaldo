/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.service.ProvidersAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"}, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> saveProviderAccount(@RequestBody String data) throws Exception {
        ProvidersAccounts providersAccount = providersAccountsService.save(data);
        return new ResponseEntity<>(mapper.writeValueAsString(providersAccount), HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByProvider(@PathVariable int idProvider) throws Exception {
        List<ProvidersAccounts> list = providersAccountsService.findByProvider(new Providers(idProvider));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.EmbeddedAccounts.class)
                .writeValueAsString(list), HttpStatus.OK);
    }
    
}

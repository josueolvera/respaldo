/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.EmployeesAccounts;
import mx.bidg.service.EmployeesAccountsService;
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
 * @author sistemask
 */
@Controller
@RequestMapping("/employees-accounts")
public class EmployeesAccountsController {
    
    @Autowired
    EmployeesAccountsService employeesAccountsService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/user/{idUser}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByIdUser(@PathVariable int idUser) throws Exception {
        List<EmployeesAccounts> list = employeesAccountsService.findByIdUser(idUser);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.EmbeddedAccounts.class)
                .writeValueAsString(list), HttpStatus.OK);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Employees;
import mx.bidg.model.EmployeesAccounts;
import mx.bidg.service.EmployeesAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(value = "/user/{idUser}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByIdUser(@PathVariable int idUser) throws Exception {
        List<EmployeesAccounts> list = employeesAccountsService.findByIdUser(idUser);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.EmbeddedAccounts.class)
                .writeValueAsString(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{idEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAccountsEmployee (@PathVariable Integer idEmployee) throws IOException {
        List<EmployeesAccounts> employeesAccount = employeesAccountsService.findByEmployee(new Employees(idEmployee));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesAccount), HttpStatus.OK);
    }

    @RequestMapping(value = "/actives/{idEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getEmployeeAccountsActive (@PathVariable Integer idEmployee) throws IOException {
        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(idEmployee);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employeesAccounts), HttpStatus.OK);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.service.PeriodicPaymentsService;
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
@RequestMapping("/periodic-payment")
public class PeriodicPaymentsController {
    
    @Autowired
    PeriodicPaymentsService paymentsService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.POST, 
            headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody String save(@PathVariable int idRequest, @RequestBody String data) throws Exception {
        PeriodicsPayments payment = paymentsService.saveData(data, idRequest);
        return (payment == null)? "Error al guardar el Pago Periodico" : "Pago Periodico guardado";
    }
    
}

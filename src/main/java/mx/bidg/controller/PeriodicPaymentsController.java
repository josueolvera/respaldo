/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.service.PeriodicPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/periodic-payment")
public class PeriodicPaymentsController {

    @Autowired
    private PeriodicPaymentsService paymentsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String findByFolio(@RequestParam(name = "folio", required = true) String folio) throws Exception {
        return mapper.writeValueAsString(paymentsService.findByFolio(folio));
    }

    @RequestMapping(value = "/{idPayment}", method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody String update(@PathVariable int idPayment, @RequestBody String data) throws Exception {
        return mapper.writeValueAsString(paymentsService.update(idPayment, data));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import mx.bidg.model.PriceEstimations;
import mx.bidg.service.PriceEstimationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/estimations")
public class PriceEstimationsController {
    
    @Autowired
    PriceEstimationsService estimationsService;
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=aplication/json; charset=UTF-8", 
            produces = "aplication/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> save(@RequestBody String data) throws Exception {
        
        PriceEstimations estimation = estimationsService.saveData(data, "file");
        return null;
        
    }
    
}

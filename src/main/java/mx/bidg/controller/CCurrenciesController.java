/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.bidg.config.JsonViews;
import mx.bidg.model.CCurrencies;
import mx.bidg.service.CCurrenciesService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/currencies")
public class CCurrenciesController {
    
    @Autowired
    CCurrenciesService currenciesService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getCurrencies() throws Exception {
        List<CCurrencies> list = currenciesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/current-rates", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> setCurrentRate() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://api.fixer.io/latest?base=MXN");
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                long len = entity.getContentLength();
                if(len != -1) {
                    JsonNode json = mapper.readTree(entity.getContent());
                    List<CCurrencies> currencies = currenciesService.findAll();
                    for(CCurrencies currency : currencies) {
                        String acronym = currency.getAcronym();
                        if(json.get("rates").get(acronym) != null) {
                            BigDecimal rate = json.get("rates").get(acronym).decimalValue();
                            currency.setRate(rate);
                            currency = currenciesService.update(currency);
                        }
                    }
                }
            }
            response.close();
        } catch (Exception e) {
            Logger.getLogger("CCurrenciesController").log(Level.WARNING, e.getMessage(), e);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    
}

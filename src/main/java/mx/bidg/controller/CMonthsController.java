/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CMonths;
import mx.bidg.service.CMonthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/months")
public class CMonthsController {
    
    @Autowired
    CMonthsService cMonthsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getCMonths() throws Exception {
        List<CMonths> list = cMonthsService.findAll();
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list));
    }
    
}

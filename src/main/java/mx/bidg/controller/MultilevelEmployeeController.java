/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javafx.scene.media.Media;
import javax.xml.ws.Response;
import mx.bidg.config.JsonViews;
import mx.bidg.model.MultilevelEmployee;
import mx.bidg.service.MultilevelEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Cristhian de la cruz
 */
@Controller
@RequestMapping("multilevel-employee")
public class MultilevelEmployeeController {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    MultilevelEmployeeService multilevelEmployeeService;
    
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
    
        List<MultilevelEmployee> multilevelEmployees = multilevelEmployeeService.findAll();
        
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(multilevelEmployees), HttpStatus.OK);
    }
    
    
    
}

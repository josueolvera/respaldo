package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CalculationRoles;
import mx.bidg.service.CalculationRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 7/09/16.
 */
@Controller
@RequestMapping("calculation-roles")
public class CalculationRolesController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CalculationRolesService calculationRolesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CalculationRoles> calculationRolesList = calculationRolesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(calculationRolesList), HttpStatus.OK);
    }
}

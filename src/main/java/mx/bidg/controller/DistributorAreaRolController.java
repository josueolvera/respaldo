package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DistributorAreaRol;
import mx.bidg.service.DistributorAreaRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 15/08/16.
 */
@Controller
@RequestMapping("/distributor-area-rol")
public class DistributorAreaRolController {

    @Autowired
    private DistributorAreaRolService distributorAreaRolService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/{idDistributor}/{idArea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getRolByDistributorArea(@PathVariable Integer idDistributor, @PathVariable Integer idArea) throws IOException {
        List<DistributorAreaRol> distributorAreaRoles = distributorAreaRolService.findRolByDistributorArea(idDistributor, idArea);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorAreaRoles), HttpStatus.OK);
    }
}

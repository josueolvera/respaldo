package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CPlaneSeatsTypes;
import mx.bidg.service.CPlaneSeatsTypesService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/plane-seats-types")
public class CPlaneSeatsTypesController {

    @Autowired
    private CPlaneSeatsTypesService cPlaneSeatsTypesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception {
        List<CPlaneSeatsTypes> planeSeatsTypes = cPlaneSeatsTypesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(planeSeatsTypes), HttpStatus.OK);
    }

}

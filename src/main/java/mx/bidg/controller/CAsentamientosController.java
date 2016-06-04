package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAsentamientos;
import mx.bidg.service.CAsentamientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Controller
@RequestMapping("settlements")
public class CAsentamientosController {

    @Autowired
    private CAsentamientosService service;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CAsentamientos> asentamientos = service.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(asentamientos), HttpStatus.OK);
    }

    @RequestMapping(value="/post-code",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByPostCode(@RequestParam(name = "cp", required = true) String codigoPostal) throws IOException{
        List<CAsentamientos> asentamientos = service.findByPostCode(codigoPostal);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(asentamientos),HttpStatus.OK);
    }
}

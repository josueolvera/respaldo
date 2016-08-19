package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CIncidence;
import mx.bidg.model.CTicketsCategories;
import mx.bidg.service.CIncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@RestController
@RequestMapping("incidence")
public class CIncidenceController {

    @Autowired
    private CIncidenceService cIncidenceService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/category/{idTicketCategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(@PathVariable int idTicketCategory) throws IOException {
        List<CIncidence> incidences = cIncidenceService.findAll(new CTicketsCategories(idTicketCategory));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(incidences), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idIncidence}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable int idIncidence) throws IOException {
        CIncidence cIncidence = cIncidenceService.findById(idIncidence);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cIncidence), HttpStatus.OK);
    }
}

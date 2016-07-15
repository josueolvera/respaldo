package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CPlaneTicketsTypes;
import mx.bidg.service.CPlaneSeatsTypesService;
import mx.bidg.service.CPlaneTicketsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/plane-tickets-types")
public class CPlaneTicketsTypesController {

    @Autowired
    private CPlaneTicketsTypesService cPlaneTicketsTypesService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception {
        List<CPlaneTicketsTypes> planeTicketsTypes = cPlaneTicketsTypesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(planeTicketsTypes), HttpStatus.OK);
    }

}

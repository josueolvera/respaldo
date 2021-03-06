package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CPlaneTicketsTypes;
import mx.bidg.model.Flights;
import mx.bidg.model.PlaneTickets;
import mx.bidg.model.Users;
import mx.bidg.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(
            @RequestBody String data,
            @RequestParam(name = "idPlaneTicket") Integer idPlaneTicket
            ) throws Exception {

        Flights flight = flightsService.save(data, idPlaneTicket);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(flight),
                HttpStatus.OK
        );
    }
}

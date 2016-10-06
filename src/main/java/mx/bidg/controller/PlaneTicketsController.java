package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CPlaneTicketsTypes;
import mx.bidg.model.PlaneTickets;
import mx.bidg.model.Users;
import mx.bidg.service.PlaneTicketsService;
import mx.bidg.service.RequestPlaneTicketEmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/plane-tickets")
public class PlaneTicketsController {

    @Autowired
    private PlaneTicketsService planeTicketsService;
    
    @Autowired
    private RequestPlaneTicketEmailNotificationService requestPlaneTicketEmailNotificationService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession httpSession) throws Exception {

        Users user = (Users) httpSession.getAttribute("user");

        PlaneTickets planeTicket = planeTicketsService.save(data, user);
        requestPlaneTicketEmailNotificationService.sendEmailToUser(planeTicket);
        requestPlaneTicketEmailNotificationService.sendEmailToAdminAux(planeTicket);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(planeTicket.getIdPlaneTicket()),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getPlaneTickets(@RequestParam(name = "user", required = false) Integer idUser) throws Exception {
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(planeTicketsService.getPlaneTickets(idUser)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idPlaneTicket}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getTravelExpenseById(@PathVariable Integer idPlaneTicket) throws Exception {
        PlaneTickets planeTicket = planeTicketsService.findById(idPlaneTicket);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(planeTicket), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeStatus(
            @RequestParam(name = "travel_expense") Integer idPlaneTicket,
            @RequestBody String data
    ) throws Exception {

        PlaneTickets planeTicket = planeTicketsService.changeRequestStatus(idPlaneTicket, data);
//        requestTravelExpenseEmailNotificationService.sendEmailToUserStatus(travelExpense);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(planeTicket), HttpStatus.OK);
    }

}

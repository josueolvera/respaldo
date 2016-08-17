package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.PassengerDocuments;
import mx.bidg.service.PassengerDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/passenger-documents")
public class PassengerDocumentsController {

    @Autowired
    private PassengerDocumentsService passengerDocumentsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(
            @RequestBody String data,
            @RequestParam(name = "idPassenger") Integer idPassenger
    ) throws Exception {

        PassengerDocuments passengerDocument = passengerDocumentsService.save(data, idPassenger);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(passengerDocument),
                HttpStatus.OK
        );
    }
}

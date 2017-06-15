package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;

import mx.bidg.model.RequestsDates;
import mx.bidg.service.RequestsDatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by jcesar on 12/06/2017.
 */
@Controller
@RequestMapping("/accounts-payables-dates")
public class RequestsDatesController {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    RequestsDatesService requestsDatesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<RequestsDates> requestsDates = requestsDatesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsDates),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findByIds(@PathVariable int id) throws IOException {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestsDatesService.findById(id));
    }

}

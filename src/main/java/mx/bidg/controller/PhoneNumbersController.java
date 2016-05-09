package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Providers;
import mx.bidg.service.PhoneNumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jolvera on 9/05/16.
 */
@Controller
@RequestMapping("phone-numbers")
public class PhoneNumbersController {

    @Autowired
    PhoneNumbersService phoneNumbersService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody public ResponseEntity<String> phonesByProvider(@PathVariable int idProvider)throws Exception{
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(phoneNumbersService
                .findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

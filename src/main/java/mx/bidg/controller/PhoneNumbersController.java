package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.PhoneNumbers;
import mx.bidg.model.Providers;
import mx.bidg.service.PhoneNumbersService;
import mx.bidg.service.ProvidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by jolvera on 9/05/16.
 */
@Controller
@RequestMapping("phone-numbers")
public class PhoneNumbersController {

    @Autowired
    PhoneNumbersService phoneNumbersService;

    @Autowired
    ProvidersService providersService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody public ResponseEntity<String> phonesByProvider(@PathVariable int idProvider)throws Exception{
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(phoneNumbersService
                .findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{idPhoneNumber}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteAccount(@PathVariable int idPhoneNumber) throws IOException {
        PhoneNumbers  phone= phoneNumbersService.findById(idPhoneNumber);
        phoneNumbersService.delete(phone);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> addProviderPhone(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        PhoneNumbers phone = new PhoneNumbers();
        phone.setPhoneNumber(node.get("phoneNumbers").asInt());
        phone.setIdProvider(provider);
        phone.setIdAccessLevel(1);
        phoneNumbersService.save(phone);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(phone), HttpStatus.OK);
    }
}

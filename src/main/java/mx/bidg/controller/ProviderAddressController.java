package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.ProviderAddressService;
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
@RequestMapping("/provider-address")
public class ProviderAddressController {

    @Autowired
    ProviderAddressService providerAddressService;

    @Autowired
    ProvidersService providersService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody public ResponseEntity<String> addressByProvider(@PathVariable int idProvider) throws Exception {
        String response = mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(providerAddressService.findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> addProviderAddress(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        ProviderAddress providerAddress = new ProviderAddress();
        providerAddress.setStreet(node.get("street").asText());
        providerAddress.setCp(node.get("cp").asInt());
        providerAddress.setNumExt(node.get("numExt").asText());
        providerAddress.setNumInt(node.get("numInt").asText());
        providerAddress.setSettlement(new CSettlement(node.get("idSettlement").asInt()));
        providerAddress.setMunicipality(new CMunicipalities(node.get("idMunicipality").asInt()));
        providerAddress.setState(new CStates(node.get("idState").asInt()));
        providerAddress.setIdProvider(provider);
        providerAddress.setIdAccessLevel(1);

        providerAddressService.save(providerAddress);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(providerAddress), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idProviderAddress}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteAddress(@PathVariable int idProviderAddress) throws IOException {
        ProviderAddress providerAddress = providerAddressService.findById(idProviderAddress);
        providerAddressService.delete(providerAddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

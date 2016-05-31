package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersProductsTypes;
import mx.bidg.service.ProvidersProductsTypesService;
import mx.bidg.service.ProvidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by jolvera on 30/05/16.
 */
@Controller
@RequestMapping("providers-products-types")
public class ProvidersProductsTypesController {

    @Autowired
    ProvidersService providersService;

    @Autowired
    ProvidersProductsTypesService providersProductsTypesService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> addProviderProduct(@PathVariable int idProvider, @RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        Providers provider = providersService.findById(idProvider);
        ProvidersProductsTypes providersProductsTypes = new ProvidersProductsTypes();
        providersProductsTypes.setcProductType(new CProductTypes(node.get("idProductType").asInt()));
        providersProductsTypes.setProvider(provider);
        providersProductsTypes.setIdAccessLevel(1);
        providersProductsTypesService.save(providersProductsTypes);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(providersProductsTypes), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idProvidersProductsTypes}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteProviderProduct(@PathVariable int idProvidersProductsTypes) throws IOException {
        ProvidersProductsTypes providersProductsTypes= providersProductsTypesService.findById(idProvidersProductsTypes);
        providersProductsTypesService.delete(providersProductsTypes);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/provider/{idProvider}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody public ResponseEntity<String> productsByProvider(@PathVariable int idProvider)throws Exception{
        String response = mapper.writerWithView(JsonViews.Root.class).writeValueAsString(providersProductsTypesService
                .findByProvider(new Providers(idProvider)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

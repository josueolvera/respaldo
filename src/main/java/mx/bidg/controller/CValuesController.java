package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CArticlesCategories;
import mx.bidg.model.CAttributes;
import mx.bidg.model.CValues;
import mx.bidg.service.CAttributesService;
import mx.bidg.service.CValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@Controller
@RequestMapping("values")
public class CValuesController {

    @Autowired
    private CValuesService valuesService;

    @Autowired
    private CAttributesService attributesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getValues(
            @RequestParam(name = "idAttribute", required = false) Integer idAttribute,
            @RequestParam(name = "idArticlesCategory", required = false) Integer idArticlesCategory
    ) throws IOException {

        List<CValues> values;

        if (idAttribute != null && idArticlesCategory != null) {
            values = valuesService.findValuesByAttribute(idAttribute,idArticlesCategory);
        } else {
            values = valuesService.findAll();
        }
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(values),
                HttpStatus.OK
        );
    }

    @RequestMapping(
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> saveValue(@RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        CValues value = new CValues();
        value.setValue(node.get("value").asText());
        value.setAttribute(new CAttributes(node.get("idAttribute").asInt()));
        value.setArticlesCategories(new CArticlesCategories(node.get("idArticlesCategory").asInt()));

        value = valuesService.save(value);

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(value),
                HttpStatus.OK
        );
    }
}

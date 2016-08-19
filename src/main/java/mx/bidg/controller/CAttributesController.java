package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CArticles;
import mx.bidg.model.CAttributes;
import mx.bidg.service.CAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/01/16.
 */
@Controller
@RequestMapping("attributes")
public class CAttributesController {

    @Autowired
    private CAttributesService attributesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/{idArticle}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getAttributes(@PathVariable int idArticle) throws IOException {
        List<CAttributes> attributes = attributesService.findByArticle(new CArticles(idArticle));

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(attributes),
                HttpStatus.OK
        );
    }
}

package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.service.FoliosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
@Controller
@RequestMapping("/folios")
public class FoliosController {

    @Autowired
    FoliosService foliosService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> findFolio(@RequestParam(name = "folio", required = false) String folio) throws IOException {
        String response;
        if (folio == null) {
            response = mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(foliosService.findAll());
        } else {
            response = mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(foliosService.findByFolio(folio));
        }
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}

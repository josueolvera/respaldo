package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CSizes;
import mx.bidg.service.CSizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 01/08/16.
 */
@RestController
@RequestMapping("sizes")
public class CSizesController {

    @Autowired
    private CSizesService cSizesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CSizes>> findAll() throws IOException {
        List<CSizes> sizes = cSizesService.findAll();
        return ResponseEntity.ok(sizes);
    }
}

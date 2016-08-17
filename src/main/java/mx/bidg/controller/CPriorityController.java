package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CPriority;
import mx.bidg.service.CPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@RestController
@RequestMapping("priority")
public class CPriorityController {

    @Autowired
    private CPriorityService cPriorityService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CPriority> priorities = cPriorityService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(priorities), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idPriority}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable int idPriority) throws IOException {
        CPriority cPriority = cPriorityService.findById(idPriority);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cPriority), HttpStatus.OK);
    }
}

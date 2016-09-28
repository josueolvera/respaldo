package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.dao.ChecksDao;
import mx.bidg.service.ChecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by gerardo8 on 27/09/16.
 */
@Controller
@RequestMapping("/checks")
public class ChecksController {

    @Autowired
    private ChecksService checksService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getChecks(@RequestParam(name = "user", required = false) Integer idUser) throws Exception {
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(checksService.getChecks(idUser)), HttpStatus.OK);
    }
}

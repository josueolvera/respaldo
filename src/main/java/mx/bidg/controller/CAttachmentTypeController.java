package mx.bidg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAttachmentType;
import mx.bidg.service.CAttachmentTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kevin Salvador on 17/01/2017.
 */
@Controller
@RequestMapping("/attachments")
public class CAttachmentTypeController {

    @Autowired
    private CAttachmentTypeService cAttachmentTypeService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getCurrencies() throws Exception {
        List<CAttachmentType> list = cAttachmentTypeService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }

}

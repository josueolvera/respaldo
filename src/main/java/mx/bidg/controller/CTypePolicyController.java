package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CTypePolicy;
import mx.bidg.service.CTypePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Kevin Salvador on 11/01/2017.
 */
@Controller
@RequestMapping("/c-type-policy")
public class CTypePolicyController {

    @Autowired
    private CTypePolicyService cTypePolicyService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>findAll()throws Exception{
        List<CTypePolicy>cTypePolicies= cTypePolicyService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cTypePolicies),
                HttpStatus.OK
        );
    }

}

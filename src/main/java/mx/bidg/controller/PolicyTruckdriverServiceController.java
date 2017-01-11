package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.PolicyTruckdriverService;
import org.apache.http.HttpStatus;
import org.apache.xml.serialize.Method;
import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

@Controller
@RequestMapping("/policy-truckdriver")
public class PolicyTruckdriverServiceController {

    @Autowired
    private PolicyTruckdriverService policyTruckdriverService;

    @Autowired
     private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findAll () throws Exception{
    return mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverService.findAll());
    }


    @RequestMapping(value = "/",method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findByDate (@RequestParam(name = "startDate", required = true) String startDate) throws Exception{
    return mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverService.findByDate(startDate));
    }







}

package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.SinisterTruckdriver;
import mx.bidg.service.SinisterTruckdriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Kevin Salvador on 11/01/2017.
 */
@Controller
@RequestMapping("/sinister-truck-driver")
public class SinisterTruckdriverController {

    @Autowired
    private SinisterTruckdriverService sinisterTruckdriverService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>findAll()throws Exception{
        List<SinisterTruckdriver>sinisterTruckdrivers=sinisterTruckdriverService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(sinisterTruckdrivers),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/get-polize/{idTypeAssistance}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>getSinister(@PathVariable int idTypeAssistance,
                                             @RequestParam (name = "startDate",required = true)String startDate
                                             )throws Exception{
        LocalDateTime ofDate = (startDate == null || startDate.equals("")) ? null :
                LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        List<SinisterTruckdriver>ListFindIdAndDate=sinisterTruckdriverService.findByDateStart(idTypeAssistance,startDate);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(ListFindIdAndDate),
                HttpStatus.OK
        );
    }


}

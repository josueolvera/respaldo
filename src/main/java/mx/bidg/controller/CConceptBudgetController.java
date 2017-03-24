package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CConceptBudget;
import mx.bidg.service.CConceptBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kevin Salvador on 07/03/2017.
 */
@Controller
@RequestMapping("conceptBudget")
public class CConceptBudgetController {

    @Autowired
    private CConceptBudgetService cConceptBudgetService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>findAll()throws IOException{
        List<CConceptBudget> cConceptBudgets = cConceptBudgetService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cConceptBudgets), HttpStatus.OK);
    }

}

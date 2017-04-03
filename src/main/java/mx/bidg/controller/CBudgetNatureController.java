package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetNature;
import mx.bidg.service.CBudgetNatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Kevin Salvador on 03/04/2017.
 */
@Controller
@RequestMapping("/c-budget-nature")
public class CBudgetNatureController {

    @Autowired
    private CBudgetNatureService cBudgetNatureService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getBudgetNature() throws Exception {
        List<CBudgetNature> list = cBudgetNatureService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK);
    }

}

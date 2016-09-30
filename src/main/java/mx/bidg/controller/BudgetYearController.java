package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetYear;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.service.BudgetYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gerardo8 on 30/09/16.
 */
@Controller
@RequestMapping("/budget-year")
public class BudgetYearController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BudgetYearService budgetYearService;

    @RequestMapping(value = "/update-all", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> updateAll() throws Exception {
        budgetYearService.updateAll();
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString("OK"));
    }
}

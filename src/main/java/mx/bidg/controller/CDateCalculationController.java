package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CDateCalculation;
import mx.bidg.service.AgreementsGroupConditionService;
import mx.bidg.service.CDateCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 6/10/16.
 */
@Controller
@RequestMapping("date-calculation")
public class CDateCalculationController {

    @Autowired
    CDateCalculationService cDateCalculationService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AgreementsGroupConditionService agreementsGroupConditionService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CDateCalculation> dateCalculationList = cDateCalculationService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dateCalculationList), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-status/{idDateCalculation}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeStatusByCalculationDate(@PathVariable Integer idDateCalculation) throws IOException{
        List<AgreementsGroupCondition> agreementsGroupConditionList = agreementsGroupConditionService.changeStatusByCalculationDate(idDateCalculation);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agreementsGroupConditionList), HttpStatus.OK);
    }
}

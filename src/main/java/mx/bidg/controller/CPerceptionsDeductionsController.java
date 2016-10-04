package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.DistributorPerceptionDeduction;
import mx.bidg.model.PerceptionsDeductions;
import mx.bidg.service.CDistributorsService;
import mx.bidg.service.CPerceptionsDeductionsService;
import mx.bidg.service.DistributorPerceptionDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 29/09/16.
 */
@Controller
@RequestMapping("perceptions-deductions")
public class CPerceptionsDeductionsController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CPerceptionsDeductionsService cPerceptionsDeductionsService;

    @Autowired
    DistributorPerceptionDeductionService distributorPerceptionDeductionService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CPerceptionsDeductions> perceptionsDeductionsList = cPerceptionsDeductionsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductionsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);

        CPerceptionsDeductions cPerceptionsDeductions = new CPerceptionsDeductions();
        cPerceptionsDeductions.setNamePD(node.get("namePD").asText());
        cPerceptionsDeductions.setIdTypeOperation(node.get("idOperation").asInt());

        cPerceptionsDeductions = cPerceptionsDeductionsService.save(cPerceptionsDeductions);

        List<DistributorPerceptionDeduction> distributorPerceptionDeductionList = distributorPerceptionDeductionService.saveNewPDtoDistributors(cPerceptionsDeductions);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorPerceptionDeductionList), HttpStatus.OK);
    }
}

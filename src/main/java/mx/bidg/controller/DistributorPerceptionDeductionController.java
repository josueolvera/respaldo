package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DistributorPerceptionDeduction;
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
@RequestMapping("distributor-pd")
public class DistributorPerceptionDeductionController {

    @Autowired
    DistributorPerceptionDeductionService distributorPerceptionDeductionService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<DistributorPerceptionDeduction> perceptionDeductionList = distributorPerceptionDeductionService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionDeductionList), HttpStatus.OK);
    }

    @RequestMapping(value="/distributor/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributor(@PathVariable Integer idDistributor) throws IOException{
        List<DistributorPerceptionDeduction> perceptionDeductionList = distributorPerceptionDeductionService.findPDbyDistributorSelected(idDistributor);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionDeductionList), HttpStatus.OK);
    }

    @RequestMapping(value="/distributor-selected/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributorAll(@PathVariable Integer idDistributor) throws IOException{
        List<DistributorPerceptionDeduction> perceptionDeductionList = distributorPerceptionDeductionService.findByDistributorAll(idDistributor);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionDeductionList), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeStatus(@RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);

        DistributorPerceptionDeduction distributorPerceptionDeduction  = distributorPerceptionDeductionService.findById(node.get("idDistributorPd").asInt());
        distributorPerceptionDeduction.setHasPd(node.get("hasPd").asBoolean());
        distributorPerceptionDeduction = distributorPerceptionDeductionService.update(distributorPerceptionDeduction);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorPerceptionDeduction), HttpStatus.OK);
    }
}

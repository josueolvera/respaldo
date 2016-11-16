package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CCommissionsCash;
import mx.bidg.service.CCommissionsCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by PC_YAIR on 15/11/2016.
 */
@Controller
@RequestMapping("commissions-cash")
public class CCommissionsCashController {

    @Autowired
    CCommissionsCashService cCommissionsCashService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CCommissionsCash> commissionsCashes = cCommissionsCashService.findAll();
        return new ResponseEntity<String>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionsCashes), HttpStatus.OK);

    }

    @RequestMapping(value = "/{idCommissionCash}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idCommissionCash) throws IOException {
        CCommissionsCash cCommissionsCash = cCommissionsCashService.findById(idCommissionCash);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cCommissionsCash), HttpStatus.OK);
    }

    @RequestMapping(value = "/modify-commission-cash/{idCommissionCash}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer idCommissionCash, @RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);

        CCommissionsCash cCommissionsCash = cCommissionsCashService.findById(idCommissionCash);

        cCommissionsCash.setCommissionsCash1(new BigDecimal(node.get("commissionCash1").asDouble()));
        cCommissionsCash.setCommissionsCash2(new BigDecimal(node.get("commissionCash2").asDouble()));

        cCommissionsCash = cCommissionsCashService.update(cCommissionsCash);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cCommissionsCash), HttpStatus.OK);
    }
}

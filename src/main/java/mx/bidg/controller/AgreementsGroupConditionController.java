package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.CDateCalculation;
import mx.bidg.service.AgreementsGroupConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author rubens
 */
@Controller
@RequestMapping("/agreement-condition")
public class AgreementsGroupConditionController {
    
    @Autowired
    AgreementsGroupConditionService agcs;
    
    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(value= "/{idAg}" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable int idAg) throws IOException{
        List<AgreementsGroupCondition> agcList = agcs.findByGroupCondition(idAg);
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agcList),
                HttpStatus.OK
        );
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> updateAgreementCondition(@RequestBody String data) throws Exception{
        JsonNode json = mapper.readTree(data);
        Integer idGroupCondition = json.get("idGroupCondition").asInt();
        boolean statusBoolean = json.get("statusBoolean").asBoolean();
        AgreementsGroupCondition agc = agcs.updateStatus(idGroupCondition, statusBoolean);
         return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agc),
                HttpStatus.OK);
    }
    
    @RequestMapping(value= "/save", method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveAgreementCondition(@RequestBody String data) throws Exception{
        JsonNode json = mapper.readTree(data);
        
        AgreementsGroupCondition ultimateRow = agcs.getFinalOrder(json.get("idAg").asInt());
        
        
        
        AgreementsGroupCondition agreementsGroupCondition= new AgreementsGroupCondition();
        agreementsGroupCondition.setAgreementsGroups(new CAgreementsGroups(json.get("idAg").asInt()));
        agreementsGroupCondition.setTabulator(new BigDecimal(json.get("tabulator").asDouble()));
        agreementsGroupCondition.setAmountMin(new BigDecimal(json.get("amountMin").asDouble()));
        agreementsGroupCondition.setAmountMax(new BigDecimal(json.get("amountMax").asDouble()));
        agreementsGroupCondition.setStatus(0);
        agreementsGroupCondition.setTypeOperation(json.get("typeOperation").asInt());
        agreementsGroupCondition.setcDateCalculation(new CDateCalculation(json.get("idDateCalculation").asInt()));
        
        if (ultimateRow == null) {
            agreementsGroupCondition.setOrder(1);
        }
        else {
            agreementsGroupCondition.setOrder(ultimateRow.getOrder() + 1);
        }
        
        AgreementsGroupCondition agc = agcs.save(agreementsGroupCondition);
         return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agc),
                HttpStatus.OK);
    }
    
}

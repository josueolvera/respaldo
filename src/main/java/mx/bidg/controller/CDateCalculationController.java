package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CDateCalculation;
import mx.bidg.model.EmailTemplates;
import mx.bidg.model.Users;
import mx.bidg.service.AgreementsGroupConditionService;
import mx.bidg.service.CDateCalculationService;
import mx.bidg.service.EmailDeliveryService;
import mx.bidg.service.EmailTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

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

    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllByStatus(@PathVariable Integer status)throws Exception{
        List<CDateCalculation> dateCalculations = cDateCalculationService.findByStatus(status);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dateCalculations), HttpStatus.OK);
    }


    @RequestMapping(value = "/auhtorized-sales", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> auhtorizedSales(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode node = mapper.readTree(data);

        Users user = (Users) session.getAttribute("user");

        CDateCalculation cDateCalculation = cDateCalculationService.findById(node.get("reportSelected").get("idDateCalculation").asInt());

        cDateCalculation.setStatus(2);
        cDateCalculation.setUsername(user.getUsername());
        cDateCalculation.setCreationDate(LocalDateTime.now());

        cDateCalculation = cDateCalculationService.update(cDateCalculation);

        EmailTemplates template = emailTemplatesService.findByName("authorized_sales");
        template.addProperty("initialDate", node.get("startDate").asText());
        template.addProperty("finalDate", node.get("endDate").asText());
        template.addProperty("users", user);
        template.addProperty("cDateCalculation", cDateCalculation);
        emailDeliveryService.deliverEmail(template);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(template), HttpStatus.OK);
    }

    @RequestMapping(value = "/auhtorized-commission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> auhtorizedCommission(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode node = mapper.readTree(data);

        Users user = (Users) session.getAttribute("user");

        CDateCalculation cDateCalculation = cDateCalculationService.findById(node.get("idDateCalculation").asInt());

        cDateCalculation.setStatus(3);
        cDateCalculation.setUsername(user.getUsername());
        cDateCalculation.setCreationDate(LocalDateTime.now());

        cDateCalculation = cDateCalculationService.update(cDateCalculation);

        EmailTemplates template = emailTemplatesService.findByName("authorized_commission_report");
        template.addProperty("users", user);
        template.addProperty("cDateCalculation", cDateCalculation);
        emailDeliveryService.deliverEmail(template);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(template), HttpStatus.OK);
    }
}

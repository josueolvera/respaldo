package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.Employees;
import mx.bidg.model.PerceptionsDeductions;
import mx.bidg.model.Users;
import mx.bidg.service.PerceptionsDeductionsService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by josueolvera on 30/09/16.
 */
@Controller
@RequestMapping("perceptions-deductions-employee")
public class PerceptionsDeductionsController {

    @Autowired
    PerceptionsDeductionsService perceptionsDeductionsService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<PerceptionsDeductions> perceptionsDeductionsList = perceptionsDeductionsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductionsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{

        JsonNode node = mapper.readTree(data);

        Users user = (Users) session.getAttribute("user");

        LocalDateTime applicationDate = (node.get("applicationDate") == null || node.findValue("applicationDate").asText().equals("")) ? null :
                LocalDateTime.parse(node.get("applicationDate").asText(), DateTimeFormatter.ISO_DATE_TIME);

        PerceptionsDeductions perceptionsDeductions = new PerceptionsDeductions();

        perceptionsDeductions.setAmount(new BigDecimal(node.get("amount").asDouble()));
        perceptionsDeductions.setcPerceptionsDeductions(new CPerceptionsDeductions(node.get("idCPd").asInt()));
        perceptionsDeductions.setEmployee(new Employees(node.get("employee").get("idEmployee").asInt()));
        perceptionsDeductions.setReason(node.get("pdReason").asText());
        perceptionsDeductions.setUsername(user.getUsername());
        perceptionsDeductions.setApplicationDate(applicationDate);
        perceptionsDeductions.setStatus(true);
        perceptionsDeductions.setCreationDate(LocalDateTime.now());

        perceptionsDeductions = perceptionsDeductionsService.save(perceptionsDeductions);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductions), HttpStatus.OK);
    }

    @RequestMapping(value = "/actives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllActives() throws IOException{
        List<PerceptionsDeductions> perceptionsDeductionsList = perceptionsDeductionsService.findAllActives();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductionsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-selected", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteAllSelected(@RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);

        for (JsonNode jNode : node.get("employeesSelected")) {
            PerceptionsDeductions perceptionsDeductions = perceptionsDeductionsService.findById(jNode.get("idPerceptionDeduction").asInt());
            if (perceptionsDeductions != null){
                perceptionsDeductionsService.delete(perceptionsDeductions);
            }
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductionsService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/reschedule/{idPerceptionDeduction}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reschedulePD(@PathVariable Integer idPerceptionDeduction, @RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);

        LocalDateTime applicationDate = (node.get("applicationDate") == null || node.findValue("applicationDate").asText().equals("")) ? null :
                LocalDateTime.parse(node.get("applicationDate").asText(), DateTimeFormatter.ISO_DATE_TIME);

        PerceptionsDeductions perceptionsDeductions = perceptionsDeductionsService.findById(idPerceptionDeduction);

        perceptionsDeductions.setApplicationDate(applicationDate);

        perceptionsDeductions = perceptionsDeductionsService.update(perceptionsDeductions);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductionsService.findAll()),HttpStatus.OK);
    }
}

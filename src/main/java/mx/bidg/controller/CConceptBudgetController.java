package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CConceptBudget;
import mx.bidg.model.Users;
import mx.bidg.service.CConceptBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kevin Salvador on 07/03/2017.
 */
@Controller
@RequestMapping("concept-budget")
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

    @RequestMapping(value ="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode jNode = mapper.readTree(data);
        Users user = (Users) session.getAttribute("user");

        CConceptBudget cConceptBudget = new CConceptBudget();

        cConceptBudget.setCreationDate(LocalDateTime.now());
        cConceptBudget.setUsername(user.getUsername());
        cConceptBudget.setNameConcept(jNode.get("budgetConcept").asText());

        cConceptBudget = cConceptBudgetService.save(cConceptBudget);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cConceptBudget), HttpStatus.OK);
    }

}

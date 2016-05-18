package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.BudgetConceptDistributorService;
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
 * @author Rafael Viveros
 * Created on 17/05/16.
 */
@Controller
@RequestMapping("/budget-concept-distributor")
public class BudgetConceptDistributorController {

    @Autowired
    private BudgetConceptDistributorService budgetConceptDistributorService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/concept/{idConcept}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByConcept(@PathVariable int idConcept) throws IOException {
        List<BudgetConceptDistributor> list = budgetConceptDistributorService.findByConcept(new CBudgetConcepts(idConcept));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list),
                HttpStatus.OK
        );
    }
}

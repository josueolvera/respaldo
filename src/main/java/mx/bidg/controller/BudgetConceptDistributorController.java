package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.service.BudgetConceptDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

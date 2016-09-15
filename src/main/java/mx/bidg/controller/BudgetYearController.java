package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.BudgetYearConceptService;
import mx.bidg.service.BudgetYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 15/09/16.
 */
@Controller
@RequestMapping("/budget-year")
public class BudgetYearController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BudgetYearService budgetYearService;

    @Autowired
    private BudgetYearConceptService budgetYearConceptService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession httpSession) throws Exception {

        Users user = (Users) httpSession.getAttribute("user");

        JsonNode budgetYearNode = mapper.readTree(data);
        JsonNode budgetYearConceptListNode = budgetYearNode.get("budgetYearConceptList");
        LocalDateTime now = LocalDateTime.now();

        BudgetYear budgetYear;

        if (budgetYearNode.has("idBudgetYear")) {
            budgetYear = budgetYearService.findById(budgetYearNode.get("idBudgetYear").asInt());

            for (JsonNode budgetYearConceptNode : budgetYearConceptListNode) {

                if (budgetYearConceptNode.has("idBudgetYearConcept")) {
                    BudgetYearConcept budgetYearConcept = budgetYearConceptService.findById(budgetYearConceptNode.get("idBudgetYearConcept").asInt());
                    budgetYearConcept.setUsername(user.getUsername());
                    budgetYearConcept.setBudgetConcept(mapper.treeToValue(budgetYearConceptNode.get("budgetConcept"), CBudgetConcepts.class));
                    budgetYearConcept.setJanuaryAmount(mapper.treeToValue(budgetYearConceptNode.get("januaryAmount"), BigDecimal.class));
                    budgetYearConcept.setFebruaryAmount(mapper.treeToValue(budgetYearConceptNode.get("februaryAmount"), BigDecimal.class));
                    budgetYearConcept.setMarchAmount(mapper.treeToValue(budgetYearConceptNode.get("marchAmount"), BigDecimal.class));
                    budgetYearConcept.setAprilAmount(mapper.treeToValue(budgetYearConceptNode.get("aprilAmount"), BigDecimal.class));
                    budgetYearConcept.setMayAmount(mapper.treeToValue(budgetYearConceptNode.get("mayAmount"), BigDecimal.class));
                    budgetYearConcept.setJuneAmount(mapper.treeToValue(budgetYearConceptNode.get("juneAmount"), BigDecimal.class));
                    budgetYearConcept.setJulyAmount(mapper.treeToValue(budgetYearConceptNode.get("julyAmount"), BigDecimal.class));
                    budgetYearConcept.setAugustAmount(mapper.treeToValue(budgetYearConceptNode.get("augustAmount"), BigDecimal.class));
                    budgetYearConcept.setSeptemberAmount(mapper.treeToValue(budgetYearConceptNode.get("septemberAmount"), BigDecimal.class));
                    budgetYearConcept.setOctoberAmount(mapper.treeToValue(budgetYearConceptNode.get("octoberAmount"), BigDecimal.class));
                    budgetYearConcept.setNovemberAmount(mapper.treeToValue(budgetYearConceptNode.get("novemberAmount"), BigDecimal.class));
                    budgetYearConcept.setDecemberAmount(mapper.treeToValue(budgetYearConceptNode.get("decemberAmount"), BigDecimal.class));
                    budgetYearConcept.setTotalAmount(mapper.treeToValue(budgetYearConceptNode.get("totalAmount"), BigDecimal.class));

                    budgetYearConceptService.update(budgetYearConcept);
                } else {
                    BudgetYearConcept budgetYearConcept = new BudgetYearConcept();
                    budgetYearConcept.setUsername(user.getUsername());
                    budgetYearConcept.setBudgetYear(budgetYear);
                    budgetYearConcept.setAuthorized(false);
                    budgetYearConcept.setCreationDate(now);
                    budgetYearConcept.setBudgetConcept(mapper.treeToValue(budgetYearConceptNode.get("budgetConcept"), CBudgetConcepts.class));
                    budgetYearConcept.setJanuaryAmount(mapper.treeToValue(budgetYearConceptNode.get("januaryAmount"), BigDecimal.class));
                    budgetYearConcept.setFebruaryAmount(mapper.treeToValue(budgetYearConceptNode.get("februaryAmount"), BigDecimal.class));
                    budgetYearConcept.setMarchAmount(mapper.treeToValue(budgetYearConceptNode.get("marchAmount"), BigDecimal.class));
                    budgetYearConcept.setAprilAmount(mapper.treeToValue(budgetYearConceptNode.get("aprilAmount"), BigDecimal.class));
                    budgetYearConcept.setMayAmount(mapper.treeToValue(budgetYearConceptNode.get("mayAmount"), BigDecimal.class));
                    budgetYearConcept.setJuneAmount(mapper.treeToValue(budgetYearConceptNode.get("juneAmount"), BigDecimal.class));
                    budgetYearConcept.setJulyAmount(mapper.treeToValue(budgetYearConceptNode.get("julyAmount"), BigDecimal.class));
                    budgetYearConcept.setAugustAmount(mapper.treeToValue(budgetYearConceptNode.get("augustAmount"), BigDecimal.class));
                    budgetYearConcept.setSeptemberAmount(mapper.treeToValue(budgetYearConceptNode.get("septemberAmount"), BigDecimal.class));
                    budgetYearConcept.setOctoberAmount(mapper.treeToValue(budgetYearConceptNode.get("octoberAmount"), BigDecimal.class));
                    budgetYearConcept.setNovemberAmount(mapper.treeToValue(budgetYearConceptNode.get("novemberAmount"), BigDecimal.class));
                    budgetYearConcept.setDecemberAmount(mapper.treeToValue(budgetYearConceptNode.get("decemberAmount"), BigDecimal.class));
                    budgetYearConcept.setTotalAmount(mapper.treeToValue(budgetYearConceptNode.get("totalAmount"), BigDecimal.class));

                    budgetYearConceptService.save(budgetYearConcept);
                }
            }

            budgetYear = budgetYearService.findById(budgetYearNode.get("idBudgetYear").asInt());

            budgetYear.setBudgetAmounts();

            budgetYear = budgetYearService.update(budgetYear);
        } else {
            budgetYear = mapper.treeToValue(budgetYearNode, BudgetYear.class);

            budgetYear.setCurrency(CCurrencies.MXN);

            for (BudgetYearConcept budgetYearConcept : budgetYear.getBudgetYearConceptList()) {
                budgetYearConcept.setUsername(user.getUsername());
                budgetYearConcept.setAuthorized(false);
                budgetYearConcept.setCreationDate(now);
            }

            budgetYear = budgetYearService.save(budgetYear);
        }

        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(budgetYear));
    }

}

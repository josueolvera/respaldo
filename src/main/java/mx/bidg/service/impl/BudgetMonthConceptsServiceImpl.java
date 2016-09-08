/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.model.*;
import mx.bidg.service.CBudgetConceptsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.BudgetYearConceptDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.service.BudgetMonthConceptsService;
import mx.bidg.service.DwEnterprisesService;
import mx.bidg.utils.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BudgetMonthConceptsServiceImpl implements BudgetMonthConceptsService {

    @Autowired
    private BudgetMonthConceptsDao budgetMonthConceptsDao;

    @Autowired
    private DwEnterprisesService dwEnterprisesService;

    @Autowired
    private CBudgetConceptsService cBudgetConceptsService;

    @Autowired
    private BudgetYearConceptDao budgetYearConceptDao;
    
    @Autowired
    private BudgetsDao budgetsDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<BudgetMonthConcepts> saveList(String data, Users user) throws Exception {

        List<BudgetMonthConcepts> list = new ArrayList<>();
        CBudgetConcepts concept;
        JsonNode jsonConcepts = mapper.readTree(data);

        for (JsonNode jsonRequest : jsonConcepts) {

            int idConcept = jsonRequest.get("idConcept").asInt();
            String conceptName = jsonRequest.get("conceptName").asText();
            Budgets budget = new Budgets(jsonRequest.get("idBudget").asInt());
            Integer year = jsonRequest.get("year").asInt();
            BudgetYearConcept budgetYearConcept;
            BigDecimal amountConcept;
            BigDecimal amount;
            CMonths month;

            if (idConcept > 0) {
                
                concept = new CBudgetConcepts(idConcept);
                concept.setBudgetConcept(conceptName);
                concept.setIdAccessLevel(1);
                list = budgetMonthConceptsDao.findByConcept(concept);
                
                for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {
                    
                    amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                    month = new CMonths(conceptMonth.get("month").asInt());
                    
                    for(BudgetMonthConcepts budgetMonthConcepts : list) {

//                        budgetYearConcept = budgetMonthConcepts.getBudgetYearConcept();
                        
//                        if(budgetYearConcept.getAuthorized())
//                            throw new ValidationException("El presupuesto ya esta autorizado!",
//                                    "No puede modificarse un presupuesto ya autorizado");
                        
//                        if(budgetYearConcept.getBudget().getIdBudget().equals(budget.getIdBudget()) &&
//                                budgetYearConcept.getMonth().getIdMonth().equals(month.getIdMonth()) &&
//                                budgetYearConcept.getYear() == year) {
                            
//                            BigDecimal amountConceptActual = budgetMonthConcepts.getAmount();
//                            BigDecimal amountActual = budgetYearConcept.getAmount();
//                            budgetYearConcept.setAmount(amountActual.subtract(amountConceptActual).add(amountConcept));
//                            budgetMonthConcepts.setAmount(amountConcept);
//                            budgetYearConcept.setUsername(user.getUsername());
//                        }

                    }
                    
                }
                cBudgetConceptsService.update(concept);

            } else {

                concept = new CBudgetConcepts();
                concept.setBudgetConcept(conceptName);
                concept.setIdAccessLevel(1);

                for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {

                    BudgetMonthConcepts budgetMonthConcepts = new BudgetMonthConcepts();
                    amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                    month = new CMonths(conceptMonth.get("month").asInt());

                    budgetYearConcept = budgetYearConceptDao.findByCombination(budget, month, year);

                    if (budgetYearConcept == null) {

                        budgetYearConcept = new BudgetYearConcept();
//                        budgetYearConcept.setAmount(amountConcept);
//                        budgetYearConcept.setExpendedAmount(new BigDecimal(0));
                        budgetYearConcept.setIdAccessLevel(1);
                        budgetYearConcept.setCurrency(new CCurrencies(1));
                        budgetYearConcept.setBudget(budget);
//                        budgetYearConcept.setMonth(month);
                        budgetYearConcept.setYear(year);
                        budgetYearConcept.setAuthorized(false);
                        budgetYearConcept.setUsername(user.getUsername());
                        budgetYearConcept = budgetYearConceptDao.save(budgetYearConcept);

                    } else {
                        
                        if(budgetYearConcept.getAuthorized())
                            throw new ValidationException("El presupuesto ya esta autorizado!", 
                                    "No puede modificarse un presupuesto ya autorizado");

//                        amount = budgetYearConcept.getAmount();
//                        budgetYearConcept.setAmount(amount.add(amountConcept));
                        budgetYearConcept.setUsername(user.getUsername());
                        budgetYearConcept = budgetYearConceptDao.update(budgetYearConcept);

                    }

                    concept = cBudgetConceptsService.save(concept);
//                    budgetMonthConcepts.setBudgetYearConcept(budgetYearConcept);
                    budgetMonthConcepts.setBudgetConcept(concept);
                    budgetMonthConcepts.setIdAccessLevel(1);
                    budgetMonthConcepts.setCurrency(new CCurrencies(1));
                    budgetMonthConcepts.setAmount(amountConcept);
                    budgetMonthConcepts = budgetMonthConceptsDao.save(budgetMonthConcepts);
                    list.add(budgetMonthConcepts);
                }

            }

        }

        return list;
    }
    

    @Override
    public boolean delete(BudgetMonthConcepts budgetMonthConcepts) {
        return budgetMonthConceptsDao.delete(budgetMonthConcepts);
    }

    @Override
    public List<BudgetMonthConcepts> findByConcept(CBudgetConcepts budgetConcept) {
        return budgetMonthConceptsDao.findByConcept(budgetConcept);
    }

    @Override
    public List<BudgetMonthConcepts> findByConcept(Integer idBudgetConcepts) {
        return budgetMonthConceptsDao.findByConcept(idBudgetConcepts);
    }

    @Override
    public BudgetMonthConcepts saveBudgetMonthConcepts(BudgetMonthConcepts bmc) {
        return budgetMonthConceptsDao.save(bmc);
    }

    @Override
    public List<BudgetMonthConcepts> findByBudgetMonthBranch(Integer idBudgetMonthBranch) {
        return budgetMonthConceptsDao.findByBudgetMonthBranch(idBudgetMonthBranch);
    }

}

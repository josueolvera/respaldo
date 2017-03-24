/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.RealBudgetSpendingDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.dao.BudgetsDao;
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
    private RealBudgetSpendingDao budgetYearConceptDao;

    @Autowired
    private BudgetsDao budgetsDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<BudgetMonthConcepts> saveList(String data, Users user) throws Exception {

        List<BudgetMonthConcepts> list = new ArrayList<>();
        JsonNode jsonConcepts = mapper.readTree(data);

        for (JsonNode jsonRequest : jsonConcepts) {

            int idConcept = jsonRequest.get("idConcept").asInt();
            String conceptName = jsonRequest.get("conceptName").asText();
            Budgets budget = new Budgets(jsonRequest.get("idBudget").asInt());
            Integer year = jsonRequest.get("year").asInt();
            RealBudgetSpending realBudgetSpending;
            BigDecimal amountConcept;
            BigDecimal amount;
            CMonths month;

            if (idConcept > 0) {

                for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {

                    amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                    month = new CMonths(conceptMonth.get("month").asInt());

                    for(BudgetMonthConcepts budgetMonthConcepts : list) {

//                        realBudgetSpending = budgetMonthConcepts.getBudgetYearConcept();

//                        if(realBudgetSpending.getAuthorized())
//                            throw new ValidationException("El presupuesto ya esta autorizado!",
//                                    "No puede modificarse un presupuesto ya autorizado");

//                        if(realBudgetSpending.getBudget().getIdBudget().equals(budget.getIdBudget()) &&
//                                realBudgetSpending.getMonth().getIdMonth().equals(month.getIdMonth()) &&
//                                realBudgetSpending.getYear() == year) {

//                            BigDecimal amountConceptActual = budgetMonthConcepts.getAmount();
//                            BigDecimal amountActual = realBudgetSpending.getAmount();
//                            realBudgetSpending.setAmount(amountActual.subtract(amountConceptActual).add(amountConcept));
//                            budgetMonthConcepts.setAmount(amountConcept);
//                            realBudgetSpending.setUsername(user.getUsername());
//                        }

                    }

                }

            } else {


                for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {

                    BudgetMonthConcepts budgetMonthConcepts = new BudgetMonthConcepts();
                    amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                    month = new CMonths(conceptMonth.get("month").asInt());

                    realBudgetSpending = budgetYearConceptDao.findByCombination(budget, month, year);

                    if (realBudgetSpending == null) {

                        //realBudgetSpending = new RealBudgetSpending();
//                        realBudgetSpending.setAmount(amountConcept);
//                        realBudgetSpending.setExpendedAmount(new BigDecimal(0));
                        //realBudgetSpending.setIdAccessLevel(1);
                        realBudgetSpending.setCurrency(new CCurrencies(1));
                        realBudgetSpending.setBudget(budget);
//                        realBudgetSpending.setMonth(month);
                        realBudgetSpending.setYear(year);
                        //realBudgetSpending.setAuthorized(false);
                        realBudgetSpending.setUsername(user.getUsername());
                        realBudgetSpending = budgetYearConceptDao.save(realBudgetSpending);

                    } else {

                       /* if(realBudgetSpending.getAuthorized())
                            throw new ValidationException("El presupuesto ya esta autorizado!",
                                    "No puede modificarse un presupuesto ya autorizado");*/

//                        amount = realBudgetSpending.getAmount();
//                        realBudgetSpending.setAmount(amount.add(amountConcept));
                        realBudgetSpending.setUsername(user.getUsername());
                        realBudgetSpending = budgetYearConceptDao.update(realBudgetSpending);

                    }


//                    budgetMonthConcepts.setBudgetYearConcept(realBudgetSpending);

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.service.CBudgetConceptsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;
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
    BudgetMonthConceptsDao budgetMonthConceptsDao;

    @Autowired
    DwEnterprisesService dwEnterprisesService;

    @Autowired
    CBudgetConceptsService cBudgetConceptsService;

    @Autowired
    BudgetMonthBranchDao budgetMonthBranchDao;
    
    @Autowired
    BudgetsDao budgetsDao;

    ObjectMapper map = new ObjectMapper();

    @Override
    public List<BudgetMonthConcepts> saveList(String data) throws Exception {

        List<BudgetMonthConcepts> list = new ArrayList<>();
        CBudgetConcepts concept;
        JsonNode jsonConcepts = map.readTree(data);

        for (JsonNode jsonRequest : jsonConcepts) {

            int idConcept = jsonRequest.get("idConcept").asInt();
            String conceptName = jsonRequest.get("conceptName").asText();
            Budgets budget = new Budgets(jsonRequest.get("idBudget").asInt());
            DwEnterprises dwEnterprise = new DwEnterprises(jsonRequest.get("dwEnterprise").asInt());
            Integer year = jsonRequest.get("year").asInt();
            BudgetMonthBranch budgetMonthBranch;
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

                        budgetMonthBranch = budgetMonthConcepts.getBudgetMonthBranch();
                        
                        if(budgetMonthBranch.getIsAuthorized().equals(1))
                            throw new ValidationException("El presupuesto ya esta autorizado!", 
                                    "No puede modificarse un presupuesto ya autorizado");
                        
                        if(budgetMonthBranch.getBudget().getIdBudget().equals(budget.getIdBudget()) && 
                                budgetMonthBranch.getMonth().getIdMonth().equals(month.getIdMonth()) && 
                                budgetMonthBranch.getDwEnterprise().getIdDwEnterprise().equals(
                                dwEnterprise.getIdDwEnterprise()) && 
                                budgetMonthBranch.getYear() == year) {
                            
                            BigDecimal amountConceptActual = budgetMonthConcepts.getAmount();
                            BigDecimal amountActual = budgetMonthBranch.getAmount();
                            budgetMonthBranch.setAmount(amountActual.subtract(amountConceptActual).add(amountConcept));
                            budgetMonthConcepts.setAmount(amountConcept);
                        }

                    }
                    
                }
                concept = cBudgetConceptsService.update(concept);

            } else {

                concept = new CBudgetConcepts();
                concept.setBudgetConcept(conceptName);
                concept.setIdAccessLevel(1);

                for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {

                    BudgetMonthConcepts budgetMonthConcepts = new BudgetMonthConcepts();
                    amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                    month = new CMonths(conceptMonth.get("month").asInt());

                    budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget, month,
                            dwEnterprise, year);

                    if (budgetMonthBranch == null) {

                        budgetMonthBranch = new BudgetMonthBranch();
                        budgetMonthBranch.setAmount(amountConcept);
                        budgetMonthBranch.setExpendedAmount(new BigDecimal(0));
                        budgetMonthBranch.setIdAccessLevel(1);
                        budgetMonthBranch.setCurrency(new CCurrencies(1));
                        budgetMonthBranch.setBudget(budget);
                        budgetMonthBranch.setDwEnterprise(dwEnterprise);
                        budgetMonthBranch.setMonth(month);
                        budgetMonthBranch.setYear(year);
                        budgetMonthBranch.setIsAuthorized(0);
                        budgetMonthBranch = budgetMonthBranchDao.save(budgetMonthBranch);

                    } else {
                        
                        if(budgetMonthBranch.getIsAuthorized().equals(1))
                            throw new ValidationException("El presupuesto ya esta autorizado!", 
                                    "No puede modificarse un presupuesto ya autorizado");

                        amount = budgetMonthBranch.getAmount();
                        budgetMonthBranch.setAmount(amount.add(amountConcept));
                        budgetMonthBranch = budgetMonthBranchDao.update(budgetMonthBranch);

                    }

                    concept = cBudgetConceptsService.save(concept);
                    budgetMonthConcepts.setBudgetMonthBranch(budgetMonthBranch);
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

}

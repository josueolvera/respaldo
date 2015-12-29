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
import mx.bidg.model.AccessLevel;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CGroups;
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
                list = budgetMonthConceptsDao.findByConcept(concept);
                concept.setBudgetConcept(conceptName);
                
                for (JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {
                    
                    amountConcept = MoneyConverter.obtainNumber(conceptMonth.get("amountConcept").asText());
                    month = new CMonths(conceptMonth.get("month").asInt());
                    
                    for(BudgetMonthConcepts budgetMonthConcepts : list) {

                        budgetMonthBranch = budgetMonthConcepts.getIdBudgetMonthBranch();
                        if(budgetMonthBranch.getIdBudget().getIdBudget().equals(budget.getIdBudget()) && 
                                budgetMonthBranch.getIdMonth().getIdMonth().equals(month.getIdMonth()) && 
                                budgetMonthBranch.getIdDwEnterprise().getIdDwEnterprise().equals(
                                dwEnterprise.getIdDwEnterprise()) && 
                                budgetMonthBranch.getYear() == year) {
                            
                            BigDecimal amountConceptActual = budgetMonthConcepts.getAmount();
                            BigDecimal amountActual = budgetMonthBranch.getAmount();
                            budgetMonthBranch.setAmount(amountActual.subtract(amountConceptActual).add(amountConcept));
                            budgetMonthConcepts.setAmount(amountConcept);
                        }

                    }
                    
                }                

            } else {

                concept = new CBudgetConcepts();
                concept.setBudgetConcept(conceptName);
                concept = cBudgetConceptsService.save(concept);

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
                        budgetMonthBranch.setIdAccessLevel(new AccessLevel(1));
                        budgetMonthBranch.setIdBudget(budget);
                        budgetMonthBranch.setIdDwEnterprise(dwEnterprise);
                        budgetMonthBranch.setIdMonth(month);
                        budgetMonthBranch.setYear(year);
                        budgetMonthBranch = budgetMonthBranchDao.save(budgetMonthBranch);

                    } else {

                        amount = budgetMonthBranch.getAmount();
                        budgetMonthBranch.setAmount(amount.add(amountConcept));
                        budgetMonthBranch = budgetMonthBranchDao.update(budgetMonthBranch);

                    }

                    budgetMonthConcepts.setIdBudgetMonthBranch(budgetMonthBranch);
                    budgetMonthConcepts.setIdBudgetConcept(concept);
                    budgetMonthConcepts.setIdAccessLevel(1);
                    budgetMonthConcepts.setAmount(amountConcept);
                    budgetMonthConcepts = budgetMonthConceptsDao.save(budgetMonthConcepts);
                    list.add(budgetMonthConcepts);
                }

            }

        }

        return list;
    }

    
    //En construccion
    @Override
    public List<BudgetMonthConcepts> findByGroupArea(int idGroup, int idArea) throws Exception {
        
        List<Budgets> budgetList = budgetsDao.findByGroupArea(new CGroups(idGroup), new CAreas(idArea));
        
        for(Budgets budget : budgetList) {
            
            List<BudgetMonthBranch> budgetMonthBranchList = budgetMonthBranchDao.findByBudget(budget);
            
            for(BudgetMonthBranch budgetMonthBranch : budgetMonthBranchList) {
                
                
                
            }
            
        }
        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.model.AccessLevel;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CMonths;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.RequestTypesBudgets;
import mx.bidg.service.BudgetMonthBranchService;
import mx.bidg.service.BudgetsService;
import mx.bidg.service.RequestTypesBudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class BudgetMonthBranchServiceImpl implements BudgetMonthBranchService {
    
    @Autowired
    BudgetMonthBranchDao budgetMonthBranchDao;
    
    @Autowired
    RequestTypesBudgetsService requestTypesBudgetsService;
    
    @Autowired
    BudgetsService budgetsService;
    
    ObjectMapper map = new ObjectMapper();

    @Override
    public BudgetMonthBranch getByRequestType(CRequestTypes cRequestTypes, Integer idGroup, Integer idArea, 
            Integer idBudgetCategories, Integer idBudgetSubcategories, Integer idBranchs) {
        
        List<RequestTypesBudgets> requestTypesBudgetsList = requestTypesBudgetsService.findByRequestType(cRequestTypes);
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idBudgetCategories, idBudgetSubcategories);
        
        List<Budgets> budgetList = new ArrayList<>();
        
        for(RequestTypesBudgets rtb : requestTypesBudgetsList) {
            budgetList.add(rtb.getIdBudget());
        }
        
        
        
            
        return null;
    }

    @Override
    public BudgetMonthBranch save(String data) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
        Integer idGroup = jsonRequest.get("group").asInt();
        Integer idArea = jsonRequest.get("area").asInt();
        Integer idCategory = jsonRequest.get("category").asInt();
        Integer idSubcategory = jsonRequest.get("subcategory").asInt();
        Integer year = jsonRequest.get("year").asInt();
        Integer idDwEnterprise = jsonRequest.get("dwEnterprise").asInt();
        String conceptName = jsonRequest.get("conceptName").asText();
        
        CBudgetConcepts concept = new CBudgetConcepts();
        concept.setBudgetConcept(conceptName);
        
        BudgetMonthConcepts budgetMonthConcepts = new BudgetMonthConcepts();
        budgetMonthConcepts.setIdAccessLevel(1);
        budgetMonthConcepts.setIdBudgetConcept(concept);
        ArrayList<BudgetMonthConcepts> budgetMonthConceptsList = new ArrayList<>();
        BudgetMonthBranch budgetMonthBranch = new BudgetMonthBranch();
        BigDecimal amountConcept;
        BigDecimal amount;
        Integer idMonth;
        
        Budgets budget = budgetsService.findByCombination(idGroup, idArea, idCategory, idSubcategory);
        
        if (budget == null) {
            return null;
        }
        
        for(JsonNode conceptMonth : jsonRequest.get("conceptMonth")) {
            
            amountConcept = conceptMonth.get("amountConcept").decimalValue();
            budgetMonthConcepts.setAmount(amountConcept);
            
            idMonth = conceptMonth.get("month").asInt();
            
            budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget, new CMonths(idMonth), 
                    new DwEnterprises(idDwEnterprise), year);
            
            if(budgetMonthBranch == null) {
                
                budgetMonthBranch = new BudgetMonthBranch();
                budgetMonthBranch.setAmount(amountConcept);
                budgetMonthBranch.setExpendedAmount(new BigDecimal(0));
                budgetMonthBranch.setIdAccessLevel(new AccessLevel(1));
                budgetMonthBranch.setIdBudget(budget);
                budgetMonthBranch.setIdDwEnterprise(new DwEnterprises(idDwEnterprise));
                budgetMonthBranch.setIdMonth(new CMonths(idMonth));
                budgetMonthBranch.setYear(year);
                
                budgetMonthConcepts.setIdBudgetMonthBranch(budgetMonthBranch);
                budgetMonthConceptsList.add(budgetMonthConcepts);
                budgetMonthBranch.setBudgetMonthConceptsList(budgetMonthConceptsList);
                budgetMonthBranchDao.save(budgetMonthBranch);
                
            } else {
                
                amount = budgetMonthBranch.getAmount();
                budgetMonthConceptsList.addAll(budgetMonthBranch.getBudgetMonthConceptsList());
                budgetMonthConceptsList.add(budgetMonthConcepts);
                
                budgetMonthBranch.setAmount(amount.add(amountConcept));
                budgetMonthBranch.setBudgetMonthConceptsList(budgetMonthConceptsList);
                budgetMonthBranchDao.update(budgetMonthBranch);
                
            }
            
            
        }
        
        return budgetMonthBranch;
    }

    @Override
    public BudgetMonthBranch findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year) {
        return budgetMonthBranchDao.findByCombination(new Budgets(budget), 
                new CMonths(month), new DwEnterprises(dwEnterprise), year);
    }
    
}

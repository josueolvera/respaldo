/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBranchs;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CGroups;
import mx.bidg.model.CRequestTypes;
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
    public BudgetMonthBranch save(BudgetMonthBranch budgetMonthBranch) {
        return budgetMonthBranchDao.save(budgetMonthBranch);
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBranchs;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CGroups;
import mx.bidg.model.CRequestTypes;

/**
 *
 * @author sistemask
 */
public interface BudgetMonthBranchService {
    
    public BudgetMonthBranch save(String data) throws Exception;
    
    public BudgetMonthBranch getByRequestType(CRequestTypes cRequestTypes, Integer idGroup, Integer idArea, 
            Integer idBudgetCategories, Integer idBudgetSubcategories, Integer idBranchs);
    
    public BudgetMonthBranch findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year);
    
}

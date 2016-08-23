/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.CRequestTypes;
import mx.bidg.model.RequestTypesProduct;

import java.util.List;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CRequestsCategories;

/**
 *
 * @author sistemask
 */
public interface RequestTypesProductService {
    
    public RequestTypesProduct findById(int id);
    public RequestTypesProduct findByCombination(int idRequestCategory, int idAccountingAccount);
    public List<RequestTypesProduct> findByRequestType(CRequestTypes cRequestTypes);
    public List<RequestTypesProduct> findByBudgetCategory(CBudgetCategories budgetCategories);
    public List<RequestTypesProduct> findByRequestCategory(CRequestsCategories cRequestCategories);    
    public List<RequestTypesProduct> findByRequestCategoryBudgetCategory(CRequestsCategories requestCategory, CBudgetCategories budgetCategories);
}

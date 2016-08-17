/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.RequestTypesProduct;

/**
 *
 * @author sistemask
 */
public interface RequestTypesProductDao extends InterfaceDao<RequestTypesProduct> {
    
    public RequestTypesProduct findByCombination(CRequestsCategories requestCategory, CRequestTypes requestType, 
            CProductTypes productType);
    
    public List<RequestTypesProduct> findByRequestCategory(CRequestsCategories requestCategory);
    
    public List<RequestTypesProduct> findByRequestCategoryBudgetCategory(CRequestsCategories requestCategory, CBudgetCategories budgetCategories);

    public List<RequestTypesProduct> findByRequestCategoryRequestType(CRequestsCategories requestCategory, CRequestTypes requestType);

    public List<RequestTypesProduct> findByRequestType(CRequestTypes requestTypes);
    
    public List<RequestTypesProduct> findByBudgetCategory(CBudgetCategories budgetCategories);
    
}

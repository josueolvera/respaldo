/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.ArrayList;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CGroups;

/**
 *
 * @author sistemask
 */
public interface BudgetsDao extends InterfaceDao<Budgets> {
    
    Budgets findByCombination(CGroups idGroup, CAreas idArea, CBudgetCategories idCategory,
            CBudgetSubcategories idSubcategory);

    ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea);
    
    ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CBudgetCategories;

/**
 *
 * @author sistemask
 */
public interface CBudgetCategoriesService {

    List<CBudgetCategories> findAll();

    List<CBudgetCategories> findAllRequest();

    CBudgetCategories findById (int idCbudgetCategories);

    CBudgetCategories save(CBudgetCategories budgetCategory);

    List<CBudgetCategories> getRequestCategories(Integer idCostCenter, Integer idRequestCategory);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.CBudgetCategories;

/**
 *
 * @author sistemask
 */
public interface CBudgetCategoriesDao extends InterfaceDao<CBudgetCategories> {

    List<CBudgetCategories> findAllRequest();
    CBudgetCategories findByFirstLevel (String firstLevel);

}

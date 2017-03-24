/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.CBudgetSubcategories;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface CBudgetSubcategoriesDao extends InterfaceDao<CBudgetSubcategories> {

    List <CBudgetSubcategories> findBySecondLevel (String secondLevel);

}

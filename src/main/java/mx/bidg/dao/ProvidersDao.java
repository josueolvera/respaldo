/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.Providers;

import java.util.List;
import mx.bidg.model.CBudgetSubcategories;

/**
 *
 * @author sistemask
 */
public interface ProvidersDao extends InterfaceDao<Providers> {
    List<Providers> findByBudgetSubtegorie(CBudgetSubcategories budgetSubcategories);
}

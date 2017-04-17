/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.math.BigDecimal;
import java.util.List;

import mx.bidg.model.Budgets;
import mx.bidg.model.RealBudgetSpending;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface RealBudgetSpendingService {

    List<RealBudgetSpending> saveList(String data, Integer idBudget, Users user) throws Exception;

    List<RealBudgetSpending> updateList(String data, Integer idBudget, Users user) throws Exception;

    RealBudgetSpending findByCombination(Integer budget, Integer month, Integer dwEnterprise, Integer year);

    RealBudgetSpending findFromRequest(String data) throws Exception;

    RealBudgetSpending update(RealBudgetSpending realBudgetSpending);

    List<RealBudgetSpending> findByBudgetsAndYear(List<Budgets> budgets, Integer year);

    List<RealBudgetSpending> findByBudgetAndYear(Integer idBudget, Integer year);

    RealBudgetSpending findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year);

    String authorizeBudget(String data) throws Exception;

    List<RealBudgetSpending> findByDWEnterpriseAndYear(Integer dwEnterprise, Integer year) throws Exception;

    RealBudgetSpending saveBudgetMonthBranch(RealBudgetSpending bmb);

    Boolean delete(Integer idBudgetYearConcept);

    List<RealBudgetSpending> copyBudget(Integer idCostCenter, Integer idBudgetType, Integer idBudgetCategory, Integer yearFromCopy, Integer yearToCopy, Boolean overwrite, Integer idBudgetNature, Users user);

    RealBudgetSpending findByAccountingAccountAndCostCenter(int idCostCenter, int idAccountingAccount);

    RealBudgetSpending save(RealBudgetSpending realBudgetSpending);

    List<RealBudgetSpending> findAll();

    List<RealBudgetSpending> findByBudgetAndYearAndNoAuthorized(Integer idBudget, Integer year, boolean authorized);

    RealBudgetSpending findByIdBudgetAndYear(Integer idBudget, Integer year);

    BigDecimal getTotalBudgetAmount (Integer idBudget, Integer year);
}

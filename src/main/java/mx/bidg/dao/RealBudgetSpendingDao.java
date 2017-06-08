/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.math.BigDecimal;
import java.util.List;

import mx.bidg.model.Budgets;
import mx.bidg.model.RealBudgetSpending;
import mx.bidg.model.CMonths;
import mx.bidg.model.DwEnterprises;

/**
 *
 * @author sistemask
 */
public interface RealBudgetSpendingDao extends InterfaceDao<RealBudgetSpending> {

    RealBudgetSpending findByCombination(Budgets budget, CMonths month, DwEnterprises dwEnterprise, Integer year);
    RealBudgetSpending findByCombination(Budgets budget, CMonths month, Integer year);
    List<RealBudgetSpending> findByBudgetsAndYear(List<Budgets> budgets, Integer year);
    List<RealBudgetSpending> findByBudgetAndYear(Integer idBudget, Integer year);
    RealBudgetSpending findByBudgetMonthAndYear(Integer idBudget, Integer idMonth, Integer year);
    List<RealBudgetSpending> findByBudget(Budgets budget);
    boolean authorizeBudget(int idDistributor, int idArea, int year);
    List<RealBudgetSpending> findByDWEnterpriseAndYear(int dwEnterprise, int year);
    RealBudgetSpending findByAccountingAccountAndCostCenter(int idCostCenter, int idAccountingAccount);
    List<RealBudgetSpending> findByBudgetAndYearAndNoAuthorized(Integer idBudget, Integer year, boolean authorized);
    RealBudgetSpending findByIdBudgetAndYear(Integer idBudget, Integer year);
    BigDecimal getTotalBudgetAmount(Integer idBuget, int year);
    BigDecimal sumTotalBudgetByMonthAndYear(List<Budgets> budgets, int month, int year);
}

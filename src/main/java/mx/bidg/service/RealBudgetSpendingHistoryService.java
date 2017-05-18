package mx.bidg.service;

import mx.bidg.model.RealBudgetSpendingHistory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kevin Salvador on 29/03/2017.
 */
public interface RealBudgetSpendingHistoryService {
    List<RealBudgetSpendingHistory> findAll();
    RealBudgetSpendingHistory findById(Integer id);
    RealBudgetSpendingHistory update(RealBudgetSpendingHistory realBudgetSpendingHistory);
    RealBudgetSpendingHistory save(RealBudgetSpendingHistory realBudgetSpendingHistory);
    boolean delete(RealBudgetSpendingHistory realBudgetSpendingHistory);
    RealBudgetSpendingHistory findByIdBudgetandYear(Integer idBudget, Integer year);
    BigDecimal getRealTotalBudgetAmount(Integer idBuget, int year);
}

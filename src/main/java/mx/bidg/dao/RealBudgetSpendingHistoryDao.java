package mx.bidg.dao;

import mx.bidg.model.RealBudgetSpendingHistory;

import java.util.List;

/**
 * Created by Kevin Salvador on 29/03/2017.
 */
public interface RealBudgetSpendingHistoryDao extends InterfaceDao<RealBudgetSpendingHistory> {
    List<RealBudgetSpendingHistory> findByIdBudgetandYear(Integer idBudget, Integer year);
}

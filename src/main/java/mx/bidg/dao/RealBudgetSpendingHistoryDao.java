package mx.bidg.dao;

import mx.bidg.model.RealBudgetSpendingHistory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kevin Salvador on 29/03/2017.
 */
public interface RealBudgetSpendingHistoryDao extends InterfaceDao<RealBudgetSpendingHistory> {
    RealBudgetSpendingHistory findByIdBudgetandYear(Integer idBudget, Integer year);
    BigDecimal getRealTotalBudgetAmount(Integer idBuget, int year);
}

package mx.bidg.service;

import mx.bidg.model.RequestBudgetSpending;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by desarrollador on 19/06/17.
 */
public interface RequestBudgetSpendingService {
    RequestBudgetSpending save(RequestBudgetSpending requestBudgetSpending);
    RequestBudgetSpending update(RequestBudgetSpending requestBudgetSpending);
    RequestBudgetSpending findById(Integer idRequestBudgetSpending);
    List<RequestBudgetSpending> findAll();
    boolean delete(RequestBudgetSpending requestBudgetSpending);
    BigDecimal getAmountDistributorCostCenter(Integer idDistributorCostCenter);
    BigDecimal getAmountSpendedDistributorCostCenter(Integer idDistributorCostCenter);
}

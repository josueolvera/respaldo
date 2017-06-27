package mx.bidg.dao;

import mx.bidg.model.RequestBudgetSpending;

import java.math.BigDecimal;

/**
 * Created by desarrollador on 19/06/17.
 */
public interface RequestBudgetSpendingDao extends InterfaceDao<RequestBudgetSpending> {
    RequestBudgetSpending findByIdDistributorCostCenterYear(Integer idDistributorCostCenter, int year);
    BigDecimal getAmountDistributorCostCenter(Integer idDistributorCostCenter, int month, int year);
    BigDecimal getAmountExpendedDistributorCostCenter(Integer idDistributorCostCenter, int month, int year);
}

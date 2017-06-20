package mx.bidg.dao;

import mx.bidg.model.RequestBudgetSpending;

/**
 * Created by desarrollador on 19/06/17.
 */
public interface RequestBudgetSpendingDao extends InterfaceDao<RequestBudgetSpending> {
    RequestBudgetSpending findByIdDistributorCostCenterYear(Integer idDistributorCostCenter, int year);
}

package mx.bidg.service.impl;

import mx.bidg.dao.RequestBudgetSpendingDao;
import mx.bidg.model.RequestBudgetSpending;
import mx.bidg.service.RequestBudgetSpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created by desarrollador on 19/06/17.
 */
@Service
@Transactional
public class RequestBudgetSpendingServiceImpl implements RequestBudgetSpendingService {

    @Autowired
    RequestBudgetSpendingDao requestBudgetSpendingDao;

    @Override
    public RequestBudgetSpending save(RequestBudgetSpending requestBudgetSpending) {
        return requestBudgetSpendingDao.save(requestBudgetSpending);
    }

    @Override
    public RequestBudgetSpending update(RequestBudgetSpending requestBudgetSpending) {
        return requestBudgetSpendingDao.update(requestBudgetSpending);
    }

    @Override
    public RequestBudgetSpending findById(Integer idRequestBudgetSpending) {
        return requestBudgetSpendingDao.findById(idRequestBudgetSpending);
    }

    @Override
    public List<RequestBudgetSpending> findAll() {
        return requestBudgetSpendingDao.findAll();
    }

    @Override
    public boolean delete(RequestBudgetSpending requestBudgetSpending) {
        return requestBudgetSpendingDao.delete(requestBudgetSpending);
    }

    @Override
    public BigDecimal getAmountDistributorCostCenter(Integer idDistributorCostCenter) {
        Calendar date = Calendar.getInstance();
        int año = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        return requestBudgetSpendingDao.getAmountDistributorCostCenter(idDistributorCostCenter, month, año);
    }

    @Override
    public BigDecimal getAmountSpendedDistributorCostCenter(Integer idDistributorCostCenter) {
        Calendar date = Calendar.getInstance();
        int año = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        return requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(idDistributorCostCenter, month, año);
    }
}

package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.RealBudgetSpendingHistoryDao;
import mx.bidg.model.RealBudgetSpendingHistory;
import mx.bidg.service.RealBudgetSpendingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Kevin Salvador on 29/03/2017.
 */
@Service
@Transactional
public class RealBudgetSpendingHistoryServiceImpl implements RealBudgetSpendingHistoryService{

    @Autowired
    private RealBudgetSpendingHistoryDao realBudgetSpendingHistoryDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<RealBudgetSpendingHistory> findAll() {
        return realBudgetSpendingHistoryDao.findAll();
    }

    @Override
    public RealBudgetSpendingHistory findById(Integer id) {
        return realBudgetSpendingHistoryDao.findById(id);
    }

    @Override
    public RealBudgetSpendingHistory update(RealBudgetSpendingHistory realBudgetSpendingHistory) {
        return realBudgetSpendingHistoryDao.update(realBudgetSpendingHistory);
    }

    @Override
    public RealBudgetSpendingHistory save(RealBudgetSpendingHistory realBudgetSpendingHistory) {
        return realBudgetSpendingHistoryDao.save(realBudgetSpendingHistory);
    }

    @Override
    public boolean delete(RealBudgetSpendingHistory realBudgetSpendingHistory) {
        return realBudgetSpendingHistoryDao.delete(realBudgetSpendingHistory);
    }

    @Override
    public RealBudgetSpendingHistory findByIdBudgetandYear(Integer idBudget, Integer year) {
        return realBudgetSpendingHistoryDao.findByIdBudgetandYear(idBudget,year);
    }
}

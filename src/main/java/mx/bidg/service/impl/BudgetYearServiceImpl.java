package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.BudgetYearConceptDao;
import mx.bidg.dao.BudgetYearDao;
import mx.bidg.model.*;
import mx.bidg.service.BudgetYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by gerardo8 on 14/09/16.
 */
@Service
@Transactional
public class BudgetYearServiceImpl implements BudgetYearService {

    @Autowired
    private BudgetYearDao budgetYearDao;

    @Autowired
    private BudgetYearConceptDao budgetYearConceptDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public BudgetYear findByBudgetAndYear(Integer idBudget, Integer year) {
        return budgetYearDao.findByBudgetAndYear(idBudget, year);
    }

    @Override
    public BudgetYear save(BudgetYear budgetYear) {
        return budgetYearDao.save(budgetYear);
    }

    @Override
    public BudgetYear findById(Integer idBudgetYear) {
        return budgetYearDao.findById(idBudgetYear);
    }

    @Override
    public BudgetYear update(BudgetYear budgetYear) {
        return budgetYearDao.update(budgetYear);
    }
}

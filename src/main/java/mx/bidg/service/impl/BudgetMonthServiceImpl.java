/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.BudgetMonthDao;
import mx.bidg.model.BudgetMonth;
import mx.bidg.service.BudgetMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class BudgetMonthServiceImpl implements BudgetMonthService{

    @Autowired
    BudgetMonthDao dao;
    
    @Override
    public BudgetMonth saveBudgetMonth(BudgetMonth budgetMonth) {
        return dao.save(budgetMonth);
    }
    
}

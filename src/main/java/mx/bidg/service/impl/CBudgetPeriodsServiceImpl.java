/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CBudgetPeriodsDao;
import mx.bidg.model.CBudgetPeriods;
import mx.bidg.service.CBudgetPeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetPeriodsServiceImpl implements CBudgetPeriodsService {
    
    @Autowired
    CBudgetPeriodsDao dao;

    @Override
    public List<CBudgetPeriods> findAll() {
        return dao.findAll();
    }
    
}

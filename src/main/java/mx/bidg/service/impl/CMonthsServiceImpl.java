/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CMonthsDao;
import mx.bidg.model.CMonths;
import mx.bidg.service.CMonthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CMonthsServiceImpl implements CMonthsService{
    
    @Autowired
    CMonthsDao dao;

    @Override
    public List<CMonths> findAll() {
        return dao.findAll();
    }
    
}

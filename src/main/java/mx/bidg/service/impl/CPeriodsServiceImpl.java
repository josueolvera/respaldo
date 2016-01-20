/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CPeriodsDao;
import mx.bidg.model.CPeriods;
import mx.bidg.service.CPeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CPeriodsServiceImpl implements CPeriodsService {
    
    @Autowired
    CPeriodsDao periodsDao;

    @Override
    public List<CPeriods> findAll() {
        return periodsDao.findAll();
    }
    
}

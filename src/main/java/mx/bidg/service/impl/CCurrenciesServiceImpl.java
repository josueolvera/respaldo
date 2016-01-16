/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CCurrenciesDao;
import mx.bidg.model.CCurrencies;
import mx.bidg.service.CCurrenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CCurrenciesServiceImpl implements CCurrenciesService {
    
    @Autowired
    CCurrenciesDao cCurrenciesDao;

    @Override
    public List<CCurrencies> findAll() {
        return cCurrenciesDao.findAll();
    }
    
}

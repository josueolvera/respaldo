/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CBudgetCategoriesDao;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.service.CBudgetCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetCategoriesServiceImpl implements CBudgetCategoriesService {
    
    @Autowired
    CBudgetCategoriesDao dao;

    @Override
    public List<CBudgetCategories> findAll() {
        return dao.findAll();
    }
    
}

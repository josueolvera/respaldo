/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CBudgetSubcategoriesDao;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.service.CBudgetSubcategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetSubcategoriesServiceImpl implements CBudgetSubcategoriesService {
    
    @Autowired
    CBudgetSubcategoriesDao dao;

    @Override
    public List<CBudgetSubcategories> findAll() {
        return dao.findAll();
    }
    
}

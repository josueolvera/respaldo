/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CBudgetTypesDao;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.service.CBudgetTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetTypesServiceImpl implements CBudgetTypesService {
    
    @Autowired
    CBudgetTypesDao dao;

    @Override
    public List<CBudgetTypes> findAll() {
        return dao.findAll();
    }
    
}

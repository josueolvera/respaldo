/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.model.CBudgetAreas;
import mx.bidg.dao.CBudgetAreasDao;
import mx.bidg.service.CBudgetAreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBudgetAreasServiceImpl implements CBudgetAreasService {
    
    @Autowired
    CBudgetAreasDao dao;

    @Override
    public List<CBudgetAreas> findAll() {
        return dao.findAll();
    }
    
}

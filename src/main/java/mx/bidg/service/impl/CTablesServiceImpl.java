/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.model.CTables;
import mx.bidg.dao.CTablesDao;
import mx.bidg.service.CTablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CTablesServiceImpl implements CTablesService{
    
    @Autowired
    CTablesDao dao;

    @Override
    public List<CTables> findAll() {
        return dao.findAll();
    }

    @Override
    public CTables findById(int id) {
        return dao.findById(id);
    }
    
}

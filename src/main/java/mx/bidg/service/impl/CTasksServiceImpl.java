/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CTasksDao;
import mx.bidg.model.CTasks;
import mx.bidg.service.CTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CTasksServiceImpl implements CTasksService{
    
    @Autowired
    private CTasksDao cTasksDao;

    @Override
    public List<CTasks> findAll() {
        return cTasksDao.findAll();
    }
    
}

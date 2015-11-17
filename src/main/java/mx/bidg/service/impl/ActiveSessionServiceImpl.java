/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.ActiveSessionDao;
import mx.bidg.model.ActiveSession;
import mx.bidg.service.ActiveSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class ActiveSessionServiceImpl implements ActiveSessionService {

    @Autowired
    ActiveSessionDao dao;
    
    @Override
    public ActiveSession save(ActiveSession activeSession) {
        return dao.save(activeSession);
    }

    @Override
    public ActiveSession findById(int id) {
        return dao.findById(id);
    }

    @Override
    public boolean delete(ActiveSession activeSession) {
        return dao.delete(activeSession);
    }
    
}

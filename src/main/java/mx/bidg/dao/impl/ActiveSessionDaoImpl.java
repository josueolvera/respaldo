/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ActiveSessionDao;
import mx.bidg.model.ActiveSession;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class ActiveSessionDaoImpl extends AbstractDao<Integer, ActiveSession> implements ActiveSessionDao{

    @Override
    public ActiveSession save(ActiveSession entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ActiveSession findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<ActiveSession> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActiveSession update(ActiveSession entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(ActiveSession entity) {
        remove(entity);
        return true;
    }
    
}

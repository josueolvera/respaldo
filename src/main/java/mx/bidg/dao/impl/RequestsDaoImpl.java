/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.Requests;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class RequestsDaoImpl extends AbstractDao<Integer, Requests> implements RequestsDao {

    @Override
    public Requests save(Requests entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Requests findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Requests> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Requests update(Requests entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Requests entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

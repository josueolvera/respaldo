/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProvidersDao;
import mx.bidg.model.Providers;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class ProvidersDaoImpl extends AbstractDao<Integer, Providers> implements ProvidersDao {

    @Override
    public Providers save(Providers entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Providers findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Providers> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<Providers>) criteria.list();
    }

    @Override
    public Providers update(Providers entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Providers entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

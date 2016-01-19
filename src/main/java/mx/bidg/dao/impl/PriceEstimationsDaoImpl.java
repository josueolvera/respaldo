/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PriceEstimationsDao;
import mx.bidg.model.PriceEstimations;
import org.springframework.stereotype.Repository;

@Repository
public class PriceEstimationsDaoImpl extends AbstractDao<Integer, PriceEstimations> implements PriceEstimationsDao {

    @Override
    public PriceEstimations save(PriceEstimations entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PriceEstimations findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PriceEstimations> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PriceEstimations update(PriceEstimations entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(PriceEstimations entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

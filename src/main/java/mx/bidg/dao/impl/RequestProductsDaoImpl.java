/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestProductsDao;
import mx.bidg.model.RequestProducts;
import org.springframework.stereotype.Repository;

@Repository
public class RequestProductsDaoImpl extends AbstractDao<Integer, RequestProducts> implements RequestProductsDao {

    @Override
    public RequestProducts save(RequestProducts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestProducts findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RequestProducts> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestProducts update(RequestProducts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RequestProducts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

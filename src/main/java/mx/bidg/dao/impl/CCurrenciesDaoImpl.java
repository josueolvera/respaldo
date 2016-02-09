/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CCurrenciesDao;
import mx.bidg.model.CCurrencies;
import org.springframework.stereotype.Repository;

@Repository
public class CCurrenciesDaoImpl extends AbstractDao<Integer, CCurrencies> implements CCurrenciesDao {

    @Override
    public CCurrencies save(CCurrencies entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CCurrencies findById(int id) {
        return getByKey(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CCurrencies> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CCurrencies update(CCurrencies entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CCurrencies entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

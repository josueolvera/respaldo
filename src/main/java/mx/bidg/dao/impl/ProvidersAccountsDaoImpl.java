/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProvidersAccountsDao;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProvidersAccountsDaoImpl extends AbstractDao<Integer, ProvidersAccounts> implements ProvidersAccountsDao {

    @Override
    public ProvidersAccounts save(ProvidersAccounts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public ProvidersAccounts findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProvidersAccounts> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProvidersAccounts update(ProvidersAccounts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(ProvidersAccounts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProvidersAccounts> findByProvider(Providers p) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("idProvider", p));
        return (List<ProvidersAccounts>) criteria.list();
    }
    
}

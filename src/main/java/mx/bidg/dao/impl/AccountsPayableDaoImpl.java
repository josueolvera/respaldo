/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AccountsPayableDao;
import mx.bidg.model.AccountsPayable;


public class AccountsPayableDaoImpl extends AbstractDao<Integer, AccountsPayable> implements AccountsPayableDao {

    @Override
    public AccountsPayable save(AccountsPayable entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AccountsPayable findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<AccountsPayable> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public AccountsPayable update(AccountsPayable entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(AccountsPayable entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

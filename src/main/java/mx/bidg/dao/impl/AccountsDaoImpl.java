/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AccountsDao;
import mx.bidg.model.Accounts;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsDaoImpl extends AbstractDao<Integer, Accounts> implements AccountsDao {

    @Override
    public Accounts save(Accounts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Accounts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Accounts> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Accounts update(Accounts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Accounts entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<Accounts> accountsActives() {
        return (List<Accounts>) createEntityCriteria().add(Restrictions.isNull("deleteDay")).list();
    }
}

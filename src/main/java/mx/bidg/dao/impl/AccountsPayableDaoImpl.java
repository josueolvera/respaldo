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
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
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
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AccountsPayable entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<AccountsPayable> findByFolio(String folio) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("folio", folio))
                .setFetchMode("accountPayableStatus", FetchMode.JOIN);
        return (List<AccountsPayable>) criteria.list();
    }
}

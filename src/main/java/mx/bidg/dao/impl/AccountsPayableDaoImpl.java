/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AccountsPayableDao;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.CAccountsPayableStatus;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
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
        return (List<AccountsPayable>) createEntityCriteria().list();
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
                .setFetchMode("accountPayableStatus", FetchMode.JOIN)
                .addOrder(Order.asc("dueDate"));
        return (List<AccountsPayable>) criteria.list();
    }

    @Override
    public Boolean deleteByFolio(String folio) {
        getSession()
                .createQuery("delete from AccountsPayable a where a.folio = :folio")
                .setString("folio", folio)
                .executeUpdate();
        return true;
    }

    @Override
    public List<AccountsPayable> findAccountsofDay() {
        LocalDateTime dateTimeStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime dateTimeFinal = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
        return createEntityCriteria()
                .add(Restrictions.between("dueDate", dateTimeStart, dateTimeFinal))
                .list();

    }

    @Override
    public List<AccountsPayable> findByReschedule() {
        Criteria criteria = createEntityCriteria();
        LocalDateTime dateTimeFinal = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
        return (List<AccountsPayable>) criteria
                .add(Restrictions.eq("accountPayableStatus",CAccountsPayableStatus.REPROGRAMADA))
                .add(Restrictions.gt("dueDate", dateTimeFinal))
                .list();
    }

    @Override
    public List<AccountsPayable> findAccountsPayable(LocalDateTime ofDate, LocalDateTime untilDate) {
        Criteria criteria = createEntityCriteria();
        return (List<AccountsPayable>) criteria
                .add(Restrictions.between("dueDate",ofDate,untilDate))
                .add(Restrictions.eq("accountPayableStatus",CAccountsPayableStatus.FINALIZADA))
                .list();
    }

    @Override
    public List<AccountsPayable> findByDueDate(LocalDateTime ofDate, LocalDateTime untilDate) {
        Criteria criteria = createEntityCriteria();
        return (List<AccountsPayable>) criteria
                .add(Restrictions.between("dueDate",ofDate,untilDate))
                .add(Restrictions.or(
                        Restrictions.eq("accountPayableStatus",CAccountsPayableStatus.PENDIENTE),
                        Restrictions.eq("accountPayableStatus",CAccountsPayableStatus.REPROGRAMADA)))
                .list();
    }
}

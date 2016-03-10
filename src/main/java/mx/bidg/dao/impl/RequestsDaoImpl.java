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
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
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
        return (Requests) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .setFetchMode("requestTypeProduct", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    public List<Requests> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Requests update(Requests entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Requests entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Requests findByIdFetchBudgetMonthBranch(Integer idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idRequest))
                .setFetchMode("budgetMonthBranch", FetchMode.JOIN);
        return (Requests) criteria.uniqueResult();
    }

    @Override
    public Requests findByIdFetchStatus(Integer idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idRequest))
                .setFetchMode("requestStatus", FetchMode.JOIN);
        return (Requests) criteria.uniqueResult();
    }

    @Override
    public Requests findByIdFetchCategory(Integer idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idRequest))
                .setFetchMode("requestTypeProduct", FetchMode.JOIN)
                .setFetchMode("requestTypeProduct.", FetchMode.JOIN);
        return (Requests) criteria.uniqueResult();
    }
    
}

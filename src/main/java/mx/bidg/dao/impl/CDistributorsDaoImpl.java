/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CDistributorsDao;
import mx.bidg.model.CDistributors;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
@SuppressWarnings("unchecked")
public class CDistributorsDaoImpl extends AbstractDao<Integer, CDistributors> implements CDistributorsDao {

    @Override
    public CDistributors save(CDistributors entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CDistributors findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CDistributors> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CDistributors>) criteria.list();
    }

    @Override
    public CDistributors update(CDistributors entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CDistributors entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CDistributors> findAllForStock() {
        return (List<CDistributors>) createEntityCriteria()
                .add(Restrictions.eq("hasStock", 1))
                .list();
    }

    @Override
    public List<CDistributors> findAllForAgreement() {
        return (List<CDistributors>) createEntityCriteria()
                .add(Restrictions.eq("hasAgreement", 1))
                .list();
    }

    @Override
    public List<CDistributors> getDistributors(Boolean forStock, Boolean forBudget, Boolean forAgreement) {

        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        if (forStock != null) {
            disjunction.add(Restrictions.eq("hasStock",forStock));
        }

        if (forBudget != null) {
            disjunction.add(Restrictions.eq("budgetShare",forBudget));
        }

        if (forAgreement != null) {
            disjunction.add(Restrictions.eq("hasAgreement",forAgreement));
        }

        criteria.add(disjunction);

        return criteria.list();
    }
}

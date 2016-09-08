/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ProvidersDao;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.Providers;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProvidersDaoImpl extends AbstractDao<Integer, Providers> implements ProvidersDao {

    @Override
    public Providers save(Providers entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Providers findById(int id) {
        return (Providers) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<Providers> findAll() {
        Criteria criteria = createEntityCriteria();
        return criteria
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .setFetchMode("providersBudgetSubcategories", FetchMode.JOIN)
                .list();
    }

    @Override
    public Providers update(Providers entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Providers entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<Providers> findByBudgetSubtegorie(CBudgetSubcategories budgetSubcategories) {
        return createEntityCriteria()
                .add(Restrictions.eq("idBudgetSubcategorie", budgetSubcategories.getIdBudgetSubcategory()))
                .list();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetConceptsDao;
import mx.bidg.model.Budgets;
import mx.bidg.model.CBudgetConcepts;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetConceptsDaoImpl extends AbstractDao<Integer, CBudgetConcepts> implements CBudgetConceptsDao {

    @Override
    public CBudgetConcepts save(CBudgetConcepts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBudgetConcepts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBudgetConcepts> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBudgetConcepts>) criteria.list();
    }

    @Override
    public CBudgetConcepts update(CBudgetConcepts entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(CBudgetConcepts entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CBudgetConcepts> findByBudget(Budgets budget) {
        Criteria criteria = createEntityCriteria()
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .setFetchMode("budgetMonthConceptsList", FetchMode.JOIN)
                .setFetchMode("budgetMonthConceptsList.idBudgetMonthBranch", FetchMode.JOIN)
                .createCriteria("budgetMonthConceptsList.idBudgetMonthBranch").add(Restrictions.eq("idBudget", budget));
        return (List<CBudgetConcepts>) criteria.list();
    }
    
}

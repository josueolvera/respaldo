/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class BudgetMonthConceptsDaoImpl extends AbstractDao<Integer, BudgetMonthConcepts> implements BudgetMonthConceptsDao {

    @Override
    public BudgetMonthConcepts save(BudgetMonthConcepts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetMonthConcepts findById(int id) {
        return (BudgetMonthConcepts) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<BudgetMonthConcepts> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public BudgetMonthConcepts update(BudgetMonthConcepts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetMonthConcepts entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<BudgetMonthConcepts> findByConcept(CBudgetConcepts budgetConcept) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("idBudgetConcept", budgetConcept.getIdBudgetConcept()))
                .setFetchMode("budgetMonthBranch", FetchMode.JOIN);
        return (List<BudgetMonthConcepts>) criteria.list();
    }

    @Override
    public List<BudgetMonthConcepts> findByConcept(Integer idBudgetConcept) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("idBudgetConcept", idBudgetConcept))
                .setFetchMode("budgetMonthBranch", FetchMode.JOIN);
        return (List<BudgetMonthConcepts>) criteria.list();
    }

    @Override
    public List<BudgetMonthConcepts> findByBudgetMonthBranch(Integer idBudgetMonthBranch) {
        return createEntityCriteria()
                .add(Restrictions.eq("idBudgetMonthBranch", idBudgetMonthBranch))
                .list();
    }

}

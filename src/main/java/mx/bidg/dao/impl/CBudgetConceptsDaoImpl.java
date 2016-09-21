/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.HashMap;
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
@SuppressWarnings("unchecked")
public class CBudgetConceptsDaoImpl extends AbstractDao<Integer, CBudgetConcepts> implements CBudgetConceptsDao {

    @Override
    public CBudgetConcepts save(CBudgetConcepts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBudgetConcepts findById(int id) {
        return (CBudgetConcepts) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CBudgetConcepts> findAll() {
        Criteria criteria = createEntityCriteria();
        return criteria.list();
    }

    @Override
    public CBudgetConcepts update(CBudgetConcepts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBudgetConcepts entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CBudgetConcepts> findByBudget(Budgets budget, int year) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("budget", budget);
        map.put("year", year);
        Criteria criteria = createEntityCriteria()
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .setFetchMode("budgetMonthConceptsList", FetchMode.JOIN)
                .setFetchMode("budgetMonthConceptsList.budgetMonthBranch", FetchMode.JOIN)
                .createCriteria("budgetMonthConceptsList.budgetMonthBranch").add(Restrictions.allEq(map));
        return (List<CBudgetConcepts>) criteria.list();
    }

    @Override
    public List<CBudgetConcepts> findByBudgetEnterprise(Budgets budget, int year, Integer idDwEnterprise) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("budget", budget);
        map.put("year", year);
        map.put("idDwEnterprise", idDwEnterprise);
        Criteria criteria = createEntityCriteria()
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .setFetchMode("budgetMonthConceptsList", FetchMode.JOIN)
                .setFetchMode("budgetMonthConceptsList.budgetMonthBranch", FetchMode.JOIN)
                .createCriteria("budgetMonthConceptsList.budgetMonthBranch").add(Restrictions.allEq(map));
        return (List<CBudgetConcepts>) criteria.list();
    }

    @Override
    public List<CBudgetConcepts> getConcepts(Integer idRequestCategory) {

        Criteria criteria = createEntityCriteria();

        if (idRequestCategory != null) {
            criteria.add(Restrictions.eq("idRequestCategory", idRequestCategory));
        }

        return criteria.list();
    }

}

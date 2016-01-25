/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.model.Budgets;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CBudgetSubcategories;
import mx.bidg.model.CGroups;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class BudgetsDaoImpl extends AbstractDao<Integer, Budgets> implements BudgetsDao {

    @Override
    public Budgets save(Budgets entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Budgets findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Budgets> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budgets update(Budgets entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Budgets entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budgets findByCombination(CGroups idGroup, CAreas idArea, CBudgetCategories idCategory, 
            CBudgetSubcategories idSubcategory) {
        
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("group", idGroup);
        map.put("area", idArea);
        map.put("budgetCategory", idCategory);
        map.put("budgetSubcategory", idSubcategory);
        
        Budgets budget = (Budgets) criteria.add(Restrictions.allEq(map)).uniqueResult();
        return budget;
    }

    @Override
    public ArrayList<Budgets> findByGroupArea(CGroups idGroup, CAreas idArea) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("group", idGroup);
        map.put("area", idArea);
        ArrayList<Budgets> list = (ArrayList<Budgets>) criteria.add(Restrictions.allEq(map))
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .list();
        return list;
    }
    
}

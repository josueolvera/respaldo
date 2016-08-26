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
import mx.bidg.model.*;
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
public class BudgetsDaoImpl extends AbstractDao<Integer, Budgets> implements BudgetsDao {

    @Override
    public Budgets save(Budgets entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Budgets findById(int id) {
       return getByKey(id);
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
    public Budgets findByCombination(CDistributors cDistributors, CAreas cArea, 
            AccountingAccounts accountingAccounts) {
        
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("distributor", cDistributors);
        map.put("area", cArea);
        map.put("accountingAccount", accountingAccounts);
        
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

    @Override
    public List<Budgets> findByDistributor(Integer idDistributor) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDistributor",idDistributor))
                .list();
    }

    @Override
    public List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature) {
        return createEntityCriteria()
                .add(Restrictions.eq("idCostCenter", idCostCenter))
                .add(Restrictions.eq("idBudgetType", idBudgetType))
                .add(Restrictions.eq("idBudgetNature", idBudgetNature))
                .list();
    }

    @Override
    public List<Budgets> findByDistributorAndArea(Integer idDistributor, Integer idArea) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDistributor",idDistributor))
                .add(Restrictions.eq("idArea",idArea))
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .list();
    }

    @Override
    public List<Budgets> findByDistributorAreaAndEnterprise(Integer idDistributor, Integer idArea, Integer idDwEnterprise) {
        return createEntityCriteria()
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .setFetchMode("budgetMonthBranchList", FetchMode.JOIN)
                .add(Restrictions.eq("idDistributor",idDistributor))
                .add(Restrictions.eq("idArea",idArea))
                .createCriteria("budgetMonthBranchList")
                .add(Restrictions.eq("idDwEnterprise", idDwEnterprise))
                .list();
    }

    @Override
    public ArrayList<Budgets> findByGroupAreaEnterprise(CGroups idGroup, CAreas idArea, Integer idDwEnterprise) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("group", idGroup);
        map.put("area", idArea);
        ArrayList<Budgets> list = (ArrayList<Budgets>) criteria
                .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
                .setFetchMode("budgetMonthBranchList", FetchMode.JOIN)
                .add(Restrictions.allEq(map))
                .createCriteria("budgetMonthBranchList")
                .add(Restrictions.eq("idDwEnterprise", idDwEnterprise))
                .list();
        return list;
    }
    
}

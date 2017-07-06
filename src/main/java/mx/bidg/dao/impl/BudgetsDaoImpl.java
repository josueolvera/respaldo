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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
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
    public List<Budgets> findAll() {
        return createEntityCriteria().add(Restrictions.ne("idBudget",0)).list();
    }

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
    public Budgets update(Budgets entity) {
        modify(entity); //To change body of generated methods, choose Tools | Templates.
        return entity;
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
    public List<Budgets> getBudgets(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, Integer idBudgetCategory) {
        Criteria criteria = createEntityCriteria();

        if (idBudgetCategory != null) {
            criteria.createCriteria("accountingAccount")
                    .add(Restrictions.eq("idBudgetCategory", idBudgetCategory));
        }

        if (idCostCenter != null) {
            criteria.add(Restrictions.eq("idCostCenter", idCostCenter));
        }

        if (idBudgetType != null) {
            criteria.add(Restrictions.eq("idBudgetType", idBudgetType));
        }

        if (idBudgetNature != null) {
            criteria.add(Restrictions.eq("idBudgetNature", idBudgetNature));
        }

        return criteria.list();
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

    @Override
    public Budgets findByAccountingAccountAndCostCenter(Integer idAccountingAccount, Integer idCostCenter) {
        return (Budgets) createEntityCriteria()
                .add(Restrictions.eq("idAccountingAccount", idAccountingAccount))
                .add(Restrictions.eq("idCostCenter", idCostCenter))
                .uniqueResult();
    }

    @Override
    public ArrayList<Budgets> findByCostCenter(Integer idCostCenter) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("idCostCenter", idCostCenter);
        map.put("idBudgetType", 1);
        DetachedCriteria accountingOfCostCenter = DetachedCriteria.forClass(Budgets.class);
        accountingOfCostCenter.setProjection(Property.forName("idAccountingAccount"));
        accountingOfCostCenter.add(Restrictions.allEq(map));

        Criteria criteria = getSession().createCriteria(RequestTypesProduct.class);
        ArrayList<Budgets> list = (ArrayList<Budgets>) criteria.add(Property.forName("idAccountingAccount").in(accountingOfCostCenter))
                .list();
        return list;
    }

    @Override
    public List<Budgets> getBudgetsByCostCenterAndRequestCategory(Integer idCostCenter, Integer idRequestCategory) {

        Criteria criteria = createEntityCriteria();

        if (idCostCenter != null) {
            criteria.add(Restrictions.eq("idCostCenter", idCostCenter));
        }

        if (idRequestCategory != null) {
            criteria.add(Restrictions.eq("idRequestCategory", idRequestCategory));
        }

        return criteria.list();
    }

    @Override
    public Budgets getBudgetForRequest(int idCostCenter, int idBudgetCategory, int idBudgetSubcategory, int idRequestCategory) {
        return (Budgets) createEntityCriteria()
                .createAlias("accountingAccount", "aa")
                .add(Restrictions.eq("idCostCenter", idCostCenter))
                .add(Restrictions.eq("aa.idBudgetCategory", idBudgetCategory))
                .add(Restrictions.eq("aa.idBudgetSubcategory", idBudgetSubcategory))
                .add(Restrictions.eq("idRequestCategory", idRequestCategory))
                .uniqueResult();
    }

    @Override
    public List<Budgets> getBudgetsfindNatureTypeAndCostCenter(Integer idCostCenter,Integer idBudgetType, Integer idBudgetNature) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idBudgetNature",idBudgetNature))
                .add(Restrictions.eq("idBudgetType",idBudgetType))
                .add(Restrictions.eq("idCostCenter",idCostCenter)).list();
    }

    @Override
    public Budgets getBudgetByNatureAndCostAndTypeAndConcept(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature, Integer idConceptBudget) {
        Criteria criteria = createEntityCriteria();
        return (Budgets) criteria.add(Restrictions.eq("idBudgetNature",idBudgetNature))
                .add(Restrictions.eq("idBudgetType",idBudgetType))
                .add(Restrictions.eq("idCostCenter",idCostCenter))
                .add(Restrictions.eq("idConceptBudget", idConceptBudget))
                .uniqueResult();
    }

    @Override
    public List<Budgets> findByIdDistributorCostCenter(Integer idDistributorCostCenter, Integer idBudgetType, Integer idBudgetNature) {
        Criteria criteria = createEntityCriteria();

        if (idDistributorCostCenter != null){
            criteria.add(Restrictions.eq("idDistributorCostCenter", idDistributorCostCenter));
        }

        if (idBudgetType != null){
            criteria.add(Restrictions.eq("idBudgetType", idBudgetType));
        }

        if (idBudgetNature != null){
            criteria.add(Restrictions.eq("idBudgetNature", idBudgetNature));
        }
        return criteria.list();
    }

    @Override
    public Budgets findByNatureTypeAndDistributor(Integer idBudgetNature, Integer idBudgetType, Integer idDistributorCostCenter) {
        Criteria criteria = createEntityCriteria();
        return (Budgets) criteria.add(Restrictions.eq("idBudgetNature",idBudgetNature)).
                add(Restrictions.eq("idBudgetType",idBudgetType)).
                add(Restrictions.eq("idDistributorCostCenter",idDistributorCostCenter)).uniqueResult();
    }

    @Override
    public List<Budgets> findByIdDistributor(Integer idDistributorCostCenter) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idDistributorCostCenter",idDistributorCostCenter)).list();
    }

    @Override
    public List<Integer> findBudgetNatureByType(Integer idBudgetType) {
        Criteria criteria = createEntityCriteria();
        return criteria
                .setProjection(Projections.distinct(Projections.property("idBudgetNature")))
                .add(Restrictions.eq("idBudgetType", idBudgetType))
                .list();
    }

    @Override
    public List<Integer> findAccountingAccountByCCAndNatureAndType(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("idBudgetType", idBudgetType))
                .add(Restrictions.eq("idBudgetNature", idBudgetNature))
                .createCriteria("distributorCostCenter", "dCC")
                .setProjection(Projections.distinct(Projections.property("dCC.idAccountingAccount")))
                .add(Restrictions.eq("dCC.idCostCenter", idCostCenter))
                .list();
    }

    @Override
    public List<Budgets> findBudgetByCCAndNatureAndType(Integer idCostCenter, Integer idBudgetType, Integer idBudgetNature) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("idBudgetType", idBudgetType))
                .add(Restrictions.eq("idBudgetNature", idBudgetNature))
                .createCriteria("distributorCostCenter", "dCC")
                .add(Restrictions.eq("dCC.idCostCenter", idCostCenter))
                .list();
    }

}

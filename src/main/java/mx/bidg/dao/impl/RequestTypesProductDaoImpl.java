/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestTypesProductDao;
import mx.bidg.model.CBudgetCategories;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.RequestTypesProduct;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class RequestTypesProductDaoImpl extends AbstractDao<Integer, RequestTypesProduct> implements RequestTypesProductDao {

    @Override
    public RequestTypesProduct save(RequestTypesProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestTypesProduct findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestTypesProduct> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestTypesProduct update(RequestTypesProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RequestTypesProduct entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestTypesProduct findByCombination(CRequestsCategories requestCategory, CRequestTypes requestType, 
            CProductTypes productType) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("requestCategory", requestCategory);
        map.put("requestType", requestType);
        map.put("productType", productType);
        return (RequestTypesProduct) criteria.add(Restrictions.allEq(map)).uniqueResult();
    }

    @Override
    public List<RequestTypesProduct> findByRequestCategory(CRequestsCategories requestCategory) {
        
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("requestCategory", requestCategory))
                 .setProjection(Projections.distinct(Projections.property("budgetCategory")));
        return (List<RequestTypesProduct>) criteria.list();
    }

    @Override
    public List<RequestTypesProduct> findByRequestCategoryRequestType(CRequestsCategories requestCategory, CRequestTypes requestType) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("requestCategory", requestCategory);
        map.put("requestType", requestType);
        return (List<RequestTypesProduct>) criteria.add(Restrictions.allEq(map)).list();
    }

    @Override
    public List<RequestTypesProduct> findByRequestType(CRequestTypes requestTypes) {
        return (List<RequestTypesProduct>)createEntityCriteria()
                .add(
                    Restrictions.disjunction()
                        .add(Restrictions.eq("idRequestCategory", CRequestsCategories.COTIZABLE))
                        .add(Restrictions.eq("idRequestCategory", CRequestsCategories.DIRECTA))
                        .add(Restrictions.eq("idRequestCategory", CRequestsCategories.PERIODICA))
                )
                .add(Restrictions.eq("requestType",requestTypes)).list();
    }

    @Override
    public List<RequestTypesProduct> findByBudgetCategory(CBudgetCategories budgetCategories) {
       Criteria criteria = createEntityCriteria().add(Restrictions.eq("budgetCategory", budgetCategories));
        return (List<RequestTypesProduct>) criteria.list(); 
    }

    @Override
    public List<RequestTypesProduct> findByRequestCategoryBudgetCategory(CRequestsCategories requestCategory, CBudgetCategories budgetCategories) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("requestCategory", requestCategory))
                .add(Restrictions.eq("budgetCategory", budgetCategories));
         return (List<RequestTypesProduct>) criteria.list(); 
    }

}

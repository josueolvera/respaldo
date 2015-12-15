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
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.RequestTypesProduct;
import org.hibernate.Criteria;
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
    public RequestTypesProduct findByCombination(int idRequestCategory, int idRequestType, int idProductType) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("idRequestCategory", new CRequestsCategories(idRequestCategory));
        map.put("idRequestType", new CRequestTypes(idRequestType));
        map.put("idProductType", new CProductTypes(idProductType));
        return (RequestTypesProduct) criteria.add(Restrictions.allEq(map)).uniqueResult();
    }
    
}

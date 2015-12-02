/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestTypesBudgetsDao;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.RequestTypesBudgets;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class RequestTypesBudgetsDaoImpl extends AbstractDao<Integer, RequestTypesBudgets> implements RequestTypesBudgetsDao {

    @Override
    public RequestTypesBudgets save(RequestTypesBudgets entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestTypesBudgets findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RequestTypesBudgets> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestTypesBudgets update(RequestTypesBudgets entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RequestTypesBudgets entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RequestTypesBudgets> findByRequestType(CRequestTypes idRequestType) {
        return (List<RequestTypesBudgets>) createEntityCriteria().add(Restrictions.eq("idRequestType", idRequestType)).list();
    }
    
}

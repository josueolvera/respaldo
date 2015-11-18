/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetPeriodsDao;
import mx.bidg.model.CBudgetPeriods;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetPeriodsDaoImpl extends AbstractDao<Integer, CBudgetPeriods> implements CBudgetPeriodsDao {

    @Override
    public CBudgetPeriods save(CBudgetPeriods entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBudgetPeriods findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CBudgetPeriods> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBudgetPeriods>) criteria.list();
    }

    @Override
    public CBudgetPeriods update(CBudgetPeriods entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBudgetPeriods entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

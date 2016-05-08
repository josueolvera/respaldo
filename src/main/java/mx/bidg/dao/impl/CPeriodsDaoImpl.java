/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CPeriodsDao;
import mx.bidg.model.CPeriods;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CPeriodsDaoImpl extends AbstractDao<Integer, CPeriods> implements CPeriodsDao {

    @Override
    public CPeriods save(CPeriods entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CPeriods findById(int id) {
        return (CPeriods) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CPeriods> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CPeriods update(CPeriods entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CPeriods entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

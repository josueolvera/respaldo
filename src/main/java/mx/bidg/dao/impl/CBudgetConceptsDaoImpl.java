/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetConceptsDao;
import mx.bidg.model.CBudgetConcepts;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetConceptsDaoImpl extends AbstractDao<Integer, CBudgetConcepts> implements CBudgetConceptsDao {

    @Override
    public CBudgetConcepts save(CBudgetConcepts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBudgetConcepts findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CBudgetConcepts> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBudgetConcepts>) criteria.list();
    }

    @Override
    public CBudgetConcepts update(CBudgetConcepts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBudgetConcepts entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

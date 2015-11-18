/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetAreasDao;
import mx.bidg.model.CBudgetAreas;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetAreasDaoImpl extends AbstractDao<Integer, CBudgetAreas> implements CBudgetAreasDao {

    @Override
    public CBudgetAreas save(CBudgetAreas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBudgetAreas findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CBudgetAreas> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBudgetAreas>) criteria.list();
    }

    @Override
    public CBudgetAreas update(CBudgetAreas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBudgetAreas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

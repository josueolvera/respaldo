/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBudgetTypesDao;
import mx.bidg.model.CBudgetTypes;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CBudgetTypesDaoImpl extends AbstractDao<Integer, CBudgetTypes> implements CBudgetTypesDao {

    @Override
    public CBudgetTypes save(CBudgetTypes entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBudgetTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBudgetTypes> findAll() {
        Criteria criteria = createEntityCriteria().
                setFetchMode("table", FetchMode.JOIN);
        return (List<CBudgetTypes>) criteria.list();
    }

    @Override
    public CBudgetTypes update(CBudgetTypes entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBudgetTypes entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

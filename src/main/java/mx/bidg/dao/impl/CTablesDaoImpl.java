/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTablesDao;
import mx.bidg.model.CTables;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CTablesDaoImpl extends AbstractDao<Integer, CTables> implements CTablesDao {

    @Override
    public CTables save(CTables entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CTables findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTables> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CTables>) criteria.list();
    }

    @Override
    public CTables update(CTables entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CTables entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

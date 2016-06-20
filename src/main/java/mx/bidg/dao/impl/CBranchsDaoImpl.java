/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBranchsDao;
import mx.bidg.model.CBranchs;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@SuppressWarnings("unchecked")
@Repository
public class CBranchsDaoImpl extends AbstractDao<Integer, CBranchs> implements CBranchsDao {

    @Override
    public CBranchs save(CBranchs entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CBranchs findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CBranchs> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CBranchs>) criteria.list();
    }

    @Override
    public CBranchs update(CBranchs entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CBranchs entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

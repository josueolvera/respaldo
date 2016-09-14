/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTypeOperationDao;
import mx.bidg.model.CTypeOperation;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class CTypeOperationDaoImpl extends AbstractDao<Integer, CTypeOperation> implements CTypeOperationDao {

    @Override
    public List<CTypeOperation> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CTypeOperation>) criteria.list();
    }

    @Override
    public CTypeOperation save(CTypeOperation entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CTypeOperation findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CTypeOperation update(CTypeOperation entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CTypeOperation entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

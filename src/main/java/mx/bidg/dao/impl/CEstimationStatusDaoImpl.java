/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CEstimationStatusDao;
import mx.bidg.model.CEstimationStatus;
import org.springframework.stereotype.Repository;

@Repository
public class CEstimationStatusDaoImpl extends AbstractDao<Integer, CEstimationStatus> implements CEstimationStatusDao {

    @Override
    public CEstimationStatus save(CEstimationStatus entity) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CEstimationStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CEstimationStatus> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CEstimationStatus update(CEstimationStatus entity) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CEstimationStatus entity) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }
    
}

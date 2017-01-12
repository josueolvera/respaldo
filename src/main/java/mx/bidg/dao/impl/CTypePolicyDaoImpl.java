package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTypePolicyDao;
import mx.bidg.model.CTypePolicy;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 11/01/2017.
 */
@Repository
public class CTypePolicyDaoImpl extends AbstractDao <Integer,CTypePolicy> implements CTypePolicyDao{
    @Override
    public CTypePolicy save(CTypePolicy entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CTypePolicy findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CTypePolicy> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List < CTypePolicy>) criteria.list();
    }

    @Override
    public CTypePolicy update(CTypePolicy entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CTypePolicy entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

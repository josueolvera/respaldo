package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAmountsSecureDao;
import mx.bidg.model.CAmountsSecure;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
@Repository
public class CAmountsSecureDaoImpl extends AbstractDao<Integer,CAmountsSecure> implements CAmountsSecureDao{
    @Override
    public CAmountsSecure save(CAmountsSecure entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CAmountsSecure findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAmountsSecure> findAll() {
        return (List<CAmountsSecure>) createEntityCriteria().list();
    }

    @Override
    public CAmountsSecure update(CAmountsSecure entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CAmountsSecure entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

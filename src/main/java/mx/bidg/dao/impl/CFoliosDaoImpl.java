package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CFoliosDao;
import mx.bidg.model.CFolios;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 3/12/15.
 */
@Repository
public class CFoliosDaoImpl extends AbstractDao<Integer, CFolios> implements CFoliosDao {
    @Override
    public CFolios save(CFolios entity) {
        this.persist(entity);
        return entity;
    }

    @Override
    public CFolios findByFolio(String folio) {
        return (CFolios) createEntityCriteria()
                .add(Restrictions.eq("folio", folio))
                .setFetchMode("authorizations", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    public CFolios findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CFolios> findAll() {
        return (List<CFolios>) createEntityCriteria().list();
    }

    @Override
    public CFolios update(CFolios entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CFolios entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationsDao;
import mx.bidg.model.Authorizations;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
@Repository
public class AuthorizationsDaoImpl extends AbstractDao<Integer, Authorizations> implements AuthorizationsDao {
    @Override
    public Authorizations save(Authorizations entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Authorizations findById(int id) {
        return (Authorizations) createEntityCriteria().add(Restrictions.eq("idAuthorization", id)).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Authorizations> findAll() {
        return (List<Authorizations>) createEntityCriteria().list();
    }

    @Override
    public Authorizations update(Authorizations entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(Authorizations entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationsDao;
import mx.bidg.model.Authorizations;
import mx.bidg.model.CAuthorizationStatus;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
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
        persist(entity);
        return entity;
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
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Authorizations entity) {
        remove(entity);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Authorizations> findByFolio(String folio) {
        return createEntityCriteria()
                .add(Restrictions.eq("folio", folio))
                .setFetchMode("users", FetchMode.JOIN)
                .addOrder(Order.asc("authorizationOrder"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Authorizations> findByFolioAndStatus(String folio, CAuthorizationStatus status) {
        return (List<Authorizations>) createEntityCriteria()
                .add(Restrictions.eq("folio", folio))
                .add(Restrictions.eq("idAuthorizationStatus", status.getIdAuthorizationStatus()))
                .list();
    }

    @Override
    public Boolean deleteByFolio(String folio) {
        getSession().createQuery("delete from Authorizations auth where auth.folio = :folio")
                .setString("folio", folio)
                .executeUpdate();
        return true;
    }

    @Override
    public Long countByFolio(String folio) {
        return (Long) getSession().createQuery("select count(*) from Authorizations auth where auth.folio = :folio")
                .setString("folio", folio)
                .uniqueResult();
    }
}

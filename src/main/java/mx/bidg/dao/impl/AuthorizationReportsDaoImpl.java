package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationReportsDao;
import mx.bidg.model.AuthorizationReports;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Repository
public class AuthorizationReportsDaoImpl extends AbstractDao<Integer, AuthorizationReports> implements AuthorizationReportsDao {

    @Override
    public AuthorizationReports save(AuthorizationReports entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AuthorizationReports findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<AuthorizationReports> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public AuthorizationReports update(AuthorizationReports entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AuthorizationReports entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<AuthorizationReports> findByIdSqlQuery(Integer idSqlQuery) {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.eq("idQuery", idSqlQuery)).list();
    }

    @Override
    public AuthorizationReports findByIdRoleAndIdQuery(Integer idRole, Integer idQuery) {
        Criteria criteria = createEntityCriteria();

        return (AuthorizationReports) criteria
                .add(Restrictions.eq("idRole", idRole))
                .add(Restrictions.eq("idQuery", idQuery))
                .uniqueResult();
    }

    @Override
    public List<AuthorizationReports> findByidQueryAndAuthorized(Integer idQuery) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("idQuery", idQuery))
                .add(Restrictions.eq("authorization", 1))
                .list();
    }
}

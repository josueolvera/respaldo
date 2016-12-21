package mx.bidg.dao;

import mx.bidg.model.AuthorizationReports;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
public interface AuthorizationReportsDao extends InterfaceDao<AuthorizationReports> {
    List<AuthorizationReports> findByIdSqlQuery(Integer idSqlQuery);
    AuthorizationReports findByIdRoleAndIdQuery(Integer idRole, Integer idQuery);
    List<AuthorizationReports> findByidQueryAndAuthorized(Integer idQuery);
}

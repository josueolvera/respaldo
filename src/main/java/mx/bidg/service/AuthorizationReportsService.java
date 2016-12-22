package mx.bidg.service;

import mx.bidg.model.AuthorizationReports;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
public interface AuthorizationReportsService {
    AuthorizationReports save (AuthorizationReports authorizationReports);
    AuthorizationReports update(AuthorizationReports authorizationReports);
    AuthorizationReports findById(Integer idAuthorizationReports);
    List<AuthorizationReports> findAll();
    boolean delete (AuthorizationReports authorizationReports);
    List<AuthorizationReports> findAllFlagsWithReportsNotAuthorized();
    List<AuthorizationReports> findByIdSqlQuery(Integer idSqlQuery);
    AuthorizationReports findByIdRoleAndIdQuery(Integer idRole, Integer idQuery);
    List<AuthorizationReports> findByIdQueryAndAuthorized(Integer idQuery);
}

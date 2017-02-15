package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationReportsDao;
import mx.bidg.dao.CalculationReportDao;
import mx.bidg.dao.SqlQueriesDao;
import mx.bidg.model.AuthorizationReports;
import mx.bidg.model.CalculationReport;
import mx.bidg.service.AuthorizationReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Service
@Transactional
public class AuthorizationReportsServiceImpl implements AuthorizationReportsService {

    @Autowired
    AuthorizationReportsDao authorizationReportsDao;

    @Autowired
    CalculationReportDao calculationReportDao;

    @Override
    public AuthorizationReports save(AuthorizationReports authorizationReports) {
        return authorizationReportsDao.save(authorizationReports);
    }

    @Override
    public AuthorizationReports update(AuthorizationReports authorizationReports) {
        return authorizationReportsDao.update(authorizationReports);
    }

    @Override
    public AuthorizationReports findById(Integer idAuthorizationReports) {
        return authorizationReportsDao.findById(idAuthorizationReports);
    }

    @Override
    public List<AuthorizationReports> findAll() {
        return authorizationReportsDao.findAll();
    }

    @Override
    public boolean delete(AuthorizationReports authorizationReports) {
        return authorizationReportsDao.delete(authorizationReports);
    }

    @Override
    public List<AuthorizationReports> findAllFlagsWithReportsNotAuthorized() {
        List<CalculationReport> calculationReportList = calculationReportDao.findReportsGeneratedAndSendedNotAuthorized();

        List<AuthorizationReports> reportsByManager = new ArrayList<>();
        List<AuthorizationReports> authorizationReportsList = null;

        if (!calculationReportList.isEmpty()){
            for (CalculationReport calculationReport : calculationReportList){
                authorizationReportsList = authorizationReportsDao.findByIdSqlQuery(calculationReport.getIdQuery());
                if (!authorizationReportsList.isEmpty()){
                    for (AuthorizationReports authorizationReports: authorizationReportsList){
                        if (authorizationReports != null){
                            authorizationReports.setCalculationReport(calculationReport);
                            reportsByManager.add(authorizationReports);
                        }
                    }
                }
            }
        }

        return reportsByManager;
    }

    @Override
    public List<AuthorizationReports> findByIdSqlQuery(Integer idSqlQueries) {
        return authorizationReportsDao.findByIdSqlQuery(idSqlQueries);
    }

    @Override
    public AuthorizationReports findByIdRoleAndIdQuery(Integer idRole, Integer idQuery) {
        return authorizationReportsDao.findByIdRoleAndIdQuery(idRole, idQuery);
    }

    @Override
    public List<AuthorizationReports> findByIdQueryAndAuthorized(Integer idQuery) {
        return authorizationReportsDao.findByidQueryAndAuthorized(idQuery);
    }
}

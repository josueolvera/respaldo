package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationReportsDao;
import mx.bidg.model.AuthorizationReports;
import mx.bidg.service.AuthorizationReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Service
@Transactional
public class AuthorizationReportsServiceImpl implements AuthorizationReportsService {

    @Autowired
    AuthorizationReportsDao authorizationReportsDao;

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
}

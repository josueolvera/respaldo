package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationsDao;
import mx.bidg.model.Authorizations;
import mx.bidg.model.CAuthorizationStatus;
import mx.bidg.service.AuthorizationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
@Service
@Transactional
public class AuthorizationsServiceImpl implements AuthorizationsService {

    @Autowired
    AuthorizationsDao authorizationsDao;

    @Override
    public Authorizations findById(int id) {
        return authorizationsDao.findById(id);
    }

    @Override
    public List<Authorizations> findAll() {
        return authorizationsDao.findAll();
    }

    @Override
    public Authorizations save(Authorizations entity) {
        return authorizationsDao.save(entity);
    }

    @Override
    public Authorizations update(Authorizations entity) {
        return authorizationsDao.update(entity);
    }

    @Override
    public Authorizations authorize(Authorizations auth) {
        auth.setCAuthorizationStatus(new CAuthorizationStatus(CAuthorizationStatus.AUTORIZADA));
        auth.setAuthorizationDate(LocalDateTime.now());
        return update(auth);
    }

    @Override
    public Authorizations reject(Authorizations auth) {
        auth.setCAuthorizationStatus(new CAuthorizationStatus(CAuthorizationStatus.RECHAZADA));
        auth.setAuthorizationDate(LocalDateTime.now());
        return update(auth);
    }
}

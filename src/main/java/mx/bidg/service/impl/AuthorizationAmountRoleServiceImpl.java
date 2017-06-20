package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationAmountRoleDao;
import mx.bidg.model.AuthorizationAmountRole;
import mx.bidg.service.AuthorizationAmountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 10/06/17.
 */
@Service
@Transactional
public class AuthorizationAmountRoleServiceImpl implements AuthorizationAmountRoleService {

    @Autowired
    private AuthorizationAmountRoleDao authorizationAmountRoleDao;

    @Override
    public AuthorizationAmountRole save(AuthorizationAmountRole authorizationAmountRole) {
        return authorizationAmountRoleDao.save(authorizationAmountRole);
    }

    @Override
    public AuthorizationAmountRole update(AuthorizationAmountRole authorizationAmountRole) {
        return authorizationAmountRoleDao.update(authorizationAmountRole);
    }

    @Override
    public AuthorizationAmountRole findById(Integer idAuthorizationAmountRole) {
        return authorizationAmountRoleDao.findById(idAuthorizationAmountRole);
    }

    @Override
    public List<AuthorizationAmountRole> findAll() {
        return authorizationAmountRoleDao.findAll();
    }

    @Override
    public boolean delete(AuthorizationAmountRole authorizationAmountRole) {
        return authorizationAmountRoleDao.delete(authorizationAmountRole);
    }
}

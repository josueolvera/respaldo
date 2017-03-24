package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationCostCenterDao;
import mx.bidg.model.AuthorizationCostCenter;
import mx.bidg.service.AuthorizationCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 15/03/2017.
 */
@Service
@Transactional
public class AuthorizationCostCenterServiceImpl implements AuthorizationCostCenterService{

    @Autowired
    private AuthorizationCostCenterDao authorizationCostCenterDao;

    @Override
    public List<AuthorizationCostCenter> findByCostCenter(Integer idCostCenter) {
        return authorizationCostCenterDao.findByCostCenter(idCostCenter);
    }

    @Override
    public List<AuthorizationCostCenter> findAll() {
        return authorizationCostCenterDao.findAll();
    }

    @Override
    public AuthorizationCostCenter save(AuthorizationCostCenter authorizationCostCenter) {
        return authorizationCostCenterDao.save(authorizationCostCenter);
    }

    @Override
    public AuthorizationCostCenter update(AuthorizationCostCenter authorizationCostCenter) {
        return authorizationCostCenterDao.update(authorizationCostCenter);
    }

    @Override
    public boolean delete(AuthorizationCostCenter authorizationCostCenter) {
        return authorizationCostCenterDao.delete(authorizationCostCenter);
    }

    @Override
    public AuthorizationCostCenter findByIdCostCenterAndYear(Integer idCostCenter, Integer year) {
        return authorizationCostCenterDao.findByIdCostCenterAndYear(idCostCenter,year);
    }
}

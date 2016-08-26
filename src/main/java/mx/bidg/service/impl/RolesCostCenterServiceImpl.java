package mx.bidg.service.impl;

import mx.bidg.dao.RolesCostCenterDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.model.RolesCostCenter;
import mx.bidg.service.RolesCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Service
@Transactional
public class RolesCostCenterServiceImpl implements RolesCostCenterService {
    
    @Autowired
    private RolesCostCenterDao rolesCostCenterDao;

    @Autowired
    private UsersDao usersDao;
    
    @Override
    public List<RolesCostCenter> findAll() {
        return rolesCostCenterDao.findAll();
    }

    @Override
    public RolesCostCenter findById(Integer id) {
        return rolesCostCenterDao.findById(id);
    }

    @Override
    public List<RolesCostCenter> findByRole(Integer idRole) {
        return rolesCostCenterDao.findByRole(idRole);
    }

    @Override
    public RolesCostCenter save(RolesCostCenter userCostCenter) {
        return rolesCostCenterDao.save(userCostCenter);
    }

    @Override
    public RolesCostCenter update(RolesCostCenter userCostCenter) {
        return rolesCostCenterDao.update(userCostCenter);
    }

    @Override
    public Boolean delete(RolesCostCenter userCostCenter) {
        return rolesCostCenterDao.delete(userCostCenter);
    }
}

package mx.bidg.service.impl;

import mx.bidg.dao.ApportionmentRoleDao;
import mx.bidg.model.ApportionmentRole;
import mx.bidg.service.ApportionmentRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Service
@Transactional
public class ApportionmentRoleServiceImpl implements ApportionmentRoleService {

    @Autowired
    ApportionmentRoleDao apportionmentRoleDao;

    @Override
    public ApportionmentRole save(ApportionmentRole apportionmentRole) {
        return apportionmentRoleDao.save(apportionmentRole);
    }

    @Override
    public ApportionmentRole update(ApportionmentRole ApportionmentRole) {
        return apportionmentRoleDao.update(ApportionmentRole);
    }

    @Override
    public ApportionmentRole findById(Integer idApportionmentRole) {
        return apportionmentRoleDao.findById(idApportionmentRole);
    }

    @Override
    public List<ApportionmentRole> findAll() {
        return apportionmentRoleDao.findAll();
    }

    @Override
    public boolean delete(ApportionmentRole apportionmentRole) {
        apportionmentRoleDao.delete(apportionmentRole);
        return true;
    }
}

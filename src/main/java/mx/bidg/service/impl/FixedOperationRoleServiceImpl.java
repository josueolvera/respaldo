package mx.bidg.service.impl;

import mx.bidg.dao.FixedOperationRoleDao;
import mx.bidg.model.FixedOperationRole;
import mx.bidg.service.FixedOperationRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class FixedOperationRoleServiceImpl implements FixedOperationRoleService {

    @Autowired
    FixedOperationRoleDao fixedOperationRoleDao;

    @Override
    public FixedOperationRole save(FixedOperationRole fixedOperationRole) {
        return fixedOperationRoleDao.save(fixedOperationRole);
    }

    @Override
    public FixedOperationRole update(FixedOperationRole fixedOperationRole) {
        return fixedOperationRoleDao.update(fixedOperationRole);
    }

    @Override
    public FixedOperationRole findById(Integer idFixedOperationRole) {
        return fixedOperationRoleDao.findById(idFixedOperationRole);
    }

    @Override
    public boolean delete(FixedOperationRole fixedOperationRole) {
        fixedOperationRoleDao.delete(fixedOperationRole);
        return true;
    }

    @Override
    public List<FixedOperationRole> findAll() {
        return fixedOperationRoleDao.findAll();
    }
}

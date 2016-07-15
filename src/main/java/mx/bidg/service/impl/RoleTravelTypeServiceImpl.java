package mx.bidg.service.impl;

import mx.bidg.dao.RoleTravelTypeDao;
import mx.bidg.model.RoleTravelType;
import mx.bidg.service.RoleTravelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class RoleTravelTypeServiceImpl implements RoleTravelTypeService {

    @Autowired
    RoleTravelTypeDao roleTravelTypeDao;

    @Override
    public RoleTravelType save(RoleTravelType roleTravelType) {
        roleTravelTypeDao.save(roleTravelType);
        return roleTravelType;
    }

    @Override
    public RoleTravelType update(RoleTravelType roleTravelType) {
        roleTravelTypeDao.update(roleTravelType);
        return roleTravelType;
    }

    @Override
    public RoleTravelType findById(Integer idRoleTravelType) {
        return roleTravelTypeDao.findById(idRoleTravelType);
    }

    @Override
    public List<RoleTravelType> findAll() {
        return roleTravelTypeDao.findAll();
    }

    @Override
    public List<RoleTravelType> findByIdRole(Integer idRole) {
        return roleTravelTypeDao.findByIdRole(idRole);
    }

    @Override
    public Boolean delete(RoleTravelType roleTravelType) {
        roleTravelTypeDao.delete(roleTravelType);
        return true;
    }
}

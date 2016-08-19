package mx.bidg.service.impl;

import mx.bidg.dao.DistributorAreaRolDao;
import mx.bidg.model.DistributorAreaRol;
import mx.bidg.service.DistributorAreaRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 15/08/16.
 */
@Service
@Transactional
public class DistributorAreaRolServiceImpl implements DistributorAreaRolService {

    @Autowired
    private DistributorAreaRolDao distributorAreaRolDao;

    @Override
    public List<DistributorAreaRol> findAll() {
        return distributorAreaRolDao.findAll();
    }

    @Override
    public DistributorAreaRol findById(Integer idDistributorAreaRol) {
        return distributorAreaRolDao.findById(idDistributorAreaRol);
    }

    @Override
    public DistributorAreaRol save(DistributorAreaRol distributorAreaRol) {
        return distributorAreaRolDao.save(distributorAreaRol);
    }

    @Override
    public DistributorAreaRol update(DistributorAreaRol distributorAreaRol) {
        return distributorAreaRolDao.update(distributorAreaRol);
    }

    @Override
    public Boolean delete(DistributorAreaRol distributorAreaRol) {
        return distributorAreaRolDao.delete(distributorAreaRol);
    }

    @Override
    public List<DistributorAreaRol> findRolByDistributorArea(Integer idDistributor, Integer idArea) {
        return distributorAreaRolDao.findRolByDistributorArea(idDistributor, idArea);
    }
}

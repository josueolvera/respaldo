package mx.bidg.service.impl;

import mx.bidg.dao.DistributorCostCenterDao;
import mx.bidg.model.DistributorCostCenter;
import mx.bidg.service.DistributorCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
@Service
@Transactional
public class DistributorCostCenterServiceImpl implements DistributorCostCenterService{

    @Autowired
    private DistributorCostCenterDao distributorCostCenterDao;

    @Override
    public List<DistributorCostCenter> findAll() {
        return distributorCostCenterDao.findAll();
    }

    @Override
    public DistributorCostCenter findById(Integer id) {
        return distributorCostCenterDao.findById(id);
    }

    @Override
    public DistributorCostCenter save(DistributorCostCenter distributorCostCenter) {
        return distributorCostCenterDao.save(distributorCostCenter);
    }

    @Override
    public DistributorCostCenter update(DistributorCostCenter distributorCostCenter) {
        return distributorCostCenterDao.update(distributorCostCenter);
    }

    @Override
    public boolean delete(DistributorCostCenter distributorCostCenter) {
        return distributorCostCenterDao.delete(distributorCostCenter);
    }

    @Override
    public List<DistributorCostCenter> findByCostCenter(Integer idCostCenter) {
        return distributorCostCenterDao.findByCostCenter(idCostCenter);
    }

    @Override
    public DistributorCostCenter findByIdCostCenter(Integer idCostCenter) {
        return distributorCostCenterDao.findByIdCostCenter(idCostCenter);
    }

    @Override
    public List<DistributorCostCenter> findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine,  Integer idDistributor,Integer idCostCenter) {
        return distributorCostCenterDao.findByIdBussinessAndDistributorAndCostCenter(idBussinessLine,idDistributor,idCostCenter);
    }
}

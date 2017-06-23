package mx.bidg.service.impl;

import mx.bidg.dao.DistributorsDetailBanksDao;
import mx.bidg.model.DistributorsDetailBanks;
import mx.bidg.service.DistributorsDetailBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Leonardo on 13/06/2017.
 */
@Service
@Transactional
public class DistributorsDetailBanksServiceImpl implements DistributorsDetailBanksService{

    @Autowired
    private DistributorsDetailBanksDao distributorsDetailBanksDao;

    @Override
    public List<DistributorsDetailBanks> findAll() { return  distributorsDetailBanksDao.findAll();}

    @Override
    public DistributorsDetailBanks findById(Integer idDistributorsDetailBanks) { return distributorsDetailBanksDao.findById(idDistributorsDetailBanks); }

    @Override
    public  DistributorsDetailBanks save (DistributorsDetailBanks distributorsDetailBanks) { return distributorsDetailBanksDao.save(distributorsDetailBanks);}

    @Override
    public DistributorsDetailBanks update (DistributorsDetailBanks distributorsDetailBanks) {return distributorsDetailBanksDao.update(distributorsDetailBanks);}

    @Override
    public  boolean delete(DistributorsDetailBanks distributorsDetailBanks) {return  distributorsDetailBanksDao.delete(distributorsDetailBanks);}

    @Override
    public List<DistributorsDetailBanks> getByDistributor(int id){
        return distributorsDetailBanksDao.getByDistributor(id);
    }

    @Override
    public DistributorsDetailBanks findByAccountNumber(String accountNumber){
        return distributorsDetailBanksDao.findByAccountNumber(accountNumber);
    }
}

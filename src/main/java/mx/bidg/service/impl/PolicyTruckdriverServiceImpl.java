package mx.bidg.service.impl;

import mx.bidg.dao.PolicyTruckdriverDao;
import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.service.PolicyTruckdriverService;
import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

@Service
@Transactional
public class PolicyTruckdriverServiceImpl implements PolicyTruckdriverService {
    @Autowired
    PolicyTruckdriverDao policyTruckdriverDao;

    @Override
    public PolicyTruckdriver save(PolicyTruckdriver policyTruckdriver) {
        return policyTruckdriverDao.save(policyTruckdriver);
    }

    @Override
    public PolicyTruckdriver update(PolicyTruckdriver policyTruckdriver) {
        return policyTruckdriverDao.update(policyTruckdriver);
    }

    @Override
    public PolicyTruckdriver findByid(Integer idPTD) {
        return policyTruckdriverDao.findById(idPTD);
    }

    @Override
    public List findByDate(String startdate) {
        return policyTruckdriverDao.findDate(startdate);
    }
    @Override
    public List<PolicyTruckdriver> findAll() {
        return policyTruckdriverDao.findAll();
    }

    @Override
    public boolean delete(PolicyTruckdriver policyTruckdriver) {
        policyTruckdriverDao.delete(policyTruckdriver);
        return true;
    }


}

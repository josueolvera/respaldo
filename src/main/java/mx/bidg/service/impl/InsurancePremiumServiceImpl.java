package mx.bidg.service.impl;

import mx.bidg.dao.InsurancePremiumDao;
import mx.bidg.model.InsurancePremium;
import mx.bidg.service.InsurancePremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
@Transactional
@Service
public class InsurancePremiumServiceImpl implements InsurancePremiumService {
    @Autowired
    private InsurancePremiumDao insurancePremiumDao;

    @Override
    public List<InsurancePremium> findAll() {
        return insurancePremiumDao.findAll();
    }

    @Override
    public InsurancePremium findById(Integer id) {
        return insurancePremiumDao.findById(id);
    }

    @Override
    public InsurancePremium save(InsurancePremium insurancePremium) {
        return insurancePremiumDao.save(insurancePremium);
    }

    @Override
    public InsurancePremium update(InsurancePremium insurancePremium) {
        return insurancePremiumDao.update(insurancePremium);
    }

    @Override
    public boolean delete(InsurancePremium insurancePremium) {
        return insurancePremiumDao.delete(insurancePremium);
    }
}

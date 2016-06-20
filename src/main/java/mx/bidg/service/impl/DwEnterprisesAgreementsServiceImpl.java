package mx.bidg.service.impl;

import mx.bidg.dao.DwEnterprisesAgreementsDao;
import mx.bidg.model.DwEnterprisesAgreements;
import mx.bidg.service.DwEnterprisesAgreementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
@Service
@Transactional
public class DwEnterprisesAgreementsServiceImpl implements DwEnterprisesAgreementsService {

    @Autowired
    DwEnterprisesAgreementsDao dwEnterprisesAgreementsDao;

    @Override
    public DwEnterprisesAgreements save(DwEnterprisesAgreements dwEnterprises) {
        dwEnterprisesAgreementsDao.save(dwEnterprises);
        return dwEnterprises;
    }

    @Override
    public DwEnterprisesAgreements update(DwEnterprisesAgreements dwEnterprises) {
        dwEnterprisesAgreementsDao.update(dwEnterprises);
        return dwEnterprises;
    }

    @Override
    public DwEnterprisesAgreements findById(Integer idDwEnterpriseAgreement) {
        return dwEnterprisesAgreementsDao.findById(idDwEnterpriseAgreement);
    }

    @Override
    public List<DwEnterprisesAgreements> findAll() {
        return dwEnterprisesAgreementsDao.findAll();
    }

    @Override
    public boolean delete(DwEnterprisesAgreements dwEnterprisesAgreements) {
        return dwEnterprisesAgreementsDao.delete(dwEnterprisesAgreements);
    }
}

package mx.bidg.service.impl;

import mx.bidg.dao.DwEnterprisesCBudgetsDao;
import mx.bidg.model.DwEnterprisesCBudgets;
import mx.bidg.service.DwEnterprisesCBudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Service
@Transactional
public class DwEnterprisesCBudgetsServiceImpl implements DwEnterprisesCBudgetsService {
    
    @Autowired
    private DwEnterprisesCBudgetsDao dwEnterprisesCBudgetsDao;
    
    @Override
    public List<DwEnterprisesCBudgets> findAll() {
        return dwEnterprisesCBudgetsDao.findAll();
    }

    @Override
    public DwEnterprisesCBudgets findById(Integer idDwEnterpriseCBudget) {
        return dwEnterprisesCBudgetsDao.findById(idDwEnterpriseCBudget);
    }

    @Override
    public DwEnterprisesCBudgets save(DwEnterprisesCBudgets dwEnterpriseCBudget) {
        return dwEnterprisesCBudgetsDao.save(dwEnterpriseCBudget);
    }

    @Override
    public DwEnterprisesCBudgets update(DwEnterprisesCBudgets dwEnterpriseCBudget) {
        return dwEnterprisesCBudgetsDao.update(dwEnterpriseCBudget);
    }

    @Override
    public Boolean delete(DwEnterprisesCBudgets dwEnterpriseCBudget) {
        return dwEnterprisesCBudgetsDao.delete(dwEnterpriseCBudget);
    }

}

package mx.bidg.service.impl;

import mx.bidg.dao.UsersDwEnterprisesCBudgetsDao;
import mx.bidg.model.UsersDwEnterprisesCBudgets;
import mx.bidg.service.UsersDwEnterprisesCBudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Service
@Transactional
public class UsersDwEnterprisesCBudgetsServiceImpl implements UsersDwEnterprisesCBudgetsService {
    
    @Autowired
    private UsersDwEnterprisesCBudgetsDao usersDwEnterprisesCBudgetsDao;
    
    @Override
    public List<UsersDwEnterprisesCBudgets> findAll() {
        return usersDwEnterprisesCBudgetsDao.findAll();
    }

    @Override
    public UsersDwEnterprisesCBudgets findById(Integer id) {
        return usersDwEnterprisesCBudgetsDao.findById(id);
    }

    @Override
    public UsersDwEnterprisesCBudgets save(UsersDwEnterprisesCBudgets userDwEnterpriseCBudget) {
        return usersDwEnterprisesCBudgetsDao.save(userDwEnterpriseCBudget);
    }

    @Override
    public UsersDwEnterprisesCBudgets update(UsersDwEnterprisesCBudgets userDwEnterpriseCBudget) {
        return usersDwEnterprisesCBudgetsDao.update(userDwEnterpriseCBudget);
    }

    @Override
    public Boolean delete(UsersDwEnterprisesCBudgets userDwEnterpriseCBudget) {
        return usersDwEnterprisesCBudgetsDao.delete(userDwEnterpriseCBudget);
    }
}

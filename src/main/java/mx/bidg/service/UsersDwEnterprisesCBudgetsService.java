package mx.bidg.service;

import mx.bidg.model.UsersDwEnterprisesCBudgets;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
public interface UsersDwEnterprisesCBudgetsService {
    List<UsersDwEnterprisesCBudgets> findAll();
    UsersDwEnterprisesCBudgets findById(Integer id);
    UsersDwEnterprisesCBudgets save(UsersDwEnterprisesCBudgets userDwEnterpriseCBudget);
    UsersDwEnterprisesCBudgets update(UsersDwEnterprisesCBudgets userDwEnterpriseCBudget);
    Boolean delete(UsersDwEnterprisesCBudgets userDwEnterpriseCBudget);
}

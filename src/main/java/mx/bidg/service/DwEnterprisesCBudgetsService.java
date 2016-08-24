package mx.bidg.service;

import mx.bidg.model.DwEnterprisesCBudgets;

import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
public interface DwEnterprisesCBudgetsService {
    List<DwEnterprisesCBudgets> findAll();
    DwEnterprisesCBudgets findById(Integer idDwEnterpriseCBudget);
    DwEnterprisesCBudgets save(DwEnterprisesCBudgets dwEnterpriseCBudget);
    DwEnterprisesCBudgets update(DwEnterprisesCBudgets dwEnterpriseCBudget);
    Boolean delete(DwEnterprisesCBudgets dwEnterpriseCBudget);
}

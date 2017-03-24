package mx.bidg.service;

import mx.bidg.model.CConceptBudget;

import java.util.List;

/**
 * Created by Kevin Salvador on 07/03/2017.
 */
public interface CConceptBudgetService {
    List<CConceptBudget>findAll();
    CConceptBudget save(CConceptBudget cConceptBudget);
    CConceptBudget update(CConceptBudget cConceptBudget);
    boolean delete(CConceptBudget cConceptBudget);
    CConceptBudget findById(Integer id);
}

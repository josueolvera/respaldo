package mx.bidg.service;

import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/05/16.
 */
public interface BudgetConceptDistributorService {
    BudgetConceptDistributor save(BudgetConceptDistributor entity);
    BudgetConceptDistributor findById(Integer id);
    BudgetConceptDistributor update(BudgetConceptDistributor entity);
    Boolean delete(BudgetConceptDistributor entity);
    List<BudgetConceptDistributor> findAll();
    List<BudgetConceptDistributor> findByConcept(CBudgetConcepts concept);
}

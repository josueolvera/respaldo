package mx.bidg.dao;

import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/05/16.
 */
public interface BudgetConceptDistributorDao extends InterfaceDao<BudgetConceptDistributor> {
    List<BudgetConceptDistributor> findByConcept(CBudgetConcepts concept);
    Boolean deleteByBudgetMonthConcept(BudgetMonthConcepts budgetMonthConcept);
}

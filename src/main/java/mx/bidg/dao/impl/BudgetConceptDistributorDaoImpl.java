package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetConceptDistributorDao;
import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.CBudgetConcepts;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/05/16.
 */
@Repository
public class BudgetConceptDistributorDaoImpl extends AbstractDao<Integer, BudgetConceptDistributor> implements BudgetConceptDistributorDao {
    @Override
    public BudgetConceptDistributor save(BudgetConceptDistributor entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetConceptDistributor findById(int id) {
        return (BudgetConceptDistributor) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<BudgetConceptDistributor> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public BudgetConceptDistributor update(BudgetConceptDistributor entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BudgetConceptDistributor entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<BudgetConceptDistributor> findByConcept(CBudgetConcepts concept) {
        return (List<BudgetConceptDistributor>) createEntityCriteria()
                .createCriteria("budgetMonthConcept")
                    .add(Restrictions.eq("idBudgetConcept", concept.getIdBudgetConcept()))
                .list();
    }
}

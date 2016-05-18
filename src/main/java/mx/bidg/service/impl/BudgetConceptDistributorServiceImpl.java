package mx.bidg.service.impl;

import mx.bidg.dao.BudgetConceptDistributorDao;
import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.BudgetConceptDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/05/16.
 */
@Service
@Transactional
public class BudgetConceptDistributorServiceImpl implements BudgetConceptDistributorService {

    @Autowired
    private BudgetConceptDistributorDao budgetConceptDistributorDao;

    @Override
    public BudgetConceptDistributor save(BudgetConceptDistributor entity) {
        return budgetConceptDistributorDao.save(entity);
    }

    @Override
    public BudgetConceptDistributor findById(Integer id) {
        return budgetConceptDistributorDao.findById(id);
    }

    @Override
    public BudgetConceptDistributor update(BudgetConceptDistributor entity) {
        return budgetConceptDistributorDao.update(entity);
    }

    @Override
    public Boolean delete(BudgetConceptDistributor entity) {
        return budgetConceptDistributorDao.delete(entity);
    }

    @Override
    public List<BudgetConceptDistributor> findAll() {
        return budgetConceptDistributorDao.findAll();
    }

    @Override
    public List<BudgetConceptDistributor> findByConcept(CBudgetConcepts concept) {
        return budgetConceptDistributorDao.findByConcept(concept);
    }
}

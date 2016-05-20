package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import mx.bidg.dao.BudgetConceptDistributorDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.service.BudgetConceptDistributorService;
import mx.bidg.utils.GroupArrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private BudgetMonthConceptsDao budgetMonthConceptsDao;

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
    public ArrayList<ArrayList<BudgetConceptDistributor>> findByConcept(CBudgetConcepts concept) {
        ArrayList<BudgetConceptDistributor> distributionList = (ArrayList) budgetConceptDistributorDao.findByConcept(concept);
        GroupArrays<BudgetConceptDistributor> grouper = new GroupArrays<>();
        ArrayList<ArrayList<BudgetConceptDistributor>> distributionGroups = grouper.groupInArray(
                distributionList, new GroupArrays.Filter<BudgetConceptDistributor>() {
                    @Override
                    public String filter(BudgetConceptDistributor item) {
                        return item.getBudgetMonthConcept().toString();
                    }
        });
        return distributionGroups;
    }

    @Override
    public List<BudgetConceptDistributor> saveJsonNode(JsonNode node) {
        throw new RuntimeException("Not implemented yet");
    }
}

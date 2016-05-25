package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import mx.bidg.dao.BudgetConceptDistributorDao;
import mx.bidg.dao.BudgetMonthConceptsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.model.BudgetMonthConcepts;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CDistributors;
import mx.bidg.service.BudgetConceptDistributorService;
import mx.bidg.utils.GroupArrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        List<BudgetConceptDistributor> response = new ArrayList<>();
        for (JsonNode arrayNode : node) {
            Integer idBudgetMonthConcept = arrayNode.get(0).get("idBudgetMonthConcept").asInt();
            BudgetMonthConcepts budgetMonthConcept = budgetMonthConceptsDao.findById(idBudgetMonthConcept);
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalPercent = BigDecimal.ZERO;

            for (JsonNode budgetPart : arrayNode) {
                BigDecimal percent = budgetPart.get("percent").decimalValue();
                totalPercent = totalPercent.add(percent);
                BigDecimal amount = totalAmount.add(budgetMonthConcept.getAmount().multiply(percent));
                BudgetConceptDistributor budgetConceptDistributor = new BudgetConceptDistributor();
                budgetConceptDistributor.setBudgetMonthConcept(budgetMonthConcept);
                budgetConceptDistributor.setDistributor(new CDistributors(budgetPart.get("idDistributor").asInt()));
                budgetConceptDistributor.setAmount(amount);
                budgetConceptDistributor.setPercent(percent);
                budgetConceptDistributorDao.save(budgetConceptDistributor);
                response.add(budgetConceptDistributor);
            }

            if (totalPercent.compareTo(BigDecimal.ONE) != 0) {
                throw new ValidationException(
                        "Porcentaje " + totalPercent + " es diferente de " + BigDecimal.ONE,
                        "El porcentaje total debe ser igual a 100%",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        return response;
    }
}

package mx.bidg.service.impl;

import mx.bidg.dao.BudgetYearConceptDao;
import mx.bidg.dao.BudgetYearDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.model.BudgetYear;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.model.Budgets;
import mx.bidg.model.CCurrencies;
import mx.bidg.pojos.BudgetSubcategory;
import mx.bidg.service.BudgetYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 29/09/16.
 */
@Service
@Transactional
public class BudgetYearServiceImpl implements BudgetYearService {

    @Autowired
    private BudgetYearDao budgetYearDao;

    @Autowired
    private BudgetYearConceptDao budgetYearConceptDao;

    @Autowired
    private BudgetsDao budgetsDao;

    @Override
    public BudgetYear findByBudgetAndYear(Integer idBudget, Integer year) {
        return budgetYearDao.findByBudgetAndYear(idBudget, year);
    }

    @Override
    public BudgetYear saveOrUpdate(Integer idBudget, Integer year) {

        Budgets budget = budgetsDao.findById(idBudget);
        BudgetYear returnBudgetYear;

        List<BudgetYearConcept> budgetYearConceptList = budgetYearConceptDao.findByBudgetAndYear(idBudget, year);

        BudgetYear budgetYear = budgetYearDao.findByBudgetAndYear(idBudget, year);

        BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
        budgetSubcategory.setBudgetYearConceptList(budgetYearConceptList);

        if (budgetYear != null) {
            budgetYear.setJanuaryAmount(budgetSubcategory.getJanuarySubcategoryAmount());
            budgetYear.setFebruaryAmount(budgetSubcategory.getFebruarySubcategoryAmount());
            budgetYear.setMarchAmount(budgetSubcategory.getMarchSubcategoryAmount());
            budgetYear.setAprilAmount(budgetSubcategory.getAprilSubcategoryAmount());
            budgetYear.setMayAmount(budgetSubcategory.getMaySubcategoryAmount());
            budgetYear.setJuneAmount(budgetSubcategory.getJuneSubcategoryAmount());
            budgetYear.setJulyAmount(budgetSubcategory.getJulySubcategoryAmount());
            budgetYear.setAugustAmount(budgetSubcategory.getAugustSubcategoryAmount());
            budgetYear.setSeptemberAmount(budgetSubcategory.getSeptemberSubcategoryAmount());
            budgetYear.setOctoberAmount(budgetSubcategory.getOctoberSubcategoryAmount());
            budgetYear.setNovemberAmount(budgetSubcategory.getNovemberSubcategoryAmount());
            budgetYear.setDecemberAmount(budgetSubcategory.getDecemberSubcategoryAmount());

            returnBudgetYear = budgetYearDao.update(budgetYear);
        } else {
            
            BudgetYear newBudgetYear = new BudgetYear(year, budget, CCurrencies.MXN);
            
            newBudgetYear.setJanuaryAmount(budgetSubcategory.getJanuarySubcategoryAmount());
            newBudgetYear.setFebruaryAmount(budgetSubcategory.getFebruarySubcategoryAmount());
            newBudgetYear.setMarchAmount(budgetSubcategory.getMarchSubcategoryAmount());
            newBudgetYear.setAprilAmount(budgetSubcategory.getAprilSubcategoryAmount());
            newBudgetYear.setMayAmount(budgetSubcategory.getMaySubcategoryAmount());
            newBudgetYear.setJuneAmount(budgetSubcategory.getJuneSubcategoryAmount());
            newBudgetYear.setJulyAmount(budgetSubcategory.getJulySubcategoryAmount());
            newBudgetYear.setAugustAmount(budgetSubcategory.getAugustSubcategoryAmount());
            newBudgetYear.setSeptemberAmount(budgetSubcategory.getSeptemberSubcategoryAmount());
            newBudgetYear.setOctoberAmount(budgetSubcategory.getOctoberSubcategoryAmount());
            newBudgetYear.setNovemberAmount(budgetSubcategory.getNovemberSubcategoryAmount());
            newBudgetYear.setDecemberAmount(budgetSubcategory.getDecemberSubcategoryAmount());

            returnBudgetYear = budgetYearDao.save(newBudgetYear);
        }

        return returnBudgetYear;
    }
}

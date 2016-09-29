package mx.bidg.utils;

import mx.bidg.model.BudgetYear;
import mx.bidg.model.BudgetYearConcept;
import mx.bidg.model.Budgets;
import mx.bidg.pojos.BudgetCategory;
import mx.bidg.pojos.BudgetSubcategory;
import mx.bidg.service.BudgetYearConceptService;
import mx.bidg.service.BudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo8 on 15/09/16.
 */
@Component
public class BudgetHelper {

    @Autowired
    private BudgetsService budgetsService;

    @Autowired
    private BudgetYearConceptService budgetYearConceptService;

    public List<BudgetCategory> getOrderedBudget(Integer idCostCenter,Integer  idBudgetType,Integer  idBudgetNature,Integer  idBudgetCategory,Integer  year) {

        List<Budgets> budgets = budgetsService.getBudgets(idCostCenter, idBudgetType, idBudgetNature, idBudgetCategory);
        List<BudgetCategory> budgetCategories = new ArrayList<>();

        for (Budgets budget : budgets) {
            BudgetCategory budgetCategory = new BudgetCategory();
            budgetCategory.setName(budget.getAccountingAccount().getBudgetCategory().getBudgetCategory());
            budgetCategory.setIdBudgetCategory(budget.getAccountingAccount().getIdBudgetCategory());

            List<BudgetYearConcept> budgetYearConceptList = budgetYearConceptService.findByBudgetAndYear(budget.getIdBudget(), year);

            BudgetSubcategory budgetSubcategory = new BudgetSubcategory();
            budgetSubcategory.setName(budget.getAccountingAccount().getBudgetSubcategory().getBudgetSubcategory());
            budgetSubcategory.setIdBudgetSubcategory(budget.getAccountingAccount().getIdBudgetSubcategory());
            budgetSubcategory.setIdBudget(budget.getIdBudget());
            budgetSubcategory.setBudgetNature(budget.getBudgetNature());
            budgetSubcategory.setCostCenter(budget.getCostCenter());
            budgetSubcategory.setBudgetType(budget.getBudgetType());
            budgetSubcategory.setBudgetYearConceptList(budgetYearConceptList);

            if (!budgetCategories.contains(budgetCategory)) {
                List<BudgetSubcategory> budgetSubcategories = new ArrayList<>();
                budgetSubcategories.add(budgetSubcategory);
                budgetCategory.setBudgetSubcategories(budgetSubcategories);
                budgetCategories.add(budgetCategory);
            } else {
                BudgetCategory oldBudgetCategory = budgetCategories.get(budgetCategories.indexOf(budgetCategory));
                oldBudgetCategory.getBudgetSubcategories().add(budgetSubcategory);
                budgetCategories.set(budgetCategories.indexOf(oldBudgetCategory), oldBudgetCategory);
            }

        }

        return budgetCategories;
    }

    public Boolean checkWhetherIsOutOfBudget(BudgetYear budgetYear, Integer month, Double amount) {

        Double budgetAmount = 0D;
        Double budgetExpendedAmount = 0D;

        switch (month) {
            case 1:
                budgetAmount += budgetYear.getJanuaryAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getJanuaryExpendedAmount().doubleValue();
                break;
            case 2:
                budgetAmount += budgetYear.getFebruaryAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getFebruaryExpendedAmount().doubleValue();
                break;
            case 3:
                budgetAmount += budgetYear.getMarchAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getMarchExpendedAmount().doubleValue();
                break;
            case 4:
                budgetAmount += budgetYear.getAprilAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getAprilExpendedAmount().doubleValue();
                break;
            case 5:
                budgetAmount += budgetYear.getMayAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getMayExpendedAmount().doubleValue();
                break;
            case 6:
                budgetAmount += budgetYear.getJuneAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getJuneExpendedAmount().doubleValue();
                break;
            case 7:
                budgetAmount += budgetYear.getJulyAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getJulyExpendedAmount().doubleValue();
                break;
            case 8:
                budgetAmount += budgetYear.getAugustAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getAugustExpendedAmount().doubleValue();
                break;
            case 9:
                budgetAmount += budgetYear.getSeptemberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getSeptemberExpendedAmount().doubleValue();
                break;
            case 10:
                budgetAmount += budgetYear.getOctoberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getOctoberExpendedAmount().doubleValue();
                break;
            case 11:
                budgetAmount += budgetYear.getNovemberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getNovemberExpendedAmount().doubleValue();
                break;
            case 12:
                budgetAmount += budgetYear.getDecemberAmount().doubleValue();
                budgetExpendedAmount += budgetYear.getDecemberExpendedAmount().doubleValue();
        }

        return amount <= (budgetAmount - budgetExpendedAmount);
    }

}

package mx.bidg.utils;

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

    public Boolean checkWhetherIsOutOfBudget(List<BudgetYearConcept> budgetYearConceptList, Integer month, Double amount) {

        Double budgetAmount = 0D;
        Double budgetExpendedAmount = 0D;

        switch (month) {
            case 1:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getJanuaryAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getJanuaryExpendedAmount().doubleValue();
                }
                break;
            case 2:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getFebruaryAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getFebruaryExpendedAmount().doubleValue();
                }
                break;
            case 3:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getMarchAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getMarchExpendedAmount().doubleValue();
                }
                break;
            case 4:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getAprilAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getAprilExpendedAmount().doubleValue();
                }
                break;
            case 5:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getMayAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getMayExpendedAmount().doubleValue();
                }
                break;
            case 6:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getJuneAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getJuneExpendedAmount().doubleValue();
                }
                break;
            case 7:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getJulyAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getJulyExpendedAmount().doubleValue();
                }
                break;
            case 8:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getAugustAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getAugustExpendedAmount().doubleValue();
                }
                break;
            case 9:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getSeptemberAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getSeptemberExpendedAmount().doubleValue();
                }
                break;
            case 10:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getOctoberAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getOctoberExpendedAmount().doubleValue();
                }
                break;
            case 11:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getNovemberAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getNovemberExpendedAmount().doubleValue();
                }
                break;
            case 12:
                for (BudgetYearConcept budgetYearConcept : budgetYearConceptList) {
                    budgetAmount += budgetYearConcept.getDecemberAmount().doubleValue();
                    budgetExpendedAmount += budgetYearConcept.getDecemberExpendedAmount().doubleValue();
                }
        }

        return amount <= (budgetAmount - budgetExpendedAmount);
    }

}

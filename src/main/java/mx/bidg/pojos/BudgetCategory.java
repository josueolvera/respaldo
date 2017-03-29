package mx.bidg.pojos;

import mx.bidg.model.RealBudgetSpending;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetCategory {
    private List<BudgetSubcategory> secondLevel;
    private List<RealBudgetSpending>levelOne;
    private List<RealBudgetSpending>levelOneYearBefore;

    public BudgetCategory() {
    }

    public List<BudgetSubcategory> getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(List<BudgetSubcategory> secondLevel) {
        this.secondLevel = secondLevel;
    }

    public List<RealBudgetSpending> getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(List<RealBudgetSpending> levelOne) {
        this.levelOne = levelOne;
    }

    public List<RealBudgetSpending> getLevelOneYearBefore() {
        return levelOneYearBefore;
    }

    public void setLevelOneYearBefore(List<RealBudgetSpending> levelOneYearBefore) {
        this.levelOneYearBefore = levelOneYearBefore;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

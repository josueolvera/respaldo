package mx.bidg.pojos;

import mx.bidg.model.RealBudgetSpending;
import mx.bidg.model.CBudgetNature;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.model.CCostCenter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetSubcategory {

    private List<RealBudgetSpending>secondLevel;
    private List<BudgetSubSubcategory> thirdLevel;

    public BudgetSubcategory() {
    }

    public List<RealBudgetSpending> getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(List<RealBudgetSpending> secondLevel) {
        this.secondLevel = secondLevel;
    }

    public List<BudgetSubSubcategory> getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(List<BudgetSubSubcategory> thirdLevel) {
        this.thirdLevel = thirdLevel;
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

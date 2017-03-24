package mx.bidg.pojos;

import mx.bidg.model.RealBudgetSpending;

import java.util.List;

/**
 * Created by Kevin Salvador on 24/03/2017.
 */
public class BudgetSubSubcategory {
    private List<RealBudgetSpending> findLevel;

    public List<RealBudgetSpending> getFindLevel() {
        return findLevel;
    }

    public void setFindLevel(List<RealBudgetSpending> findLevel) {
        this.findLevel = findLevel;
    }

    public BudgetSubSubcategory(){

    }

    public BudgetSubSubcategory(List<RealBudgetSpending> findLevel) {
        this.findLevel = findLevel;
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

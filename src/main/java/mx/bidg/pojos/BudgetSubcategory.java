package mx.bidg.pojos;

import mx.bidg.model.BudgetMonthBranch;

import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetSubcategory {
    private Integer idBudgetSubcategory;
    private String name;
    private List<BudgetMonthBranch> budgetMonthBranchList;

    public BudgetSubcategory() {
    }

    public BudgetSubcategory(Integer idBudgetSubcategory, String name) {
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.name = name;
    }

    public Integer getIdBudgetSubcategory() {
        return idBudgetSubcategory;
    }

    public void setIdBudgetSubcategory(Integer idBudgetSubcategory) {
        this.idBudgetSubcategory = idBudgetSubcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BudgetMonthBranch> getBudgetMonthBranchList() {
        return budgetMonthBranchList;
    }

    public void setBudgetMonthBranchList(List<BudgetMonthBranch> budgetMonthBranchList) {
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetSubcategory that = (BudgetSubcategory) o;

        if (idBudgetSubcategory != null ? !idBudgetSubcategory.equals(that.idBudgetSubcategory) : that.idBudgetSubcategory != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = idBudgetSubcategory != null ? idBudgetSubcategory.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetSubcategory{" +
                "idBudgetSubcategory=" + idBudgetSubcategory +
                ", name='" + name + '\'' +
                ", budgetMonthBranchList=" + budgetMonthBranchList +
                '}';
    }
}

package mx.bidg.pojos;

import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetCategory {
    private Integer idBudgetCategory;
    private String name;
    private List<BudgetSubcategory> budgetSubcategories;

    public BudgetCategory() {
    }

    public BudgetCategory(Integer idBudgetCategory, String name) {
        this.idBudgetCategory = idBudgetCategory;
        this.name = name;
    }

    public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BudgetSubcategory> getBudgetSubcategories() {
        return budgetSubcategories;
    }

    public void setBudgetSubcategories(List<BudgetSubcategory> budgetSubcategories) {
        this.budgetSubcategories = budgetSubcategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetCategory that = (BudgetCategory) o;

        return idBudgetCategory != null ? idBudgetCategory.equals(that.idBudgetCategory) : that.idBudgetCategory == null;

    }

    @Override
    public int hashCode() {
        return idBudgetCategory != null ? idBudgetCategory.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BudgetCategory{" +
                "idBudgetCategory=" + idBudgetCategory +
                ", name='" + name + '\'' +
                ", budgetSubcategories=" + budgetSubcategories +
                '}';
    }
}

package mx.bidg.pojos;

import mx.bidg.model.CBudgetNature;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.model.CCostCenter;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class Budget {
    private Integer idBudget;
    private Integer idBudgetCategory;
    private CCostCenter costCenter;
    private CBudgetTypes budgetType;
    private CBudgetNature budgetNature;
    private BudgetCategory budgetCategory;

    public Budget() {
    }

    public Budget(Integer idBudget, Integer idBudgetCategory, CCostCenter costCenter, CBudgetTypes budgetType, CBudgetNature budgetNature, BudgetCategory budgetCategory) {
        this.idBudget = idBudget;
        this.idBudgetCategory = idBudgetCategory;
        this.costCenter = costCenter;
        this.budgetType = budgetType;
        this.budgetNature = budgetNature;
        this.budgetCategory = budgetCategory;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Integer getIdBudgetCategory() {
        return idBudgetCategory;
    }

    public void setIdBudgetCategory(Integer idBudgetCategory) {
        this.idBudgetCategory = idBudgetCategory;
    }

    public CCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CCostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public CBudgetTypes getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(CBudgetTypes budgetType) {
        this.budgetType = budgetType;
    }

    public CBudgetNature getBudgetNature() {
        return budgetNature;
    }

    public void setBudgetNature(CBudgetNature budgetNature) {
        this.budgetNature = budgetNature;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;

        return idBudgetCategory != null ? idBudgetCategory.equals(budget.idBudgetCategory) : budget.idBudgetCategory == null;

    }

    @Override
    public int hashCode() {
        return idBudgetCategory != null ? idBudgetCategory.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "idBudget=" + idBudget +
                ", idBudgetCategory=" + idBudgetCategory +
                ", costCenter=" + costCenter +
                ", budgetType=" + budgetType +
                ", budgetNature=" + budgetNature +
                ", budgetCategory=" + budgetCategory +
                '}';
    }
}

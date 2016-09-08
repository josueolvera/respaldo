package mx.bidg.pojos;

import mx.bidg.model.BudgetYearConcept;
import mx.bidg.model.CBudgetNature;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.model.CCostCenter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetSubcategory {

    private Integer idBudget;
    private CCostCenter costCenter;
    private CBudgetTypes budgetType;
    private CBudgetNature budgetNature;
    private Integer idBudgetSubcategory;
    private String name;
    private BigDecimal totalSubcategoryAmount;
    private List<BudgetYearConcept> budgetYearConceptList;

    public BudgetSubcategory() {
    }

    public BudgetSubcategory(Integer idBudget, CCostCenter costCenter, CBudgetTypes budgetType, CBudgetNature budgetNature, Integer idBudgetSubcategory, String name) {
        this.idBudget = idBudget;
        this.costCenter = costCenter;
        this.budgetType = budgetType;
        this.budgetNature = budgetNature;
        this.idBudgetSubcategory = idBudgetSubcategory;
        this.name = name;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
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

    public List<BudgetYearConcept> getBudgetYearConceptList() {
        return budgetYearConceptList;
    }

    public void setBudgetYearConceptList(List<BudgetYearConcept> budgetYearConceptList) {
        this.budgetYearConceptList = budgetYearConceptList;
    }

    public BigDecimal getTotalSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getTotalAmount().doubleValue();
        }
        this.totalSubcategoryAmount = new BigDecimal(zero);
        return totalSubcategoryAmount;
    }

    public void setTotalSubcategoryAmount(BigDecimal totalSubcategoryAmount) {
        this.totalSubcategoryAmount = totalSubcategoryAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetSubcategory that = (BudgetSubcategory) o;

        return idBudget != null ? idBudget.equals(that.idBudget) : that.idBudget == null;

    }

    @Override
    public int hashCode() {
        return idBudget != null ? idBudget.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BudgetSubcategory{" +
                "idBudget=" + idBudget +
                ", costCenter=" + costCenter +
                ", budgetType=" + budgetType +
                ", budgetNature=" + budgetNature +
                ", idBudgetSubcategory=" + idBudgetSubcategory +
                ", name='" + name + '\'' +
                ", budgetYearConceptList=" + budgetYearConceptList +
                '}';
    }
}

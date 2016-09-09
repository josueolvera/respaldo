package mx.bidg.pojos;

import mx.bidg.model.BudgetYearConcept;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetCategory {
    private Integer idBudgetCategory;
    private String name;
    private BigDecimal januaryCategoryAmount;
    private BigDecimal februaryCategoryAmount;
    private BigDecimal marchCategoryAmount;
    private BigDecimal aprilCategoryAmount;
    private BigDecimal mayCategoryAmount;
    private BigDecimal juneCategoryAmount;
    private BigDecimal julyCategoryAmount;
    private BigDecimal augustCategoryAmount;
    private BigDecimal septemberCategoryAmount;
    private BigDecimal octoberCategoryAmount;
    private BigDecimal novemberCategoryAmount;
    private BigDecimal decemberCategoryAmount;
    private BigDecimal totalCategoryAmount;
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

    public BigDecimal getJanuaryCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getJanuarySubcategoryAmount().doubleValue();
        }
        this.januaryCategoryAmount = new BigDecimal(zero);
        return januaryCategoryAmount;
    }

    public void setJanuaryCategoryAmount(BigDecimal januaryCategoryAmount) {
        this.januaryCategoryAmount = januaryCategoryAmount;
    }

    public BigDecimal getFebruaryCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getFebruarySubcategoryAmount().doubleValue();
        }
        this.februaryCategoryAmount = new BigDecimal(zero);
        return februaryCategoryAmount;
    }

    public void setFebruaryCategoryAmount(BigDecimal februaryCategoryAmount) {
        this.februaryCategoryAmount = februaryCategoryAmount;
    }

    public BigDecimal getMarchCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getMarchSubcategoryAmount().doubleValue();
        }
        this.marchCategoryAmount = new BigDecimal(zero);
        return marchCategoryAmount;
    }

    public void setMarchCategoryAmount(BigDecimal marchCategoryAmount) {
        this.marchCategoryAmount = marchCategoryAmount;
    }

    public BigDecimal getAprilCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getAprilSubcategoryAmount().doubleValue();
        }
        this.aprilCategoryAmount = new BigDecimal(zero);
        return aprilCategoryAmount;
    }

    public void setAprilCategoryAmount(BigDecimal aprilCategoryAmount) {
        this.aprilCategoryAmount = aprilCategoryAmount;
    }

    public BigDecimal getMayCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getMaySubcategoryAmount().doubleValue();
        }
        this.mayCategoryAmount = new BigDecimal(zero);
        return mayCategoryAmount;
    }

    public void setMayCategoryAmount(BigDecimal mayCategoryAmount) {
        this.mayCategoryAmount = mayCategoryAmount;
    }

    public BigDecimal getJuneCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getJuneSubcategoryAmount().doubleValue();
        }
        this.juneCategoryAmount = new BigDecimal(zero);
        return juneCategoryAmount;
    }

    public void setJuneCategoryAmount(BigDecimal juneCategoryAmount) {
        this.juneCategoryAmount = juneCategoryAmount;
    }

    public BigDecimal getJulyCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getJulySubcategoryAmount().doubleValue();
        }
        this.julyCategoryAmount = new BigDecimal(zero);
        return julyCategoryAmount;
    }

    public void setJulyCategoryAmount(BigDecimal julyCategoryAmount) {
        this.julyCategoryAmount = julyCategoryAmount;
    }

    public BigDecimal getAugustCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getAugustSubcategoryAmount().doubleValue();
        }
        this.augustCategoryAmount = new BigDecimal(zero);
        return augustCategoryAmount;
    }

    public void setAugustCategoryAmount(BigDecimal augustCategoryAmount) {
        this.augustCategoryAmount = augustCategoryAmount;
    }

    public BigDecimal getSeptemberCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getSeptemberSubcategoryAmount().doubleValue();
        }
        this.septemberCategoryAmount = new BigDecimal(zero);
        return septemberCategoryAmount;
    }

    public void setSeptemberCategoryAmount(BigDecimal septemberCategoryAmount) {
        this.septemberCategoryAmount = septemberCategoryAmount;
    }

    public BigDecimal getOctoberCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getOctoberSubcategoryAmount().doubleValue();
        }
        this.octoberCategoryAmount = new BigDecimal(zero);
        return octoberCategoryAmount;
    }

    public void setOctoberCategoryAmount(BigDecimal octoberCategoryAmount) {
        this.octoberCategoryAmount = octoberCategoryAmount;
    }

    public BigDecimal getNovemberCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getNovemberSubcategoryAmount().doubleValue();
        }
        this.novemberCategoryAmount = new BigDecimal(zero);
        return novemberCategoryAmount;
    }

    public void setNovemberCategoryAmount(BigDecimal novemberCategoryAmount) {
        this.novemberCategoryAmount = novemberCategoryAmount;
    }

    public BigDecimal getDecemberCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getDecemberSubcategoryAmount().doubleValue();
        }
        this.decemberCategoryAmount = new BigDecimal(zero);
        return decemberCategoryAmount;
    }

    public void setDecemberCategoryAmount(BigDecimal decemberCategoryAmount) {
        this.decemberCategoryAmount = decemberCategoryAmount;
    }

    public BigDecimal getTotalCategoryAmount() {
        double zero = 0;
        for (BudgetSubcategory budgetSubcategory : getBudgetSubcategories()) {
            zero += budgetSubcategory.getTotalSubcategoryAmount().doubleValue();
        }
        this.totalCategoryAmount = new BigDecimal(zero);
        return totalCategoryAmount;
    }

    public void setTotalCategoryAmount(BigDecimal totalCategoryAmount) {
        this.totalCategoryAmount = totalCategoryAmount;
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

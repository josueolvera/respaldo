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
    private BigDecimal januarySubcategoryAmount;
    private BigDecimal februarySubcategoryAmount;
    private BigDecimal marchSubcategoryAmount;
    private BigDecimal aprilSubcategoryAmount;
    private BigDecimal maySubcategoryAmount;
    private BigDecimal juneSubcategoryAmount;
    private BigDecimal julySubcategoryAmount;
    private BigDecimal augustSubcategoryAmount;
    private BigDecimal septemberSubcategoryAmount;
    private BigDecimal octoberSubcategoryAmount;
    private BigDecimal novemberSubcategoryAmount;
    private BigDecimal decemberSubcategoryAmount;
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

    public BigDecimal getJanuarySubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJanuaryAmount().doubleValue();
        }
        this.januarySubcategoryAmount = new BigDecimal(zero);
        return januarySubcategoryAmount;
    }

    public void setJanuarySubcategoryAmount(BigDecimal januarySubcategoryAmount) {
        this.januarySubcategoryAmount = januarySubcategoryAmount;
    }

    public BigDecimal getFebruarySubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getFebruaryAmount().doubleValue();
        }
        this.februarySubcategoryAmount = new BigDecimal(zero);
        return februarySubcategoryAmount;
    }

    public void setFebruarySubcategoryAmount(BigDecimal februarySubcategoryAmount) {
        this.februarySubcategoryAmount = februarySubcategoryAmount;
    }

    public BigDecimal getMarchSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getMarchAmount().doubleValue();
        }
        this.marchSubcategoryAmount = new BigDecimal(zero);
        return marchSubcategoryAmount;
    }

    public void setMarchSubcategoryAmount(BigDecimal marchSubcategoryAmount) {
        this.marchSubcategoryAmount = marchSubcategoryAmount;
    }

    public BigDecimal getAprilSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getAprilAmount().doubleValue();
        }
        this.aprilSubcategoryAmount = new BigDecimal(zero);
        return aprilSubcategoryAmount;
    }

    public void setAprilSubcategoryAmount(BigDecimal aprilSubcategoryAmount) {
        this.aprilSubcategoryAmount = aprilSubcategoryAmount;
    }

    public BigDecimal getMaySubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getMayAmount().doubleValue();
        }
        this.maySubcategoryAmount = new BigDecimal(zero);
        return maySubcategoryAmount;
    }

    public void setMaySubcategoryAmount(BigDecimal maySubcategoryAmount) {
        this.maySubcategoryAmount = maySubcategoryAmount;
    }

    public BigDecimal getJuneSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJuneAmount().doubleValue();
        }
        this.juneSubcategoryAmount = new BigDecimal(zero);
        return juneSubcategoryAmount;
    }

    public void setJuneSubcategoryAmount(BigDecimal juneSubcategoryAmount) {
        this.juneSubcategoryAmount = juneSubcategoryAmount;
    }

    public BigDecimal getJulySubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJulyAmount().doubleValue();
        }
        this.julySubcategoryAmount = new BigDecimal(zero);
        return julySubcategoryAmount;
    }

    public void setJulySubcategoryAmount(BigDecimal julySubcategoryAmount) {
        this.julySubcategoryAmount = julySubcategoryAmount;
    }

    public BigDecimal getAugustSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getAugustAmount().doubleValue();
        }
        this.augustSubcategoryAmount = new BigDecimal(zero);
        return augustSubcategoryAmount;
    }

    public void setAugustSubcategoryAmount(BigDecimal augustSubcategoryAmount) {
        this.augustSubcategoryAmount = augustSubcategoryAmount;
    }

    public BigDecimal getSeptemberSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getSeptemberAmount().doubleValue();
        }
        this.septemberSubcategoryAmount = new BigDecimal(zero);
        return septemberSubcategoryAmount;
    }

    public void setSeptemberSubcategoryAmount(BigDecimal septemberSubcategoryAmount) {
        this.septemberSubcategoryAmount = septemberSubcategoryAmount;
    }

    public BigDecimal getOctoberSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getOctoberAmount().doubleValue();
        }
        this.octoberSubcategoryAmount = new BigDecimal(zero);
        return octoberSubcategoryAmount;
    }

    public void setOctoberSubcategoryAmount(BigDecimal octoberSubcategoryAmount) {
        this.octoberSubcategoryAmount = octoberSubcategoryAmount;
    }

    public BigDecimal getNovemberSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getNovemberAmount().doubleValue();
        }
        this.novemberSubcategoryAmount = new BigDecimal(zero);
        return novemberSubcategoryAmount;
    }

    public void setNovemberSubcategoryAmount(BigDecimal novemberSubcategoryAmount) {
        this.novemberSubcategoryAmount = novemberSubcategoryAmount;
    }

    public BigDecimal getDecemberSubcategoryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getDecemberAmount().doubleValue();
        }
        this.decemberSubcategoryAmount = new BigDecimal(zero);
        return decemberSubcategoryAmount;
    }

    public void setDecemberSubcategoryAmount(BigDecimal decemberSubcategoryAmount) {
        this.decemberSubcategoryAmount = decemberSubcategoryAmount;
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

    public List<BudgetYearConcept> getBudgetYearConceptList() {
        return budgetYearConceptList;
    }

    public void setBudgetYearConceptList(List<BudgetYearConcept> budgetYearConceptList) {
        this.budgetYearConceptList = budgetYearConceptList;
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

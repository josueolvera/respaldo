package mx.bidg.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollador on 27/03/2017.
 */
public class CostCenter {
    private Integer idCostCenter;
    private String name;
    private boolean show;
    private BigDecimal januaryAmount;
    private BigDecimal februaryAmount;
    private BigDecimal marchAmount;
    private BigDecimal aprilAmount;
    private BigDecimal mayAmount;
    private BigDecimal juneAmount;
    private BigDecimal julyAmount;
    private BigDecimal augustAmount;
    private BigDecimal septemberAmount;
    private BigDecimal octoberAmount;
    private BigDecimal novemberAmount;
    private BigDecimal decemberAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalBudgetAmountLastYear;
    private BigDecimal realTotalBudgetAmount;
    private List<BudgetCategory> budgetCategories;

    public CostCenter() {
        this.budgetCategories = new ArrayList<>();
        this.show = false;
    }

    public CostCenter(Integer idCostCenter, String name) {
        this.idCostCenter = idCostCenter;
        this.name = name;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getJanuaryAmount() {
        double zero = 0;

        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getJanuaryCategoryAmount().doubleValue();
            }
        }

        this.januaryAmount = new BigDecimal(zero);
        return januaryAmount;
    }

    public void setJanuaryAmount(BigDecimal januaryAmount) {
        this.januaryAmount = januaryAmount;
    }

    public BigDecimal getFebruaryAmount() {
        double zero = 0;

        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getFebruaryCategoryAmount().doubleValue();
            }
        }

        this.februaryAmount = new BigDecimal(zero);
        return februaryAmount;
    }

    public void setFebruaryAmount(BigDecimal februaryAmount) {
        this.februaryAmount = februaryAmount;
    }

    public BigDecimal getMarchAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getMarchCategoryAmount().doubleValue();
            }
        }
        this.marchAmount = new BigDecimal(zero);
        return marchAmount;
    }

    public void setMarchAmount(BigDecimal marchAmount) {
        this.marchAmount = marchAmount;
    }

    public BigDecimal getAprilAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getAprilCategoryAmount().doubleValue();
            }
        }
        this.aprilAmount = new BigDecimal(zero);
        return aprilAmount;
    }

    public void setAprilAmount(BigDecimal aprilAmount) {
        this.aprilAmount = aprilAmount;
    }

    public BigDecimal getMayAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getMayCategoryAmount().doubleValue();
            }
        }
        this.mayAmount = new BigDecimal(zero);
        return mayAmount;
    }

    public void setMayAmount(BigDecimal mayAmount) {
        this.mayAmount = mayAmount;
    }

    public BigDecimal getJuneAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getJuneCategoryAmount().doubleValue();
            }
        }
        this.juneAmount = new BigDecimal(zero);
        return juneAmount;
    }

    public void setJuneAmount(BigDecimal juneAmount) {
        this.juneAmount = juneAmount;
    }

    public BigDecimal getJulyAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getJulyCategoryAmount().doubleValue();
            }
        }
        this.julyAmount = new BigDecimal(zero);
        return julyAmount;
    }

    public void setJulyAmount(BigDecimal julyAmount) {
        this.julyAmount = julyAmount;
    }

    public BigDecimal getAugustAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getAugustCategoryAmount().doubleValue();
            }
        }
        this.augustAmount = new BigDecimal(zero);
        return augustAmount;
    }

    public void setAugustAmount(BigDecimal augustAmount) {
        this.augustAmount = augustAmount;
    }

    public BigDecimal getSeptemberAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getSeptemberCategoryAmount().doubleValue();
            }
        }
        this.septemberAmount = new BigDecimal(zero);
        return septemberAmount;
    }

    public void setSeptemberAmount(BigDecimal septemberAmount) {
        this.septemberAmount = septemberAmount;
    }

    public BigDecimal getOctoberAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getOctoberCategoryAmount().doubleValue();
            }
        }
        this.octoberAmount = new BigDecimal(zero);
        return octoberAmount;
    }

    public void setOctoberAmount(BigDecimal octoberAmount) {
        this.octoberAmount = octoberAmount;
    }

    public BigDecimal getNovemberAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getNovemberCategoryAmount().doubleValue();
            }
        }
        this.novemberAmount = new BigDecimal(zero);
        return novemberAmount;
    }

    public void setNovemberAmount(BigDecimal novemberAmount) {
        this.novemberAmount = novemberAmount;
    }

    public BigDecimal getDecemberAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getDecemberCategoryAmount().doubleValue();
            }
        }
        this.decemberAmount = new BigDecimal(zero);
        return decemberAmount;
    }

    public void setDecemberAmount(BigDecimal decemberAmount) {
        this.decemberAmount = decemberAmount;
    }

    public BigDecimal getTotalAmount() {
        double zero = 0;
        if (!getBudgetCategories().isEmpty()) {
            for (BudgetCategory budgetCategory : getBudgetCategories()) {
                zero += budgetCategory.getTotalCategoryAmount().doubleValue();
            }
        }
        this.totalAmount = new BigDecimal(zero);
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalBudgetAmountLastYear() {
        double zero = 0;

        if (!getBudgetCategories().isEmpty()){
            for(BudgetCategory budgetCategory : getBudgetCategories()){
                zero += budgetCategory.getTotalBudgetAmountLastYear().doubleValue();
            }
        }
        this.totalBudgetAmountLastYear = new BigDecimal(zero);
        return totalBudgetAmountLastYear;
    }

    public void setTotalBudgetAmountLastYear(BigDecimal totalBudgetAmountLastYear) {
        this.totalBudgetAmountLastYear = totalBudgetAmountLastYear;
    }

    public List<BudgetCategory> getBudgetCategories() {
        return budgetCategories;
    }

    public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
        this.budgetCategories = budgetCategories;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public BigDecimal getRealTotalBudgetAmount() {
        double zero = 0;

        if (!getBudgetCategories().isEmpty()){
            for (BudgetCategory budgetCategory : getBudgetCategories()){
                if (budgetCategory != null) {
                    if (budgetCategory.getRealTotalBudgetAmount() != null){
                        zero += budgetCategory.getRealTotalBudgetAmount().doubleValue();
                    }
                }
            }
        }

        this.realTotalBudgetAmount = new BigDecimal(zero);
        return realTotalBudgetAmount;
    }

    public void setRealTotalBudgetAmount(BigDecimal realTotalBudgetAmount) {
        this.realTotalBudgetAmount = realTotalBudgetAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CostCenter)) return false;

        CostCenter that = (CostCenter) o;

        return idCostCenter != null ? idCostCenter.equals(that.idCostCenter) : that.idCostCenter == null;

    }

    @Override
    public int hashCode() {
        return idCostCenter != null ? idCostCenter.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CostCenter{" +
                "idCostCenter=" + idCostCenter +
                ", name='" + name + '\'' +
                ", budgetCategories=" + budgetCategories +
                '}';
    }
}

package mx.bidg.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.bidg.model.RealBudgetSpending;
import mx.bidg.model.CBudgetNature;
import mx.bidg.model.CBudgetTypes;
import mx.bidg.model.CCostCenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo8 on 30/08/16.
 */
public class BudgetSubcategory {

    private Integer idBudgetSubcategory;
    private String name;
    private boolean show;
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
    private BigDecimal totalBudgetAmountLastYear;
    private BigDecimal realTotalBudgetAmount;
    private List<RealBudgetSpending> realBudgetSpendings;
    private List<BudgetSubSubCategory> budgetSubSubCategories;

    public BudgetSubcategory() {
        this.show = false;
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

    public BigDecimal getJanuarySubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null){
            if (!getRealBudgetSpendings().isEmpty()){
                for(RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()){
                    if (realBudgetSpending != null){
                        if (realBudgetSpending.getJanuaryBudgetAmount() != null){
                            zero += realBudgetSpending.getJanuaryBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null){
            if (!getBudgetSubSubCategories().isEmpty()){
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()){
                    if (budgetSubSubCategory != null){
                        if (budgetSubSubCategory.getJanuarySubSubcategoryAmount() != null){
                            zero += budgetSubSubCategory.getJanuarySubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }

        this.januarySubcategoryAmount = new BigDecimal(zero);
        return januarySubcategoryAmount;
    }

    public void setJanuarySubcategoryAmount(BigDecimal januarySubcategoryAmount) {
        this.januarySubcategoryAmount = januarySubcategoryAmount;
    }

    public BigDecimal getFebruarySubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getFebruaryBudgetAmount() != null) {
                            zero += realBudgetSpending.getFebruaryBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getFebruarySubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getFebruarySubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.februarySubcategoryAmount = new BigDecimal(zero);
        return februarySubcategoryAmount;
    }

    public void setFebruarySubcategoryAmount(BigDecimal februarySubcategoryAmount) {
        this.februarySubcategoryAmount = februarySubcategoryAmount;
    }

    public BigDecimal getMarchSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getMarchBudgetAmount() != null) {
                            zero += realBudgetSpending.getMarchBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getMarchSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getMarchSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.marchSubcategoryAmount = new BigDecimal(zero);
        return marchSubcategoryAmount;
    }

    public void setMarchSubcategoryAmount(BigDecimal marchSubcategoryAmount) {
        this.marchSubcategoryAmount = marchSubcategoryAmount;
    }

    public BigDecimal getAprilSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getAprilBudgetAmount() != null) {
                            zero += realBudgetSpending.getAprilBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getAprilSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getAprilSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.aprilSubcategoryAmount = new BigDecimal(zero);
        return aprilSubcategoryAmount;
    }

    public void setAprilSubcategoryAmount(BigDecimal aprilSubcategoryAmount) {
        this.aprilSubcategoryAmount = aprilSubcategoryAmount;
    }

    public BigDecimal getMaySubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getMayBudgetAmount() != null) {
                            zero += realBudgetSpending.getMayBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getMaySubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getMaySubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.maySubcategoryAmount = new BigDecimal(zero);
        return maySubcategoryAmount;
    }

    public void setMaySubcategoryAmount(BigDecimal maySubcategoryAmount) {
        this.maySubcategoryAmount = maySubcategoryAmount;
    }

    public BigDecimal getJuneSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getJuneBudgetAmount() != null) {
                            zero += realBudgetSpending.getJuneBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getJuneSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getJuneSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.juneSubcategoryAmount = new BigDecimal(zero);
        return juneSubcategoryAmount;
    }

    public void setJuneSubcategoryAmount(BigDecimal juneSubcategoryAmount) {
        this.juneSubcategoryAmount = juneSubcategoryAmount;
    }

    public BigDecimal getJulySubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getJulyBudgetAmount() != null) {
                            zero += realBudgetSpending.getJulyBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getJulySubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getJulySubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.julySubcategoryAmount = new BigDecimal(zero);
        return julySubcategoryAmount;
    }

    public void setJulySubcategoryAmount(BigDecimal julySubcategoryAmount) {
        this.julySubcategoryAmount = julySubcategoryAmount;
    }

    public BigDecimal getAugustSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getAugustBudgetAmount() != null) {
                            zero += realBudgetSpending.getAugustBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getAugustSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getAugustSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.augustSubcategoryAmount = new BigDecimal(zero);
        return augustSubcategoryAmount;
    }

    public void setAugustSubcategoryAmount(BigDecimal augustSubcategoryAmount) {
        this.augustSubcategoryAmount = augustSubcategoryAmount;
    }

    public BigDecimal getSeptemberSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getSeptemberBudgetAmount() != null) {
                            zero += realBudgetSpending.getSeptemberBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getSeptemberSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getSeptemberSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.septemberSubcategoryAmount = new BigDecimal(zero);
        return septemberSubcategoryAmount;
    }

    public void setSeptemberSubcategoryAmount(BigDecimal septemberSubcategoryAmount) {
        this.septemberSubcategoryAmount = septemberSubcategoryAmount;
    }

    public BigDecimal getOctoberSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getOctoberBudgetAmount() != null) {
                            zero += realBudgetSpending.getOctoberBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getOctoberSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getOctoberSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.octoberSubcategoryAmount = new BigDecimal(zero);
        return octoberSubcategoryAmount;
    }

    public void setOctoberSubcategoryAmount(BigDecimal octoberSubcategoryAmount) {
        this.octoberSubcategoryAmount = octoberSubcategoryAmount;
    }

    public BigDecimal getNovemberSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getNovemberBudgetAmount() != null) {
                            zero += realBudgetSpending.getNovemberBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getNovemberSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getNovemberSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.novemberSubcategoryAmount = new BigDecimal(zero);
        return novemberSubcategoryAmount;
    }

    public void setNovemberSubcategoryAmount(BigDecimal novemberSubcategoryAmount) {
        this.novemberSubcategoryAmount = novemberSubcategoryAmount;
    }

    public BigDecimal getDecemberSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getDecemberBudgetAmount() != null) {
                            zero += realBudgetSpending.getDecemberBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getDecemberSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getDecemberSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.decemberSubcategoryAmount = new BigDecimal(zero);
        return decemberSubcategoryAmount;
    }

    public void setDecemberSubcategoryAmount(BigDecimal decemberSubcategoryAmount) {
        this.decemberSubcategoryAmount = decemberSubcategoryAmount;
    }

    public BigDecimal getTotalSubcategoryAmount() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (getRealBudgetSpendings() != null) {
                if (!getRealBudgetSpendings().isEmpty()) {
                    for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                        if (realBudgetSpending != null) {
                            if (realBudgetSpending.getTotalBudgetAmount() != null) {
                                zero += realBudgetSpending.getTotalBudgetAmount().doubleValue();
                            }
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getTotalSubSubcategoryAmount() != null) {
                            zero += budgetSubSubCategory.getTotalSubSubcategoryAmount().doubleValue();
                        }
                    }
                }
            }
        }
        this.totalSubcategoryAmount = new BigDecimal(zero);
        return totalSubcategoryAmount;
    }

    public void setTotalSubcategoryAmount(BigDecimal totalSubcategoryAmount) {
        this.totalSubcategoryAmount = totalSubcategoryAmount;
    }

    public BigDecimal getTotalBudgetAmountLastYear() {
        double zero = 0;
        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getTotalLastYearAmount() != null) {
                            zero += realBudgetSpending.getTotalLastYearAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getTotalBudgetAmountLastYear() != null) {
                            zero += budgetSubSubCategory.getTotalBudgetAmountLastYear().doubleValue();
                        }
                    }
                }
            }
        }
        this.totalBudgetAmountLastYear = new BigDecimal(zero);
        return totalBudgetAmountLastYear;
    }

    public void setTotalBudgetAmountLastYear(BigDecimal totalBudgetAmountLastYear) {
        this.totalBudgetAmountLastYear = totalBudgetAmountLastYear;
    }

    public List<RealBudgetSpending> getRealBudgetSpendings() {
        return realBudgetSpendings;
    }

    public void setRealBudgetSpendings(List<RealBudgetSpending> realBudgetSpendings) {
        this.realBudgetSpendings = realBudgetSpendings;
    }

    public List<BudgetSubSubCategory> getBudgetSubSubCategories() {
        return budgetSubSubCategories;
    }

    public void setBudgetSubSubCategories(List<BudgetSubSubCategory> budgetSubSubCategories) {
        this.budgetSubSubCategories = budgetSubSubCategories;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public BigDecimal getRealTotalBudgetAmount() {
        double zero = 0;

        if (getRealBudgetSpendings() != null) {
            if (!getRealBudgetSpendings().isEmpty()) {
                for (RealBudgetSpending realBudgetSpending : getRealBudgetSpendings()) {
                    if (realBudgetSpending != null) {
                        if (realBudgetSpending.getRealTotalBudgetAmount() != null) {
                            zero += realBudgetSpending.getRealTotalBudgetAmount().doubleValue();
                        }
                    }
                }
            }
        }

        if (getBudgetSubSubCategories() != null) {
            if (!getBudgetSubSubCategories().isEmpty()) {
                for (BudgetSubSubCategory budgetSubSubCategory : getBudgetSubSubCategories()) {
                    if (budgetSubSubCategory != null) {
                        if (budgetSubSubCategory.getRealTotalBudgetAmount() != null) {
                            zero += budgetSubSubCategory.getRealTotalBudgetAmount().doubleValue();
                        }
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
        if (!(o instanceof BudgetSubcategory)) return false;

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
                ", realBudgetSpendings=" + realBudgetSpendings +
                ", budgetSubSubCategories=" + budgetSubSubCategories +
                '}';
    }
}

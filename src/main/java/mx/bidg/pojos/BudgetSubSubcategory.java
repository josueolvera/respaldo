package mx.bidg.pojos;

import mx.bidg.model.RealBudgetSpending;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kevin Salvador on 24/03/2017.
 */
public class BudgetSubSubcategory {
    private BigDecimal januaryBudgetAmount;
    private BigDecimal februaryBudgetAmount;
    private BigDecimal marchBudgetAmount;
    private BigDecimal aprilBudgetAmount;
    private BigDecimal mayBudgetAmount;
    private BigDecimal juneBudgetAmount;
    private BigDecimal julyBudgetAmount;
    private BigDecimal augustBudgetAmount;
    private BigDecimal septemberBudgetAmount;
    private BigDecimal octoberBudgetAmount;
    private BigDecimal novemberBudgetAmount;
    private BigDecimal decemberBudgetAmount;
    private BigDecimal totalBudgetAmount;
    private List<RealBudgetSpending> findLevel;

    public BigDecimal getJanuaryBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getJanuaryBudgetAmount().doubleValue();
        }
        this.januaryBudgetAmount = new BigDecimal(zero);
        return januaryBudgetAmount;
    }

    public void setJanuaryBudgetAmount(BigDecimal januaryBudgetAmount) {
        this.januaryBudgetAmount = januaryBudgetAmount;
    }

    public BigDecimal getFebruaryBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getFebruaryBudgetAmount().doubleValue();
        }
        this.februaryBudgetAmount = new BigDecimal(zero);
        return februaryBudgetAmount;
    }

    public void setFebruaryBudgetAmount(BigDecimal februaryBudgetAmount) {
        this.februaryBudgetAmount = februaryBudgetAmount;
    }

    public BigDecimal getMarchBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getMarchBudgetAmount().doubleValue();
        }
        this.marchBudgetAmount = new BigDecimal(zero);
        return marchBudgetAmount;
    }

    public void setMarchBudgetAmount(BigDecimal marchBudgetAmount) {
        this.marchBudgetAmount = marchBudgetAmount;
    }

    public BigDecimal getAprilBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getAprilBudgetAmount().doubleValue();
        }
        this.aprilBudgetAmount = new BigDecimal(zero);
        return aprilBudgetAmount;
    }

    public void setAprilBudgetAmount(BigDecimal aprilBudgetAmount) {
        this.aprilBudgetAmount = aprilBudgetAmount;
    }

    public BigDecimal getMayBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getMayBudgetAmount().doubleValue();
        }
        this.mayBudgetAmount = new BigDecimal(zero);
        return mayBudgetAmount;
    }

    public void setMayBudgetAmount(BigDecimal mayBudgetAmount) {
        this.mayBudgetAmount = mayBudgetAmount;
    }

    public BigDecimal getJuneBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getJuneBudgetAmount().doubleValue();
        }
        this.juneBudgetAmount = new BigDecimal(zero);
        return juneBudgetAmount;
    }

    public void setJuneBudgetAmount(BigDecimal juneBudgetAmount) {
        this.juneBudgetAmount = juneBudgetAmount;
    }

    public BigDecimal getJulyBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getJulyBudgetAmount().doubleValue();
        }
        this.julyBudgetAmount = new BigDecimal(zero);
        return julyBudgetAmount;
    }

    public void setJulyBudgetAmount(BigDecimal julyBudgetAmount) {
        this.julyBudgetAmount = julyBudgetAmount;
    }

    public BigDecimal getAugustBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getAugustBudgetAmount().doubleValue();
        }
        this.augustBudgetAmount = new BigDecimal(zero);
        return augustBudgetAmount;
    }

    public void setAugustBudgetAmount(BigDecimal augustBudgetAmount) {
        this.augustBudgetAmount = augustBudgetAmount;
    }

    public BigDecimal getSeptemberBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getSeptemberBudgetAmount().doubleValue();
        }
        this.septemberBudgetAmount = new BigDecimal(zero);
        return septemberBudgetAmount;
    }

    public void setSeptemberBudgetAmount(BigDecimal septemberBudgetAmount) {
        this.septemberBudgetAmount = septemberBudgetAmount;
    }

    public BigDecimal getOctoberBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getOctoberBudgetAmount().doubleValue();
        }
        this.octoberBudgetAmount = new BigDecimal(zero);
        return octoberBudgetAmount;
    }

    public void setOctoberBudgetAmount(BigDecimal octoberBudgetAmount) {
        this.octoberBudgetAmount = octoberBudgetAmount;
    }

    public BigDecimal getNovemberBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getNovemberBudgetAmount().doubleValue();
        }
        this.novemberBudgetAmount = new BigDecimal(zero);
        return novemberBudgetAmount;
    }

    public void setNovemberBudgetAmount(BigDecimal novemberBudgetAmount) {
        this.novemberBudgetAmount = novemberBudgetAmount;
    }

    public BigDecimal getDecemberBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getDecemberBudgetAmount().doubleValue();
        }
        this.decemberBudgetAmount = new BigDecimal(zero);
        return decemberBudgetAmount;
    }

    public void setDecemberBudgetAmount(BigDecimal decemberBudgetAmount) {
        this.decemberBudgetAmount = decemberBudgetAmount;
    }

    public BigDecimal getTotalBudgetAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevel()){
            zero += r.getTotalBudgetAmount().doubleValue();
        }
        this.totalBudgetAmount = new BigDecimal(zero);
        return totalBudgetAmount;
    }

    public void setTotalBudgetAmount(BigDecimal totalBudgetAmount) {
        this.totalBudgetAmount = totalBudgetAmount;
    }

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

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
    private BigDecimal januaryBeforeAmount;
    private BigDecimal februaryBeforeAmount;
    private BigDecimal marchBeforeAmount;
    private BigDecimal aprilBeforeAmount;
    private BigDecimal mayBeforeAmount;
    private BigDecimal juneBeforeAmount;
    private BigDecimal julyBeforeAmount;
    private BigDecimal augustBeforeAmount;
    private BigDecimal septemberBeforeAmount;
    private BigDecimal octoberBeforeAmount;
    private BigDecimal novemberBeforeAmount;
    private BigDecimal decemberBeforeAmount;
    private BigDecimal totalBeforeAmount;
    private List<RealBudgetSpending> findLevel;
    private List<RealBudgetSpending> findLevelBeforeYear;

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

    public BigDecimal getJanuaryBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getJanuaryBudgetAmount().doubleValue();
        }
        this.januaryBeforeAmount = new BigDecimal(zero);
        return januaryBeforeAmount;
    }

    public void setJanuaryBeforeAmount(BigDecimal januaryBeforeAmount) {
        this.januaryBeforeAmount = januaryBeforeAmount;
    }

    public BigDecimal getFebruaryBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getFebruaryBudgetAmount().doubleValue();
        }
        this.februaryBeforeAmount = new BigDecimal(zero);
        return februaryBeforeAmount;
    }

    public void setFebruaryBeforeAmount(BigDecimal februaryBeforeAmount) {
        this.februaryBeforeAmount = februaryBeforeAmount;
    }

    public BigDecimal getMarchBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getMarchBudgetAmount().doubleValue();
        }
        this.marchBeforeAmount = new BigDecimal(zero);
        return marchBeforeAmount;
    }

    public void setMarchBeforeAmount(BigDecimal marchBeforeAmount) {
        this.marchBeforeAmount = marchBeforeAmount;
    }

    public BigDecimal getAprilBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getAprilBudgetAmount().doubleValue();
        }
        this.aprilBeforeAmount = new BigDecimal(zero);
        return aprilBeforeAmount;
    }

    public void setAprilBeforeAmount(BigDecimal aprilBeforeAmount) {
        this.aprilBeforeAmount = aprilBeforeAmount;
    }

    public BigDecimal getMayBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getMayBudgetAmount().doubleValue();
        }
        this.mayBeforeAmount = new BigDecimal(zero);
        return mayBeforeAmount;
    }

    public void setMayBeforeAmount(BigDecimal mayBeforeAmount) {
        this.mayBeforeAmount = mayBeforeAmount;
    }

    public BigDecimal getJuneBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getJuneBudgetAmount().doubleValue();
        }
        this.juneBeforeAmount = new BigDecimal(zero);
        return juneBeforeAmount;
    }

    public void setJuneBeforeAmount(BigDecimal juneBeforeAmount) {
        this.juneBeforeAmount = juneBeforeAmount;
    }

    public BigDecimal getJulyBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getJulyBudgetAmount().doubleValue();
        }
        this.julyBeforeAmount = new BigDecimal(zero);
        return julyBeforeAmount;
    }

    public void setJulyBeforeAmount(BigDecimal julyBeforeAmount) {
        this.julyBeforeAmount = julyBeforeAmount;
    }

    public BigDecimal getAugustBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getAugustBudgetAmount().doubleValue();
        }
        this.augustBeforeAmount = new BigDecimal(zero);
        return augustBeforeAmount;
    }

    public void setAugustBeforeAmount(BigDecimal augustBeforeAmount) {
        this.augustBeforeAmount = augustBeforeAmount;
    }

    public BigDecimal getSeptemberBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getSeptemberBudgetAmount().doubleValue();
        }
        this.septemberBeforeAmount = new BigDecimal(zero);
        return septemberBeforeAmount;
    }

    public void setSeptemberBeforeAmount(BigDecimal septemberBeforeAmount) {
        this.septemberBeforeAmount = septemberBeforeAmount;
    }

    public BigDecimal getOctoberBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getOctoberBudgetAmount().doubleValue();
        }
        this.octoberBeforeAmount = new BigDecimal(zero);
        return octoberBeforeAmount;
    }

    public void setOctoberBeforeAmount(BigDecimal octoberBeforeAmount) {
        this.octoberBeforeAmount = octoberBeforeAmount;
    }

    public BigDecimal getNovemberBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getNovemberBudgetAmount().doubleValue();
        }
        this.novemberBeforeAmount = new BigDecimal(zero);
        return novemberBeforeAmount;
    }

    public void setNovemberBeforeAmount(BigDecimal novemberBeforeAmount) {
        this.novemberBeforeAmount = novemberBeforeAmount;
    }

    public BigDecimal getDecemberBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getDecemberBudgetAmount().doubleValue();
        }
        this.decemberBeforeAmount = new BigDecimal(zero);
        return decemberBeforeAmount;
    }

    public void setDecemberBeforeAmount(BigDecimal decemberBeforeAmount) {
        this.decemberBeforeAmount = decemberBeforeAmount;
    }

    public BigDecimal getTotalBeforeAmount() {
        double zero=0;
        for(RealBudgetSpending r: getFindLevelBeforeYear()){
            zero += r.getTotalBudgetAmount().doubleValue();
        }
        this.totalBeforeAmount = new BigDecimal(zero);
        return totalBeforeAmount;
    }

    public void setTotalBeforeAmount(BigDecimal totalBeforeAmount) {
        this.totalBeforeAmount = totalBeforeAmount;
    }

    public List<RealBudgetSpending> getFindLevel() {
        return findLevel;
    }

    public void setFindLevel(List<RealBudgetSpending> findLevel) {
        this.findLevel = findLevel;
    }

    public List<RealBudgetSpending> getFindLevelBeforeYear() {
        return findLevelBeforeYear;
    }

    public void setFindLevelBeforeYear(List<RealBudgetSpending> findLevelBeforeYear) {
        this.findLevelBeforeYear = findLevelBeforeYear;
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

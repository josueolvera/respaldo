package mx.bidg.pojos;

import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.RealBudgetSpending;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Serch on 06/07/2017.
 */
public class AccountingAccountPojo {
    private Integer idAccountingAcount;
    private String acronym;
    private AccountingAccounts accountingAccounts;
    private BigDecimal januarySubSubcategoryAmount;
    private BigDecimal februarySubSubcategoryAmount;
    private BigDecimal marchSubSubcategoryAmount;
    private BigDecimal aprilSubSubcategoryAmount;
    private BigDecimal maySubSubcategoryAmount;
    private BigDecimal juneSubSubcategoryAmount;
    private BigDecimal julySubSubcategoryAmount;
    private BigDecimal augustSubSubcategoryAmount;
    private BigDecimal septemberSubSubcategoryAmount;
    private BigDecimal octoberSubSubcategoryAmount;
    private BigDecimal novemberSubSubcategoryAmount;
    private BigDecimal decemberSubSubcategoryAmount;
    private BigDecimal totalSubSubcategoryAmount;
    private List<RealBudgetSpending> realBudgetSpendingList;

    public AccountingAccountPojo() {
    }

    public AccountingAccountPojo(Integer idAccountingAcount) {
        this.idAccountingAcount = idAccountingAcount;
    }

    public Integer getIdAccountingAcount() {
        return idAccountingAcount;
    }

    public void setIdAccountingAcount(Integer idAccountingAcount) {
        this.idAccountingAcount = idAccountingAcount;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public AccountingAccounts getAccountingAccounts() {
        return accountingAccounts;
    }

    public void setAccountingAccounts(AccountingAccounts accountingAccounts) {
        this.accountingAccounts = accountingAccounts;
    }

    public BigDecimal getJanuarySubSubcategoryAmount() {
        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getJanuaryBudgetAmount().doubleValue();
        }
        this.januarySubSubcategoryAmount = new BigDecimal(zero);
        return januarySubSubcategoryAmount;
    }

    public void setJanuarySubSubcategoryAmount(BigDecimal januarySubSubcategoryAmount) {
        this.januarySubSubcategoryAmount = januarySubSubcategoryAmount;
    }

    public BigDecimal getFebruarySubSubcategoryAmount() {
        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getFebruaryBudgetAmount().doubleValue();
        }
        this.februarySubSubcategoryAmount = new BigDecimal(zero);
        return februarySubSubcategoryAmount;
    }

    public void setFebruarySubSubcategoryAmount(BigDecimal februarySubSubcategoryAmount) {
        this.februarySubSubcategoryAmount = februarySubSubcategoryAmount;
    }

    public BigDecimal getMarchSubSubcategoryAmount() {
        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getMarchBudgetAmount().doubleValue();
        }
        this.marchSubSubcategoryAmount = new BigDecimal(zero);
        return marchSubSubcategoryAmount;
    }

    public void setMarchSubSubcategoryAmount(BigDecimal marchSubSubcategoryAmount) {
        this.marchSubSubcategoryAmount = marchSubSubcategoryAmount;
    }

    public BigDecimal getAprilSubSubcategoryAmount() {

        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getAprilBudgetAmount().doubleValue();
        }
        this.aprilSubSubcategoryAmount = new BigDecimal(zero);
        return aprilSubSubcategoryAmount;
    }

    public void setAprilSubSubcategoryAmount(BigDecimal aprilSubSubcategoryAmount) {
        this.aprilSubSubcategoryAmount = aprilSubSubcategoryAmount;
    }

    public BigDecimal getMaySubSubcategoryAmount() {

        double zero = 0;
        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getMayBudgetAmount().doubleValue();
        }

        this.maySubSubcategoryAmount = new BigDecimal(zero);
        return maySubSubcategoryAmount;
    }

    public void setMaySubSubcategoryAmount(BigDecimal maySubSubcategoryAmount) {
        this.maySubSubcategoryAmount = maySubSubcategoryAmount;
    }

    public BigDecimal getJuneSubSubcategoryAmount() {

        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getJuneBudgetAmount().doubleValue();
        }
        this.juneSubSubcategoryAmount = new BigDecimal(zero);
        return juneSubSubcategoryAmount;
    }

    public void setJuneSubSubcategoryAmount(BigDecimal juneSubSubcategoryAmount) {
        this.juneSubSubcategoryAmount = juneSubSubcategoryAmount;
    }

    public BigDecimal getJulySubSubcategoryAmount() {

        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getJulyBudgetAmount().doubleValue();
        }
        this.julySubSubcategoryAmount = new BigDecimal(zero);
        return julySubSubcategoryAmount;
    }

    public void setJulySubSubcategoryAmount(BigDecimal julySubSubcategoryAmount) {
        this.julySubSubcategoryAmount = julySubSubcategoryAmount;
    }

    public BigDecimal getAugustSubSubcategoryAmount() {

        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getAugustBudgetAmount().doubleValue();
        }
        this.augustSubSubcategoryAmount = new BigDecimal(zero);
        return augustSubSubcategoryAmount;
    }

    public void setAugustSubSubcategoryAmount(BigDecimal augustSubSubcategoryAmount) {
        this.augustSubSubcategoryAmount = augustSubSubcategoryAmount;
    }

    public BigDecimal getSeptemberSubSubcategoryAmount() {

        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getSeptemberBudgetAmount().doubleValue();
        }
        this.septemberSubSubcategoryAmount = new BigDecimal(zero);
        return septemberSubSubcategoryAmount;
    }

    public void setSeptemberSubSubcategoryAmount(BigDecimal septemberSubSubcategoryAmount) {
        this.septemberSubSubcategoryAmount = septemberSubSubcategoryAmount;
    }

    public BigDecimal getOctoberSubSubcategoryAmount() {

        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getOctoberBudgetAmount().doubleValue();
        }
        this.octoberSubSubcategoryAmount = new BigDecimal(zero);
        return octoberSubSubcategoryAmount;
    }

    public void setOctoberSubSubcategoryAmount(BigDecimal octoberSubSubcategoryAmount) {
        this.octoberSubSubcategoryAmount = octoberSubSubcategoryAmount;
    }

    public BigDecimal getNovemberSubSubcategoryAmount() {
        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getNovemberBudgetAmount().doubleValue();
        }

        this.novemberSubSubcategoryAmount = new BigDecimal(zero);
        return novemberSubSubcategoryAmount;
    }

    public void setNovemberSubSubcategoryAmount(BigDecimal novemberSubSubcategoryAmount) {
        this.novemberSubSubcategoryAmount = novemberSubSubcategoryAmount;
    }

    public BigDecimal getDecemberSubSubcategoryAmount() {
        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getDecemberBudgetAmount().doubleValue();
        }
        this.decemberSubSubcategoryAmount = new BigDecimal(zero);
        return decemberSubSubcategoryAmount;
    }

    public void setDecemberSubSubcategoryAmount(BigDecimal decemberSubSubcategoryAmount) {
        this.decemberSubSubcategoryAmount = decemberSubSubcategoryAmount;
    }

    public BigDecimal getTotalSubSubcategoryAmount() {
        double zero = 0;

        for (RealBudgetSpending r : getRealBudgetSpendingList()) {
            zero += r.getTotalBudgetAmount().doubleValue();
        }
        this.totalSubSubcategoryAmount = new BigDecimal(zero);
        return totalSubSubcategoryAmount;
    }

    public void setTotalSubSubcategoryAmount(BigDecimal totalSubSubcategoryAmount) {
        this.totalSubSubcategoryAmount = totalSubSubcategoryAmount;
    }

    public List<RealBudgetSpending> getRealBudgetSpendingList() {
        return realBudgetSpendingList;
    }

    public void setRealBudgetSpendingList(List<RealBudgetSpending> realBudgetSpendingList) {
        this.realBudgetSpendingList = realBudgetSpendingList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountingAccountPojo that = (AccountingAccountPojo) o;

        return idAccountingAcount != null ? idAccountingAcount.equals(that.idAccountingAcount) : that.idAccountingAcount == null;
    }

    @Override
    public int hashCode() {
        return idAccountingAcount != null ? idAccountingAcount.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AccountingAccountPojo{" +
                "idAccountingAcount=" + idAccountingAcount +
                ", acronym='" + acronym + '\'' +
                ", accountingAccounts=" + accountingAccounts +
                ", realBudgetSpendingList=" + realBudgetSpendingList +
                '}';
    }
}

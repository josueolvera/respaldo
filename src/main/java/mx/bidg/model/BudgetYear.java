package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 29/09/16.
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_YEAR")
public class BudgetYear implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_YEAR")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetYear;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JANUARY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FEBRUARY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MARCH_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "APRIL_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JUNE_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JULY_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AUGUST_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SEPTEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OCTOBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NOVEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DECEMBER_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberAmount;

    @Transient
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalAmount;

    @Column(name = "JANUARY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryExpendedAmount;

    @Column(name = "FEBRUARY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryExpendedAmount;

    @Column(name = "MARCH_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchExpendedAmount;

    @Column(name = "APRIL_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilExpendedAmount;

    @Column(name = "MAY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayExpendedAmount;

    @Column(name = "JUNE_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneExpendedAmount;

    @Column(name = "JULY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyExpendedAmount;

    @Column(name = "AUGUST_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustExpendedAmount;

    @Column(name = "SEPTEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberExpendedAmount;

    @Column(name = "OCTOBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberExpendedAmount;

    @Column(name = "NOVEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberExpendedAmount;

    @Column(name = "DECEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberExpendedAmount;

    @Transient
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalExpendedAmount;

    @Column(name = "ID_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private Integer year;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private Budgets budget;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetYear")
    @JsonView(JsonViews.Embedded.class)
    private List<Requests> requestsList;

    public BudgetYear() {
        this.januaryExpendedAmount = BigDecimal.ZERO;
        this.februaryExpendedAmount = BigDecimal.ZERO;
        this.marchExpendedAmount = BigDecimal.ZERO;
        this.aprilExpendedAmount = BigDecimal.ZERO;
        this.mayExpendedAmount = BigDecimal.ZERO;
        this.juneExpendedAmount = BigDecimal.ZERO;
        this.julyExpendedAmount = BigDecimal.ZERO;
        this.augustExpendedAmount = BigDecimal.ZERO;
        this.septemberExpendedAmount = BigDecimal.ZERO;
        this.octoberExpendedAmount = BigDecimal.ZERO;
        this.novemberExpendedAmount = BigDecimal.ZERO;
        this.decemberExpendedAmount = BigDecimal.ZERO;
    }

    public BudgetYear(int year, Budgets budget, CCurrencies currency) {
        this.year = year;
        this.budget = budget;
        this.currency = currency;
        this.januaryExpendedAmount = BigDecimal.ZERO;
        this.februaryExpendedAmount = BigDecimal.ZERO;
        this.marchExpendedAmount = BigDecimal.ZERO;
        this.aprilExpendedAmount = BigDecimal.ZERO;
        this.mayExpendedAmount = BigDecimal.ZERO;
        this.juneExpendedAmount = BigDecimal.ZERO;
        this.julyExpendedAmount = BigDecimal.ZERO;
        this.augustExpendedAmount = BigDecimal.ZERO;
        this.septemberExpendedAmount = BigDecimal.ZERO;
        this.octoberExpendedAmount = BigDecimal.ZERO;
        this.novemberExpendedAmount = BigDecimal.ZERO;
        this.decemberExpendedAmount = BigDecimal.ZERO;
    }

    public Integer getIdBudgetYear() {
        return idBudgetYear;
    }

    public void setIdBudgetYear(Integer idBudgetYear) {
        this.idBudgetYear = idBudgetYear;
    }

    public BigDecimal getJanuaryAmount() {
        return januaryAmount;
    }

    public void setJanuaryAmount(BigDecimal januaryAmount) {
        this.januaryAmount = januaryAmount;
    }

    public BigDecimal getFebruaryAmount() {
        return februaryAmount;
    }

    public void setFebruaryAmount(BigDecimal februaryAmount) {
        this.februaryAmount = februaryAmount;
    }

    public BigDecimal getMarchAmount() {
        return marchAmount;
    }

    public void setMarchAmount(BigDecimal marchAmount) {
        this.marchAmount = marchAmount;
    }

    public BigDecimal getAprilAmount() {
        return aprilAmount;
    }

    public void setAprilAmount(BigDecimal aprilAmount) {
        this.aprilAmount = aprilAmount;
    }

    public BigDecimal getMayAmount() {
        return mayAmount;
    }

    public void setMayAmount(BigDecimal mayAmount) {
        this.mayAmount = mayAmount;
    }

    public BigDecimal getJuneAmount() {
        return juneAmount;
    }

    public void setJuneAmount(BigDecimal juneAmount) {
        this.juneAmount = juneAmount;
    }

    public BigDecimal getJulyAmount() {
        return julyAmount;
    }

    public void setJulyAmount(BigDecimal julyAmount) {
        this.julyAmount = julyAmount;
    }

    public BigDecimal getAugustAmount() {
        return augustAmount;
    }

    public void setAugustAmount(BigDecimal augustAmount) {
        this.augustAmount = augustAmount;
    }

    public BigDecimal getSeptemberAmount() {
        return septemberAmount;
    }

    public void setSeptemberAmount(BigDecimal septemberAmount) {
        this.septemberAmount = septemberAmount;
    }

    public BigDecimal getOctoberAmount() {
        return octoberAmount;
    }

    public void setOctoberAmount(BigDecimal octoberAmount) {
        this.octoberAmount = octoberAmount;
    }

    public BigDecimal getNovemberAmount() {
        return novemberAmount;
    }

    public void setNovemberAmount(BigDecimal novemberAmount) {
        this.novemberAmount = novemberAmount;
    }

    public BigDecimal getDecemberAmount() {
        return decemberAmount;
    }

    public void setDecemberAmount(BigDecimal decemberAmount) {
        this.decemberAmount = decemberAmount;
    }

    public BigDecimal getTotalAmount() {
        this.totalAmount = BigDecimal.ZERO;
        this.totalAmount = this.totalAmount
                .add(this.januaryAmount)
                .add(this.februaryAmount)
                .add(this.marchAmount)
                .add(this.aprilAmount)
                .add(this.mayAmount)
                .add(this.juneAmount)
                .add(this.julyAmount)
                .add(this.augustAmount)
                .add(this.septemberAmount)
                .add(this.octoberAmount)
                .add(this.novemberAmount)
                .add(this.decemberAmount);
        return totalAmount;
    }

    public BigDecimal getJanuaryExpendedAmount() {
        return januaryExpendedAmount;
    }

    public void setJanuaryExpendedAmount(BigDecimal januaryExpendedAmount) {
        this.januaryExpendedAmount = januaryExpendedAmount;
    }

    public BigDecimal getFebruaryExpendedAmount() {
        return februaryExpendedAmount;
    }

    public void setFebruaryExpendedAmount(BigDecimal februaryExpendedAmount) {
        this.februaryExpendedAmount = februaryExpendedAmount;
    }

    public BigDecimal getMarchExpendedAmount() {
        return marchExpendedAmount;
    }

    public void setMarchExpendedAmount(BigDecimal marchExpendedAmount) {
        this.marchExpendedAmount = marchExpendedAmount;
    }

    public BigDecimal getAprilExpendedAmount() {
        return aprilExpendedAmount;
    }

    public void setAprilExpendedAmount(BigDecimal aprilExpendedAmount) {
        this.aprilExpendedAmount = aprilExpendedAmount;
    }

    public BigDecimal getMayExpendedAmount() {
        return mayExpendedAmount;
    }

    public void setMayExpendedAmount(BigDecimal mayExpendedAmount) {
        this.mayExpendedAmount = mayExpendedAmount;
    }

    public BigDecimal getJuneExpendedAmount() {
        return juneExpendedAmount;
    }

    public void setJuneExpendedAmount(BigDecimal juneExpendedAmount) {
        this.juneExpendedAmount = juneExpendedAmount;
    }

    public BigDecimal getJulyExpendedAmount() {
        return julyExpendedAmount;
    }

    public void setJulyExpendedAmount(BigDecimal julyExpendedAmount) {
        this.julyExpendedAmount = julyExpendedAmount;
    }

    public BigDecimal getAugustExpendedAmount() {
        return augustExpendedAmount;
    }

    public void setAugustExpendedAmount(BigDecimal augustExpendedAmount) {
        this.augustExpendedAmount = augustExpendedAmount;
    }

    public BigDecimal getSeptemberExpendedAmount() {
        return septemberExpendedAmount;
    }

    public void setSeptemberExpendedAmount(BigDecimal septemberExpendedAmount) {
        this.septemberExpendedAmount = septemberExpendedAmount;
    }

    public BigDecimal getOctoberExpendedAmount() {
        return octoberExpendedAmount;
    }

    public void setOctoberExpendedAmount(BigDecimal octoberExpendedAmount) {
        this.octoberExpendedAmount = octoberExpendedAmount;
    }

    public BigDecimal getNovemberExpendedAmount() {
        return novemberExpendedAmount;
    }

    public void setNovemberExpendedAmount(BigDecimal novemberExpendedAmount) {
        this.novemberExpendedAmount = novemberExpendedAmount;
    }

    public BigDecimal getDecemberExpendedAmount() {
        return decemberExpendedAmount;
    }

    public void setDecemberExpendedAmount(BigDecimal decemberExpendedAmount) {
        this.decemberExpendedAmount = decemberExpendedAmount;
    }

    public BigDecimal getTotalExpendedAmount() {
        this.totalExpendedAmount = BigDecimal.ZERO;
        this.totalExpendedAmount = this.totalAmount
                .add(this.januaryExpendedAmount)
                .add(this.februaryExpendedAmount)
                .add(this.marchExpendedAmount)
                .add(this.aprilExpendedAmount)
                .add(this.mayExpendedAmount)
                .add(this.juneExpendedAmount)
                .add(this.julyExpendedAmount)
                .add(this.augustExpendedAmount)
                .add(this.septemberExpendedAmount)
                .add(this.octoberExpendedAmount)
                .add(this.novemberExpendedAmount)
                .add(this.decemberExpendedAmount);
        return totalExpendedAmount;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Budgets getBudget() {
        return budget;
    }

    public void setBudget(Budgets budget) {
        this.budget = budget;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetYear that = (BudgetYear) o;

        if (!year.equals(that.year)) return false;
        return budget.equals(that.budget);

    }

    @Override
    public int hashCode() {
        int result = year.hashCode();
        result = 31 * result + budget.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BudgetYear{" +
                "idBudgetYear=" + idBudgetYear +
                ", januaryAmount=" + januaryAmount +
                ", februaryAmount=" + februaryAmount +
                ", marchAmount=" + marchAmount +
                ", aprilAmount=" + aprilAmount +
                ", mayAmount=" + mayAmount +
                ", juneAmount=" + juneAmount +
                ", julyAmount=" + julyAmount +
                ", augustAmount=" + augustAmount +
                ", septemberAmount=" + septemberAmount +
                ", octoberAmount=" + octoberAmount +
                ", novemberAmount=" + novemberAmount +
                ", decemberAmount=" + decemberAmount +
                ", totalAmount=" + totalAmount +
                ", januaryExpendedAmount=" + januaryExpendedAmount +
                ", februaryExpendedAmount=" + februaryExpendedAmount +
                ", marchExpendedAmount=" + marchExpendedAmount +
                ", aprilExpendedAmount=" + aprilExpendedAmount +
                ", mayExpendedAmount=" + mayExpendedAmount +
                ", juneExpendedAmount=" + juneExpendedAmount +
                ", julyExpendedAmount=" + julyExpendedAmount +
                ", augustExpendedAmount=" + augustExpendedAmount +
                ", septemberExpendedAmount=" + septemberExpendedAmount +
                ", octoberExpendedAmount=" + octoberExpendedAmount +
                ", novemberExpendedAmount=" + novemberExpendedAmount +
                ", decemberExpendedAmount=" + decemberExpendedAmount +
                ", totalExpendedAmount=" + totalExpendedAmount +
                ", idBudget=" + idBudget +
                ", idCurrency=" + idCurrency +
                ", year=" + year +
                ", budget=" + budget +
                ", currency=" + currency +
                '}';
    }
}

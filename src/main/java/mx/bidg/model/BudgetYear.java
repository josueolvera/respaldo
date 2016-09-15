package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.pojos.BudgetSubcategory;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo8 on 14/09/16.
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

    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_AMOUNT")
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

    @Column(name = "TOTAL_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private Integer year;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private Budgets budget;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;

    @Column(name = "ID_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetYear")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetYearConcept> budgetYearConceptList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetYear")
    @JsonView(JsonViews.Embedded.class)
    private List<Requests> requestsList;

    public BudgetYear() {
    }

    public BudgetYear(Integer year, Budgets budget) {
        this.year = year;
        this.budget = budget;
        this.januaryAmount = BigDecimal.ZERO;
        this.februaryAmount = BigDecimal.ZERO;
        this.marchAmount = BigDecimal.ZERO;
        this.aprilAmount = BigDecimal.ZERO;
        this.mayAmount = BigDecimal.ZERO;
        this.juneAmount = BigDecimal.ZERO;
        this.julyAmount = BigDecimal.ZERO;
        this.augustAmount = BigDecimal.ZERO;
        this.septemberAmount = BigDecimal.ZERO;
        this.octoberAmount = BigDecimal.ZERO;
        this.novemberAmount = BigDecimal.ZERO;
        this.decemberAmount = BigDecimal.ZERO;
        this.totalAmount = BigDecimal.ZERO;
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
        this.totalExpendedAmount = BigDecimal.ZERO;
        this.budgetYearConceptList = new ArrayList<>();
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

    public void setJanuaryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJanuaryAmount().doubleValue();
        }
        this.januaryAmount = new BigDecimal(zero);
    }

    public BigDecimal getFebruaryAmount() {
        return februaryAmount;
    }

    public void setFebruaryAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getFebruaryAmount().doubleValue();
        }
        this.februaryAmount = new BigDecimal(zero);
    }

    public BigDecimal getMarchAmount() {
        return marchAmount;
    }

    public void setMarchAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getMarchAmount().doubleValue();
        }
        this.marchAmount = new BigDecimal(zero);
    }

    public BigDecimal getAprilAmount() {
        return aprilAmount;
    }

    public void setAprilAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getAprilAmount().doubleValue();
        }
        this.aprilAmount = new BigDecimal(zero);
    }

    public BigDecimal getMayAmount() {
        return mayAmount;
    }

    public void setMayAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getMayAmount().doubleValue();
        }
        this.mayAmount = new BigDecimal(zero);
    }

    public BigDecimal getJuneAmount() {
        return juneAmount;
    }

    public void setJuneAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJuneAmount().doubleValue();
        }
        this.juneAmount = new BigDecimal(zero);
    }

    public BigDecimal getJulyAmount() {
        return julyAmount;
    }

    public void setJulyAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJulyAmount().doubleValue();
        }
        this.julyAmount = new BigDecimal(zero);
    }

    public BigDecimal getAugustAmount() {
        return augustAmount;
    }

    public void setAugustAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getAugustAmount().doubleValue();
        }
        this.augustAmount = new BigDecimal(zero);
    }

    public BigDecimal getSeptemberAmount() {
        return septemberAmount;
    }

    public void setSeptemberAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getSeptemberAmount().doubleValue();
        }
        this.septemberAmount = new BigDecimal(zero);
    }

    public BigDecimal getOctoberAmount() {
        return octoberAmount;
    }

    public void setOctoberAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getOctoberAmount().doubleValue();
        }
        this.octoberAmount = new BigDecimal(zero);
    }

    public BigDecimal getNovemberAmount() {
        return novemberAmount;
    }

    public void setNovemberAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getNovemberAmount().doubleValue();
        }
        this.novemberAmount = new BigDecimal(zero);
    }

    public BigDecimal getDecemberAmount() {
        return decemberAmount;
    }

    public void setDecemberAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getDecemberAmount().doubleValue();
        }
        this.decemberAmount = new BigDecimal(zero);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getTotalAmount().doubleValue();
        }
        this.totalAmount = new BigDecimal(zero);
    }

    public BigDecimal getJanuaryExpendedAmount() {
        return januaryExpendedAmount;
    }

    public void setJanuaryExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJanuaryExpendedAmount().doubleValue();
        }
        this.januaryExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getFebruaryExpendedAmount() {
        return februaryExpendedAmount;
    }

    public void setFebruaryExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getFebruaryExpendedAmount().doubleValue();
        }
        this.februaryExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getMarchExpendedAmount() {
        return marchExpendedAmount;
    }

    public void setMarchExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getMarchExpendedAmount().doubleValue();
        }
        this.marchExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getAprilExpendedAmount() {
        return aprilExpendedAmount;
    }

    public void setAprilExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getAprilExpendedAmount().doubleValue();
        }
        this.aprilExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getMayExpendedAmount() {
        return mayExpendedAmount;
    }

    public void setMayExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getMayExpendedAmount().doubleValue();
        }
        this.mayExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getJuneExpendedAmount() {
        return juneExpendedAmount;
    }

    public void setJuneExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJuneExpendedAmount().doubleValue();
        }
        this.juneExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getJulyExpendedAmount() {
        return julyExpendedAmount;
    }

    public void setJulyExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getJulyExpendedAmount().doubleValue();
        }
        this.julyExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getAugustExpendedAmount() {
        return augustExpendedAmount;
    }

    public void setAugustExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getAugustExpendedAmount().doubleValue();
        }
        this.augustExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getSeptemberExpendedAmount() {
        return septemberExpendedAmount;
    }

    public void setSeptemberExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getSeptemberExpendedAmount().doubleValue();
        }
        this.septemberExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getOctoberExpendedAmount() {
        return octoberExpendedAmount;
    }

    public void setOctoberExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getOctoberExpendedAmount().doubleValue();
        }
        this.octoberExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getNovemberExpendedAmount() {
        return novemberExpendedAmount;
    }

    public void setNovemberExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getNovemberExpendedAmount().doubleValue();
        }
        this.novemberExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getDecemberExpendedAmount() {
        return decemberExpendedAmount;
    }

    public void setDecemberExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getDecemberExpendedAmount().doubleValue();
        }
        this.decemberExpendedAmount = new BigDecimal(zero);
    }

    public BigDecimal getTotalExpendedAmount() {
        return totalExpendedAmount;
    }

    public void setTotalExpendedAmount() {
        double zero = 0;
        for (BudgetYearConcept budgetYearConcept : getBudgetYearConceptList()) {
            zero += budgetYearConcept.getTotalExpendedAmount().doubleValue();
        }
        this.totalExpendedAmount = new BigDecimal(zero);
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

    public List<BudgetYearConcept> getBudgetYearConceptList() {
        return budgetYearConceptList;
    }

    public void setBudgetYearConceptList(List<BudgetYearConcept> budgetYearConceptList) {
        this.budgetYearConceptList = budgetYearConceptList;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }
    
    public void setBudgetAmounts() {
        setJanuaryAmount();
        setFebruaryAmount();
        setMarchAmount();
        setAprilAmount();
        setMayAmount();
        setJuneAmount();
        setJulyAmount();
        setAugustAmount();
        setSeptemberAmount();
        setOctoberAmount();
        setNovemberAmount();
        setDecemberAmount();
        setTotalAmount();
    }

    public void setBudgetExpendedAmounts() {
        setJanuaryExpendedAmount();
        setFebruaryExpendedAmount();
        setMarchExpendedAmount();
        setAprilExpendedAmount();
        setMayExpendedAmount();
        setJuneExpendedAmount();
        setJulyExpendedAmount();
        setAugustExpendedAmount();
        setSeptemberExpendedAmount();
        setOctoberExpendedAmount();
        setNovemberExpendedAmount();
        setDecemberExpendedAmount();
        setTotalExpendedAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetYear that = (BudgetYear) o;

        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        return idBudget != null ? idBudget.equals(that.idBudget) : that.idBudget == null;

    }

    @Override
    public int hashCode() {
        int result = year != null ? year.hashCode() : 0;
        result = 31 * result + (idBudget != null ? idBudget.hashCode() : 0);
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
                ", year=" + year +
                ", budget=" + budget +
                ", currency=" + currency +
                ", idBudget=" + idBudget +
                ", idCurrency=" + idCurrency +
                ", budgetYearConceptList=" + budgetYearConceptList +
                ", requestsList=" + requestsList +
                '}';
    }
}

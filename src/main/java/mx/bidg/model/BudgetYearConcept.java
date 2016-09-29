/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_YEAR_CONCEPT")
public class BudgetYearConcept implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_YEAR_CONCEPT")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetYearConcept;

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

    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private int year;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private Budgets budget;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;

    @JoinColumn(name = "ID_BUDGET_CONCEPT", referencedColumnName = "ID_BUDGET_CONCEPT")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CBudgetConcepts budgetConcept;
    
    @Column(name = "ID_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;

    @Column(name = "ID_BUDGET_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetConcept;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_AUTHORIZED", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonView(JsonViews.Root.class)
    private Boolean isAuthorized;

    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    


    public BudgetYearConcept() {
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
    }

    public Integer getIdBudgetYearConcept() {
        return idBudgetYearConcept;
    }

    public void setIdBudgetYearConcept(Integer idBudgetYearConcept) {
        this.idBudgetYearConcept = idBudgetYearConcept;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

    public CBudgetConcepts getBudgetConcept() {
        return budgetConcept;
    }

    public void setBudgetConcept(CBudgetConcepts budgetConcept) {
        this.budgetConcept = budgetConcept;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Integer getIdBudgetConcept() {
        return idBudgetConcept;
    }

    public void setIdBudgetConcept(Integer idBudgetConcept) {
        this.idBudgetConcept = idBudgetConcept;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetYearConcept that = (BudgetYearConcept) o;

        if (year != that.year) return false;
        if (idBudget != null ? !idBudget.equals(that.idBudget) : that.idBudget != null) return false;
        if (idBudgetConcept != null ? !idBudgetConcept.equals(that.idBudgetConcept) : that.idBudgetConcept != null)
            return false;
        return idCurrency != null ? idCurrency.equals(that.idCurrency) : that.idCurrency == null;

    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + (idBudget != null ? idBudget.hashCode() : 0);
        result = 31 * result + (idBudgetConcept != null ? idBudgetConcept.hashCode() : 0);
        result = 31 * result + (idCurrency != null ? idCurrency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetYearConcept{" +
                "idBudgetYearConcept=" + idBudgetYearConcept +
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
                ", year=" + year +
                ", budget=" + budget +
                ", currency=" + currency +
                ", budgetConcept=" + budgetConcept +
                ", idBudget=" + idBudget +
                ", idBudgetConcept=" + idBudgetConcept +
                ", idAccessLevel=" + idAccessLevel +
                ", isAuthorized=" + isAuthorized +
                ", username='" + username + '\'' +
                ", creationDate=" + creationDate +
                ", idCurrency=" + idCurrency +
                '}';
    }
}

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
@Table(name = "REAL_BUDGET_SPENDING")
public class RealBudgetSpending implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REAL_SPENDING")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetYearConcept;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JANUARY_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JANUARY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal januaryExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FEBRUARY_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FEBRUARY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal februaryExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MARCH_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MARCH_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal marchExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "APRIL_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "APRIL_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal aprilExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAY_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal mayExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JUNE_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JUNE_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal juneExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JULY_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "JULY_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal julyExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AUGUST_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AUGUST_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal augustExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SEPTEMBER_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SEPTEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal septemberExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OCTOBER_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OCTOBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal octoberExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NOVEMBER_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NOVEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal novemberExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DECEMBER_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DECEMBER_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal decemberExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_BUDGET_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalBudgetAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalExpendedAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private Integer year;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private Budgets budget;

    @Column(name = "ID_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;

    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;

    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;

    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Transient
    @JsonView(JsonViews.Root.class)
    private BigDecimal totalLastYearAmount;

    @Transient
    @JsonView(JsonViews.Root.class)
    private BigDecimal realTotalBudgetAmount;

    public RealBudgetSpending() {
    }

    public RealBudgetSpending(BigDecimal januaryBudgetAmount, BigDecimal januaryExpendedAmount, BigDecimal februaryBudgetAmount, BigDecimal februaryExpendedAmount, BigDecimal marchBudgetAmount, BigDecimal marchExpendedAmount, BigDecimal aprilBudgetAmount, BigDecimal aprilExpendedAmount, BigDecimal mayBudgetAmount, BigDecimal mayExpendedAmount, BigDecimal juneBudgetAmount, BigDecimal juneExpendedAmount, BigDecimal julyBudgetAmount, BigDecimal julyExpendedAmount, BigDecimal augustBudgetAmount, BigDecimal augustExpendedAmount, BigDecimal septemberBudgetAmount, BigDecimal septemberExpendedAmount, BigDecimal octoberBudgetAmount, BigDecimal octoberExpendedAmount, BigDecimal novemberBudgetAmount, BigDecimal novemberExpendedAmount, BigDecimal decemberBudgetAmount, BigDecimal decemberExpendedAmount, BigDecimal totalBudgetAmount, BigDecimal totalExpendedAmount, Integer year) {
        this.januaryBudgetAmount = januaryBudgetAmount;
        this.januaryExpendedAmount = januaryExpendedAmount;
        this.februaryBudgetAmount = februaryBudgetAmount;
        this.februaryExpendedAmount = februaryExpendedAmount;
        this.marchBudgetAmount = marchBudgetAmount;
        this.marchExpendedAmount = marchExpendedAmount;
        this.aprilBudgetAmount = aprilBudgetAmount;
        this.aprilExpendedAmount = aprilExpendedAmount;
        this.mayBudgetAmount = mayBudgetAmount;
        this.mayExpendedAmount = mayExpendedAmount;
        this.juneBudgetAmount = juneBudgetAmount;
        this.juneExpendedAmount = juneExpendedAmount;
        this.julyBudgetAmount = julyBudgetAmount;
        this.julyExpendedAmount = julyExpendedAmount;
        this.augustBudgetAmount = augustBudgetAmount;
        this.augustExpendedAmount = augustExpendedAmount;
        this.septemberBudgetAmount = septemberBudgetAmount;
        this.septemberExpendedAmount = septemberExpendedAmount;
        this.octoberBudgetAmount = octoberBudgetAmount;
        this.octoberExpendedAmount = octoberExpendedAmount;
        this.novemberBudgetAmount = novemberBudgetAmount;
        this.novemberExpendedAmount = novemberExpendedAmount;
        this.decemberBudgetAmount = decemberBudgetAmount;
        this.decemberExpendedAmount = decemberExpendedAmount;
        this.totalBudgetAmount = totalBudgetAmount;
        this.totalExpendedAmount = totalExpendedAmount;
        this.year = year;
    }

    public Integer getIdBudgetYearConcept() {
        return idBudgetYearConcept;
    }

    public void setIdBudgetYearConcept(Integer idBudgetYearConcept) {
        this.idBudgetYearConcept = idBudgetYearConcept;
    }

    public BigDecimal getJanuaryBudgetAmount() {
        return januaryBudgetAmount;
    }

    public void setJanuaryBudgetAmount(BigDecimal januaryBudgetAmount) {
        this.januaryBudgetAmount = januaryBudgetAmount;
    }

    public BigDecimal getJanuaryExpendedAmount() {
        return januaryExpendedAmount;
    }

    public void setJanuaryExpendedAmount(BigDecimal januaryExpendedAmount) {
        this.januaryExpendedAmount = januaryExpendedAmount;
    }

    public BigDecimal getFebruaryBudgetAmount() {
        return februaryBudgetAmount;
    }

    public void setFebruaryBudgetAmount(BigDecimal februaryBudgetAmount) {
        this.februaryBudgetAmount = februaryBudgetAmount;
    }

    public BigDecimal getFebruaryExpendedAmount() {
        return februaryExpendedAmount;
    }

    public void setFebruaryExpendedAmount(BigDecimal februaryExpendedAmount) {
        this.februaryExpendedAmount = februaryExpendedAmount;
    }

    public BigDecimal getMarchBudgetAmount() {
        return marchBudgetAmount;
    }

    public void setMarchBudgetAmount(BigDecimal marchBudgetAmount) {
        this.marchBudgetAmount = marchBudgetAmount;
    }

    public BigDecimal getMarchExpendedAmount() {
        return marchExpendedAmount;
    }

    public void setMarchExpendedAmount(BigDecimal marchExpendedAmount) {
        this.marchExpendedAmount = marchExpendedAmount;
    }

    public BigDecimal getAprilBudgetAmount() {
        return aprilBudgetAmount;
    }

    public void setAprilBudgetAmount(BigDecimal aprilBudgetAmount) {
        this.aprilBudgetAmount = aprilBudgetAmount;
    }

    public BigDecimal getAprilExpendedAmount() {
        return aprilExpendedAmount;
    }

    public void setAprilExpendedAmount(BigDecimal aprilExpendedAmount) {
        this.aprilExpendedAmount = aprilExpendedAmount;
    }

    public BigDecimal getMayBudgetAmount() {
        return mayBudgetAmount;
    }

    public void setMayBudgetAmount(BigDecimal mayBudgetAmount) {
        this.mayBudgetAmount = mayBudgetAmount;
    }

    public BigDecimal getMayExpendedAmount() {
        return mayExpendedAmount;
    }

    public void setMayExpendedAmount(BigDecimal mayExpendedAmount) {
        this.mayExpendedAmount = mayExpendedAmount;
    }

    public BigDecimal getJuneBudgetAmount() {
        return juneBudgetAmount;
    }

    public void setJuneBudgetAmount(BigDecimal juneBudgetAmount) {
        this.juneBudgetAmount = juneBudgetAmount;
    }

    public BigDecimal getJuneExpendedAmount() {
        return juneExpendedAmount;
    }

    public void setJuneExpendedAmount(BigDecimal juneExpendedAmount) {
        this.juneExpendedAmount = juneExpendedAmount;
    }

    public BigDecimal getJulyBudgetAmount() {
        return julyBudgetAmount;
    }

    public void setJulyBudgetAmount(BigDecimal julyBudgetAmount) {
        this.julyBudgetAmount = julyBudgetAmount;
    }

    public BigDecimal getJulyExpendedAmount() {
        return julyExpendedAmount;
    }

    public void setJulyExpendedAmount(BigDecimal julyExpendedAmount) {
        this.julyExpendedAmount = julyExpendedAmount;
    }

    public BigDecimal getAugustBudgetAmount() {
        return augustBudgetAmount;
    }

    public void setAugustBudgetAmount(BigDecimal augustBudgetAmount) {
        this.augustBudgetAmount = augustBudgetAmount;
    }

    public BigDecimal getAugustExpendedAmount() {
        return augustExpendedAmount;
    }

    public void setAugustExpendedAmount(BigDecimal augustExpendedAmount) {
        this.augustExpendedAmount = augustExpendedAmount;
    }

    public BigDecimal getSeptemberBudgetAmount() {
        return septemberBudgetAmount;
    }

    public void setSeptemberBudgetAmount(BigDecimal septemberBudgetAmount) {
        this.septemberBudgetAmount = septemberBudgetAmount;
    }

    public BigDecimal getSeptemberExpendedAmount() {
        return septemberExpendedAmount;
    }

    public void setSeptemberExpendedAmount(BigDecimal septemberExpendedAmount) {
        this.septemberExpendedAmount = septemberExpendedAmount;
    }

    public BigDecimal getOctoberBudgetAmount() {
        return octoberBudgetAmount;
    }

    public void setOctoberBudgetAmount(BigDecimal octoberBudgetAmount) {
        this.octoberBudgetAmount = octoberBudgetAmount;
    }

    public BigDecimal getOctoberExpendedAmount() {
        return octoberExpendedAmount;
    }

    public void setOctoberExpendedAmount(BigDecimal octoberExpendedAmount) {
        this.octoberExpendedAmount = octoberExpendedAmount;
    }

    public BigDecimal getNovemberBudgetAmount() {
        return novemberBudgetAmount;
    }

    public void setNovemberBudgetAmount(BigDecimal novemberBudgetAmount) {
        this.novemberBudgetAmount = novemberBudgetAmount;
    }

    public BigDecimal getNovemberExpendedAmount() {
        return novemberExpendedAmount;
    }

    public void setNovemberExpendedAmount(BigDecimal novemberExpendedAmount) {
        this.novemberExpendedAmount = novemberExpendedAmount;
    }

    public BigDecimal getDecemberBudgetAmount() {
        return decemberBudgetAmount;
    }

    public void setDecemberBudgetAmount(BigDecimal decemberBudgetAmount) {
        this.decemberBudgetAmount = decemberBudgetAmount;
    }

    public BigDecimal getDecemberExpendedAmount() {
        return decemberExpendedAmount;
    }

    public void setDecemberExpendedAmount(BigDecimal decemberExpendedAmount) {
        this.decemberExpendedAmount = decemberExpendedAmount;
    }

    public BigDecimal getTotalBudgetAmount() {
        return totalBudgetAmount;
    }

    public void setTotalBudgetAmount(BigDecimal totalBudgetAmount) {
        this.totalBudgetAmount = totalBudgetAmount;
    }

    public BigDecimal getTotalExpendedAmount() {
        return totalExpendedAmount;
    }

    public void setTotalExpendedAmount(BigDecimal totalExpendedAmount) {
        this.totalExpendedAmount = totalExpendedAmount;
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

    public BigDecimal getTotalLastYearAmount() {
        return totalLastYearAmount;
    }

    public void setTotalLastYearAmount(BigDecimal totalLastYearAmount) {
        this.totalLastYearAmount = totalLastYearAmount;
    }

    public BigDecimal getRealTotalBudgetAmount() {
        return realTotalBudgetAmount;
    }

    public void setRealTotalBudgetAmount(BigDecimal realTotalBudgetAmount) {
        this.realTotalBudgetAmount = realTotalBudgetAmount;
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

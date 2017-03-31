/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "REAL_BUDGET_SPENDING_HISTORY")

public class RealBudgetSpendingHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REAL_BUDGET_SPENDING_HISTORY")
    private Integer idRealBudgetSpendingHistory;

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

    public RealBudgetSpendingHistory() {
    }

    public RealBudgetSpendingHistory(Integer idRealBudgetSpendingHistory) {
        this.idRealBudgetSpendingHistory = idRealBudgetSpendingHistory;
    }

    public RealBudgetSpendingHistory(Integer idRealBudgetSpendingHistory, int year, String username, LocalDateTime creationDate) {
        this.idRealBudgetSpendingHistory = idRealBudgetSpendingHistory;
        this.year = year;
        this.username = username;
        this.creationDate = creationDate;
    }

    public Integer getIdRealBudgetSpendingHistory() {
        return idRealBudgetSpendingHistory;
    }

    public void setIdRealBudgetSpendingHistory(Integer idRealBudgetSpendingHistory) {
        this.idRealBudgetSpendingHistory = idRealBudgetSpendingHistory;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public void setYear(Integer year) {
        this.year = year;
    }

    public Budgets getBudget() {
        return budget;
    }

    public void setBudget(Budgets budget) {
        this.budget = budget;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRealBudgetSpendingHistory != null ? idRealBudgetSpendingHistory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RealBudgetSpendingHistory)) {
            return false;
        }
        RealBudgetSpendingHistory other = (RealBudgetSpendingHistory) object;
        if ((this.idRealBudgetSpendingHistory == null && other.idRealBudgetSpendingHistory != null) || (this.idRealBudgetSpendingHistory != null && !this.idRealBudgetSpendingHistory.equals(other.idRealBudgetSpendingHistory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.RealBudgetSpendingHistory[ idRealBudgetSpendingHistory=" + idRealBudgetSpendingHistory + " ]";
    }
    
}

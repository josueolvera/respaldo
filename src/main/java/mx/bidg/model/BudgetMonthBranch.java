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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "BUDGET_MONTH_BRANCH")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetMonthBranch implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_MONTH_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthBranch;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal expendedAmount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private int year;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private Budgets idBudget;

    @JoinColumn(name = "ID_MONTH", referencedColumnName = "ID_MONTH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CMonths month;
    
    @Column(name = "ID_MONTH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private int idMonth;
    
    @JoinColumn(name = "ID_DW_ENTERPRISE", referencedColumnName = "ID_DW_ENTERPRISE")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private DwEnterprises idDwEnterprise;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_AUTHORIZED")
    @JsonView(JsonViews.Root.class)
    private Integer isAuthorized;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetMonthBranch")
    @JsonView(JsonViews.Embedded.class)
    private List<Requests> requestsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetMonthBranch")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthConcepts> budgetMonthConceptsList;

    public BudgetMonthBranch() {
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch, BigDecimal amount, int year) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
        this.amount = amount;
        this.year = year;
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch, BigDecimal amount, int year, Budgets idBudget, CMonths month) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
        this.amount = amount;
        this.year = year;
        this.idBudget = idBudget;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getIdBudgetMonthBranch() {
        return idBudgetMonthBranch;
    }

    public void setIdBudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Budgets getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Budgets idBudget) {
        this.idBudget = idBudget;
    }

    public List<BudgetMonthConcepts> getBudgetMonthConceptsList() {
        return budgetMonthConceptsList;
    }

    public void setBudgetMonthConceptsList(List<BudgetMonthConcepts> budgetMonthConceptsList) {
        this.budgetMonthConceptsList = budgetMonthConceptsList;
    }
    
    public BigDecimal getExpendedAmount() {
        return expendedAmount;
    }

    public void setExpendedAmount(BigDecimal expendedAmount) {
        this.expendedAmount = expendedAmount;
    }

    public DwEnterprises getIdDwEnterprise() {
        return idDwEnterprise;
    }

    public void setIdDwEnterprise(DwEnterprises idDwEnterprise) {
        this.idDwEnterprise = idDwEnterprise;
    }
    
    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    public Integer getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(Integer isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public CMonths getMonth() {
        return month;
    }

    public void setMonth(CMonths month) {
        this.month = month;
    }

    public int getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(int idMonth) {
        this.idMonth = idMonth;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetMonthBranch != null ? idBudgetMonthBranch.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetMonthBranch)) {
            return false;
        }
        BudgetMonthBranch other = (BudgetMonthBranch) object;
        if ((this.idBudgetMonthBranch == null && other.idBudgetMonthBranch != null) || (this.idBudgetMonthBranch != null && !this.idBudgetMonthBranch.equals(other.idBudgetMonthBranch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetMonthBranch[ idBudgetMonthBranch=" + idBudgetMonthBranch + " ]";
    }
    
}

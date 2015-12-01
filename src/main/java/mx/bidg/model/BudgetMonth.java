/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;

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

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "BUDGET_MONTH")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetMonth implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_MONTH")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonth;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private int year;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Budgets idBudget;

    @JoinColumn(name = "ID_MONTH", referencedColumnName = "ID_MONTH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CMonths idMonth;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudgetMonth")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthBranch> budgetMonthBranchList;

    public BudgetMonth() {
    }

    public BudgetMonth(Integer idBudgetMonth) {
        this.idBudgetMonth = idBudgetMonth;
    }

    public BudgetMonth(Integer idBudgetMonth, BigDecimal amount, int year, int idAccessLevel) {
        this.idBudgetMonth = idBudgetMonth;
        this.amount = amount;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
    }

    public BudgetMonth(Integer idBudgetMonth, BigDecimal amount, int year, int idAccessLevel, Budgets idBudget, CMonths idMonth) {
        this.idBudgetMonth = idBudgetMonth;
        this.amount = amount;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
        this.idBudget = idBudget;
        this.idMonth = idMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getIdBudgetMonth() {
        return idBudgetMonth;
    }

    public void setIdBudgetMonth(Integer idBudgetMonth) {
        this.idBudgetMonth = idBudgetMonth;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Budgets getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Budgets idBudget) {
        this.idBudget = idBudget;
    }

    public CMonths getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(CMonths idMonth) {
        this.idMonth = idMonth;
    }
    
    public List<BudgetMonthBranch> getBudgetMonthBranchList() {
        return budgetMonthBranchList;
    }

    public void setBudgetMonthBranchList(List<BudgetMonthBranch> budgetMonthBranchList) {
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetMonth != null ? idBudgetMonth.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetMonth)) {
            return false;
        }
        BudgetMonth other = (BudgetMonth) object;
        if ((this.idBudgetMonth == null && other.idBudgetMonth != null) || (this.idBudgetMonth != null && !this.idBudgetMonth.equals(other.idBudgetMonth))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetMonth[ idBudgetMonth=" + idBudgetMonth + " ]";
    }
    
}

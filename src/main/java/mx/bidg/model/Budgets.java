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
@Table(name = "BUDGETS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Budgets implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET")
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REG_TABLE")
    @JsonView(JsonViews.Root.class)
    private int idRegTable;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudget")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonth> budgetMonthList;

    @JoinColumn(name = "ID_BUDGET_AREA", referencedColumnName = "ID_BUDGET_AREA")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBudgetAreas idBudgetArea;

    @JoinColumn(name = "ID_BUDGET_TYPE", referencedColumnName = "ID_BUDGET_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBudgetTypes idBudgetType;

    @JoinColumn(name = "ID_BUDGET_PERIOD", referencedColumnName = "ID_BUDGET_PERIOD")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CBudgetPeriods idBudgetPeriod;

    public Budgets() {
    }

    public Budgets(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Budgets(Integer idBudget, int idRegTable, int year, int idAccessLevel) {
        this.idBudget = idBudget;
        this.idRegTable = idRegTable;
        this.year = year;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public int getIdRegTable() {
        return idRegTable;
    }

    public void setIdRegTable(int idRegTable) {
        this.idRegTable = idRegTable;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<BudgetMonth> getBudgetMonthList() {
        return budgetMonthList;
    }

    public void setBudgetMonthList(List<BudgetMonth> budgetMonthList) {
        this.budgetMonthList = budgetMonthList;
    }

    public CBudgetAreas getIdBudgetArea() {
        return idBudgetArea;
    }

    public void setIdBudgetArea(CBudgetAreas idBudgetArea) {
        this.idBudgetArea = idBudgetArea;
    }

    public CBudgetTypes getIdBudgetType() {
        return idBudgetType;
    }

    public void setIdBudgetType(CBudgetTypes idBudgetType) {
        this.idBudgetType = idBudgetType;
    }

    public CBudgetPeriods getIdBudgetPeriod() {
        return idBudgetPeriod;
    }

    public void setIdBudgetPeriod(CBudgetPeriods idBudgetPeriod) {
        this.idBudgetPeriod = idBudgetPeriod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudget != null ? idBudget.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Budgets)) {
            return false;
        }
        Budgets other = (Budgets) object;
        if ((this.idBudget == null && other.idBudget != null) || (this.idBudget != null && !this.idBudget.equals(other.idBudget))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Budgets[ idBudget=" + idBudget + " ]";
    }
    
}

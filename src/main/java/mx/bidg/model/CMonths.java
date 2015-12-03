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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_MONTHS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CMonths implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MONTH")
    @JsonView(JsonViews.Root.class)
    private Integer idMonth;

    @Size(max = 20)
    @Column(name = "MONTH")
    @JsonView(JsonViews.Root.class)
    private String month;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMonth")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthBranch> budgetMonthBranchList;

    @OneToMany(mappedBy = "idMonthFirst")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetPeriodMonths> budgetPeriodMonthsList;

    @OneToMany(mappedBy = "idMonthLast")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetPeriodMonths> budgetPeriodMonthsList1;

    public CMonths() {
    }

    public CMonths(Integer idMonth) {
        this.idMonth = idMonth;
    }

    public Integer getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(Integer idMonth) {
        this.idMonth = idMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<BudgetMonthBranch> getBudgetMonthBranchList() {
        return budgetMonthBranchList;
    }

    public void setBudgetMonthBranchList(List<BudgetMonthBranch> budgetMonthBranchList) {
        this.budgetMonthBranchList = budgetMonthBranchList;
    }

    public List<BudgetPeriodMonths> getBudgetPeriodMonthsList() {
        return budgetPeriodMonthsList;
    }

    public void setBudgetPeriodMonthsList(List<BudgetPeriodMonths> budgetPeriodMonthsList) {
        this.budgetPeriodMonthsList = budgetPeriodMonthsList;
    }

    public List<BudgetPeriodMonths> getBudgetPeriodMonthsList1() {
        return budgetPeriodMonthsList1;
    }

    public void setBudgetPeriodMonthsList1(List<BudgetPeriodMonths> budgetPeriodMonthsList1) {
        this.budgetPeriodMonthsList1 = budgetPeriodMonthsList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMonth != null ? idMonth.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CMonths)) {
            return false;
        }
        CMonths other = (CMonths) object;
        if ((this.idMonth == null && other.idMonth != null) || (this.idMonth != null && !this.idMonth.equals(other.idMonth))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CMonths[ idMonth=" + idMonth + " ]";
    }
    
}

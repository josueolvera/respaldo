/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_MONTHS")
@NamedQueries({
    @NamedQuery(name = "CMonths.findAll", query = "SELECT c FROM CMonths c")})
public class CMonths implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MONTH")
    private Integer idMonth;
    @Size(max = 20)
    @Column(name = "MONTH")
    private String month;
    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMonth")
    private List<BudgetMonth> budgetMonthList;
    @OneToMany(mappedBy = "idMonthFirst")
    private List<BudgetPeriodMonths> budgetPeriodMonthsList;
    @OneToMany(mappedBy = "idMonthLast")
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

    public List<BudgetMonth> getBudgetMonthList() {
        return budgetMonthList;
    }

    public void setBudgetMonthList(List<BudgetMonth> budgetMonthList) {
        this.budgetMonthList = budgetMonthList;
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

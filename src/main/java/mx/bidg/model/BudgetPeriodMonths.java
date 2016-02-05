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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_PERIOD_MONTHS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetPeriodMonths implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_PERIOD_MONTH")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetPeriodMonth;

    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Column(name = "ID_BUDGET_PERIOD", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetPeriod;

    @Column(name = "ID_MONTH_FIRST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idMonthFirst;

    @Column(name = "ID_MONTH_LAST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idMonthLast;

    @JoinColumn(name = "ID_BUDGET_PERIOD", referencedColumnName = "ID_BUDGET_PERIOD")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CBudgetPeriods budgetPeriod;

    @JoinColumn(name = "ID_MONTH_FIRST", referencedColumnName = "ID_MONTH")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CMonths monthFirst;

    @JoinColumn(name = "ID_MONTH_LAST", referencedColumnName = "ID_MONTH")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CMonths monthLast;

    public BudgetPeriodMonths() {
    }

    public BudgetPeriodMonths(Integer idBudgetPeriodMonth) {
        this.idBudgetPeriodMonth = idBudgetPeriodMonth;
    }

    public Integer getIdBudgetPeriodMonth() {
        return idBudgetPeriodMonth;
    }

    public void setIdBudgetPeriodMonth(Integer idBudgetPeriodMonth) {
        this.idBudgetPeriodMonth = idBudgetPeriodMonth;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudgetPeriod() {
        return idBudgetPeriod;
    }

    public void setIdBudgetPeriod(Integer idBudgetPeriod) {
        this.idBudgetPeriod = idBudgetPeriod;
    }

    public Integer getIdMonthFirst() {
        return idMonthFirst;
    }

    public void setIdMonthFirst(Integer idMonthFirst) {
        this.idMonthFirst = idMonthFirst;
    }

    public Integer getIdMonthLast() {
        return idMonthLast;
    }

    public void setIdMonthLast(Integer idMonthLast) {
        this.idMonthLast = idMonthLast;
    }

    public CBudgetPeriods getBudgetPeriod() {
        return budgetPeriod;
    }

    public void setBudgetPeriod(CBudgetPeriods budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    public CMonths getMonthFirst() {
        return monthFirst;
    }

    public void setMonthFirst(CMonths monthFirst) {
        this.monthFirst = monthFirst;
    }

    public CMonths getMonthLast() {
        return monthLast;
    }

    public void setMonthLast(CMonths monthLast) {
        this.monthLast = monthLast;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetPeriodMonth != null ? idBudgetPeriodMonth.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetPeriodMonths)) {
            return false;
        }
        BudgetPeriodMonths other = (BudgetPeriodMonths) object;
        if ((this.idBudgetPeriodMonth == null && other.idBudgetPeriodMonth != null) || (this.idBudgetPeriodMonth != null && !this.idBudgetPeriodMonth.equals(other.idBudgetPeriodMonth))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetPeriodMonths[ idBudgetPeriodMonth=" + idBudgetPeriodMonth + " ]";
    }
    
}

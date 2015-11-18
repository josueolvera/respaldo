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

/**
 *
 * @author sistemask
 */
@Entity
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

    @JoinColumn(name = "ID_BUDGET_PERIOD", referencedColumnName = "ID_BUDGET_PERIOD")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CBudgetPeriods idBudgetPeriod;

    @JoinColumn(name = "ID_MONTH_FIRST", referencedColumnName = "ID_MONTH")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CMonths idMonthFirst;

    @JoinColumn(name = "ID_MONTH_LAST", referencedColumnName = "ID_MONTH")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CMonths idMonthLast;

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

    public CBudgetPeriods getIdBudgetPeriod() {
        return idBudgetPeriod;
    }

    public void setIdBudgetPeriod(CBudgetPeriods idBudgetPeriod) {
        this.idBudgetPeriod = idBudgetPeriod;
    }

    public CMonths getIdMonthFirst() {
        return idMonthFirst;
    }

    public void setIdMonthFirst(CMonths idMonthFirst) {
        this.idMonthFirst = idMonthFirst;
    }

    public CMonths getIdMonthLast() {
        return idMonthLast;
    }

    public void setIdMonthLast(CMonths idMonthLast) {
        this.idMonthLast = idMonthLast;
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

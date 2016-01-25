/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_BUDGET_PERIODS")
public class CBudgetPeriods implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_PERIOD")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetPeriod;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PERIOD")
    @JsonView(JsonViews.Root.class)
    private String period;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    public CBudgetPeriods() {
    }

    public CBudgetPeriods(Integer idBudgetPeriod) {
        this.idBudgetPeriod = idBudgetPeriod;
    }

    public CBudgetPeriods(Integer idBudgetPeriod, String period, int idAccessLevel) {
        this.idBudgetPeriod = idBudgetPeriod;
        this.period = period;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdBudgetPeriod() {
        return idBudgetPeriod;
    }

    public void setIdBudgetPeriod(Integer idBudgetPeriod) {
        this.idBudgetPeriod = idBudgetPeriod;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetPeriod != null ? idBudgetPeriod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBudgetPeriods)) {
            return false;
        }
        CBudgetPeriods other = (CBudgetPeriods) object;
        if ((this.idBudgetPeriod == null && other.idBudgetPeriod != null) || (this.idBudgetPeriod != null && !this.idBudgetPeriod.equals(other.idBudgetPeriod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CBudgetPeriods[ idBudgetPeriod=" + idBudgetPeriod + " ]";
    }
    
}

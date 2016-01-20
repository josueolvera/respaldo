/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_PERIODS")
public class CPeriods implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERIOD")
    @JsonView(JsonViews.Root.class)
    private Integer idPeriod;
    
    @Size(max = 100)
    @Column(name = "PERIOD")
    @JsonView(JsonViews.Root.class)
    private String period;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAYS")
    @JsonView(JsonViews.Root.class)
    private int days;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTHS")
    @JsonView(JsonViews.Root.class)
    private int months;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEARS")
    @JsonView(JsonViews.Root.class)
    private int years;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPeriod")
    @JsonView(JsonViews.Embedded.class)
    private List<PeriodicsPayments> periodicsPaymentsList;

    public CPeriods() {
    }

    public CPeriods(Integer idPeriod) {
        this.idPeriod = idPeriod;
    }

    public CPeriods(Integer idPeriod, int days, int months, int years, int idAccessLevel) {
        this.idPeriod = idPeriod;
        this.days = days;
        this.months = months;
        this.years = years;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdPeriod() {
        return idPeriod;
    }

    public void setIdPeriod(Integer idPeriod) {
        this.idPeriod = idPeriod;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public List<PeriodicsPayments> getPeriodicsPaymentsList() {
        return periodicsPaymentsList;
    }

    public void setPeriodicsPaymentsList(List<PeriodicsPayments> periodicsPaymentsList) {
        this.periodicsPaymentsList = periodicsPaymentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriod != null ? idPeriod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPeriods)) {
            return false;
        }
        CPeriods other = (CPeriods) object;
        if ((this.idPeriod == null && other.idPeriod != null) || (this.idPeriod != null && !this.idPeriod.equals(other.idPeriod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPeriods[ idPeriod=" + idPeriod + " ]";
    }
    
}

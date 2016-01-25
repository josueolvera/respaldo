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
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_MONTHS")
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

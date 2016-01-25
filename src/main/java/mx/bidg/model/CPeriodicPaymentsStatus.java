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
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_PERIODIC_PAYMENTS_STATUS")
public class CPeriodicPaymentsStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERIODIC_PAYMENT_STATUS")
    @JsonView(JsonViews.Root.class)
    private Integer idPeriodicPaymentStatus;
    
    @Size(max = 45)
    @Column(name = "PERIODIC_PAYMENT_STATUS")
    @JsonView(JsonViews.Root.class)
    private String periodicPaymentStatus;

    public CPeriodicPaymentsStatus() {
    }

    public CPeriodicPaymentsStatus(Integer idPeriodicPaymentStatus) {
        this.idPeriodicPaymentStatus = idPeriodicPaymentStatus;
    }

    public Integer getIdPeriodicPaymentStatus() {
        return idPeriodicPaymentStatus;
    }

    public void setIdPeriodicPaymentStatus(Integer idPeriodicPaymentStatus) {
        this.idPeriodicPaymentStatus = idPeriodicPaymentStatus;
    }

    public String getPeriodicPaymentStatus() {
        return periodicPaymentStatus;
    }

    public void setPeriodicPaymentStatus(String periodicPaymentStatus) {
        this.periodicPaymentStatus = periodicPaymentStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriodicPaymentStatus != null ? idPeriodicPaymentStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CPeriodicPaymentsStatus)) {
            return false;
        }
        CPeriodicPaymentsStatus other = (CPeriodicPaymentsStatus) object;
        if ((this.idPeriodicPaymentStatus == null && other.idPeriodicPaymentStatus != null) || (this.idPeriodicPaymentStatus != null && !this.idPeriodicPaymentStatus.equals(other.idPeriodicPaymentStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CPeriodicPaymentsStatus[ idPeriodicPaymentStatus=" + idPeriodicPaymentStatus + " ]";
    }
    
}

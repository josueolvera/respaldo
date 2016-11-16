/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC_YAIR
 */
@Entity
@Table(name = "C_COMMISSIONS_CASH")
public class CCommissionsCash implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMMISSIONS_CASH")
    @JsonView(JsonViews.Root.class)
    private Integer idCommissionsCash;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Column(name = "COMMISSIONS_CASH_1")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commissionsCash1;

    @Column(name = "COMMISSIONS_CASH_2")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commissionsCash2;

    @JoinColumn(name = "ID_C_PD", referencedColumnName = "ID_C_PD")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CPerceptionsDeductions idCPd;

    public CCommissionsCash() {
    }

    public CCommissionsCash(Integer idCommissionsCash) {
        this.idCommissionsCash = idCommissionsCash;
    }

    public Integer getIdCommissionsCash() {
        return idCommissionsCash;
    }

    public void setIdCommissionsCash(Integer idCommissionsCash) {
        this.idCommissionsCash = idCommissionsCash;
    }

    public BigDecimal getCommissionsCash1() {
        return commissionsCash1;
    }

    public void setCommissionsCash1(BigDecimal commissionsCash1) {
        this.commissionsCash1 = commissionsCash1;
    }

    public BigDecimal getCommissionsCash2() {
        return commissionsCash2;
    }

    public void setCommissionsCash2(BigDecimal commissionsCash2) {
        this.commissionsCash2 = commissionsCash2;
    }

    public CPerceptionsDeductions getIdCPd() {
        return idCPd;
    }

    public void setIdCPd(CPerceptionsDeductions idCPd) {
        this.idCPd = idCPd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCommissionsCash != null ? idCommissionsCash.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CCommissionsCash)) {
            return false;
        }
        CCommissionsCash other = (CCommissionsCash) object;
        if ((this.idCommissionsCash == null && other.idCommissionsCash != null) || (this.idCommissionsCash != null && !this.idCommissionsCash.equals(other.idCommissionsCash))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CCommissionsCash[ idCommissionsCash=" + idCommissionsCash + " ]";
    }
    
}

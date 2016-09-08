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
import org.hibernate.annotations.DynamicUpdate;

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
 * @author josueolvera
 */
@Entity
@DynamicUpdate
@Table(name = "AGREEMENTS_GROUP_CONDITION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class AgreementsGroupCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GROUP_CONDITION")
    @JsonView(JsonViews.Root.class)
    private Integer idGroupCondition;

    @Column(name = "ORDER")
    @JsonView(JsonViews.Root.class)
    private Integer order;

    @Column(name = "TABULATOR")
    @JsonView(JsonViews.Root.class)
    private BigDecimal tabulator;

    @Column(name = "AMOUNT_MIN")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountMin;

    @Column(name = "AMOUNT_MAX")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amountMax;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    @Column(name = "ID_AG", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAg;

    @JoinColumn(name = "ID_AG", referencedColumnName = "ID_AG")
    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    private CAgreementsGroups agreementsGroups;
    
    @Column(name = "STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private boolean statusBoolean;

    public AgreementsGroupCondition() {
    }

    public AgreementsGroupCondition(Integer idGroupCondition) {
        this.idGroupCondition = idGroupCondition;
    }

    public Integer getIdGroupCondition() {
        return idGroupCondition;
    }

    public void setIdGroupCondition(Integer idGroupCondition) {
        this.idGroupCondition = idGroupCondition;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public BigDecimal getTabulator() {
        return tabulator;
    }

    public void setTabulator(BigDecimal tabulator) {
        this.tabulator = tabulator;
    }

    public Integer getIdAg() {
        return idAg;
    }

    public void setIdAg(Integer idAg) {
        this.idAg = idAg;
    }

    public CAgreementsGroups getAgreementsGroups() {
        return agreementsGroups;
    }

    public void setAgreementsGroups(CAgreementsGroups agreementsGroups) {
        this.agreementsGroups = agreementsGroups;
    }

    public BigDecimal getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(BigDecimal amountMin) {
        this.amountMin = amountMin;
    }

    public BigDecimal getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(BigDecimal amountMax) {
        this.amountMax = amountMax;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public boolean isStatusBoolean() {
        if (this.status == 0) 
        {
         statusBoolean = false;   
        }
        else {
         statusBoolean = true;
        }
        return statusBoolean;
    }

    public void setStatusBoolean(boolean statusBoolean) {
        this.statusBoolean = statusBoolean;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGroupCondition != null ? idGroupCondition.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgreementsGroupCondition)) {
            return false;
        }
        AgreementsGroupCondition other = (AgreementsGroupCondition) object;
        if ((this.idGroupCondition == null && other.idGroupCondition != null) || (this.idGroupCondition != null && !this.idGroupCondition.equals(other.idGroupCondition))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.AgreementsGroupCondition[ idGroupCondition=" + idGroupCondition + " ]";
    }
    
}
